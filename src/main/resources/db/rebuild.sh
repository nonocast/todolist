#!/usr/bin/env bash
mysql -uroot -p -Bse "DROP DATABASE todolist;CREATE DATABASE todolist;"
mysql todolist -uroot -p < schema.sql
mysql todolist -uroot -p < data.sql
