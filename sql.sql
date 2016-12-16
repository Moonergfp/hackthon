
CREATE TABLE `hk_user` (
 `id` int unsigned NOT NULL  AUTO_INCREMENT,
 `acct` varchar(60) default '' comment '账号',
 `pwd` varchar(255) default '' comment '账号',
 `name` varchar(255) default '' comment '昵称',
 `token` varchar(255) default '' comment 'token',
 `head_pic` varchar(500) default '' comment '头像地址',
  PRIMARY KEY (`id`),
  UNIQUE key acct (`acct`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 comment '用户表';


CREATE TABLE `hk_group` (
 `id` int unsigned NOT NULL  AUTO_INCREMENT,
 `group_name` varchar(255) default '' comment '组名',
 `des` varchar(2000) default '' comment '描述',
 `remark` varchar(255) default '' comment '备注',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1  DEFAULT CHARSET=utf8mb4 comment '组表';



CREATE TABLE `hk_friend_relation` (
 `id` int unsigned NOT NULL  AUTO_INCREMENT,
 `user_id` int(11) unsigned  default 0,
 `asked_user_id` int(11) unsigned default 0,
   PRIMARY KEY (`id`),
   unique key(`user_id`,`asked_user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1  DEFAULT CHARSET=utf8mb4 comment '好友关系表';


CREATE TABLE `hk_group_relation` (
 `id` int unsigned NOT NULL  AUTO_INCREMENT,
 `user_id` int unsigned  default 0,
 `group_id` int unsigned default 0,
   PRIMARY KEY (`id`),
   unique key(`user_id`,`group_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1  DEFAULT CHARSET=utf8mb4 comment '用户组关系表';



CREATE TABLE `hk_activity` (
 `id` int unsigned NOT NULL  AUTO_INCREMENT,
 `name` varchar(255) default '' comment '账号',
 `title` varchar(255) default '' comment '账号',
 `text` varchar(2000) default '' comment '昵称',
 `group_id_list` varchar(255) default '' comment 'token',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 comment '活动表';

