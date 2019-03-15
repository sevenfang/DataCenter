import os,sys

from database.mysqlConnPool import DB_dw_new
from util.SysInitData import SysInitData
from util.staticUtil import staticCode


class PublishDaoImpl:
    def __init__(self):
        self.index = 0

    def findFileVersion(self, path):
        sql = "select publish_version from t_biservise_file where path='{}'".format(path)
        t_biservise_file = DB_dw_new.fetchall(sql)
        if len(t_biservise_file) == 1:
            return t_biservise_file[0][0]
        elif len(t_biservise_file) > 1:
            raise Exception(path + ",有多条记录")
        else:
            return None

    def findId(self, path, isDir):
        id = SysInitData.getIdFromPath(path, isDir)
        if id is None:
            if isDir:
                sql = "select id from t_biservise_dir where path='{}'".format(path)
                t_biservise_dir = DB_dw_new.fetchall(sql)
                if len(t_biservise_dir) == 1:
                    SysInitData.setPathId(path, t_biservise_dir[0][0], isDir)
                    return t_biservise_dir[0][0]
                elif len(t_biservise_dir) > 1:
                    raise Exception(path + ",有多条记录")
                else:
                    return None
            else:
                sql = "select id from t_biservise_file where path='{}'".format(path)
                t_biservise_file = DB_dw_new.fetchall(sql)
                if len(t_biservise_file) == 1:
                    SysInitData.setPathId(path, t_biservise_file[0][0], isDir)
                    return t_biservise_file[0][0]
                elif len(t_biservise_file) > 1:
                    raise Exception(path + ",有多条记录")
                else:
                    return None
        else:
            return id

    def addDir(self, f_path, path, name):
        id = self.findId(path, True)
        if id is None:
            fid = self.findId(f_path, True)
            if fid is None:
                raise Exception(path + "的父文件夹无法找到")
            else:
                sql = "insert into t_biservise_dir (pid,name,path) VALUES ({},'{}','{}')"\
                    .format(fid, name, path)
                return DB_dw_new.executeOne(sql)
        else:
            return True

    def addFile(self, m_path, path, name, svn_author, publish_author, publish_version):
        id = self.findId(path,False)
        if id is None:
            mid = self.findId(m_path, True)
            if mid is None:
                raise Exception(path + "的父文件夹无法找到")
            else:
                sql = "insert into t_biservise_file (mid,name,path,svn_author,publish_author,publish_version) " \
                      "VALUES ({},'{}','{}','{}','{}',{})".format(mid, name, path, svn_author, publish_author, publish_version)
                SysInitData.setPathsByName(name, path)
                return DB_dw_new.executeOne(sql)
        else:
            sql = "update t_biservise_file set svn_author='{}',publish_author='{}',publish_version={} where id={}"\
                .format(svn_author,publish_author,publish_version,id)
            return DB_dw_new.executeOne(sql)