import svn.local
import os,sys

from util.staticUtil import staticCode
from business.publish.PublishDao import PublishDaoImpl


class PublishInit:
    def __init__(self):
        self.index = 0
        self.SUCCESS = "发布成功"
        self.ERROR = "python处有异常"
        self.ERROR_UPDATE_1 = "出错的原因可能是:提供的svn版本大于现有最大版本"
        self.ERROR_LIST_1 = "出错的原因可能是:提供的路径不正确,或者提供的svn版本不正确"
        self.ERROR_NO_EXIST_FILE = "出错的原因可能是:不是文件 或 文件不存在 或提供的svn版本不正确"
        self.ERROR_NO_EXIST_DIR = "出错的原因可能是:不是文件夹 或 文件夹不存在"
        self.ERROR_DIR_MUST_ZERO = "使用文件夹发布,填写的SVN版本必须是0"
        self.ERROR_TYPE_NOT_EXIST = "0:文件发布;1:文件夹发布;;找不到你使用的发布type"
        self.ERROR_F_DIR_CHECK_FALSE = "检查父类文件夹失败,请联系管理员"
        self.ERROR_PUBLISH_FALSE = "最后发布时失败,请联系管理员"
        self.ERROR_UPDATE_MYSQL_FALSE = "最后更新数据库时失败,请联系管理员"
        self.publishDao = PublishDaoImpl()
        self.publish_author = "0"

    def check_svn_path(self, svn_path):
        svn_path = str(svn_path)
        while (svn_path.__contains__("//")):
            svn_path = svn_path.replace("//", "/")
        if (svn_path.endswith("/")):
            svn_path = svn_path[0: len(svn_path) - 1]
        if (svn_path.startswith("/")):
            svn_path = svn_path[1: len(svn_path)]
        return svn_path

    def check_svn_f_dir(self, svn_path, remote=False):
        svn_path = str(svn_path)
        # 拆分所有父类
        svn_f_p_list = []
        while (svn_path.__contains__("/")):
            index = svn_path.rindex("/")
            svn_path = svn_path[0:index]
            svn_f_p_list.append(svn_path)

        # 查询数据库,看有哪个父类存在
        svn_f_p_list_index = len(svn_f_p_list)
        for index, path in enumerate(svn_f_p_list):
            # 通过查询数据库获取
            if self.publishDao.findId(path, True) is not None:
                svn_f_p_list_index = index
                break

        # 将不存在的父类add
        for i in range(svn_f_p_list_index - 1, -1, -1):
            if not self.addDir(svn_f_p_list[i], remote):
                return False

        return True

    def svn_list(self, svn_path, isDir=False):
        list = []
        # 获取kind和version备用
        b = staticCode.svn_local_client.list(extended=True, rel_path=svn_path)

        if isDir:
            for fn in b:
                data = {
                    "name": str(fn.get("name")),
                    "path": svn_path + "/" + str(fn.get("name")),
                    "svnVersion": int(fn.get("commit_revision")),
                    "isDir": bool(fn.get("is_directory")),
                    "svnAuthor": str(fn.get("author")),
                }
                list.append(data)
                if data["isDir"]:
                    list += self.svn_list(data["path"], True)
        else:
            for fn in b:
                list.append({
                    "name":str(fn.get("name")),
                    "path":svn_path,
                    "svnVersion":int(fn.get("commit_revision")),
                    "isDir":bool(fn.get("is_directory")),
                    "svnAuthor":str(fn.get("author")),
                })
        return list

    def publish_process(self, publish_type, publish_version, svn_path, remote=False):
        try:
            svn_root = staticCode.svn_root
            svn_local_client = staticCode.svn_local_client
            svn_path = self.check_svn_path(svn_path)
            real_path = svn_root + "/" + svn_path
            list = []   #所有涉及的文件和文件夹,更新mysql用
            if publish_type == 0:
                # 更新需要publish的文件
                if publish_version == 0:
                    # 如果为0,则默认发布最新版本
                    publish_version = None
                #更新svn
                try:
                    svn_local_client.update(rel_filepaths=[svn_path], revision=publish_version)
                except Exception as e:
                    # 版本过大异常
                    return False, self.ERROR_UPDATE_1, str(e)
                # 判定是否存在此文件
                if not os.path.isfile(real_path):
                    return False, self.ERROR_NO_EXIST_FILE, ''
                # 获取kind和version备用
                try:
                    list = self.svn_list(svn_path)
                except Exception as e:
                    # 找不到该文件的异常,通常是版本号错误
                    return False, self.ERROR_LIST_1, str(e)
                #分割svn_path,验证父文件夹是否存在,不存在则创建,注意同时更新数据库
                if not self.check_svn_f_dir(svn_path, remote):
                    return False, self.ERROR_F_DIR_CHECK_FALSE, ''
                # 发布
                if remote:
                    if not self.publish(svn_path, False):
                        return False,self.ERROR_PUBLISH_FALSE,''
                # 更新发布的数据库
                if not self.updateMysqlEnd(list):
                    return False,self.ERROR_UPDATE_MYSQL_FALSE,''

            elif publish_type == 1:
                if publish_version == 0:
                    #更新svn
                    svn_local_client.update()
                    isDir = os.path.isdir(real_path)
                    # 获取kind和version备用
                    try:
                        list = self.svn_list(svn_path, isDir)
                    except Exception as e:
                        # 找不到该文件的异常,通常是版本号错误
                        return False, self.ERROR_LIST_1, str(e)
                    #分割svn_path,验证父文件夹是否存在,不存在则创建,注意同时更新数据库
                    if not self.check_svn_f_dir(svn_path, remote):
                        return False, self.ERROR_F_DIR_CHECK_FALSE, ''
                    # 创建此文件夹
                    if isDir:
                        self.addDir(svn_path)
                    # 发布
                    if remote:
                        if not self.publish(svn_path, True):
                            return False, self.ERROR_PUBLISH_FALSE, ''
                    # 更新发布数据库,更新文件夹和文件
                    if not self.updateMysqlEnd(list):
                        return False, self.ERROR_UPDATE_MYSQL_FALSE, ''

                else:
                    return False, self.ERROR_DIR_MUST_ZERO, ''
            else:
                return False, self.ERROR_TYPE_NOT_EXIST, ''

            return True, self.SUCCESS, ''
        except Exception as e:
            return False, self.ERROR, str(e)

    def addDir(self, dir_path, isRemote = False):
        dir_path = str(dir_path)
        f_path = ""
        if dir_path.__contains__("/"):
            index = dir_path.rindex("/")
            name = dir_path[index+1: len(dir_path)]
            f_path = dir_path[0: index]
        else:
            name = dir_path

        if isRemote:
            for host in staticCode.remote_hosts:
                ret = os.system('ssh %s "mkdir %s/%s"' % (host,staticCode.remote_root,dir_path))
                print("此处远程创建dir_path-%s,is-" % dir_path, str(ret == 0))
        if not self.publishDao.addDir(f_path, dir_path, name):
            return False

        return True

    def updateMysqlEnd(self, list):
        # data = {
        #     "name": str(fn.get("name")),
        #     "path": svn_path + "/" + str(fn.get("name")),
        #     "svnVersion": int(fn.get("commit_revision")),
        #     "isDir": bool(fn.get("is_directory")),
        #     "svnAuthor": str(fn.get("author")),
        # }
        for data in list:
            path = data['path']
            name = data['name']
            svnVersion = data['svnVersion']
            isDir = data['isDir']
            svnAuthor = data['svnAuthor']
            f_path = ''

            if path.__contains__("/"):
                f_path = path[0: path.rindex("/")]

            if isDir:
                if not self.publishDao.addDir(f_path, path, name):
                    return False
            else:
                if not self.publishDao.addFile(f_path, path, name, svnAuthor, self.publish_author, svnVersion):
                    return False
        return True

    def publish(self, path, isDir):
        f_path = ''
        if path.__contains__("/"):
            f_path = path[0: path.rindex("/")]
        if isDir:
            for host in staticCode.remote_hosts:
                zhiling = "scp -r %s/%s %s:%s/%s" \
                          % (staticCode.svn_root, path, host, staticCode.remote_root, f_path)
                print(zhiling)
                ret = os.system(zhiling)
                if ret != 0:
                    return False
        else:
            for host in staticCode.remote_hosts:
                zhiling = "scp %s/%s %s:%s/%s"\
                                  % (staticCode.svn_root, path, host, staticCode.remote_root, f_path)
                print(zhiling)
                ret = os.system(zhiling)
                if ret != 0:
                    return False
        return True



