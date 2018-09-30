
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

deploy
"java -jar iot-thing.jar -Xdebug -Xrunjdwp:transport=dt_socket,address=4242,server=y,suspend=y 

I2C:
GND->GND
VIN->+5v
SDA->SDA
SCL->SCL

wget https://raw.githubusercontent.com/torvalds/linux/master/tools/spi/spidev_test.c
gcc -o spidev_test spidev_test.c
./spidev_test -D /dev/spidev0.0

  -D --device   device to use (default /dev/spidev1.1)
  -s --speed    max speed (Hz)
  -d --delay    delay (usec)
  -b --bpw      bits per word 
  -l --loop     loopback
  -H --cpha     clock phase
  -O --cpol     clock polarity
  -L --lsb      least significant bit first
  -C --cs-high  chip select active high
  -3 --3wire    SI/SO signals shared
  -v --verbose  Verbose (show tx buffer)
  -p            Send data (z.B. 1234\xde\xad)
  -N --no-cs    no chip select
  -R --ready    slave pulls low to pause
  -2 --dual     dual transfer
  -4 --quad     quad transfer
  
```