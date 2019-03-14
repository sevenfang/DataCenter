# from tornado_mysql import pools
from DBUtils.PooledDB import PooledDB
import pymysql
from config import config


class Singleton(object):
    def __new__(cls, *args, **kw):
        if not hasattr(cls, '_instance'):
            orig = super(Singleton, cls)
            cls._instance = orig.__new__(cls, *args, **kw)
        return cls._instance

class DB_dw_new(Singleton):
    pool = None

    def get_pool(self):
        if self.pool is None:
            print("create db pool == dw_new")
            dev = "dev." if str(config.getConfig('public', 'dev')) == "true" else ""
            pool = PooledDB(creator=pymysql, host=config.getConfig(dev + 'database.dw_new', 'host'),
                     port=int(config.getConfig(dev + 'database.dw_new', 'port')),
                     user=config.getConfig(dev + 'database.dw_new', 'user'),
                     passwd=config.getConfig(dev + 'database.dw_new', 'password'),
                     charset=config.getConfig(dev + 'database.dw_new', 'charset'),
                     db=config.getConfig(dev + 'database.dw_new', 'db'))
            self.pool = pool
        return self.pool

    def close_pool(self):
        self.pool = None

    @staticmethod
    def fetchall(sql):
        db = DB_dw_new()
        try:
            pool = db.get_pool()
            conn = pool.connection()
            cursor = conn.cursor()
            cursor.execute(sql)
            result = cursor.fetchall()
        except Exception as e:
            db.close_pool()
            db.get_pool()
            pool = db.get_pool()
            conn = pool.connection()
            cursor = conn.cursor()
            cursor.execute(sql)
            result = cursor.fetchall()
        finally:
            cursor.close()
            conn.close()
            return result

    @staticmethod
    def fetchone(sql):
        db = DB_dw_new()
        try:
            pool = db.get_pool()
            conn = pool.connection()
            cursor = conn.cursor()
            cursor.execute(sql)
            result = cursor.fetchone()
        except Exception as e:
            db.close_pool()
            db.get_pool()
            pool = db.get_pool()
            conn = pool.connection()
            cursor = conn.cursor()
            cursor.execute(sql)
            result = cursor.fetchone()
        finally:
            cursor.close()
            conn.close()
            return result

    @staticmethod
    def executeOne(sql):
        db = DB_dw_new()
        cursor = None
        conn = None
        try:
            pool = db.get_pool()
            conn = pool.connection()
            cursor = conn.cursor()
            cursor.execute(sql)
            conn.commit()
            return True
        except Exception as e:
            return False
        finally:
            if cursor is None:
                cursor.close()
            if conn is None:
                conn.close()