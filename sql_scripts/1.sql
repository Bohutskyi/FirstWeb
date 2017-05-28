create database students default character SET 'utf8';

USE students;

create table groups (
	group_id int not null auto_increment,
    groupName varchar(10) not null,
    curator varchar(30) not null,
    speciality varchar(50) not null,
    primary key (group_id)
);

Use students;
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

use students;
insert into groups (groupName, curator, speciality)
values ('ФІ-33', 'Южакова', 'Математичний аналіз'),
('ФФ-31', 'Шумська', 'Лінійний аналіз');

select * from groups;


insert into students (first_name, sur_name, last_name, sex, date_of_birth, group_id, education_year)
values
('Олександ', 'Богуцький', 'Миколайович', 'Ч', '1996-05-18', 1, 2013),
('Олеся', 'Столова', 'Валеріївна', 'Ж', '1994-05-12', 1, 2013),
('Іван', 'Свічкарьов', 'Володимирович', 'Ч', '1996-07-11', 2, 2013),
('Антон', 'Карпець', 'Сергійович', 'Ч', '1995-11-09', 2, 2013);



insert into students (first_name, sur_name, last_name, sex, date_of_birth, group_id, education_year)
values
('Марина', 'Соловйова', 'Сергіївна', 'Ж', '1995-09-21', 1, 2013),
('Оксана', 'Науменко', 'Валеріївна', 'Ж', '1995-10-23', 1, 2013),
('Максим', 'Хоменко', 'Русланович', 'Ч', '1995-09-24', 2, 2013),
('Андрій', 'Турчин', 'Якович', 'Ч', '1995-04-22', 2, 2013);

use students;
select * from groups;
select * from students;

select * from groups g inner join students s on g.group_id = s.group_id;

SHOW VARIABLES LIKE 'character_set_client';

select now();