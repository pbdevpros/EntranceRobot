//Stepper motor stuff//
#include <Arduino.h>
#include "BasicStepperDriver.h"

//RFID MFRC522 libraries
#include <SPI.h>
#include <MFRC522.h>

// Motor steps per revolution. Most steppers are 200 steps or 1.8 degrees/step
#define MOTOR_STEPS 96
#define RPM 165

//RFID Defines
#define SS_PIN 10
#define RST_PIN 9
MFRC522 mfrc522(SS_PIN, RST_PIN);   // Create MFRC522 instance.

// Since microstepping is set externally, make sure this matches the selected mode
// If it doesn't, the motor will move at a different RPM than chosen
// 1=full step, 2=half step etc.
#define MICROSTEPS 4 //was 4

// All the wires needed for full functionality
#define DIR 2
#define STEP 3

//var for stepper enable/disable
int enablePin = 17;
int led = 18;

//vars for limit switch
int limit = 6; //limitswitch on pin 6
int increment = 50; //distance to travel each iteration through homeZ function

//distance to rotate each way
int distance = 1500;



// 2-wire basic config, microstepping is hardwired on the driver
BasicStepperDriver stepper(MOTOR_STEPS, DIR, STEP);




//Uncomment line to use enable/disable functionality
//BasicStepperDriver stepper(MOTOR_STEPS, DIR, STEP, ENABLE);

void setup() {
  //RFID Stuff
  Serial.begin(9600);   // Initiate a serial communication
  SPI.begin();      // Initiate  SPI bus
  mfrc522.PCD_Init();   // Initiate MFRC522
  Serial.println("Approximate your card to the reader...");
  Serial.println();

  //stepper motor stuff//
  stepper.begin(RPM, MICROSTEPS);
  pinMode(enablePin, OUTPUT); //pint to enable/disable stepper motor

  //Disables stepper to save power at startup
  delay(500);
  disableStepper();
}

void loop() {
  
  // Look for new cards
  if ( ! mfrc522.PICC_IsNewCardPresent()) 
  {
    return;
  }
  // Select one of the cards
  if ( ! mfrc522.PICC_ReadCardSerial()) 
  {
    return;
  }
  //Show UID on serial monitor
  Serial.print("UID tag :");
  String content= "";
  byte letter;
  for (byte i = 0; i < mfrc522.uid.size; i++) 
  {
     Serial.print(mfrc522.uid.uidByte[i] < 0x10 ? " 0" : " ");
     Serial.print(mfrc522.uid.uidByte[i], HEX);
     content.concat(String(mfrc522.uid.uidByte[i] < 0x10 ? " 0" : " "));
     content.concat(String(mfrc522.uid.uidByte[i], HEX));
  }
  Serial.println();
  Serial.print("Message : ");
  content.toUpperCase();
  if (content.substring(1) == "1C 74 B2 65") //change here the UID of the card/cards that you want to give access
  {
    Serial.println("Authorized access");
    Serial.println();
    openClose();
    delay(3000);
  }
  else if(content.substring(1) == "E5 84 B1 65")
  {
    Serial.println("Authorized access");
    Serial.println();
    openClose();
    delay(3000);
  }
  else if(content.substring(1) == "0E 15 2A 2B")
  {
    Serial.println("Authorized access");
    Serial.println();
    openClose();
    delay(3000);
  }
 
 else   {
    Serial.println(" Access denied");
    delay(3000);
  }


    
}

//calls function to rotate stepper open and then return to closed
void openClose(){
  //digitalWrite(led, HIGH);
  Serial.println("Unlocking Door for 5 seconds...");
  
  enableStepper();
  stepper.rotate(-distance);

  Serial.println("Locking Door in 5...");
  delay(1000);
  Serial.println("Locking Door in 4...");
  delay(1000);
  Serial.println("Locking Door in 3...");
  delay(1000);
  Serial.println("Locking Door in 2...");
  delay(1000);
  Serial.println("Locking Door in 1...");
  delay(1000);
  Serial.println("Door Locked");
  Serial.println("Door Locked");
  //digitalWrite(led, LOW);

  stepper.rotate(distance);
  disableStepper();
}

//calls function to rotate stepper to open
void openDoor(){
  enableStepper();
  stepper.rotate(distance);
  disableStepper();//comment this out or remove, included for testing only
}

//calls function to rotate stepper to closed
void closeDoor(){
    enableStepper();//comment this out or remove, included for testing only
    stepper.rotate(-distance);
    disableStepper();
}

void enableStepper(){
  digitalWrite(enablePin, LOW);
}

void disableStepper(){
  digitalWrite(enablePin, HIGH);
}

void homeZ(){
  int checker = 1;
  enableStepper();
  while(checker == 1){
    bool limitCheck = digitalRead(limit);
    if(limitCheck == HIGH){
      //BT.println("Lim  high");
      stepper.rotate(-increment);
    }
    if(limitCheck == LOW){
      //BT.println("Lim  low");
      checker = 0;
      disableStepper();
      return;
    }
  }
}

void startUpSeq(){
      //digitalWrite(led, HIGH);
      //BT.println("Homing");
      Serial.println("Homing");
      homeZ();
      //digitalWrite(led, LOW);

      //digitalWrite(led, HIGH);
      openDoor();
      //digitalWrite(led, LOW);
}
