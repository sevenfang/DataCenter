from database.mysqlConnPool import DB_dw_new

class Singleton(object):
    def __new__(cls, *args, **kw):
        if not hasattr(cls, '_instance'):
            orig = super(Singleton, cls)
            cls._instance = orig.__new__(cls, *args, **kw)
        return cls._instance

class SysInitData(Singleton):
    dir_path_id = {}
    file_path_id = {}
    file_path_userid = {}
    file_name_paths = {}

    def init_load(self):
        sql = "select path,id from t_biservise_dir"
        t_biservise_dir = DB_dw_new.fetchall(sql)
        for one in t_biservise_dir:
            self.dir_path_id[one[0]] = one[1]
        print("数据预加载 - t_biservise_dir加载完成")

        sql = "select path,id from t_biservise_file"
        t_biservise_file = DB_dw_new.fetchall(sql)
        for one in t_biservise_file:
            self.file_path_id[one[0]] = one[1]
        print("数据预加载 - t_biservise_dir加载完成")

        sql = "select userid,path from t_biservise_user"
        t_biservise_user = DB_dw_new.fetchall(sql)
        for one in t_biservise_user:
            self.file_path_userid[one[0]] = one[1]
        print("数据预加载 - t_biservise_user加载完成")

        sql = "select name,path from t_biservise_file"
        t_biservise_file_np = DB_dw_new.fetchall(sql)
        for one in t_biservise_file_np:
            if self.file_name_paths.get(one[0]) is None:
                self.file_name_paths[one[0]] = {one[1]}
            else:
                self.file_name_paths[one[0]].add(one[1])
        print("数据预加载 - t_biservise_file(name,path)加载完成")

    @staticmethod
    def getIdFromPath(path, isDir=True):
        sysInitData = SysInitData()
        if isDir:
            return sysInitData.dir_path_id.get(path)
        else:
            return sysInitData.file_path_id.get(path)

    @staticmethod
    def setPathId(path, id, isDir):
        sysInitData = SysInitData()
        if isDir:
            sysInitData.dir_path_id[path] = id
        else:
            sysInitData.file_path_id[path] = id

    @staticmethod
    def getPathByUserid(userid):
        sysInitData = SysInitData()
        return sysInitData.file_path_userid.get(userid)

    @staticmethod
    def getPathsByName(name):
        sysInitData = SysInitData()
        return sysInitData.file_name_paths.get(name, set())

    @staticmethod
    def setPathsByName(name,path):
        sysInitData = SysInitData()
        if sysInitData.file_name_paths.get(name) is None:
            sysInitData.file_name_paths[name] = {path}
        else:
            sysInitData.file_name_paths[name].add(path)
