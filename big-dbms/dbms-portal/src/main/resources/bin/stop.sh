#!/bin/bash
source $1
p=${PID}

#获取执行文件所在目录
BASH_PATH=$(cd `dirname $0`;pwd)
# 进入此目录
cd ${BASH_PATH}

# 检查进程ID是否存在
if [ -n "`jps | grep ${p}`" ];then
    echo '结束进程ID：'${p}
    kill -9 ${p}
fi
rm -rf $1