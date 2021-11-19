/**
 * 创建程序需要的基本表并导入一些初始数据
 */

use attendance_system;

-- 删除已经存在的表

drop table if exists staff;
drop table if exists sign_in;
drop table if exists sign_out;
drop table if exists department;
drop table if exists attendance;
drop table if exists remaining;
drop table if exists vac_type;
drop table if exists vac_status;
drop table if exists vacation;


-- 建表并导入初始数据

# 员工基本信息表
create table staff (
	user_id 	char(5),			# 员工号,如'u1235'
	user_name 	varchar(50),		# 姓名,如'刘亦菲'
	sex 		varchar(10),		# 姓名, {'男', '女'}
	email		varchar(50),		# 电子邮箱
	age 		int,				# 年龄
	employed 	int, 				# 工龄
	depart_id 	int,				# 部门编号

	primary key (user_id) using btree
);

insert into staff values 
('u5001', '黄泓', '男', '1240216837@qq.com', 50, 25, 5),
('u5002', '周陈铮', '男', '1338996688@qq.com', 45, 20, 5),
('u5003', '菜虚琨', '男', '2197992378@qq.com', 35, 15, 5),
('u5004', '王冰冰', '女', '8761223788@qq.com', 30, 5, 5),

('u1001', '胡英俊', '男', '3672638769@qq.com', 45, 13, 1),
('u1002', '傻妞', '女', '1279579757@qq.com', 20, 0, 1),
('u1003', '柯洁', '男', '2439787832@qq.com', 37, 6, 1),

('u2001', '李云龙', '男', '6756564555@qq.com', 42, 12, 2),
('u2002', '工藤新一', '男', '3453267467@qq.com', 18, 2, 2),

('u3001', 'papi酱', '女', '9247723232@qq.com', 35, 11, 3),
('u3002', '川宝', '男', '1242432322@qq.com', 58, 20, 3),

('u4001', '常山赵子龙', '男', '12372886767@qq.com', 28, 10, 4),
('u4002', '奶茶妹妹', '女', '2311232378@qq.com', 28, 7, 4);

# 签到表
create table sign_in (
	user_id			char(5),		# 员工号
	time_year		int,			# 签到时间-年
	time_month		int,			# 签到时间-月,1-12
	time_day 		int,			# 签到时间-日,1-31
	time_hour 		int,			# 签到时间-时,0-23
	time_minute		int 			# 签到时间-分,0-59
);

# 签退表
create table sign_out (
	user_id			char(5),		# 员工号
	time_year		int,			# 签退时间-年
	time_month		int,			# 签退时间-月,1-12
	time_day 		int,			# 签退时间-日,1-31
	time_hour 		int,			# 签退时间-时,0-23
	time_minute		int 			# 签退时间-分,0-59
);

# 部门表
create table department (
	id				int auto_increment,			# 部门编号,from 1
	depart			varchar(50),				# 部门名
	
	primary key (id) using btree
);

insert into department (depart) values
('技术部门'),
('运营部门'),
('产品部门'),
('市场部门'),
('综合部门');

# 考勤表
create table attendance (
	user_id			char(5),			# 员工号
	work_hour		int,				# 出勤(工作)时长(时)
	paid			int,				# 带薪假期时长(时)
	unpaid 			int,				# 不带薪假期时长(时)
	valid 			int,				# 有效工作时长,用于计算工资(时)

	primary key (user_id) using btree
);

insert into attendance 
(user_id)
values
('u5001'),
('u5002'),
('u5003'),
('u5004'),
('u1001'),
('u1002'),
('u1003'),
('u2001'),
('u2002'),
('u3001'),
('u3002'),
('u4001'),
('u4002');

# 所有员工的考勤信息最初都为0

update attendance
set
work_hour = 5,
unpaid = 5,
paid = 5,
valid = 5;

# 假期余额表
create table remaining (
	user_id 		char(5),			# 员工号
	annual 			int,				# 剩余年假(天)
	sick 			int,				# 剩余带薪病假(天)
	marriage		int,				# 婚假
	check_up		int,				# 产检假
	maternity		int,				# 产假
	feeding 		int,				# 哺乳假
	paternity 		int,				# 陪产假
	affair 			int,				# 事假
	
	primary key (user_id) using btree
);

insert into remaining 
(user_id, annual, sick)
values
('u5001', 15, 5),
('u5002', 15, 5),
('u5003', 10, 5),
('u5004', 5, 5),

('u1001', 10, 5),
('u1002', 0, 5),
('u1003', 5, 5),

('u2001', 10, 5),
('u2002', 5, 5),

('u3001', 10, 5),
('u3002', 10, 5),

('u4001', 10, 5),
('u4002', 5, 5);

# 将其它假期的天数均设为0

update remaining
set
marriage = 0,
check_up = 0,
maternity = 0,
feeding = 0,
paternity = 0,
affair = 0;

# 假期类型表
create table vac_type (
	id				int auto_increment, # 假期类型编号,from 1
	type 			varchar(50),		# 假期类型名
	
	primary key (id) using btree
);

insert into vac_type (type) values
('年假'),
('病假'),
('婚假'),
('产检假'),
('产假'),
('哺乳假'),
('陪产假'),
('事假');

# 请假单状态表
create table vac_status (
	id 				int auto_increment,		# 请假单状态编号,from 1
	status			varchar(50),			# 请假单状态名
	
	primary key (id) using btree 
);

insert into vac_status (status) values
('人事部审核中'),
('部门经理审核中'),
('总经理审核中'),
('通过'),
('拒绝');

# 请假单表
create table vacation (
	user_id		char(5),			# 员工号
	user_name	varchar(50),		# 姓名
	type_id		int,				# 假期类型编号
	status_id 	int,				# 请假单状态编号
	depart_id	int,				# 员工所属部门编号
	start_date  varchar(50),		# 请假开始日期
	start_time  varchar(50),		# 请假开始时间
	end_date  	varchar(50),		# 请假开始日期
	end_time  	varchar(50),		# 请假开始时间
	vac_long	int,				# 请假时长(时)
	reason 		varchar(200),		# 请假理由
	
	primary key (user_id) using btree
);
