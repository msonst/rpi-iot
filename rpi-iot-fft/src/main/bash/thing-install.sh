################################################################################
#!/bin/sh
#
#  Copyright (c) 2018, Michael Sonst, All Rights Reserved.
# 
#  Licensed under the Apache License, Version 2.0 (the "License");
#  you may not use this file except in compliance with the License.
#  You may obtain a copy of the License at
# 
#  http://www.apache.org/licenses/LICENSE-2.0
# 
#  Unless required by applicable law or agreed to in writing, software
#  distributed under the License is distributed on an "AS IS" BASIS,
#  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#  See the License for the specific language governing permissions and
#  limitations under the License.
################################################################################

sudo timedatectl set-ntp on
sudo systemctl daemon-reload
sudo apt-get -y install i2c-tools

# Docker
sudo apt-get -y update
curl -fsSL get.docker.com -o get-docker.sh && sh get-docker.sh
sudo groupadd docker
sudo gpasswd -a $USER docker
sudo systemctl enable docker
sudo systemctl start docker

# Folders
sudo mkdir -p /data/nodered
sudo chown :docker /data/nodered
sudo chmod 777 /data/nodered/

# Docker Compose
sudo apt-get -y update
sudo apt-get -y upgrade
sudo apt-get -y autoremove -y && sudo apt-get -y autoclean
sudo apt-get -y install python python-pip
sudo pip install docker-compose
sudo cp iot-thing.service /etc/systemd/system/
sudo mkdir -p /iot-thing/
sudo cp -r ../docker /iot-thing/
sudo chown -R :docker /iot-thing/docker

cd /data/nodered/
sudo apt-get -y install npm
npm i --unsafe-perm node-red-contrib-i2c
npm i node-red-contrib-fft
# broken: npm install node-red-contrib-opcua
#npm install node-red-contrib-modbus
#npm install node-red-contrib-cip-ethernet-ip  

# Start Docker Compose
sudo systemctl enable iot-thing
sudo systemctl start iot-thing

sudo apt-get -y install oracle-java8-jdk
