# SMERGY: An Arduino based Smart Cycling Powermeter Pedal with Andriod App


![image](https://github.com/JimiZhou/SMERGY-Smart-Pedal/blob/master/smergy-prototype.jpg)


## Description
- SMERGY stands for **SM**ART **E**NE**GRY**.
- This project aims at mesuring user's power while cycling, and the smart pedal is capable of sending data to an Android application in real time.

## Design
- Based on four load cells we use HX711 with a brige circuit to measure the force applied to the pedal. See https://circuitjournal.com/50kg-load-cells-with-HX711 for details.
![image](https://github.com/JimiZhou/SMERGY-Smart-Pedal/blob/master/Pedal.png)

- Here's the prototype of our pedal. All the electronic parts fit perfectly in the gaps of pedal and they'll be covered by load cells with a flat wood on top of them.
![image](https://github.com/JimiZhou/SMERGY-Smart-Pedal/blob/master/smergy.jpg)
- We choose *Arduino Nano* as it's small enough to fit the limited space of pedal, and a *Hall sensor* to measure the time spent for the user to complete one pedal cycle. Besides a *MPU6050* is used to acquire more gyro and acclero info for future use. A *600mAh LiPo battery* is used to power all the circuits. The commonly used *HC-06* is used to communicate with Android application.

## Power Calculation
-To simplify the calculation we suppose the pedal force is perpendicular to the pedal surface, and average the power while pedaling up and down:
![image](https://github.com/JimiZhou/SMERGY-Smart-Pedal/blob/master/PowerMeasurement.jpg)

## Android app screenshot
![image](https://github.com/JimiZhou/SMERGY-Smart-Pedal/blob/master/Screenshots_Sprint2.jpg)
