#!/bin/bash

arr=($*)
STOP_PARAM=${arr[0]}

#获取执行文件所在目录
BASH_PATH=$(cd `dirname $0`;pwd)
# 进入此目录
cd ${BASH_PATH}
${BASH_PATH}/stop.sh ${STOP_PARAM}
len=${#arr[*]}-1
${BASH_PATH}/startup.sh ${arr[*]:1:${len}}