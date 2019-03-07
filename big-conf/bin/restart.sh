#!/bin/bash

p=`cat /opt/app/bigdata/pid/config-server`

# 检查进程ID是否存在
if [ -n "`jps | grep ${p}`" ];then
    echo 'config-server进程ID：'${p}
    kill -9 ${p}
fi

#临时名称
TEMP_PATH=""
#循环遍历路径下的文件 应该加上是否时jar包的判断
function getdir(){

    for element in `ls $1 | grep $2`
    do
        dir_or_file=$1"/"${element}
        if [ -f ${dir_or_file} ]
        then
           # getdir $dir_or_file
        #else
            TEMP_PATH=${TEMP_PATH}","${dir_or_file}
        fi
    done
    LEN=${#TEMP_PATH}
    #获取lib目录下的所有文件
    TEMP_PATH=${TEMP_PATH:1:${LEN}}
}

cd ../
main_jar_dir="."
getdir ${main_jar_dir} .jar
MAIN_JAR=${TEMP_PATH}
echo `pwd`
echo java -jar ${MAIN_JAR}
java -jar ${MAIN_JAR} >> /dev/null 2>&1 &
