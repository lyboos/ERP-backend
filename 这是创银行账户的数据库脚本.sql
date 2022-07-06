use seec_erp;
-- ----------------------------
-- Table structure for bank_account
-- ----------------------------
DROP TABLE IF EXISTS `BankAccount`;
CREATE TABLE `BankAccount`  (
                                       `id` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'id',
                                       `name` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'name',
                                       `remaining_sum` decimal(10, 2) NULL DEFAULT NULL COMMENT '账户余额'

) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

alter table bankaccount
	add constraint bankaccount_pk
		primary key (id);

alter table bankaccount modify id INT not null comment 'id';

INSERT INTO bankaccount (id, name, remaining_sum)
values (1,'LYBOOS',1200);

INSERT INTO bankaccount (id, name, remaining_sum)
values (2,'hjb',220000);

alter table bankaccount modify id varchar(31) not null comment 'id';