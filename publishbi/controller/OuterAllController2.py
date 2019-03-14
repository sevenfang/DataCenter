import tornado
from tornado import gen
import time
import json
import os

# 外部接口服务
from business.publish.PublishService import PublishProcess
from util.staticUtil import staticCode


class OuterAllHandler2(tornado.web.RequestHandler):

    @gen.coroutine
    def get(self):
        return self.write(Result(False, "请使用GET请求").to_json({}))

    @gen.coroutine
    def post(self):
        request = {}
        try:
            body = bytes.decode(self.request.body)
            # request_data = json.loads(body)
            svn_paths = str(body)
            request['svn_paths'] = svn_paths
            if svn_paths == '':
                return self.write(Result(False, "入参异常").to_json(request))
        except Exception as e:
            return self.write(Result(False, "入参异常").to_json(request))

        try:
            if staticCode.env == 'dev':
                publish_author = 0
            else:
                publish_author = int(str(self.get_cookie('LOGIN_USER_ID')).split(",")[0])
            request['publish_author'] = publish_author
            allow_authors = [0,12603,9955]
            if not allow_authors.__contains__(publish_author):
                return self.write(Result(False, "无权限,请联系管理员").to_json(request))
        except Exception as e:
            return self.write(Result(False, "未获取到用户信息,可尝试刷新重新登录").to_json(request))

        all_success = True
        all_data = []
        true_count = 0
        false_count = 0
        all_count = 0

        process = PublishProcess()

        # 分隔符是 [split]
        paths = svn_paths.split("[split]")
        for path in paths:
            if path.strip() != '':
                all_count += 1
                success, message, data = process.publish_process(0, 0, path.strip(), publish_author)
                if not success:
                    false_count += 1
                    all_data.append({
                        "path":path.strip(),
                        "message" : message,
                        "data":data,
                    })
                else:
                    true_count += 1

        all_message = "共处理 %s 条,成功 %s 条,失败 %s 条.失败原因如下:"\
                      % (str(all_count),str(true_count),str(false_count),)

        return self.write(Result(all_success, all_message, all_data).to_json(request))


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