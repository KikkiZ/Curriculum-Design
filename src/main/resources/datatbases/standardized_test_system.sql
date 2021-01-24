/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : localhost:3306
 Source Schema         : standardized_test_system

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001

 Date: 19/12/2020 14:03:44
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for multiple_choice_question
-- ----------------------------
DROP TABLE IF EXISTS `multiple_choice_question`;
CREATE TABLE `multiple_choice_question`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `question` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `choice_a` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `choice_b` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `choice_c` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `choice_d` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `answers` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of multiple_choice_question
-- ----------------------------
INSERT INTO `multiple_choice_question` VALUES (1, '下列属于jsp中注释的有（）', 'a. <%-- 与 --%>', 'b. /', 'c . /** 与 **/', 'd. <!-- 与 -->', 'AD');
INSERT INTO `multiple_choice_question` VALUES (2, '下列说法错误的有（）', 'a. 数据时一种对象', 'b. 数据属于一种原生类', 'c. int number=[]={31,23,33,43,35,63}', 'd. 数组的大小可以任意改变', 'BCD');
INSERT INTO `multiple_choice_question` VALUES (3, '不能用来修饰interface的有（）', 'a. private', 'b. public', 'c. protected', 'd. static', 'ACD');

-- ----------------------------
-- Table structure for one_choice_question
-- ----------------------------
DROP TABLE IF EXISTS `one_choice_question`;
CREATE TABLE `one_choice_question`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `question` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `choice_a` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `choice_b` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `choice_c` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `choice_d` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `answer` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of one_choice_question
-- ----------------------------
INSERT INTO `one_choice_question` VALUES (1, 'Internet网中不同网络和不同计算机相互通讯的基础是 （ ）', 'a. ATM', 'b. TCP/IP', 'c. Novell', 'd. X.25', 'B');
INSERT INTO `one_choice_question` VALUES (2, '要往具有n个元素的有序顺序表中插入一个新元素,要保证插入后仍然有序,则在最坏情况下需要移动的元素个数为（）', 'a. n/2', 'b. n', 'c. n+1', 'd. n-1', 'B');
INSERT INTO `one_choice_question` VALUES (3, '以下叙述正确的是（）', 'a. 采用顺序存储的完全二叉树属于非线性结构', 'b. 具有多个指针域的链表一定属于非线性结构', 'c. 具有两个以上根节点的数据结构有可能是线性结构', 'd. 循环队列是队列的一种存储结构，它属于非线性结构', 'A');
INSERT INTO `one_choice_question` VALUES (4, '循环队列的存储空间为Q(1:50)，初始状态为空。经过一系列正常的入队与退队操作后,front=24,rear=25。此时该循环队列中的元素个数为（）', 'a. 1', 'b. 25', 'c. 49', 'd. 50', 'A');
INSERT INTO `one_choice_question` VALUES (5, '设某树的度为3,其度为3的结点数为4,度1为的结点数为9,没有度为2的结点。则该树中的叶子结点数为（）', 'a. 1', 'b. 4', 'c. 9', 'd. 不可能有这样的树', 'C');
INSERT INTO `one_choice_question` VALUES (6, '下面对软件特点描述错误的是（）', 'a. 软件 的复杂性高', 'b. 软件的使用存在老化问题', 'c. 软件是逻辑实体具有抽象性', 'd. 软件的运行对计算机系统具有依赖性', 'B');
INSERT INTO `one_choice_question` VALUES (7, '数据流图(DFD)的作用是（）', 'a. 描述软件系统的控制流', 'b. 支持软件系统功能建模', 'c. 描述软件系统的数据结构', 'd.支持软件系统的面向对象分析', 'C');
INSERT INTO `one_choice_question` VALUES (8, '结构化程序的三种基本控制结构是（）', 'a. 调用、返回和转移', 'b. 顺序、选择和重复', 'c. 递归、堆栈和队列', 'd. 过程。子程序和函数', 'B');
INSERT INTO `one_choice_question` VALUES (9, '数据库(DB)、数据库系统(DBS)和数据库管理系统(DBMS)之间的关系是（）', 'a. DB包括DBS和DBMS', 'b. DBS包括DB和DBMS', 'c. DBMS包括DB和DBS', 'd. DBS就是DB，也是DBMS', 'B');
INSERT INTO `one_choice_question` VALUES (10, '公司中有不同部门,而每个员工分属不同的部门,则实体部门与实体员工间的联系是（）', 'a. 1：N', 'b. M：N', 'c. 1:1', 'd. N：1', 'A');

-- ----------------------------
-- Table structure for records
-- ----------------------------
DROP TABLE IF EXISTS `records`;
CREATE TABLE `records`  (
  `id` int UNSIGNED NOT NULL,
  `times` int UNSIGNED NOT NULL,
  `grade` float(6, 3) UNSIGNED ZEROFILL NOT NULL,
  `correct_number` int UNSIGNED NOT NULL,
  `question_number` int UNSIGNED NOT NULL,
  `one_choice_number` int UNSIGNED NOT NULL,
  `multiple_choice_number` int UNSIGNED NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of records
-- ----------------------------
INSERT INTO `records` VALUES (1, 1, 100.000, 100, 100, 50, 50);
INSERT INTO `records` VALUES (1, 2, 99.000, 99, 100, 50, 50);
INSERT INTO `records` VALUES (13, 1, 75.000, 3, 4, 2, 2);
INSERT INTO `records` VALUES (13, 2, 00.000, 0, 5, 3, 2);
INSERT INTO `records` VALUES (13, 3, 16.667, 1, 6, 3, 3);
INSERT INTO `records` VALUES (13, 4, 16.667, 1, 6, 4, 2);
INSERT INTO `records` VALUES (13, 5, 20.000, 1, 5, 3, 2);
INSERT INTO `records` VALUES (13, 6, 25.000, 1, 4, 2, 2);
INSERT INTO `records` VALUES (13, 7, 00.000, 0, 4, 2, 2);
INSERT INTO `records` VALUES (13, 8, 00.000, 0, 4, 2, 2);
INSERT INTO `records` VALUES (13, 9, 00.000, 0, 2, 1, 1);
INSERT INTO `records` VALUES (13, 10, 12.500, 1, 8, 5, 3);
INSERT INTO `records` VALUES (13, 11, 00.000, 0, 8, 5, 3);
INSERT INTO `records` VALUES (13, 12, 50.000, 4, 8, 5, 3);
INSERT INTO `records` VALUES (1, 3, 90.909, 10, 11, 8, 3);

-- ----------------------------
-- Table structure for users_data
-- ----------------------------
DROP TABLE IF EXISTS `users_data`;
CREATE TABLE `users_data`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of users_data
-- ----------------------------


SET FOREIGN_KEY_CHECKS = 1;
