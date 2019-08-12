
# httpserver.py

# Creates a simple local server on the host.
# Server will count the amount of requests made and log them to std out.

# Usage:
#	python httpserver.py


# Owner: Patrick Basquel
# Revision: 0.0.1
# TODO: 
#	Add security keys, hidden in json file on server. 
# 	Configure server to give access only on secure key being present.

import BaseHTTPServer
import time

global COUNT
COUNT = 0
RESET_LIMIT = 100
KEEP_RUNNING = True

class httpHandler(BaseHTTPServer.BaseHTTPRequestHandler):
	def do_HEAD(s):
		s.send_response(200)
		s.send_header("Content-type", "text/html")
		s.end_headers()
	def do_GET(s):
		"""Respond to a GET request."""
		s.send_response(200)
		s.send_header("Content-type", "text/html")
		s.end_headers()
		s.wfile.write("<html><head><title>SJCC Robotics and Automation Club</title></head>")
		s.wfile.write("<body><p>This site is for internal server use only. Please disconnect immediately if there has been no specific instruction to do so.</p>")
		# If someone went to "http://something.somewhere.net/foo/bar/",
		# then s.path equals "/foo/bar/".
		s.wfile.write("<p>You accessed path: /localhost:8000%s</p>" % s.path)
		s.wfile.write("</body></html>")
		if (s.path == "/server/reset"):
			s.send_error(204)

def keep_running():
    return KEEP_RUNNING

httpd = BaseHTTPServer.HTTPServer(("", 8000), httpHandler)

while keep_running():
    httpd.handle_request()
    COUNT = COUNT + 1
    print(COUNT)
    