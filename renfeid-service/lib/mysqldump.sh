#!/usr/bin/env bash

CRT_DIR=$(pwd)

mysqldump -h 127.0.0.1 -P 3306 -uroot -proot renfeid>"$CRT_DIR"/mysqldump.sql