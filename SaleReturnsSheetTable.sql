-- ----------------------------
-- Table structure for sale_returns_sheet
-- ----------------------------
DROP TABLE IF EXISTS `sale_returns_sheet`;
CREATE TABLE `sale_returns_sheet`  (
                                           `id` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '销售退货单id',
                                           `sale_sheet_id` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '关联的销售单id',
                                           `salesman` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '业务员',
                                           `operator` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作员',
                                           `state` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '单据状态',
                                           `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
                                           `raw_total_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '折让前总额',
                                           `discount` decimal(10, 2) NULL DEFAULT NULL COMMENT '折让',
                                           `voucher_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '使用代金券总额',
                                           `final_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '折让后总额（似乎包括代金券的减价）',
                                           `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;