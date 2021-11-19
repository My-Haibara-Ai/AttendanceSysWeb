use attendance_system;

-- 删除已经存在的表

drop table if exists sys_menu;
drop table if exists sys_role;
drop table if exists sys_role_menu;
drop table if exists sys_user;
drop table if exists sys_user_role;

# 权限表
create table sys_menu (
  menu_id 		bigint(20) not null auto_increment,	# id
  name 			varchar(50) not null,				# 权限名称
  permission 	varchar(200) default null,			# 权限标识
  
  primary key (menu_id) using btree
) engine=innodb auto_increment=1 default charset=utf8 row_format=dynamic;

# 角色与权限关系表
create table sys_role_menu (
  id 			bigint(11) not null auto_increment,	# id,
  role_id 		bigint(11) default null,			# 角色id
  menu_id 		bigint(11) default null,			# 权限id
  
  primary key (id) using btree
) engine=innodb auto_increment=1 default charset=utf8 row_format=dynamic;

# 系统用户表
create table sys_user (
  user_id 		bigint(11) not null auto_increment,		# 用户id
  username 		char(5) not null,						# 用户名(以员工号作为 用户名)
  password 		varchar(100) default null,				# 密码
  status 		varchar(10) default null,				# 状态 prohibit：禁用   normal：正常
  
  primary key (user_id) using btree,
  unique key username (username) using btree
) engine=innodb auto_increment=1 default charset=utf8 row_format=dynamic;

insert into sys_user (username, password, status)
values
('u5001', 'p5001', 'normal'),
('u5002', 'p5002', 'normal'),
('u5003', 'p5003', 'normal'),
('u5004', 'p5004', 'normal'),

('u1001', 'p1001', 'normal'),
('u1002', 'p1002', 'normal'),
('u1003', 'p1003', 'normal'),


('u2001', 'p2001', 'normal'),
('u2002', 'p2002', 'normal'),


('u3001', 'p3001', 'normal'),
('u3002', 'p3002', 'normal'),


('u4001', 'p4001', 'normal'),
('u4002', 'p4002', 'normal');

# 用户与角色关系表
create table sys_user_role (
  id 		bigint(11) not null auto_increment,			# id
  user_id 	bigint(11) default null,					# 用户id
  role_id 	bigint(11) default null,					# 角色id
  
  primary key (id) using btree
) engine=innodb auto_increment=1 default charset=utf8 row_format=dynamic;

insert into sys_user_role (user_id, role_id)
values
(1, 3),		# 总
(2, 4),		# 人事
(3, 2),		# 部门
(4, 1),		# 员工

(5, 2),		# 部门
(6, 1),		# 员工
(7, 2), 	# 员工

(8, 2),		# 部门
(9, 1),		# 员工

(10, 2),	# 部门
(10, 1),	# 员工

(11, 2),	# 部门
(12, 1);	# 员工

# 角色表
create table sys_role (
  role_id 		bigint(11) not null auto_increment,		# 角色id
  role_name		varchar(50) not null,					# 角色名称
  
  primary key (role_id) using btree
) engine=innodb auto_increment=1 default charset=utf8 row_format=dynamic;


insert into sys_role (role_name)
values
('staff'),			# 普通员工
('manager'),		# 部门经理
('general'),		# 总经理 
('personnel');		# 人事部
