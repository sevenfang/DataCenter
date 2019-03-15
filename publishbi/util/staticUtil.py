import svn.local
class Singleton(object):
    def __new__(cls, *args, **kw):
        if not hasattr(cls, '_instance'):
            orig = super(Singleton, cls)
            cls._instance = orig.__new__(cls, *args, **kw)
        return cls._instance

class staticCode(Singleton):
    env = 'dev'

    if env == 'dev':
        # svn_root = "/home/jerry/che/SVN/test"
        svn_root = "/home/jerry/che/SVN/bi_service_test"
        remote_root = "/root/SVN"
        remote_hosts = ["root@escnode6","root@escnode5"]
        svn_local_client = svn.local.LocalClient(svn_root)
    else:
        svn_root = "/opt/app/home/dw/bi2SVN/bi_service"
        remote_root = "/opt/app/home/dw/SVN"
        remote_hosts = ["dw@hadoop20", "dw@hadoop21", "dw@hadoop22", "dw@hadoop57", "dw@hadoop58", "dw@hadoop59"]
        svn_local_client = svn.local.LocalClient(svn_root)

