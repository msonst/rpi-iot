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

sudo apt-get -y update
sudo apt-get -y upgrade
sudo apt-get -y install xrdp 
sudo systemctl enable xrdp
sudo systemctl start xrdp
sudo ufw allow from 192.168.0.0/24 to any port 3389

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
sudo apt-get -y install python python-pip
pip install docker-compose
sudo cp docker-compose-app.service /etc/systemd/system/

cd /data/nodered/
npm i node-red-dashboard
# nodejs>8 required: npm i node-red-node-email
#npm i node-red-node-watson
#npm i node-red-contrib-watson-machine-learning
#npm i node-red-contrib-thinger

# RabbitMQ
#docker run rabbitmq

#https://www.rabbitmq.com/mqtt.html
#rabbitmq-plugins enable rabbitmq_mqtt

# username and password are both "mqtt-test"
#rabbitmqctl add_user mqtt-test mqtt-test
#rabbitmqctl set_permissions -p / mqtt-test ".*" ".*" ".*"
#rabbitmqctl set_user_tags mqtt-test management


# Start Docker Compose
systemctl enable docker-compose-app