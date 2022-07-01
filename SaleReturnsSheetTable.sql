use seec_erp;
-- ----------------------------
-- Table structure for sale_returns_sheet
-- ----------------------------
DROP TABLE IF EXISTS `sale_returns_sheet`;
CREATE TABLE `sale_returns_sheet`  (
                                           `id` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '销售退货单id',
                                           `sale_sheet_id` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '关联的销售单id',
                                           `salesman` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '业务员',
                                           `operator` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作员',
                                           `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
                                           `raw_total_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '折让前总额',
                                           `discount` decimal(10, 2) NULL DEFAULT NULL COMMENT '折让',
                                           `voucher_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '使用代金券总额',
                                           `final_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '折让后总额（似乎包括代金券的减价）',
                                           `state` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '单据状态',
                                           `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sale_returns_sheet_content
-- ----------------------------
DROP TABLE IF EXISTS `sale_returns_sheet_content`;
CREATE TABLE `sale_returns_sheet_content`  (
                                                   `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
                                                   `sale_returns_sheet_id` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '销售退货单id',
                                                   `pid` char(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品id',
                                                   `quantity` int(11) NULL DEFAULT NULL COMMENT '数量',
                                                   `total_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '该商品的总金额',
                                                   `unit_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '该商品的单价',
                                                   `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
                                                   PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;