/*本文件提供HR相关表的创建sql语句*/
/*checkin打卡记录表*/
create table checkin
(
    name varchar(31) not null,
    date date        not null,
    primary key (name, date)
);

/*staff_info员工信息表*/
create table staff_info
(
    id                           int auto_increment
        primary key,
    name                         varchar(31)    not null,
    gender                       varchar(4)     not null,
    birthday                     date           null,
    phone                        varchar(15)    null,
    role                         varchar(31)    null,
    base_salary                  decimal(10, 2) null,
    commission                   decimal(10, 2) null,
    level                        int            null,
    payment_calculating_strategy varchar(31)    null,
    payment_schedule_strategy    varchar(31)    null,
    bank_account_id              varchar(31)    not null,
    constraint staff_info_id_uindex
        unique (id),
    constraint staff_info_name_uindex
        unique (name)
)
    comment '员工信息表，包括id、姓名、性别、出生日期、手机、工作岗位、基本工资、
提成、岗位级别、薪资计算方式、薪资方法方式、工资卡账户。';


