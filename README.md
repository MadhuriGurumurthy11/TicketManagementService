BUILING AND EXECUTION OF MAIN PROGRAM ():

1. Open Command Prompt in Windows
2. Change to the directory where the project is saved (This project is called "TicketManagement"), enter the command
mvn clean install

3. To compile, enter:
mvn compile

4. To run the main program, enter the following command

mvn exec:java -Dexec.mainClass="TicketManagement.service.TicketManagement" -q

(-q to avoid maven logs)


//------------------------------------------------------------------
//------------------------------------------------------------------

INPUT TO THE PROGRAM:

When you run the program, the following options are displayed:

Welcome to reservation system.
Press 1 to find the total number of seats available.
Press 2 to find and hold the seats.
Press 5 to quit.

//------------------------------------------------------------------

When pressed 1:

The program asks the following question and waits for user to respond:

Do you wish to find out the number of available seats at a particular level?
Yes or no ?

If input is "no", the program displays the total number of available seats in the whole venue.
If input is "yes", it asks for the level number out of the options:

1 for Orchestra
2 for Main
3 for Balcony1
4 for Balcony2

Based on the level entered, respective total number of available seats is displayed.

//------------------------------------------------------------------

When pressed 2:

The program asks the following questions:

How many seats you want to book? <Enter an integer number of seats to be booked>

Enter the email id. <Enter a valid email id>

Do you have the preference of the level? yes or no? 
<If you do not have a specific level in demand, type "no". 
If you do have a preference for specific levels, type "yes". The program will further ask to enter level numbers>

Next, A suitable reservation is displayed telling the user about the row number, seats etc.

If the user is happy with the reservation, then press 3 to reserve the held seats.
If the user is not happy with the reservation, then press 4 to cancel the held seats.
The user is given 60 seconds to reserve/cancel seats, after which the held seats are cancelled by default.

//------------------------------------------------------------------

When pressed 5:

Program quits the execution.

//------------------------------------------------------------------
//------------------------------------------------------------------

BUILING AND EXECUTION OF Unit Tests:

To execute all the tests:

mvn test

//------------------------------------------------------------------

To excute individual test class:

mvn test -Dtest=<testClassname>

//------------------------------------------------------------------
//------------------------------------------------------------------

ASSUMPTIONS:

1. Database is not refreshed after every quit.
2. While canceling the held seats, it is assumed that the seatHeld object is a valid one.
3. If the choice of levels entered is incorrect (i.e level > 4 or level < 1), it is assumed to be set to default values (i.e. maximum and minimum).

//-------------------------------------------------------------------
//-------------------------------------------------------------------

DATABASE:

SQL is used.

Tables are LEVEL_INFO, SEAT_AVAILABILITY and RESERVATIONS.

Following are the queries used to create the database required to begin execution of Ticket Management program.

CREATE TABLE LEVEL_INFO(
   LEVEL_ID INT PRIMARY KEY     NOT NULL,
   LEVEL_NAME           TEXT    NOT NULL,
   PRICE            INT     NOT NULL,
   ROWS    INT     NOT NULL,
  SEATS INT NOT NULL,
  TOTAL_SEATS  INT NOT NULL,
  TOTAL_AVAILABLE_SEATS INT NOT NULL
);
 
INSERT INTO LEVEL_INFO (LEVEL_ID,LEVEL_NAME,PRICE,ROWS,SEATS,TOTAL_SEATS,TOTAL_AVAILABLE_SEATS)
VALUES (1,"Orchestra",100,25 ,50,1250,1250);

INSERT INTO LEVEL_INFO (LEVEL_ID,LEVEL_NAME,PRICE,ROWS,SEATS,TOTAL_SEATS,TOTAL_AVAILABLE_SEATS)
VALUES (2,"Main",75,20,100,2000,2000);

INSERT INTO LEVEL_INFO (LEVEL_ID,LEVEL_NAME,PRICE,ROWS,SEATS,TOTAL_SEATS,TOTAL_AVAILABLE_SEATS)
VALUES (3,"Balcony1",50,15,100,1500,1500);


INSERT INTO LEVEL_INFO (LEVEL_ID,LEVEL_NAME,PRICE,ROWS,SEATS,TOTAL_SEATS,TOTAL_AVAILABLE_SEATS)
VALUES (4,"Balcony2",40,15,100,1500,1500);

//-----------------------------------------------------------------------

CREATE TABLE RESERVATIONS(
   LEVEL_ID INTEGER NOT NULL,
   SEAT_HOLD_ID INTEGER PRIMARY KEY,
   ROW_NO    INTEGER     NOT NULL,
  NO_OF_SEATS INTEGER NOT NULL,
  SEAT_NO TEXT NOT_NULL,
  EMAIL TEXT NOT_NULL,
  STATUS TEXT NOT NULL,
  CONFIRMATION_CODE TEXT,
  FOREIGN KEY (LEVEL_ID) REFERENCES LEVEL_INFO(LEVEL_ID)
);

//-------------------------------------------------------------------------

CREATE TABLE SEAT_AVAILABILITY(
   LEVEL_ID INT NOT NULL,
   AVAILABLE_START_ROW INT  NOT NULL,
   AVAILABLE_START_SEAT_NO    INT     NOT NULL,
  FOREIGN KEY (LEVEL_ID) REFERENCES LEVEL_INFO(LEVEL_ID)
);


INSERT INTO SEAT_AVAILABILITY (LEVEL_ID,AVAILABLE_START_ROW,AVAILABLE_START_SEAT_NO)
VALUES (1,1,1);

INSERT INTO SEAT_AVAILABILITY (LEVEL_ID,AVAILABLE_START_ROW,AVAILABLE_START_SEAT_NO)
VALUES (2,1,1);

INSERT INTO SEAT_AVAILABILITY (LEVEL_ID,AVAILABLE_START_ROW,AVAILABLE_START_SEAT_NO)
VALUES (3,1,1);

INSERT INTO SEAT_AVAILABILITY (LEVEL_ID,AVAILABLE_START_ROW,AVAILABLE_START_SEAT_NO)
VALUES (4,1,1);




