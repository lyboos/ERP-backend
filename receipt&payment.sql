/*这里包括了receipt_sheet&receipt_sheet_content&payment_sheet&payment_sheet_content
  共四张表的创建sql语句
*/
/*receipt_sheet*/
create table receipt_sheet
(
    id           varchar(31)    not null
        primary key,
    supplier_id  int            null,
    operator     varchar(255)   null,
    total_amount decimal(10, 2) null,
    state        varchar(31)    null,
    create_time  datetime       null,
    constraint Receipt_sheet_id_uindex
        unique (id)
)
    comment '收款单';

/*receipt_sheet_content*/
create table receipt_sheet_content
(
    id              int            not null
        primary key,
    sheet_id        varchar(31)    not null,
    bank_account_id varchar(31)    not null,
    amount          decimal(10, 2) null,
    remark          varchar(31)    null,
    constraint receipt_sheet_content_id_uindex
        unique (id),
    constraint receipt_sheet_content_receipt_sheet_id_fk
        foreign key (sheet_id) references receipt_sheet (id)
)
    comment '转账单';

/*payment_sheet*/
create table payment_sheet
(
    id           varchar(31)    not null
        primary key,
    supplier_id  int            null,
    operator     varchar(255)   null,
    total_amount decimal(10, 2) null,
    state        varchar(31)    null,
    create_time  datetime       null,
    constraint payment_sheet_id_uindex
        unique (id)
)
    comment '付款单';

/*payment_sheet_content*/
create table payment_sheet_content
(
    id              int            not null
        primary key,
    sheet_id        varchar(31)    not null,
    bank_account_id varchar(31)    null,
    remark          varchar(31)    null,
    amount          decimal(10, 2) null,
    constraint payment_sheet_content_id_uindex
        unique (id),
    constraint payment_sheet_content_payment_sheet_id_fk
        foreign key (sheet_id) references payment_sheet (id)
);