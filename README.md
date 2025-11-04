DPApp - Doctors \& Patients Management App



A Java Swing application for managing doctors, patients, and their relationships.
It uses DAO, Service, and DTO layers, includes validation, and has unit tests.



Features:

* Doctors Management:

  * Insert, Update, Delete doctors
  * Search by lastname

* Patients Management:

  * Insert, Update, Delete patients
  * Search by lastname

* Doctor-Patient Links:

  * Assign patients to doctors
  * Remove assignments
  * View patients per doctor

* Validation:

  * Ensures firstname and lastname are not empty when inserting/updating

* Database:

  * MySQL database: dpdb
  * Tables: doctors, patients, doctorspatients
  * DAO layer handles CRUD operations
  * Make sure database and tables exist before running the app



Technologies:

* Java 17+
* Swing for GUI
* JDBC for database connection
* MySQL Workbench
* JUnit 5 for testing
* Maven



Database Setup:

1. Open MySQL Workbench.
2. Create the database:
   CREATE DATABASE dpdb;
   USE dpdb;
3. Create tables:

   CREATE TABLE doctors (
   id INT AUTO\_INCREMENT PRIMARY KEY,
   firstname VARCHAR(45) NOT NULL,
   lastname VARCHAR(45) NOT NULL
   );

   CREATE TABLE patients (
   id INT AUTO\_INCREMENT PRIMARY KEY,
   firstname VARCHAR(45) NOT NULL,
   lastname VARCHAR(45) NOT NULL
   );

   CREATE TABLE doctorspatients (
   d\_id INT NOT NULL,
   p\_id INT NOT NULL,
   PRIMARY KEY (d\_id, p\_id),
   FOREIGN KEY (d\_id) REFERENCES doctors(id) ON DELETE CASCADE,
   FOREIGN KEY (p\_id) REFERENCES patients(id) ON DELETE CASCADE
   );

4. Update database credentials in DBUtil.java if necessary:
   private static final String URL = "jdbc:mysql://localhost:3306/dpdb";
   private static final String USER = "root";
   private static final String PASSWORD = "yourpassword";

   

   How to Run:

1. Open the project in your favorite IDE (IntelliJ, Eclipse, NetBeans).
2. Make sure the database dpdb exists with the required tables.
3. Run Main.java.
4. Use the GUI to manage doctors, patients, and doctor-patient relationships.

   Testing:

* Unit tests use JUnit 5.
* Test classes are in src/test/java.
* Run all tests to ensure DAOs and Services work correctly.



  GitHub Setup:

1. Initialize git in your project folder:
   git init
   git add .
   git commit -m "Initial commit"
2. Create a repository on GitHub (e.g., dpapp).
3. Add remote and push:
   git remote add origin https://github.com/yourusername/dpapp.git
   git branch -M main
   git push -u origin main
