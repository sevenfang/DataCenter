import os,sys

from util.SysInitData import SysInitData
from util.staticUtil import staticCode
from business.publish.PublishDao import PublishDaoImpl


class PublishFindProcess:
    def __init__(self):
        self.index = 0
        self.SUCCESS = "查询成功"
        self.ERROR = "python处有异常"
        self.publishDao = PublishDaoImpl()

    def findVersionByPath(self, path):
        try:
            version = self.publishDao.findFileVersion(path)
            if version is None:
                return True, self.SUCCESS, 0
            return True, self.SUCCESS, version
        except Exception as e:
            return False, self.ERROR, str(e)

    def findPathByName(self, name):
        try:
            #从数据库查找
            result = SysInitData.getPathsByName(name)
            path = None
            if ".sh" in name:
                dirs = str(name).split("_")
                if len(dirs) >= 2:
                    path = "%s/%s/job/%s" % (dirs[0], dirs[1], name)
            elif ".ctsh" in name:
                dirs = str(name).split("_")
                if len(dirs) >= 2:
                    path = "%s/%s/createtable/%s" % (dirs[0], dirs[1], name)
            elif ".json" in name:
                path = "json/%s" % (name)
            elif ".conf" in name:
                path = "bdp/bdp2.0/conf/%s" % name
            elif ".ctconf" in name:
                path = "bdp/bdp2.0/createtable/%s" % name
            if path is not None:
                result.add(path)
            return True, self.SUCCESS, list(result)
        except Exception as e:
            return False, self.ERROR, str(e)