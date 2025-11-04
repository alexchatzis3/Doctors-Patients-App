\# 🏥 DPApp - Doctors \& Patients Management App



A \*\*Java Swing\*\* application for managing doctors, patients, and their relationships.  

It uses DAO, Service, and DTO layers, includes validation, and has unit tests.



---



\## ✨ Features



\- \*\*🩺 Doctors Management\*\*

&nbsp; - Insert, Update, Delete doctors

&nbsp; - Search by lastname

\- \*\*👨‍⚕️ Patients Management\*\*

&nbsp; - Insert, Update, Delete patients

&nbsp; - Search by lastname

\- \*\*🔗 Doctor-Patient Links\*\*

&nbsp; - Assign patients to doctors

&nbsp; - Remove assignments

&nbsp; - View patients per doctor

\- \*\*✅ Validation\*\*

&nbsp; - Ensures firstname and lastname are not empty when inserting/updating

\- \*\*💾 Database\*\*

&nbsp; - MySQL database: `dpdb`

&nbsp; - Tables: `doctors`, `patients`, `doctorspatients`

&nbsp; - DAO layer handles CRUD operations

&nbsp; - Make sure database and tables exist before running the app



---



\## 🛠 Technologies



\- Java 17+

\- Swing for GUI

\- JDBC for database connection

\- MySQL Workbench

\- JUnit 5 for testing

\- Maven



---



\## 🗄 Database Setup



1\. Open MySQL Workbench.

2\. Create the database:



```sql

CREATE DATABASE dpdb;

USE dpdb;

```



3\. Create tables:



```sql

CREATE TABLE doctors (

&nbsp;   id INT AUTO\_INCREMENT PRIMARY KEY,

&nbsp;   firstname VARCHAR(45) NOT NULL,

&nbsp;   lastname VARCHAR(45) NOT NULL

);



CREATE TABLE patients (

&nbsp;   id INT AUTO\_INCREMENT PRIMARY KEY,

&nbsp;   firstname VARCHAR(45) NOT NULL,

&nbsp;   lastname VARCHAR(45) NOT NULL

);



CREATE TABLE doctorspatients (

&nbsp;   d\_id INT NOT NULL,

&nbsp;   p\_id INT NOT NULL,

&nbsp;   PRIMARY KEY (d\_id, p\_id),

&nbsp;   FOREIGN KEY (d\_id) REFERENCES doctors(id) ON DELETE CASCADE,

&nbsp;   FOREIGN KEY (p\_id) REFERENCES patients(id) ON DELETE CASCADE

);

```



4\. Update database credentials in `DBUtil.java` if necessary:



```java

private static final String URL = "jdbc:mysql://localhost:3306/dpdb";

private static final String USER = "root";

private static final String PASSWORD = "yourpassword";

```



---



\## ▶️ How to Run



1\. Open the project in your favorite IDE (IntelliJ, Eclipse, NetBeans).  

2\. Make sure the database `dpdb` exists with the required tables.  

3\. Run `Main.java`.  

4\. Use the GUI to manage doctors, patients, and doctor-patient relationships.



---



\## 🧪 Testing



\- Unit tests use \*\*JUnit 5\*\*.  

\- Test classes are in `src/test/java`.  

\- Run all tests to ensure DAOs and Services work correctly.



---



\## 🌐 GitHub Setup



1\. Initialize git in your project folder:



```bash

git init

git add .

git commit -m "Initial commit with project files and README"

```



2\. Create a repository on GitHub (e.g., `dpapp`).  

3\. Add remote and push:



```bash

git remote add origin https://github.com/yourusername/dpapp.git

git branch -M main

git push -u origin main

```



---



💡 \*\*Tip:\*\* Keep your `DBUtil.java` credentials correct, otherwise the app will not connect to MySQL.



