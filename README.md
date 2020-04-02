# Android Lab
### To run this project(instruction for Linux users):
1. Connect to the same wi-fi as laptop
2. Disable firewall using command below ``systemctl stop ufw``
3. Check laptop id using command below ``hostname -I`` 
4. Go to [TestApplication class](https://github.com/maksimk99/android-app/blob/master/app/src/main/java/by/brstu/redlabrat/testinternet/TestApplication.kt) 
and change IP_ADDRESS on IP address obtained from step 2
5. Download [android-server](https://github.com/maksimk99/android-server) repository
6. Go to root of downloaded repository and run it using command below ``mvn spring-boot:run``
7. Run android app and test it