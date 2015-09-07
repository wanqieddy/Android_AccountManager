#!/bin/bash
basedir=$(cd "$(dirname "$0")";pwd)
cd $basedir

ant_home=$CBB_HOME/build/ant/apache-ant-1.9.3/bin
$ant_home/ant clean
