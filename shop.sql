/*
 Navicat Premium Data Transfer

 Source Server         : localRoot
 Source Server Type    : MySQL
 Source Server Version : 80022
 Source Host           : localhost:3306
 Source Schema         : shop

 Target Server Type    : MySQL
 Target Server Version : 80022
 File Encoding         : 65001

 Date: 31/12/2020 14:58:14
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_product
-- ----------------------------
DROP TABLE IF EXISTS `t_product`;
CREATE TABLE `t_product`  (
  `id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `price` int(0) NOT NULL,
  `sum` int(0) NOT NULL,
  `visitCount` int(0) NOT NULL,
  `status` int(0) NOT NULL,
  `addDate` datetime(0) NOT NULL,
  `description` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `pic_url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 101 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_product
-- ----------------------------
INSERT INTO `t_product` VALUES (67, '最后一个吊坠', 3444, 3333, 3443, 0, '2020-12-30 00:00:00', '这是一件首饰', 'cfc35333-9db9-4d87-b0c5-55dfccf4835c.jpg');
INSERT INTO `t_product` VALUES (77, 'testProduct1', 77, 8888, 77, 1, '2020-12-28 00:00:00', '7', '4fc76509-78ea-443e-8494-b13ee1552192.jpg');
INSERT INTO `t_product` VALUES (78, 'productTest78', 200, 1000, 8122, 1, '2020-12-30 00:00:00', '123', 'd41dcd37-50f5-43ca-9ad3-e92aa9b4dbc7.jpg');
INSERT INTO `t_product` VALUES (79, 'testProduct3', 1, 8888, 1, 1, '2020-12-10 17:29:31', '1', '1');
INSERT INTO `t_product` VALUES (83, 'productTest73', 200, 1000, 122, 0, '2020-12-28 00:00:00', '123', 'a5e9c5df-2f09-44b1-a613-e99a1fa15236.jpg');
INSERT INTO `t_product` VALUES (87, '汽车', 12, 333, 1, 11, '2020-12-28 00:00:00', '1', '71812d4e-1913-412d-b9bf-1f980f99b822.jpg');
INSERT INTO `t_product` VALUES (90, 'productTest75', 200, 1000, 122, 1, '2020-12-29 00:00:00', '123', '5be10581-e0a9-4e68-8a74-054344acfdce.jpg');
INSERT INTO `t_product` VALUES (91, 'productTest77', 200, 1000, 122, 1, '2020-12-29 00:00:00', '123', 'aecf35f7-be43-4cb6-bc40-c0a8edf47bb9.jpg');
INSERT INTO `t_product` VALUES (93, 'productTest80', 200, 1000, 122, 1, '2020-12-29 00:00:00', '123', '04c8608e-f99d-4958-a59d-a401d65c7d40.jpg');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `age` int(0) NULL DEFAULT NULL,
  `mobile` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `address` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `status` int(0) NOT NULL,
  `head_pic` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8203419 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (20, 'wangwu', '123', 12, '17703741864', '1714099478@qq.com', '河南郑州', 1, '27cafb15-4342-4045-83b3-e5c9c68b4cd5.jpg');
INSERT INTO `t_user` VALUES (21, '孙六', '123', 21, '17703741864', '1714099478@qq.com', NULL, 1, NULL);
INSERT INTO `t_user` VALUES (23, '55', '123', 21, '17703741864', '1714099478@qq.com', NULL, 1, NULL);
INSERT INTO `t_user` VALUES (24, '6', '123', 21, '17703741864', NULL, NULL, 1, NULL);
INSERT INTO `t_user` VALUES (25, '7', '123', 21, '17703741864', NULL, NULL, 1, NULL);
INSERT INTO `t_user` VALUES (26, '8', '123', 21, '17703741864', NULL, NULL, 1, NULL);
INSERT INTO `t_user` VALUES (666, '0', '0', 6, '0', '0', '0', 1, '0');
INSERT INTO `t_user` VALUES (787555, 'pm', '123', 21, '17703741864', 'ailef@focmail.com', 'zhengzhou', 1, 'log.jpg');
INSERT INTO `t_user` VALUES (1884018, 'pm3', '123', 21, '17703741864', 'ailef@focmail.com', 'zhengzhou', 1, 'u=3763515803,2683427526&fm=15&gp=0.jpg');
INSERT INTO `t_user` VALUES (4512109, '派蒙', '123', 21, '17703741864', 'ailef@focmail.com', 'zhengzhou', 1, '');
INSERT INTO `t_user` VALUES (5756336, 'zhangsan1', '123', 21, '17703741864', 'ailef@focmail.com', 'zhengzhou', 1, '');
INSERT INTO `t_user` VALUES (6928006, 'kl', '123', 21, '17703741864', 'ailef@focmail.com', 'zhengzhou', 1, 'u=3763515803,2683427526&fm=15&gp=0.jpg');
INSERT INTO `t_user` VALUES (8075165, 'pm5', '123', 21, '17703741864', 'ailef@focmail.com', 'zhengzhou', 1, 'pm5.jpg');
INSERT INTO `t_user` VALUES (8203396, 'pm2', '123', 21, '17703741864', 'ailef@focmail.com', 'zhengzhou', 1, 'u=4106051701,918216351&fm=15&gp=0.jpg');
INSERT INTO `t_user` VALUES (8203397, 'pm6', '123', 21, '17703741864', 'ailef@focmail.com', 'zhengzhou', 1, 'pm6.jpg');
INSERT INTO `t_user` VALUES (8203400, '懒人1', '123', 21, '17703741864', 'ailef@focmail.com', 'zhengzhou', 1, '懒人.gif');
INSERT INTO `t_user` VALUES (8203402, 'pm7', '123', 21, '17703741864', 'ailef@focmail.com', 'zhengzhou', 1, '蓝帽子.jpg');
INSERT INTO `t_user` VALUES (8203403, 'pm8', '123', 21, '17703741864', 'ailef@focmail.com', 'zhengzhou', 1, 'a77836d7-228f-440d-a698-3573c2d7b664.jpg');
INSERT INTO `t_user` VALUES (8203404, 'pm9', '123', 21, '17703741864', 'ailef@focmail.com', 'zhengzhou', 1, '18e30495-4103-4f45-afb1-a5a807c0d910.jpg');
INSERT INTO `t_user` VALUES (8203406, 'pm10', '123', 21, '17703741864', 'ailef@focmail.com', 'zhengzhou', 1, '53e02b5a-b140-4246-b9a3-af44422829af.jpg');
INSERT INTO `t_user` VALUES (8203411, '111', '123', 21, '17703741864', 'ailef@focmail.com', 'zhengzhou', 1, '2397f03a-673a-4c3b-a35a-7bf3495be360.jpg');
INSERT INTO `t_user` VALUES (8203412, 'pic_url', 'password', 21, '17703741864', 'ailef@foxmail.com', '河南郑州', 1, 'db9f5df1-f251-4841-b0b1-06bad9b1c567.jpg');
INSERT INTO `t_user` VALUES (8203414, '问问群二无确认', 'password', 21, '17703741864', 'ailef@foxmail.com', '河南郑州', 1, 'c5c22393-0552-46d6-b0b5-8f00ba23c1f0.jpg');
INSERT INTO `t_user` VALUES (8203417, 'user', '123', 2, '2', '2', '2', 2, 'ff31cee5-cdc3-4a2d-b45c-fc5a04150677.gif');
INSERT INTO `t_user` VALUES (8203418, 'wangwu1', '123', 12, '12', 'ailef@focmail.com', '2', 1, '168766a2-8f98-4c8e-9cbe-54e4c2de843b.gif');

SET FOREIGN_KEY_CHECKS = 1;
