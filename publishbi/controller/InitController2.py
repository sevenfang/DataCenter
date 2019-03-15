import tornado
from tornado import gen
import time
import json
import os

# 外部接口服务
from business.publish.PublishInit import PublishInit


class InitHandler2(tornado.web.RequestHandler):

    @gen.coroutine
    def get(self):
        try:
            # publish_type, publish_version, svn_path
            publish_type = int(self.get_argument("type"))
            publish_version = int(self.get_argument("version"))
            svn_path = str(self.get_argument("path"))
            remote = bool(self.get_argument("remote", False))
        except Exception as e:
            return self.write(Result(False, "入参异常").to_json())
        process = PublishInit()
        success,message,data = process.publish_process(publish_type, publish_version, svn_path, remote=False)
        return self.write(Result(success, message, data).to_json())

    @gen.coroutine
    def post(self):
        return self.write(Result(False, "请使用GET请求").to_json())


class Result:
    def __init__(self, success, message, data=""):
        self.success = success
        self.message = message
        self.data = data

    def to_json(self):
        return json.dumps({
            "success": self.success,
            "message": self.message,
            "data": self.data,
            "timestamp": time.time() * 1000
        }, ensure_ascii = False)


    def to_json_success(self, username, isR, path):
        response = json.dumps({
            "success": self.success,
            "message": self.message,
            "data": self.data,
            "timestamp": time.time() * 1000
        }, ensure_ascii=False)
        # self.writeLog("[%s,%s,%s]" % (username, isR, path), response, "GET", "INFO")
        return response


    def writeLog(self, requestmsg, responsemsg, method, level='INFO'):
        now = time.localtime()
        nowDate = time.strftime("%Y-%m-%d", now)
        nowTime = time.strftime("%Y-%m-%d %H:%M:%S", now)
        logDir = 'log/publish'
        logPath = '%s/%s_publish.log' % (logDir, nowDate)
        if not os.path.exists(logDir):
            os.mkdir('log')
        f = open(logPath, 'a')
        f.write((u'%s:\001%s\001%s\001%s\001%s\n' % (nowTime, level, method, requestmsg, responsemsg)))
        f.close()