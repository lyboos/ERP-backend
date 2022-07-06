/*本文件提供HR相关表的创建sql语句*/
/*checkin打卡记录表*/
create table checkin
(
    name varchar(31) not null,
    date date        not null,
    primary key (name, date)
);

