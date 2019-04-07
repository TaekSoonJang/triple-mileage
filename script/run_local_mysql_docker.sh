#!/usr/bin/env bash

SCRIPT_PATH=$(dirname "$0")
cd ${SCRIPT_PATH}
SCHEMA_SQL_PATH=$(pwd)/../docs/schema.sql

docker run \
-d \
-p 3306:3306 \
-v ${SCHEMA_SQL_PATH}:/docker-entrypoint-initdb.d/schema.sql \
-e MYSQL_DATABASE=triple \
-e MYSQL_USER=triple \
-e MYSQL_PASSWORD=triple \
-e MYSQL_ROOT_PASSWORD=root \
mysql:5.7.22