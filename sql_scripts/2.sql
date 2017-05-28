create database students_system default character SET 'utf8';

use students_system;

drop table specialties;
drop table teachers;
drop table groups;


create table specialties (
	specialty_id int not null auto_increment,
    specialty_name varchar(50) not null,
    primary key (specialty_id)
);

create table teachers (
	teacher_id int not null auto_increment,
    first_name varchar(25) not null,
    sur_name varchar(25) not null,
    last_name varchar(25) not null,
    date_of_birth date not null,
    sex char(1),
    primary key (teacher_id)
);

create table groups (
	group_id int not null auto_increment,
    group_name varchar(10) not null,
    curator_id int not null,
    speciality_id int not null,
    primary key (group_id)
);

create table students (
	student_id int not null auto_increment,
    first_name varchar(25) not null,
    sur_name varchar(25) not null,
    last_name varchar(25),
    date_of_birth date not null,
    sex char(1),
    group_id int not null,
    education_year int not null,
    primary key (student_id)
);

insert into teachers (first_name, sur_name, last_name, date_of_birth, sex) values 
('Анна', 'Южакова', 'Олексіївна', '1960-09-21', 'Ж'),
('Сергій', 'Яковлев', 'Володимирович', '1980-05-13', 'Ч'),
('Михайло', 'Савчук', 'Михайлович', '1947-05-19', 'Ч'),
('Алла', 'Шумська', 'Антонівна', '1950-11-15', 'Ж');

INSERT INTO `students_system`.`specialties` (`specialty_name`) VALUES ('Прикладна математика');
INSERT INTO `students_system`.`specialties` (`specialty_name`) VALUES ('Математичний аналіз');


insert into groups (group_name, curator_id, speciality_id) values
('ФІ-33', 1, 1),
('ФІ-31', 3, 2);

select * from groups g inner join teachers t on g.teacher_id = t.teacher_id
inner join specialties s on g.speciality_id = s.specialty_id;

INSERT INTO `students_system`.`students` (`first_name`, `sur_name`, `last_name`, `date_of_birth`, `sex`, `group_id`, `education_year`) VALUES ('Олександр', 'Богуцький', 'Миколайович', '1996-05-18', 'Ч', '1', '2013');
INSERT INTO `students_system`.`students` (`first_name`, `sur_name`, `last_name`, `date_of_birth`, `sex`, `group_id`, `education_year`) VALUES ('Іван', 'Свічкарьов', 'Володимирович', '1996-07-11', 'Ч', '1', '2013');
INSERT INTO `students_system`.`students` (`first_name`, `sur_name`, `last_name`, `date_of_birth`, `sex`, `group_id`, `education_year`) VALUES ('Олеся', 'Столова', 'Хирзнаївна', '1994-05-13', 'Ж', '2', '2013');
INSERT INTO `students_system`.`students` (`first_name`, `sur_name`, `last_name`, `date_of_birth`, `sex`, `group_id`, `education_year`) VALUES ('Антон', 'Карпець', 'Сергійович', '1995-10-11', 'Ч', '1', '2013');
INSERT INTO `students_system`.`students` (`first_name`, `sur_name`, `last_name`, `date_of_birth`, `sex`, `group_id`, `education_year`) VALUES ('Марина', 'Соловйова', 'Сергіївна', '1995-09-21', 'Ж', '2', '2013');
INSERT INTO `students_system`.`students` (`first_name`, `sur_name`, `last_name`, `date_of_birth`, `sex`, `group_id`, `education_year`) VALUES ('Максим', 'Хоменко', 'Русланович', '1995-09-23', 'Ч', '2', '2013');

use students_system;
select * from specialties;
select * from teachers;
select * from groups;
select * from students;


select * from students s inner join groups g on s.group_id = g.group_id;

