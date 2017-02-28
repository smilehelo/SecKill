package org.seckill.service;

import java.util.List;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;



/**
 * ҵ��ӿڣ�վ��"ʹ����"�ĽǶ���ƽӿ�
 * �������棺������������ȣ��������������ͣ�return ����/�쳣��
 * @author Administrator
 *
 */
public interface SeckillService {

	
	/**
	 * ��ѯ������ɱ��Ʒ�б�
	 * @return
	 */
	List<Seckill> getSeckillList();
	
	
	/**
	 * ��ѯ������ɱ��Ʒ
	 * @param seckillId
	 * @return
	 */
	Seckill getById(long seckillId);
	
	
	/**
	 * ��ɱ����ʱ�����ɱ�ӿڵ�ַ���������ϵͳʱ�����ɱʱ��
	 * @param seckillId
	 */
	Exposer exportSeckillUrl(long seckillId);
	
	
	/**
	 * ִ����ɱ����
	 * @param seckillId
	 * @param userPhone
	 * @param md5
	 */
	SeckillExecution executeSeckill(long seckillId,long userPhone,String md5) 
			throws SeckillException,RepeatKillException,SeckillCloseException;
}
