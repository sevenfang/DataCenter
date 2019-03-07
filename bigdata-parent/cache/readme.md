# 系统编号
400000

#安装部署
###1、打包上传
打包后成 bigmsg-0.0.1-SNAPSHOT.zip  
部署机器：10.100.16.43  
将压缩包上传到该机器，解压   
###2、修改配置
#####application.yml
```yaml
server:
  port: 8597
  servlet:
    context-path: /
spring:
 ds:
   dataSources:
     - dbname: default
       driverClassName: com.mysql.jdbc.Driver
       url: jdbc:mysql://10.100.16.12:3306/test?characterEncoding=UTF-8
       username: db_dataware
       password: HGfuin34HJk%itO
       initialSize: 1
       maxActive: 5
       maxIdle: 2
       minIdle: 5
       maxWait: 60000
sql:
  template:
    path: classpath:sqltemplates/**/*.xml

```
#####push.properties
```properties
APPID=21
appKey=aa6fa0059b3b4e6cbfd35793f8edd32f
appSecret=d74310a553a30404e12e291766d1dbb6
msgUrl=http://message.local.mychebao.com/
#msgUrl=http://message.public.mychebao.com/
```
#####application.properties
```properties
logging.level.org.springframework=ERROR
logging.level.com.chezhibao=ERROR
logging.file=logs/logging.log

```
##测试
#####测试苹果
```
curl -d 'data=[{"buyerId":"164921","msg":"123test","DAUID":"564","mobile":"18120132039","token":"e83777eebd2965c2bec2ead3b2f110e867b28ab1aadcefc5325d27c3f2ec7808","client":"2"}]' http://10.100.16.43:8597/msg/push/buyer
```
#####测试安卓
```
curl -d 'data=[{"buyerId":"164921","msg":"123test","DAUID":"564","mobile":"18120132039","token":"AqfvPPsZS9OM-GXjrO5RruuimNMYvnJxw1F7rPj3Vk8I","client":"3"}]' http://10.100.16.43:8597/msg/push/buyer
```
#####测试短信
```
curl -d 'data=[{"buyerId":"164921","msg":"123test","DAUID":"564","mobile":"18120132039","token":"AqfvPPsZS9OM-GXjrO5RruuimNMYvnJxw1F7rPj3Vk8I","client":"3"}]' http://10.100.16.43:8597/msg/sms/buyer

```

# 附录
### 错误码
##### app消息：210000  dao:211000 service:212000  controller: 213000
##### sms消息：220000  dao:221000 service:222000  controller: 223000
##### 钉钉消息：230000 dao:231000 service:232000  controller: 233000