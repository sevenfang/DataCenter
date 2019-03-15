import tornado.ioloop
import tornado.web
import tornado
import logging
import tornado.log
import tornado.options


from config import config
from controller.FindPathController2 import FindPathHandler2
from controller.FindVersionController2 import FindVersionHandler2
from controller.InitController2 import InitHandler2
from controller.OuterAllController2 import OuterAllHandler2
from controller.OuterController2 import OuterHandler2
from controller.TestController2 import TestHandler2
from database.mysqlConnPool import DB_dw_new
from util.SysInitData import SysInitData


class MainHandler(tornado.web.RequestHandler):
    def get(self):
        self.write('index')

    def post(self):
        self.write('post')


class LogFormatter(tornado.log.LogFormatter):
    def __init__(self):
        super(LogFormatter, self).__init__(
            fmt='%(color)s[%(asctime)s %(filename)s:%(funcName)s:%(lineno)d %(levelname)s]%(end_color)s %(message)s',
            datefmt='%Y-%m-%d %H:%M:%S'
        )

db1 = DB_dw_new()
db1.get_pool()
#数据预加载
sysInitData = SysInitData()
sysInitData.init_load()

if __name__ == "__main__":
    dev = "dev." if str(config.getConfig('public', 'dev')) == "true" else ""
    application = tornado.web.Application(
        handlers = [
            # (r"/deploy/init2", InitHandler2),
            (r"/deploy/publish2", OuterHandler2),
            (r"/deploy/publishall2", OuterAllHandler2),
            (r"/deploy/find2", FindVersionHandler2),
            (r"/deploy/findPath2", FindPathHandler2)
        ],
        debug = True if str(config.getConfig(dev + 'web', 'debug')) == "true" else False
    )
    # 日志设置
    tornado.options.parse_command_line()
    [i.setFormatter(LogFormatter()) for i in logging.getLogger().handlers]

    application.listen(int(config.getConfig(dev + 'web', 'port')))
    tornado.ioloop.IOLoop.current().start()
