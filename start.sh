#!/bin/bash

cd /home/ec2-user/AlongTheBlue_SERVER

git pull origin main

./gradlew build

sudo systemctl stop AlongTheBlue.service

sudo systemctl start AlongTheBlue.service