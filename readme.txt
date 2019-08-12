# Lab Access Control System using RFID, WIFI, NFC, & BLE

###### The SJCC Robotics & Automation Club

## Details

The aim of this project is to create a simple, reliable and easy-to-bring-up prototype of a remotely controlled door opening robot. 

The hardware was chosen to press down/release the panic bar of any door. Actuation is controlled by a ESP8266. Remote connection is made over WiFi on a LAN network. Raspberry Pi used as local server. User interface to access control is an Android application. 

###### EntryRobot

This contains all the source and image files necessary for the Android application. 

Android Studio can be used to build and deploy this project on a debug machine. Source files are written in Java.
https://developer.android.com/studio

To date, there has been no official release of the application.

###### ESP8266_wificlient && ESP8266_wifiserver

Contains code written using Arduino platform (.ino files).
https://www.arduino.cc/en/main/software

###### PiServer

This contains the code for the local server, which can be run on any machine with Python.

###### LabDoorOpener_V1.0.0

This contains initial source code for prototype door actuator. This is intended to be deprecated by the ESP8266 source, which will handle both connection requests and mechanical control.


###### Other

Any other files are miscellaneous test/sample files. This is an ongoing project... :)


## Downloading and building the project

Clone project using link provided above.

Necessary links to build source are included above.

## Contributors:
    Joseph Heady - Club President/Founder
    Michael Boeggeman - Lead Industry Professional Advisor
    Patrick Basquel - Industry Professional Advisor


