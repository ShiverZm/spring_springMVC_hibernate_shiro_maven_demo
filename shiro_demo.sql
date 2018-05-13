/*
 Navicat Premium Data Transfer

 Source Server         : 111.230.236.152
 Source Server Type    : MySQL
 Source Server Version : 50717
 Source Host           : 111.230.236.152:3306
 Source Schema         : shiro_demo

 Target Server Type    : MySQL
 Target Server Version : 50717
 File Encoding         : 65001

 Date: 13/05/2018 23:29:30
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`  (
  `ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `permissionCode` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `permissionName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES ('402881e56340a0fe016340a107880000', '200001', 'admin');
INSERT INTO `permission` VALUES ('402881e56340a126016340a12fde0000', '200002', 'managerCart');
INSERT INTO `permission` VALUES ('402881e56340a151016340a15b9c0000', '200003', 'add');
INSERT INTO `permission` VALUES ('402881e56340a17c016340a186350000', '200004', 'delete');
INSERT INTO `permission` VALUES ('402881e56340a1a4016340a1ae250000', '200005', 'query');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `roleCode` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `roleName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('402881e56340a04f016340a059210000', '100001', 'admin');
INSERT INTO `role` VALUES ('402881e56340a078016340a082d90000', '100002', 'customer');

-- ----------------------------
-- Table structure for rolepermission
-- ----------------------------
DROP TABLE IF EXISTS `rolepermission`;
CREATE TABLE `rolepermission`  (
  `ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `permission_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `role_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE,
  INDEX `FK_sso4kx05y6klpbhf2bif56hwm`(`permission_id`) USING BTREE,
  INDEX `FK_sx47ge56exdwr6sxr0srr58eg`(`role_id`) USING BTREE,
  CONSTRAINT `FK_sso4kx05y6klpbhf2bif56hwm` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_sx47ge56exdwr6sxr0srr58eg` FOREIGN KEY (`role_id`) REFERENCES `role` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rolepermission
-- ----------------------------
INSERT INTO `rolepermission` VALUES ('402881e56340a21a016340a223650000', '402881e56340a0fe016340a107880000', '402881e56340a04f016340a059210000');
INSERT INTO `rolepermission` VALUES ('402881e56340a247016340a250f30000', '402881e56340a126016340a12fde0000', '402881e56340a078016340a082d90000');
INSERT INTO `rolepermission` VALUES ('402881e56340a274016340a27d920000', '402881e56340a151016340a15b9c0000', '402881e56340a078016340a082d90000');
INSERT INTO `rolepermission` VALUES ('402881e56340a29f016340a2a9180000', '402881e56340a17c016340a186350000', '402881e56340a078016340a082d90000');
INSERT INTO `rolepermission` VALUES ('402881e56340a2c7016340a2d0770000', '402881e56340a1a4016340a1ae250000', '402881e56340a078016340a082d90000');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `createTime` datetime(0) NULL DEFAULT NULL,
  `mailbox` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `phoneNum` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `salt` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sex` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `status` smallint(6) NULL DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('402881e563409c610163409cdf4f0000', '重庆', '2018-05-09 00:36:53', '953@qq.com', 'e0728777c9e220e0f3c2e7ca69171f82', '12345678901', 'c2b1243cfb86e4155fa55d0ccab214cc', '男', 1, 'admin');
INSERT INTO `user` VALUES ('402881e56359fef7016359ff01eb0000', '重庆', '2018-05-13 22:54:34', '123456@qq.com', '2000180ebd55dd472d5f01aa26773d34', '98745612301', 'ce59a2f557c2bee3483d651faaf64d94', '女', 1, '123456');

-- ----------------------------
-- Table structure for userrole
-- ----------------------------
DROP TABLE IF EXISTS `userrole`;
CREATE TABLE `userrole`  (
  `ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `role_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE,
  INDEX `FK_l2vc7ef3hyhxy2094ldyf762s`(`role_id`) USING BTREE,
  INDEX `FK_hyddjyserbkrnpbveirrw7phm`(`user_id`) USING BTREE,
  CONSTRAINT `FK_hyddjyserbkrnpbveirrw7phm` FOREIGN KEY (`user_id`) REFERENCES `user` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_l2vc7ef3hyhxy2094ldyf762s` FOREIGN KEY (`role_id`) REFERENCES `role` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of userrole
-- ----------------------------
INSERT INTO `userrole` VALUES ('402881e563409c610163409ce1e80001', '402881e56340a04f016340a059210000', '402881e563409c610163409cdf4f0000');
INSERT INTO `userrole` VALUES ('402881e563409f5c0163409f66220000', '402881e56340a078016340a082d90000', '402881e56359fef7016359ff01eb0000');

SET FOREIGN_KEY_CHECKS = 1;
