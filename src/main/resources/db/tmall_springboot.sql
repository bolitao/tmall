/*
 Navicat Premium Data Transfer

 Source Server         : LocalMySQL
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : localhost:3306
 Source Schema         : tmall_springboot

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`
(
    `id`   int(11)                                                 NOT NULL AUTO_INCREMENT,
    `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for order_
-- ----------------------------
DROP TABLE IF EXISTS `order_`;
CREATE TABLE `order_`
(
    `id`           int(11)                                                 NOT NULL AUTO_INCREMENT,
    `orderCode`    varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `address`      varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `post`         varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `receiver`     varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `mobile`       varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `userMessage`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `createDate`   datetime(0)                                             NULL DEFAULT NULL,
    `payDate`      datetime(0)                                             NULL DEFAULT NULL,
    `deliveryDate` datetime(0)                                             NULL DEFAULT NULL,
    `confirmDate`  datetime(0)                                             NULL DEFAULT NULL,
    `uid`          int(11)                                                 NULL DEFAULT NULL,
    `status`       varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `fk_order_user` (`uid`) USING BTREE,
    CONSTRAINT `fk_order_user` FOREIGN KEY (`uid`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for orderitem
-- ----------------------------
DROP TABLE IF EXISTS `orderitem`;
CREATE TABLE `orderitem`
(
    `id`     int(11) NOT NULL AUTO_INCREMENT,
    `pid`    int(11) NULL DEFAULT NULL,
    `oid`    int(11) NULL DEFAULT NULL,
    `uid`    int(11) NULL DEFAULT NULL,
    `number` int(11) NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `fk_orderitem_user` (`uid`) USING BTREE,
    INDEX `fk_orderitem_product` (`pid`) USING BTREE,
    INDEX `fk_orderitem_order` (`oid`) USING BTREE,
    CONSTRAINT `fk_orderitem_order` FOREIGN KEY (`oid`) REFERENCES `order_` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `fk_orderitem_product` FOREIGN KEY (`pid`) REFERENCES `product` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `fk_orderitem_user` FOREIGN KEY (`uid`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`
(
    `id`            int(11)                                                 NOT NULL AUTO_INCREMENT,
    `name`          varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `subTitle`      varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `originalPrice` float                                                   NULL DEFAULT NULL,
    `promotePrice`  float                                                   NULL DEFAULT NULL,
    `stock`         int(11)                                                 NULL DEFAULT NULL,
    `cid`           int(11)                                                 NULL DEFAULT NULL,
    `createDate`    datetime(0)                                             NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `fk_product_category` (`cid`) USING BTREE,
    CONSTRAINT `fk_product_category` FOREIGN KEY (`cid`) REFERENCES `category` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for productimage
-- ----------------------------
DROP TABLE IF EXISTS `productimage`;
CREATE TABLE `productimage`
(
    `id`   int(11)                                                 NOT NULL AUTO_INCREMENT,
    `pid`  int(11)                                                 NULL DEFAULT NULL,
    `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `fk_productimage_product` (`pid`) USING BTREE,
    CONSTRAINT `fk_productimage_product` FOREIGN KEY (`pid`) REFERENCES `product` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for property
-- ----------------------------
DROP TABLE IF EXISTS `property`;
CREATE TABLE `property`
(
    `id`   int(11)                                                 NOT NULL AUTO_INCREMENT,
    `cid`  int(11)                                                 NULL DEFAULT NULL,
    `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `fk_property_category` (`cid`) USING BTREE,
    CONSTRAINT `fk_property_category` FOREIGN KEY (`cid`) REFERENCES `category` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for propertyvalue
-- ----------------------------
DROP TABLE IF EXISTS `propertyvalue`;
CREATE TABLE `propertyvalue`
(
    `id`    int(11)                                                 NOT NULL AUTO_INCREMENT,
    `pid`   int(11)                                                 NULL DEFAULT NULL,
    `ptid`  int(11)                                                 NULL DEFAULT NULL,
    `value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `fk_propertyvalue_property` (`ptid`) USING BTREE,
    INDEX `fk_propertyvalue_product` (`pid`) USING BTREE,
    CONSTRAINT `fk_propertyvalue_product` FOREIGN KEY (`pid`) REFERENCES `product` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `fk_propertyvalue_property` FOREIGN KEY (`ptid`) REFERENCES `property` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for review
-- ----------------------------
DROP TABLE IF EXISTS `review`;
CREATE TABLE `review`
(
    `id`         int(11)                                                  NOT NULL AUTO_INCREMENT,
    `content`    varchar(4000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `uid`        int(11)                                                  NULL DEFAULT NULL,
    `pid`        int(11)                                                  NULL DEFAULT NULL,
    `createDate` datetime(0)                                              NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `fk_review_product` (`pid`) USING BTREE,
    INDEX `fk_review_user` (`uid`) USING BTREE,
    CONSTRAINT `fk_review_product` FOREIGN KEY (`pid`) REFERENCES `product` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `fk_review_user` FOREIGN KEY (`uid`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `id`       int(11)                                                 NOT NULL AUTO_INCREMENT,
    `name`     varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `salt`     varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
