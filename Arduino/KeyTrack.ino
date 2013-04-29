#include "RunningAverage.h"


//Variables
int prevX = -1;
int currX = -1;

int prevY = -1;
int currY = -1;

boolean isClick = true;


//DEFINE PORTS ALLOCATED FOR EACH SENSOR ARRAY
const int X_START_PORT = 0;
const int X_END_PORT = 4;

const int Y_START_PORT = 5;
const int Y_END_PORT = 9;


const int X_ARRAY_LENGTH = X_END_PORT - X_START_PORT + 1;
const int Y_ARRAY_LENGTH = Y_END_PORT - Y_START_PORT + 1;


//DEFINE AND ALLOCATE #OFSENSRORS
int sensArray[10];


float prevXTime = millis();
float prevYTime = millis();


//DEFINE AVERAGE RULES
const int NR_OF_AVERAGE_MEASUREMENTS = 2;
RunningAverage averageX((X_END_PORT - X_START_PORT + 1) * NR_OF_AVERAGE_MEASUREMENTS);
RunningAverage averageY((Y_END_PORT - Y_START_PORT + 1) * NR_OF_AVERAGE_MEASUREMENTS); 



//CALIBRATION THRESHOLDS
int THRESHOLD_X = 0;
int THRESHOLD_Y = 0;


const int SENSITIVITY = 5;

//SHADOW ARRAY
int shadowXArray[5] = {-1,-1,-1,-1,0};
int shadowYArray[5] = {-1,-1,-1,-1,0};  

boolean DEV_MODE = false;

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  Mouse.begin(); 
  //SET UP ARRAYS
  allocateArray(sensArray, 10);


  //Setup Average Class 
  averageX.clear();
  averageY.clear();

  readPorts();
  calibrateSensors();
  
  Serial.println("Start");
}

void loop() {
  readPorts();
  //calibrateSensors();

  
  getShadows(sensArray, X_START_PORT, X_END_PORT, shadowXArray);
  getShadows(sensArray, Y_START_PORT, Y_END_PORT, shadowYArray);


  currX = (shadowXArray[0] + shadowXArray[1])/2;
  currY = (shadowYArray[0] + shadowYArray[1])/2;

  moveMouse(prevX, currX, prevY, currY);
 

  isClick = doClick(prevX, currX);


  prevX = currX;
  prevY = currY;

  if(DEV_MODE){
    printSensArrayValues();
    delay(500);
  } 
}

void allocateArray(int array[], int length){
  for(int i = 0; i < length; i++){
    array[i] = 1;
  }
}

int getBinValue(int value, int sVal){
  int binValue;
  if (value < sVal){
    binValue = 0;
  }
  else{
    binValue = 1;
  }
  return binValue;
}

boolean doClick(int pX, int cX){
  if(pX == -1 && cX != -1 && pX == -1 && cX != -1){
    return true;
  }
  else if(isClick == true && pX == cX){
    return true;
  }
  else if(isClick == true && cX == -1){
    Mouse.click();
    return false;
  }
  else return false;
}

void getShadows(int array[], int startPos, int endPos, int *shadowRef){
  int nrOfShadows = 0;
  
  for(int i = startPos; i<=endPos; i++){
    if(array[i] == 0){    // Om skugga hittas

      shadowRef[nrOfShadows*2] = i - startPos; //Lägg till skuggans startposition
      
      for(int j = i; j<=endPos-1; j++){
        if(array[j+1] == 1 || j+1>=endPos){ //kolla om skuggan är slut
           shadowRef[nrOfShadows*2+1] = j - startPos; //Lägg till skuggan slutposition
           i = j+1;
           break;
         }
       }
       nrOfShadows++;
     }
   }


   if(nrOfShadows>2 || nrOfShadows == 0){
    shadowRef[0] = -1;
    shadowRef[1] = -1;
    shadowRef[2] = -1;
    shadowRef[3] = -1;
    shadowRef[4] = 0;    
  }
  else{
      shadowRef[4] = nrOfShadows; //Lägg till skuggan slutposition      
    }  
  }

  void moveMouse(int pX, int cX, int pY, int cY){
    int diffX = cX - pX;
    int diffY= cY - pY;
    int moveX = 0;
    int moveY = 0;

  
    if(pX != -1 && cX != -1 && diffX != 0 ){

      moveX = diffX*SENSITIVITY/(((millis() - prevXTime)/100));
      Serial.println(moveX);
      prevXTime = millis();
    }
    if(pY != -1 && cY != -1 && diffY > 0 ){
      moveY = diffY*SENSITIVITY/((millis() - prevYTime));
      prevYTime = millis();
    }

    Mouse.move(moveX, moveY, 0);
  }


  void readPorts(){
  if(X_END_PORT != 0){
    for(int i = X_START_PORT; i <= X_END_PORT; i++){

      sensArray[i] = getBinValue(analogRead(i), THRESHOLD_X);

    }
  }
  
  if(Y_END_PORT != 0){
    for(int i = Y_START_PORT ; i  <= Y_END_PORT; i++) {
      sensArray[i] = getBinValue(analogRead(i), THRESHOLD_Y);
    }
  }

}


void calibrateSensors(){
  
  if(X_END_PORT != 0){
   for(int i = X_START_PORT; i <= X_END_PORT; i++){
     averageX.addValue(analogRead(i));
   }
  }
  if(Y_END_PORT != 0){
   for(int i = Y_START_PORT; i <= Y_END_PORT; i++){
     averageY.addValue(analogRead(i));
   }
 }

 THRESHOLD_X = averageX.getAverage() * 0.7;
 THRESHOLD_Y = averageY.getAverage() * 0.7;

}

void printSensArrayValues(){
  Serial.println("_________________________");
  Serial.println("SENS ARRAY: ");

  
  Serial.println("X Values: ");  
  for(int i = X_START_PORT; i <= X_END_PORT && X_END_PORT != 0; i++){
    Serial.print(sensArray[i]);
    Serial.print(" ");
  }
  Serial.println("");
  
  Serial.println("Y Values: ");  
  for(int i = Y_START_PORT ; i  <= Y_END_PORT && Y_END_PORT != 0; i++) {
    Serial.print(sensArray[i]);
    Serial.print(" ");  
  }
  Serial.println("_________________________");

   Serial.println("_________________________");
  Serial.println("SHADOW ARRAYS: ");

  
  Serial.println("X shadows: ");  
  for(int i = 0; i <= 4 ; i++){
    Serial.print(shadowXArray[i]);
    Serial.print(" ");
  }
  Serial.println("");
  
  Serial.println("Y shadows: ");  
  for(int i = 0; i <= 4 ; i++){
    Serial.print(shadowYArray[i]);
    Serial.print(" ");
  }
  Serial.println("");
  
  Serial.println("_________________________");

  Serial.print("THRESHOLD X: ");
  Serial.println(THRESHOLD_X);
  Serial.print("THRESHOLD Y: ");
  Serial.println(THRESHOLD_Y);

}



