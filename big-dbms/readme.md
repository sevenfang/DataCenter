# 系统介绍
dbms数据库管理系统，主要用来管理数据库的操作层
## 主要的功能：
1. 使用的注解@DS来控制使用的哪个数据源  
2. rdbms为关系型数据源 mysql 等  
3. ndbms为非关系型数据源 hbase redis 等nosql类
# 打包部署
## 1、 打包
 ```bash
 cd path/to/big-dbms
 mvn clean package
 ```
## 2、 解压打包文件
 
 进入path/to/big-dbms/dbms-portal/target目录下,获取打包好的tar.gz文件
 
 将此文件解压打指定目录
 ```bash
 tar -zxvf dbms-xxxxx.tar.gz
 ```
## 3、 配置
 打开nacos管理页面，添加配置文件
 1、 application.yml