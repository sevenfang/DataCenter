#!/bin/bash
###########程序特定配置#############
MAIN_CLASS=com.chezhibao.bigdata.CBRApplication
APPLICATION_NAME=cbr
APPLICATION_VERSION=@project.version@
#########################
#获取执行文件所在目录
BASH_PATH=$(cd `dirname $0`;pwd)
# 进入此目录
cd ${BASH_PATH}
arr=($*)
OPT=${arr[0]}
len=${#arr[*]}-1
START_PARAMS=${arr[*]:1:${len}}
#unset arr[0]
APPLICATION_INFO_PATH=/opt/app/bigdata/info/${APPLICATION_NAME}
if [ ${OPT}x == "start"x ]; then
    ${BASH_PATH}/startup.sh ${START_PARAMS} -m ${MAIN_CLASS}
elif [ ${OPT}x == "stop"x ]; then
    ${BASH_PATH}/stop.sh ${APPLICATION_INFO_PATH}
elif [ ${OPT}x == "restart"x ]; then
    ${BASH_PATH}/restart.sh ${APPLICATION_INFO_PATH} ${START_PARAMS} -m ${MAIN_CLASS}
elif [ ${OPT}x == "version"x ]; then
    echo ${APPLICATION_VERSION}
elif [ ${OPT}x == "info"x ]; then
    echo "===========APPLICATION INFO=============="
    cat ${APPLICATION_INFO_PATH}
    echo "========================================="
else
    echo "please input start、stop、restart、version or info";
    exit 1;
fi