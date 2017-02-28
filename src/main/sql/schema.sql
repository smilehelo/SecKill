--���ݿ��ʼ���ű�

--�������ݿ�
create database seckill;

--ʹ�����ݿ�
use seckill;

--������ɱ����
CREATE TABLE seckill(
seckill_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '��Ʒ���ID',
commodity_name VARCHAR(120) NOT NULL COMMENT '��Ʒ����',
commodity_number INT NOT NULL COMMENT '�������',
start_time TIMESTAMP NOT NULL COMMENT '��ɱ����ʱ��',
end_time TIMESTAMP NOT NULL COMMENT '��ɱ����ʱ��',
create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '����ʱ��',
PRIMARY KEY (seckill_id),
KEY idx_start_time(start_time),
KEY idx_end_time(end_time),
KEY idx_create_time(create_time)
)ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='��ɱ����';

--��ʼ������
insert into 
	seckill(commodity_name,commodity_number,start_time,end_time)
values
	('2000Ԫ��ɱimac',100,'2017-01-19 00:00:00','2017-01-20 00:00:00'),
	('1000Ԫ��ɱiphone7',200,'2017-01-19 00:00:00','2017-01-20 00:00:00'),
	('800Ԫ��ɱipad air',300,'2017-01-19 00:00:00','2017-01-20 00:00:00'),
	('600Ԫ��ɱipad mini',400,'2017-01-19 00:00:00','2017-01-20 00:00:00');
	
--��ɱ��ϸ��
--�û���¼��֤�����Ϣ
CREATE TABLE success_killed(
seckill_id BIGINT NOT NULL COMMENT '��ɱ����Ʒid',
user_phone BIGINT NOT NULL COMMENT '�û��ֻ���',
state INT NOT NULL DEFAULT -1 COMMENT '��ɱ״̬:-1:��Ч��0���ɹ���1���Ѹ��2���ѷ���',
create_time TIMESTAMP NOT NULL COMMENT '����ʱ��',
PRIMARY KEY (seckill_id,user_phone),  /* �������� */
KEY idx_create_time(create_time)
)ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='��ɱ�ɹ���ϸ��';
	