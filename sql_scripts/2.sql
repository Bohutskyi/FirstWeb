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

use students_system;
create view main_view
as
select * from groups g inner join teachers t on g.teacher_id = t.teacher_id inner join specialties s on g.speciality_id = s.specialty_id;



#getGroups()
use students_system;
select g.group_id, g.group_name, t.sur_name, s.specialty_name from groups g inner join teachers t on g.teacher_id = t.teacher_id inner join specialties s on g.speciality_id = s.specialty_id;

#getStudents()
use students_system;
select * from students order by sur_name, first_name;

#getStudentsFromGroup(...)
use students_system;
select * from students where group_id = 1 and education_year = 2013 order by sur_name, first_name;

#moveStudentsToGroup(...)
use students_system;
update students set group_id = 1, education_year = 2010
where group_id = 2 and education_year = 2009;

#removeStudentsFromGroup(...)
use students_system;
delete from students where group_id = 1 and education_year = 2010;

#insertStudent(...)
use students_system;
INSERT INTO `students_system`.`students` (`first_name`, `sur_name`, `last_name`, `date_of_birth`, `sex`, `group_id`, `education_year`) VALUES ('Test', 'T', 'TT', '1990-06-28', 'Ч', '1', '2007');

#updateStudent(...)
use students_system;
update students set first_name = '123', last_name = '543', sur_name = '123', sex = 'Ч', date_of_birth = '1996-03-05', group_id = 1, education_year = 2007
where student_id = 13;

#deleteStudent(...)
use students_system;
delete from students where student_id = 13;

#getTeachers()
use students_system;
select * from teachers;

#createGroup(...)
use students_system;
insert into `students_system`.`groups` (`group_name`, `teacher_id`, `speciality_id`) values ('ФЕ-93', '4', '1');

delete from groups where group_id > 3;
select * from groups;



use students_system;
select * from groups;
select * from groups order by group_id desc limit 1;

select * from teachers where teacher_id = 1;
select GROUP_CONCAT(sur_name, ' ',  first_name, ' ', last_name) from teachers where teacher_id = 1;
