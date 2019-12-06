#!/bin/bash

echo "Moving launch daemon to designated folder..."
mv ./PiServer/http_server.service /lib/systemd/system/http_server.service
echo "Moving server file to home location..."
mv ./PiServer/httpserver.py /home/pi/server.py
echo "Deploy complete."