#!/bin/bash

#获取执行文件所在目录
BASH_PATH=$(cd `dirname $0`;pwd)
# 进入此目录
cd ${BASH_PATH}
./stop.sh
./startup.sh
