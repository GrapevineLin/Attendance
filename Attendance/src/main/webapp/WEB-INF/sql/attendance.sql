/*
 Navicat Premium Data Transfer

 Source Server         : Luo
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : 120.77.209.0:3306
 Source Schema         : attendance

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 08/07/2019 08:44:13
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for classes
-- ----------------------------
DROP TABLE IF EXISTS `classes`;
CREATE TABLE `classes`  (
  `classId` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '安排班次id',
  `classCode` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '班次编码',
  `className` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '班次名称',
  `am` time(6) NOT NULL COMMENT '早上上班时间',
  `pm` time(6) NOT NULL COMMENT '下午下班时间',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`classId`) USING BTREE,
  UNIQUE INDEX `c_code`(`classCode`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of classes
-- ----------------------------
INSERT INTO `classes` VALUES (1, 'EBD', '二卡制11', '09:00:00.000000', '18:00:00.000000', '');

-- ----------------------------
-- Table structure for department
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department`  (
  `depId` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '部门ID',
  `depCode` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '部门编码',
  `depName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门名称',
  `depHead` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门负责人',
  `depResp` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门职责',
  `supDepId` bigint(10) NULL DEFAULT NULL COMMENT '上级部门ID',
  PRIMARY KEY (`depId`) USING BTREE,
  UNIQUE INDEX `dep`(`depCode`) USING BTREE,
  INDEX `depName`(`depName`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of department
-- ----------------------------
INSERT INTO `department` VALUES (1, 'ZCS', '总裁室', '王二狗', '管理公司', NULL);
INSERT INTO `department` VALUES (2, 'CWB', '财务部', '张三', '管理公司财务', 1);
INSERT INTO `department` VALUES (3, 'HR', '人力资源部', '王二狗', '管理人力资源', 1);

-- ----------------------------
-- Table structure for employee
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee`  (
  `empId` bigint(10) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `empCode` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '员工编码',
  `empName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '姓名',
  `sex` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '性别',
  `age` bigint(10) NOT NULL COMMENT '年龄',
  `nation` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '民族',
  `IDC` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份证',
  `money` bigint(255) NULL DEFAULT NULL COMMENT '薪水',
  `tel` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `ecp` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '紧急联系人',
  `ecpTel` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '紧急联系人电话',
  `jobId` bigint(10) NULL DEFAULT NULL COMMENT '岗位id',
  `des` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '个人描述',
  `classCode` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '班次\r\n',
  PRIMARY KEY (`empId`) USING BTREE,
  UNIQUE INDEX `1`(`empCode`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of employee
-- ----------------------------
INSERT INTO `employee` VALUES (1, 'ZS', '张三', '男', 20, '汉', NULL, 100, '17750925707', NULL, NULL, 2, NULL, NULL);
INSERT INTO `employee` VALUES (2, 'LS', '李四', '男', 18, '汉', '2', 100, '217750925707', '2', NULL, 4, '', NULL);
INSERT INTO `employee` VALUES (3, 'WEG', '王二狗', '男', 14, '汉', '1', 100, '117750925707', '1', NULL, 3, '', NULL);
INSERT INTO `employee` VALUES (6, 'WK', 'WK', '男', 20, '汉', '1', 100, '17750925707', '1', NULL, 3, '', NULL);
INSERT INTO `employee` VALUES (7, 'SCH', 'SCH', '男', 21, '汉', '1', 100, '17750925707', '1', NULL, 2, '', NULL);
INSERT INTO `employee` VALUES (12, 'admin', 'admin', '男', 20, '爱新觉罗', '1', 100, '17750925707', '1', NULL, 1, '', NULL);
INSERT INTO `employee` VALUES (21, 'LYD', 'LYD', '男', 20, '汉', '1', 100, '17750925707', '1', NULL, 4, '', NULL);
INSERT INTO `employee` VALUES (22, 'LSJ', 'LSJ', '男', 20, '汉', '1', 0, '17750925707', '1', NULL, 2, '', NULL);
INSERT INTO `employee` VALUES (23, 'ERTS', 'nvren', '女', 33, '22', 'ss', 11, '111111', '11111', NULL, 1, '', NULL);

-- ----------------------------
-- Table structure for leave
-- ----------------------------
DROP TABLE IF EXISTS `leave`;
CREATE TABLE `leave`  (
  `leaveId` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '请假id',
  `empCode` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '请假人code',
  `beginDate` datetime(0) NOT NULL COMMENT '开始时间',
  `endDate` datetime(0) NOT NULL COMMENT '结束时间',
  `reason` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请假原因',
  PRIMARY KEY (`leaveId`) USING BTREE,
  INDEX `l_pid`(`empCode`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 57 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of leave
-- ----------------------------
INSERT INTO `leave` VALUES (18, 'WEG', '2019-07-01 00:00:00', '2019-07-02 00:00:00', '');
INSERT INTO `leave` VALUES (20, 'WK', '2019-07-04 00:00:00', '2019-07-19 00:00:00', '冬眠');

-- ----------------------------
-- Table structure for paysalary
-- ----------------------------
DROP TABLE IF EXISTS `paysalary`;
CREATE TABLE `paysalary`  (
  `payId` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '领薪id',
  `empId` bigint(10) NOT NULL COMMENT '领薪人id',
  `beginDate` datetime(0) NULL DEFAULT NULL COMMENT '开始时间',
  `endDate` datetime(0) NULL DEFAULT NULL COMMENT '结束时间',
  `salary` bigint(10) NOT NULL COMMENT '薪水 计算方式=员工薪水*出勤小时数/应出勤小时数。',
  PRIMARY KEY (`payId`) USING BTREE,
  INDEX `ps_pid`(`empId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 58 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of paysalary
-- ----------------------------
INSERT INTO `paysalary` VALUES (1, 1, '2019-06-01 00:00:00', '2019-06-27 15:30:00', 40);
INSERT INTO `paysalary` VALUES (2, 1, '2019-06-01 00:00:00', '2019-06-27 00:00:00', 596);
INSERT INTO `paysalary` VALUES (10, 1, '2019-06-28 00:00:00', '2019-06-28 00:00:00', 250);
INSERT INTO `paysalary` VALUES (13, 3, '2019-06-01 00:00:00', '2019-06-29 00:00:00', 3321);
INSERT INTO `paysalary` VALUES (34, 12, '2019-06-26 00:00:00', '2019-06-28 00:00:00', 175);
INSERT INTO `paysalary` VALUES (35, 12, '2019-06-01 00:00:00', '2019-07-01 00:00:00', 1003);
INSERT INTO `paysalary` VALUES (40, 12, '2019-06-26 00:00:00', '2019-06-28 00:00:00', 175);
INSERT INTO `paysalary` VALUES (41, 12, '2019-06-26 00:00:00', '2019-06-28 00:00:00', 87);
INSERT INTO `paysalary` VALUES (42, 12, '2019-06-26 00:00:00', '2019-06-27 00:00:00', 87);
INSERT INTO `paysalary` VALUES (43, 12, '2019-06-26 00:00:00', '2019-06-28 00:00:00', 87);
INSERT INTO `paysalary` VALUES (44, 12, '2019-06-26 00:00:00', '2019-06-28 00:00:00', 87);
INSERT INTO `paysalary` VALUES (45, 12, '2019-06-26 00:00:00', '2019-06-27 00:00:00', 87);
INSERT INTO `paysalary` VALUES (46, 12, '2019-06-26 00:00:00', '2019-06-28 00:00:00', 87);
INSERT INTO `paysalary` VALUES (47, 12, '2019-06-26 00:00:00', '2019-06-28 00:00:00', 87);
INSERT INTO `paysalary` VALUES (48, 12, '2019-06-26 00:00:00', '2019-06-28 00:00:00', 87);
INSERT INTO `paysalary` VALUES (49, 12, '2019-06-26 00:00:00', '2019-06-28 00:00:00', 87);
INSERT INTO `paysalary` VALUES (50, 12, '2019-06-26 00:00:00', '2019-06-28 00:00:00', 87);
INSERT INTO `paysalary` VALUES (51, 12, '2019-06-26 00:00:00', '2019-06-28 00:00:00', 87);
INSERT INTO `paysalary` VALUES (52, 12, '2019-06-26 00:00:00', '2019-06-28 00:00:00', 87);
INSERT INTO `paysalary` VALUES (53, 12, '2019-06-26 00:00:00', '2019-06-28 00:00:00', 87);
INSERT INTO `paysalary` VALUES (54, 12, '2019-06-26 00:00:00', '2019-06-27 00:00:00', 87);
INSERT INTO `paysalary` VALUES (55, 1, '2019-06-26 00:00:00', '2019-07-04 00:00:00', 107);
INSERT INTO `paysalary` VALUES (56, 1, '2019-06-04 00:00:00', '2019-07-04 16:01:09', 107);
INSERT INTO `paysalary` VALUES (57, 12, '2019-06-26 00:00:00', '2019-06-28 00:00:00', 112);

-- ----------------------------
-- Table structure for punchcard
-- ----------------------------
DROP TABLE IF EXISTS `punchcard`;
CREATE TABLE `punchcard`  (
  `punchId` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '打卡id',
  `empCode` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '打卡人编码',
  `empName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `date` datetime(0) NOT NULL COMMENT '打卡时间',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`punchId`) USING BTREE,
  INDEX `pc_pid`(`empCode`) USING BTREE,
  CONSTRAINT `punchcard_ibfk_1` FOREIGN KEY (`empCode`) REFERENCES `employee` (`empCode`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB AUTO_INCREMENT = 57 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of punchcard
-- ----------------------------
INSERT INTO `punchcard` VALUES (1, 'ZS', '张三', '2019-06-27 11:09:50', '');
INSERT INTO `punchcard` VALUES (2, 'LS', '李四', '2019-06-27 08:10:17', '');
INSERT INTO `punchcard` VALUES (40, 'admin', 'admin', '2019-06-28 20:49:00', '');
INSERT INTO `punchcard` VALUES (41, 'admin', 'admin', '2019-06-26 09:49:00', '');
INSERT INTO `punchcard` VALUES (42, 'admin', 'admin', '2019-06-26 18:51:00', '');
INSERT INTO `punchcard` VALUES (43, 'WK', 'WA', '2019-06-28 20:55:00', '');
INSERT INTO `punchcard` VALUES (44, 'SCH', 'SCH', '2019-06-29 08:38:00', '');
INSERT INTO `punchcard` VALUES (45, 'WK', 'WK', '2019-06-29 01:09:25', NULL);
INSERT INTO `punchcard` VALUES (46, 'WK', 'WK', '2019-06-29 20:11:01', NULL);
INSERT INTO `punchcard` VALUES (50, 'ZS', '张三', '2019-06-27 19:46:12', NULL);
INSERT INTO `punchcard` VALUES (51, 'LS', '李四', '2019-06-27 19:47:08', NULL);
INSERT INTO `punchcard` VALUES (52, 'WK', 'WK', '2019-06-28 07:49:44', NULL);
INSERT INTO `punchcard` VALUES (55, 'admin', 'admin', '2019-07-06 09:03:00', '测试');
INSERT INTO `punchcard` VALUES (56, 'admin', 'admin', '2019-07-06 16:06:14', NULL);

-- ----------------------------
-- Table structure for repaircard
-- ----------------------------
DROP TABLE IF EXISTS `repaircard`;
CREATE TABLE `repaircard`  (
  `repairId` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '补卡id',
  `empCode` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '补卡人',
  `date` datetime(0) NOT NULL COMMENT '补卡时间',
  `reason` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '补卡原因',
  PRIMARY KEY (`repairId`) USING BTREE,
  INDEX `r_pid`(`empCode`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 39 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of repaircard
-- ----------------------------
INSERT INTO `repaircard` VALUES (16, 'ZS', '2019-01-27 00:00:00', '老子不想上班');
INSERT INTO `repaircard` VALUES (17, 'WK', '2019-01-13 00:00:00', '忘记了吧');
INSERT INTO `repaircard` VALUES (30, 'WEG', '2019-01-12 00:00:00', '');
INSERT INTO `repaircard` VALUES (35, 'admin', '2019-06-28 08:07:05', NULL);

-- ----------------------------
-- Table structure for station
-- ----------------------------
DROP TABLE IF EXISTS `station`;
CREATE TABLE `station`  (
  `jobId` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
  `jobCode` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '岗位编码',
  `jobName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '岗位名称',
  `dep` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所在部门',
  `dirSup` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '直接上级',
  `jobCat` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '岗位类别',
  `jobDes` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '岗位职责描述',
  PRIMARY KEY (`jobId`) USING BTREE,
  UNIQUE INDEX `job`(`jobCode`) USING BTREE,
  INDEX `Dep`(`dep`) USING BTREE,
  INDEX `jobName`(`jobName`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of station
-- ----------------------------
INSERT INTO `station` VALUES (1, 'CEO', '总经理', '总裁室', '', '管理类', NULL);
INSERT INTO `station` VALUES (2, 'KJ', '会计', '财务部', 'CEO', '技术类', NULL);
INSERT INTO `station` VALUES (3, 'QT', '前台', '人力资源部', 'CEO', '文职类', NULL);
INSERT INTO `station` VALUES (4, 'HQ', '后勤', '人力资源部', 'QT', '技术类', NULL);

-- ----------------------------
-- Table structure for tlogin
-- ----------------------------
DROP TABLE IF EXISTS `tlogin`;
CREATE TABLE `tlogin`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `passWord` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `userName`(`userName`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tlogin
-- ----------------------------
INSERT INTO `tlogin` VALUES (1, 'admin', '1');
INSERT INTO `tlogin` VALUES (2, 'WK', '123456');
INSERT INTO `tlogin` VALUES (3, 'SCH', '123456');
INSERT INTO `tlogin` VALUES (10, 'LYD', '123456');
INSERT INTO `tlogin` VALUES (11, 'LSJ', '123456');
INSERT INTO `tlogin` VALUES (12, 'ERTS', '123456');

SET FOREIGN_KEY_CHECKS = 1;
