
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


GND->GND
VIN->+5v
SDA->SDA
SCL->SCL
```