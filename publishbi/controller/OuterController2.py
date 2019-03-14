import tornado
from tornado import gen
import time
import json
import os

# 外部接口服务
from business.publish.PublishService import PublishProcess
from util.staticUtil import staticCode


class OuterHandler2(tornado.web.RequestHandler):

    @gen.coroutine
    def get(self):
        request = {}
        try:
            # publish_type, publish_version, svn_path
            publish_type = int(self.get_argument("type"))
            publish_version = int(self.get_argument("version"))
            svn_path = str(self.get_argument("path"))
            request['publish_type'] = publish_type
            request['publish_version'] = publish_version
            request['svn_path'] = svn_path
            if svn_path == '':
                return self.write(Result(False, "入参异常").to_json(request))
        except Exception as e:
            return self.write(Result(False, "入参异常").to_json(request))

        try:
            if staticCode.env == 'dev':
                publish_author = 0
            else:
                publish_author = int(str(self.get_cookie('LOGIN_USER_ID')).split(",")[0])
            request['publish_author'] = publish_author
        except Exception as e:
            return self.write(Result(False, "未获取到用户信息,可尝试刷新重新登录").to_json(request))
        process = PublishProcess()
        success,message,data = process.publish_process(publish_type, publish_version, svn_path, publish_author)
        return self.write(Result(success, message, data).to_json(request))

    @gen.coroutine
    def post(self):
        return self.write(Result(False, "请使用GET请求").to_json({}))


class Result:
    def __init__(self, success, message, data=""):
        self.success = success
        self.message = message
        self.data = data

    def to_json(self, request):
        response = json.dumps({
            "success": self.success,
            "message": self.message,
            "data": self.data,
            "timestamp": time.time() * 1000
        }, ensure_ascii = False)
        self.writeLog(json.dumps(request), response, "GET", "INFO")
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