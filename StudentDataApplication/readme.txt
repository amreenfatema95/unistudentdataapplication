%% readme.txt

Name:-         		Student Data Application
ReadMe Author:- 	Amreen Fatima S Surani
Product Authors:-      	Amreen Fatima S Surani
Date:-         		March 21, 2015

(1) INTRODUCTION
-----------------
Student Data Application is a software currently intended for use by the staff at the Kings College London. This software is designed to help users extract student information in an interactive form. The key functionalities of this application include:
- Loading a list of students from the server
- Loading results for several modules and assessments in multiple csv formats.
- Load csv file to provide a lookup of student numbers with their anonymous marking codes.
- Generate information panel per student from different parts of the system.
- Generate a scatter plot to compare students performance across assessments.
- Print student details along with their results in a single pdf.

(2) External Libraries:
-----------------------
The following libraries have been used to deliver this application:

- StudentData.jar
- opencsv-3.3.jar
- jfreechart-1.0.19.jar
- jcommon-1.0.23.jar
- itextpdf-5.5.5.jar

(3) USAGE
---------

The application can be initiated using a command line interface or through an IDE.

3.1 Usage through an IDE
------------------------

If an IDE like eclipse is being used then it would be required to run the MainApp.java file. 

3.2 Usage through a command line interface
------------------------------------------

If the application is being initiated through a command line interface, then the following syntax can be used:

java -cp bin:lib/StudentData.jar:lib/opencsv-3.3.jar:lib/jfreechart-1.0.19.jar:lib/jcommon-1.0.23.jar:lib/itextpdf-5.5.5.jar studentDataApp.MainApp

The following assumptions apply:
- All class files are in the bin folder, 
- All external jars are in lib folder, and
- Package is called studentDataApp

-----------------
End of README.txt
-----------------

