use  patientdatabase;


CREATE TABLE `patientdata` (
  `patient_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `address` varchar(45) NOT NULL,
  `gender` varchar(45) NOT NULL,
  `disease` varchar(45) NOT NULL,
  `email_id` varchar(45) NOT NULL,
  `contact_number` varchar(45) NOT NULL,
  `age` int NOT NULL,
  PRIMARY KEY (`patient_id`)
) ;



insert into patientdata (address,contact_number,disease,email_id,gender,name,age)
values('Vasai','8655312612','Fever','rahul@mail.com','Male','Rahul',15);

insert into patientdata(address,contact_number,disease,email_id,gender,name,age)
values('Kalyan',9875641235,'Cold','neha@mail.com','Female','Neha',40);
insert into patientdata(address,contact_number,disease,email_id,gender,name,age)
values('Thane',9568425613,'Head Ache','nilesh@mail.com','Male','Nilesh',55);
insert into patientdata(address,contact_number,disease,email_id,gender,name,age)
values('Thane',8659754868,'Malaria','kavita@mail.com','Female','Kavita',80);
insert into patientdata(address,contact_number,disease,email_id,gender,name,age)
values('Mahim',9932541645,'Cold','rajesh@mail.com','Male','Rajesh',90);
insert into patientdata(address,contact_number,disease,email_id,gender,name,age)
values('Kandivali',9915243686,'Heart Problem','atul@mail.com','Male','Atul',17);
insert into patientdata(address,contact_number,disease,email_id,gender,name,age)
values('Dombivali',9685741526,'Dengue','rutuja@mail.com','Female','Rutuja',20);
insert into patientdata(address,contact_number,disease,email_id,gender,name,age)
values('Thane',9586742563,'Kidney Stone','megha@mail.com','Female','Megha',30);
insert into patientdata(address,contact_number,disease,email_id,gender,name,age)
values('Dombivali',9856785496,'Cough','sujay@mail.com','Male','Sujay',35);

insert into patientdata(address,contact_number,disease,email_id,gender,name,age)
values('Thane',6598549836,'Corona','amol@mail.com','Male','amol',31);
insert into patientdata(address,contact_number,disease,email_id,gender,name,age)
values('Kandivali',6598325465,'Corona','sagar@mail.com','Male','sagar',32);
insert into patientdata(address,contact_number,disease,email_id,gender,name,age)
values('Kalyan',6598125465,'Corona','karina@mail.com','Female','karina',33);