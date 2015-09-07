#!/bin/bash
basedir=$(cd "$(dirname "$0")";pwd)
cd $basedir

echo packaging AccountManager ...
mkdir .apt_generated
ant_home=$CBB_HOME/build/ant/apache-ant-1.9.3/bin
source_dir="src;.apt_generated"
$ant_home/ant -Dsource.dir=$source_dir release

