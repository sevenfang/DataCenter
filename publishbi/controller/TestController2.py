import tornado
from tornado import gen
import time
import json
import os

# 外部接口服务
from business.publish.FindService import PublishFindProcess
from util.staticUtil import staticCode


class TestHandler2(tornado.web.RequestHandler):

    @gen.coroutine
    def get(self):
        try:
            # svn_path
            svn_path = str(self.get_argument("path"))
            if svn_path == '':
                return self.write(Result(False, "入参异常").to_json())
        except Exception as e:
            return self.write(Result(False, "入参异常").to_json())
        process = PublishFindProcess()
        # success,message,data = process.findVersionByPath(svn_path)
        data = {
            "LOGIN_USER_ID":self.get_cookie('LOGIN_USER_ID'),
        }
        return self.write(Result(True, "success", data).to_json())

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