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
sudo apt-get -y dist-upgrade
sudo rpi-update
sudo sed -i 's/jessie/stretch/g' /etc/apt/sources.list    
sudo sed -i 's/jessie/stretch/g' /etc/apt/sources.list.d/raspi.list    
sudo apt-get -y remove apt-listchanges
sudo apt-get -y update && sudo apt-get upgrade 
sudo apt-get -y dist-upgrade 
sudo apt-get -y autoremove -y && sudo apt-get -y autoclean
sudo rpi-update