Please find enclusures on [thingiverse](https://www.thingiverse.com/thing:3092356)

```
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


# rpi-iot

# Set new password
sudo adduser docker
sudo usermod -aG sudo docker 
sudo su docker

sudo apt-get -y install git
git clone https://github.com/msonst/rpi-iot.git

sudo systemctl enable ssh
sudo systemctl start ssh


# Server
# assumes you're user docker
cd rpi-iot/rpi-iot-server/src/main/bash/
sudo chmod +x server-install.sh
./server-install.sh

# Thing
sudo raspi-config
> Interfacing Options > Enable SSH
> Interfacing Options > Enable I2C
> Interfacing Options > Enable SPI
> Hostname > private-cloud
> Advanced > Expand Filesystem
> Localisation Options > Change Wi-fi Country > US
> Localisation Options > Change Timezone > US > Eastern
> reboot
sudo chmod +x src/main/bash/pi-upgrade_jessie_stretch.sh
cd src/main/bash
./pi-upgrade_jessie_stretch.sh
sudo chmod +x rpi-iot/rpi-iot-thing/src/main/bash/thing-install.sh
cd rpi-iot/rpi-iot-thing/src/main/bash/
./thing-install.sh


Node Red: IP/1880
RabbitMq Management:15672
RabbitMq MQTT: tcp://IP:1883
RaabbitMq AMPQ: tcp://IP:5672
Map: IP/1880/worldmap
Dashboard: IP/1880/ui

gradlew rpi-iot-server:build
gradlew rpi-iot-thing:build

```
