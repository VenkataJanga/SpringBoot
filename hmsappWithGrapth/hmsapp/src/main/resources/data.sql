create database patientdb;

use  patientdb;


CREATE TABLE `patientdata` (
  `SourceSystem` varchar(25) NOT NULL,
  `PatientId` varchar(50) NOT NULL,
  `LastName` varchar(50) ,
  `PrimaryPhoneNumber` varchar(20) ,
  `PrimaryEmail` varchar(50) ,
  `BirthDate` date ,
  `HomeAddressLine1` varchar(50) ,
  `HomeAddressLine2` varchar(50) ,
  `HomeCity` varchar(50) ,
  `HomeState` varchar(50) ,
  `HomePostalCode` varchar(10) ,
  `FullName` varchar(50) ,
  `FirstName` varchar(50) ,
  `MiddleName` varchar(50) ,
  `race` varchar(50) ,
  `ethnicity` varchar(50) ,
  `gender` varchar(30) ,
  `UPDT_DT_TM` datetime ,
  `age` int ,
) ;



insert into patientdata (SourceSystem,PatientId,LastName,PrimaryPhoneNumber,PrimaryEmail,BirthDate,HomeAddressLine1,HomeAddressLine2,HomeCity,HomeState,HomePostalCode,FullName,FirstName,MiddleName,race,ethnicity,gender)
values('Web','1','Sharma','8655312612','rahul@mail.com','2008-7-04','address1','address2','Mumbai','Maharashtra','425620','Rahul Sharma','Rahul','M','African','Black','Male');
 
 insert into patientdata (SourceSystem,PatientId,LastName,PrimaryPhoneNumber,PrimaryEmail,BirthDate,HomeAddressLine1,HomeAddressLine2,HomeCity,HomeState,HomePostalCode,FullName,FirstName,MiddleName,race,ethnicity,gender)
values('Web','2','Sharma','9875641235','Sharma@mail.com','1980-7-04','address1','address2','Mumbai','Maharashtra','425620','Neha Sharma','Neha','M','American','American','Female');
 
insert into patientdata (SourceSystem,PatientId,LastName,PrimaryPhoneNumber,PrimaryEmail,BirthDate,HomeAddressLine1,HomeAddressLine2,HomeCity,HomeState,HomePostalCode,FullName,FirstName,MiddleName,race,ethnicity,gender)
values('Web','3','Sahu','8655312612','rahul@mail.com','2008-7-04','address1','address2','Mumbai','Maharashtra','425620','nilesh Sahu','nilesh','M','Oceanian','White','Male');

insert into patientdata (SourceSystem,PatientId,LastName,PrimaryPhoneNumber,PrimaryEmail,BirthDate,HomeAddressLine1,HomeAddressLine2,HomeCity,HomeState,HomePostalCode,FullName,FirstName,MiddleName,race,ethnicity,gender)
values('Web','4','Sharma','8655312612','kavita@mail.com','1975-7-04','address1','address2','Mumbai','Maharashtra','425620','kavita Sharma','kavita','M','African','Black','Female');

insert into patientdata (SourceSystem,PatientId,LastName,PrimaryPhoneNumber,PrimaryEmail,BirthDate,HomeAddressLine1,HomeAddressLine2,HomeCity,HomeState,HomePostalCode,FullName,FirstName,MiddleName,race,ethnicity,gender)
values('Web','5','patil','8655381112','Rajesh@mail.com','1995-7-04','address1','address2','Mumbai','Maharashtra','425620','Rajesh patil','Rajesh','M','Asian','Asian','Male');



insert into patientdata (SourceSystem,PatientId,LastName,PrimaryPhoneNumber,PrimaryEmail,BirthDate,HomeAddressLine1,HomeAddressLine2,HomeCity,HomeState,HomePostalCode,FullName,FirstName,MiddleName,race,ethnicity,gender)
values('Web','6','Khade','8655992612','rutuja@mail.com','2015-7-04','address1','address2','Mumbai','Maharashtra','425620','rutuja Khade','rutuja','M','Asian','Asian','Female');



insert into patientdata (SourceSystem,PatientId,LastName,PrimaryPhoneNumber,PrimaryEmail,BirthDate,HomeAddressLine1,HomeAddressLine2,HomeCity,HomeState,HomePostalCode,FullName,FirstName,MiddleName,race,ethnicity,gender)
values('Web','7','Sharma','8655312552','megha@mail.com','1950-7-04','address1','address2','Mumbai','Maharashtra','425620','megha Sharma','megha','M','African','Black','Female');

insert into patientdata (SourceSystem,PatientId,LastName,PrimaryPhoneNumber,PrimaryEmail,BirthDate,HomeAddressLine1,HomeAddressLine2,HomeCity,HomeState,HomePostalCode,FullName,FirstName,MiddleName,race,ethnicity,gender)
values('Web','8','Pandey','8655312612','sujay@mail.com','1956-7-04','address1','address2','Mumbai','Maharashtra','425620','sujay Pandey','sujay','M','African','Black','Male');
  
  
  
 
  SET SQL_SAFE_UPDATES = 0;


UPDATE patientdata a, 
      ( SELECT PatientId, DATE_FORMAT(NOW(), '%Y') - DATE_FORMAT(`BirthDate`, '%Y') - (DATE_FORMAT(NOW(), '00-%m-%d') < DATE_FORMAT(`BirthDate`, '00-%m-%d')) AS age
        FROM `patientdata`) b
SET a.age = b.age
WHERE a.PatientId = b.PatientId;