#!/bin/bash

# initialize_station.sh

# Usage:
# 	./initialize_station.sh
# 
# Organizes files related to the project into the correct location on a RasberryPi and initializes the start-up scripts.

# SJCC Robotics Club

# Use : sudo service http_server status # to check if the daemon is active.
# 		sudo journalctl -u http_server.service # to see the std out for the service.

# RasPi file to launch http server program. File acts as a daemon to ensure the program controlling the http server is running/reboots upon failure
# Stored in /lib/systemd/system/$filename.service - i.e. http_server.service on RasPi
# See https://learn.sparkfun.com/tutorials/how-to-run-a-raspberry-pi-program-on-startup/all for further details...
echo "Copying launch daemon to /lib/systemd..."
cp http_server.service /lib/systemd/system/http_server
sleep 2
echo "Copying server scripts to /home/pi/"
sudo cp server.py /home/pi/server.py
echo "Setting daemon to launch on boot..."
sudo systemctl daemon-reload # this command actually needs to be run every time the .service file is changed
sudo systemctl enable http_server
sleep 2

echo "Rebooting system, please hit Ctrl + C to exit script. NB: RPi will need to be rebooted to verify the launch service will run on boot..."
for i in 5 4 3 2 1
do 
	printf "Rebooting in $i seconds\\r" 
	sleep 1
done

sudo reboot

# eof
