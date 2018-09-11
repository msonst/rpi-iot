pigpiod
node-red-node-pi-gpiod                 1

sudo apt-get update
sudo apt-get upgrade
sudo apt-get install xrdp 
sudo systemctl enable xrdp
sudo systemctl start xrdp
sudo ufw allow from 192.168.0.0/24 to any port 3389


## Docker
sudo apt-get update
curl -fsSL get.docker.com -o get-docker.sh && sh get-docker.sh
sudo groupadd docker
sudo gpasswd -a $USER docker
newgrp docker

## Docker Compose
sudo curl -L "https://github.com/docker/compose/releases/download/1.22.0/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose
sudo ln -s /usr/local/bin/docker-compose /usr/bin/docker-compose

## SStart Docker Compose
docker-compose up


## RabbitMQ
docker run rabbitmq


#https://www.rabbitmq.com/mqtt.html
#rabbitmq-plugins enable rabbitmq_mqtt

# username and password are both "mqtt-test"
#rabbitmqctl add_user mqtt-test mqtt-test
#rabbitmqctl set_permissions -p / mqtt-test ".*" ".*" ".*"
#rabbitmqctl set_user_tags mqtt-test management