
# httpserver.py

# Creates a simple local server on the host.
# Server will count the amount of requests made and log them to std out.

# Usage:
#	python httpserver.py


# Owner: Patrick Basquel
# Revision: 0.0.2
# TODO: 
#	Add security keys, hidden in json file on server. 
# 	Configure server to give access only on secure key being present.

import BaseHTTPServer
import time

global COUNT
COUNT = 0
RESET_LIMIT = 100
KEEP_RUNNING = True

logger = open('/tmp/server.log', 'w+')

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
	if COUNT == 0 or COUNT == 1:
		logger.write('Server is running, it has been accessed %d times.\n' %(COUNT))
	httpd.handle_request()
	COUNT = COUNT + 1
	print(COUNT)
	if COUNT == 10:
		logger.write('Closing server logs.')
		logger.close()

# =====================================================================================================================================================================
#                                                                   Functionality to validate security keys
#													TODO: 	Add http server with increased functionality (like unlock/add user/send server messages)
#															Add file signing (and reading)
#															Include main functionality. Utiltiy functions are in development and should not be used!
# =====================================================================================================================================================================


 # == PLACEHOLDER - THIS FUNCTION SHOULD BE IN MAIN - PUTTING HERE FOR TEST PURPOSES
def testMainUNLOCK():
	# if header is "UNLOCK"
	# An "UNLOCK" message can come from an app or other machine
	# response = http.handlerequest_withAREsponse?
	user_key = PiServer_read_message_from_client(response)
	security_keys = PiServer_load_signed_json_file("testJSON.json")
	answer = PiServer_make_sure_security_key_is_valid(security_keys, user_key)
	if answer == "NONE":
		print("User has been recognised! Unlocking the door!")
	else:
		PiServer_push_message_to_client(answer + " field(s) incorrect. Please try again.")

# == PLACEHOLDER - THIS FUNCTION SHOULD BE IN MAIN - PUTTING HERE FOR TEST PURPOSES
def testMainADDUSER():
	# if header is "ADD USER"
	# An "ADD USER" message should never come from the app. It should only be added by admin.
	key_to_add = { "some_key" : "some_password" }
	filepath = "testJSON.json"
	security_keys = PiServer_load_signed_json_file(filepath)
	security_keys = security_keys + key_to_add
	PiServer_remove_file(filepath)
	PiServer_write_json_file(security_keys)
	PiServer_sign_json_file(filepath)
	print("Success! New user created! The username and password has been stored (and signed) at " + filepath)




def PiServer_write_json_file(dictionary):
	with open(writepath, 'a+') as file:
		file.write(json.dumps(dictionary))

def PiServer_sign_json_file(filepath):
	return 0

def PiServer_remove_file(filename):
	cmd = 'rm -f ' + filename
	process = subprocess.Popen(cmd.split(), stdout=subprocess.PIPE)
	response, error = process.communicate()
	print('File removed, response: ' + response)


def PiServer_initialise():
	global CLIENT_LOGIN_TYPE
	CLIENT_LOGIN_TYPE = {
		0 : "ALL",
		1 : "USERNAME",
		2 : "PASSWORD",
		3 : "NONE"
	}

def PiServer_load_json_file(filepath):
	with open(filepath, "r") as file:
		security_keys = json.load(file)
	return security_keys

def PiServer_load_signed_json_file(filepath):
	# placeholder
	keys = PiServer_load_json_file(filepath)
	return keys


def PiServer_make_sure_security_key_is_valid(security_keys, received_value):
	# placehodler
	result = 0
	# TODO: CHANGE THE CONDITIONAL STATEMENTS
	for key in security_keys:
		if key == received_value[key]:
			result = result + 1
		if security_keys[key] == received_value[value]:
			result = result + 2
	return CLIENT_LOGIN_TYPE[result]

def PiServer_push_message_to_client(message):
	# placeholder
	print('help')
	# this will send back a response to the user indicating which variable passed was incorrect.
	# This will take some work on the server side to send messages

def PiServer_read_message_from_client(response):
	# placeholder
	# this will take the header of the HTTP request and figure out how to handle it
	user_key = {}
	#TODO: conver response to dictionary
	return user_key    