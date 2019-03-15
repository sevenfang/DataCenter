CREATE TABLE `t_biservise_dir` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) NOT NULL COMMENT '父文件夹ID',
  `name` varchar(255) NOT NULL COMMENT '文件夹名称',
  `path` varchar(1500) NOT NULL COMMENT '文件夹路径',
  `cutime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='biservise文件夹管理';

CREATE TABLE `t_biservise_file` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mid` int(11) NOT NULL COMMENT '父文件夹ID',
  `name` varchar(255) NOT NULL COMMENT '文件夹名称',
  `path` varchar(1500) NOT NULL COMMENT '文件夹路径',
  `svn_version` int(11) NOT NULL DEFAULT '0' COMMENT 'svn最新版本',
  `svn_author` varchar(255) NOT NULL DEFAULT '' COMMENT 'svn作者',
  `check_version` int(11) NOT NULL DEFAULT '0' COMMENT '检查通过最新版本',
  `check_author` varchar(255) NOT NULL DEFAULT '' COMMENT '检查通过作者',
  `publish_version` int(11) NOT NULL DEFAULT '0' COMMENT '发布最新版本',
  `publish_author` varchar(255) NOT NULL DEFAULT '' COMMENT '发布作者',
  `cutime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='biservise文件管理';

CREATE TABLE `t_biservise_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) NOT NULL COMMENT '用户ID',
  `username` varchar(255) NOT NULL COMMENT '用户名',
  `path` varchar(1500) NOT NULL COMMENT '用户拥有的权限',
  `cutime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='biservise用户权限管理';


insert into t_biservise_dir (pid,name,path) values (0,'','');
insert into t_biservise_user (userid,username,path) values (0,'dr.who','');
