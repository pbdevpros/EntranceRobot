#include <ESP8266WiFi.h>

WiFiServer server(80); //Initialize the server on Port 80
int LED_PIN = D1;

void setup() {
  
  WiFi.mode(WIFI_AP); //Our ESP8266-12E is an AccessPoint 
  WiFi.softAP("Hello_IoT", "12345678"); // Provide the (SSID, password); . 
  server.begin(); // Start the HTTP Server
  
  //Looking under the hood
  Serial.begin(115200); //Start communication between the ESP8266-12E and the monitor window
  IPAddress HTTPS_ServerIP= WiFi.softAPIP(); // Obtain the IP of the Server 
  Serial.print("Server IP is: "); // Print the IP to the monitor window 
  Serial.println(HTTPS_ServerIP);

  pinMode(LED_PIN, OUTPUT); //GPIO16 is an OUTPUT pin;
  digitalWrite(LED_PIN, LOW); //Initial state is ON
  pinMode(BUILTIN_LED, OUTPUT);
  digitalWrite(BUILTIN_LED, LOW);

}

void loop() { 

  WiFiClient client = server.available();
  if (!client) { 
    return; 
  } 
  //Looking under the hood 
  Serial.println("Somebody has connected :)");

  //Read what the browser has sent into a String class and print the request to the monitor
  String request = client.readString(); 
  //Looking under the hood 
  Serial.println(request);

  // Handle the Request
  int value = LOW;
  if (request.indexOf("/ON") != -1) {
    digitalWrite(LED_PIN, HIGH);
    digitalWrite(BUILTIN_LED, LOW); //LED set to low to turn on in this case
    value = HIGH;
  } 
  if (request.indexOf("/OFF") != -1){
    digitalWrite(LED_PIN, LOW);
    digitalWrite(BUILTIN_LED, HIGH); //turn LED off
    value = LOW;
  }

  //Serve the HTML document to the browser.

  client.println("HTTP/1.1 200 OK");
  client.println("Content-Type: text/html");
  client.println(""); //  do not forget this one
  client.println("<!DOCTYPE HTML>");
  client.println("<html>");

  if(value == HIGH) {
    client.print("Engaged (ON)");  
  } else {
    client.print("Disengaged (OFF)");
  }

  client.println("<br><br><br>");
  client.println("<a href=\"/ON\">Click here to engage (Turn ON) the relay.</a> <br><br><br>");
  client.println("<a href=\"/OFF\">Click here to disengage (Turn OFF) the relay.</a><br>");
  client.println("</html>");

  client.flush(); //clear previous info in the stream 
//  client.print(s); // Send the response to the client 
  Serial.println("Client disonnected"); //Looking under the hood
 
 }
