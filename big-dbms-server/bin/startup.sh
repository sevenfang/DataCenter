#!/bin/bash
# 指定执行的MainClass
MAIN_CLASS=com.chezhibao.bigdata.dbms.server.DbmsServerApplication
DEBUG_OPT=""
ENV="prod"
while getopts ":m:d:c:j:h:e:" opt
do
    case $opt in
        j)
        # 获取用户输入的JAVA_HOME
        JAVA_HOME=${OPTARG}
        ;;
        m)
        MAIN_CLASS=${OPTARG}
        ;;
        d)
        DEBUG_OPT="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address="${OPTARG}
        ;;
        e)
        ENV=${OPTARG}
        ;;
        h)
        echo "参数-m==>需要指定的运行的主类;参数-d==>指定dubug模式下的端口;参数-j==>指定java_home目录;参数-e==>指定运行环境"
        exit 0;
        ;;
        ?)
        echo "未知参数"
        exit 1;;
    esac
done


#获取执行文件所在目录
BASH_PATH=$(cd `dirname $0`;pwd)
# 进入此目录
cd ${BASH_PATH}

# 检查JAVA_HOME
if [ -z "${JAVA_HOME}" ];then
   echo "JAVA_HOME can not bu null!!";
   exit 1;
fi
cd ../
echo `pwd`
echo ${JAVA_HOME}/bin/java ${DEBUG_OPT} -Denv=${ENV} -classpath "./lib/*:." ${MAIN_CLASS}
${JAVA_HOME}/bin/java ${DEBUG_OPT} -Denv=${ENV} -classpath "./lib/*:." ${MAIN_CLASS} >> /dev/null 2>&1 &