# README

## 简介
	该服务主要用于脚本管理和发布。
	1、通过svn将脚本提交到svn服务器。
	2、通过该服务奖svn中的脚本发布到执行服务器。
	3、在发布的过程中，会使用mysql管理。

## 一、环境准备
	* python3.6
	```
	TODO:
	pip install svn
	pip install sqlalchemy
	pip install DBUtils
	```
	* svn-1.7.14
	```
	svn服务
	并在服务部署机器上，新建一个文件夹，checkout该svn目录。
	并且需要保存svn update的用户密码。
	```
	* mysql-版本任意
	```
	mysql初始化建表脚本[publish.sql](publish.sql)
	```
	* 机器，需要从部署机器往所有执行脚本机器的免密登录。
## 二、配置
	* dev环境，不需要权限管理的配置
	```
	util/staticUtil.py配置
	env = 'dev'
	svn_root = "/home/jerry/che/SVN/bi_service_test" #配置部署机器上checkout的svn目录
	remote_root = "/root/SVN"	#配置执行服务器上，需要将脚本放置的文件夹（所有服务器需要相同）
	remote_hosts = ["root@escnode6","root@escnode5"]	#配置执行脚本服务器
	```
	* prod环境，需要权限管理的配置
	```
	util/staticUtil.py配置与dev环境同
	单独配置数据库t_biservice_user表。
	userid用来区分权限（接口传参时，由cookie中的LOGIN_USER_ID获取）。
	username设置随意。
	path为svn_root目录下的相对路径
	```
## 三、部署
	```
	1、config/config.conf配置端口号和mysql信息。
	2、直接将项目复制到服务器某目录下，python index.py 即可。
	```
## 四、使用
	配合vue前端使用，功能主要有：
	1、通过文件名查询svn全路径。
	2、通过svn全路径查询当前发布的svn版本。
	3、通过svn全路径和svn版本发布脚本。
	4、通过svn全路径批量发布最新版本的脚本。