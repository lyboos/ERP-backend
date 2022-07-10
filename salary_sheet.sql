DROP TABLE IF EXISTS `salary_sheet`;
create table salary_sheet
(
    id              varchar(31)    not null
        primary key,
    staffId         int            null,
    name            varchar(31)    null,
    bank_account_id varchar(31)    null,
    rawSalary       decimal(10, 2) null,
    taxes           decimal(10, 2) null,
    finalSalary     decimal(10, 2) null,
    state           varchar(31)    null,
    isBonus         tinyint(1)     null,
    create_time     datetime       null,
    constraint salary_sheet_id_uindex
        unique (id)
);