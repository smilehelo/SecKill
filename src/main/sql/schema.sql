--数据库初始化脚本

--创建数据库
create database seckill;

--使用数据库
use seckill;

--创建秒杀库存表
CREATE TABLE seckill(
seckill_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '商品库存ID',
commodity_name VARCHAR(120) NOT NULL COMMENT '商品名称',
commodity_number INT NOT NULL COMMENT '库存数量',
start_time TIMESTAMP NOT NULL COMMENT '秒杀开启时间',
end_time TIMESTAMP NOT NULL COMMENT '秒杀结束时间',
create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
PRIMARY KEY (seckill_id),
KEY idx_start_time(start_time),
KEY idx_end_time(end_time),
KEY idx_create_time(create_time)
)ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='秒杀库存表';

--初始化数据
insert into 
	seckill(commodity_name,commodity_number,start_time,end_time)
values
	('2000元秒杀imac',100,'2017-01-19 00:00:00','2017-01-20 00:00:00'),
	('1000元秒杀iphone7',200,'2017-01-19 00:00:00','2017-01-20 00:00:00'),
	('800元秒杀ipad air',300,'2017-01-19 00:00:00','2017-01-20 00:00:00'),
	('600元秒杀ipad mini',400,'2017-01-19 00:00:00','2017-01-20 00:00:00');
	
--秒杀明细表
--用户登录认证相关信息
CREATE TABLE success_killed(
seckill_id BIGINT NOT NULL COMMENT '秒杀的商品id',
user_phone BIGINT NOT NULL COMMENT '用户手机号',
state INT NOT NULL DEFAULT -1 COMMENT '秒杀状态:-1:无效，0：成功，1：已付款，2：已发货',
create_time TIMESTAMP NOT NULL COMMENT '创建时间',
PRIMARY KEY (seckill_id,user_phone),  /* 联合主键 */
KEY idx_create_time(create_time)
)ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='秒杀成功明细表';
	