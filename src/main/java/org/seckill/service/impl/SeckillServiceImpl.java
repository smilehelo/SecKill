package org.seckill.service.impl;

import java.util.Date;
import java.util.List;

import org.seckill.dao.SeckillDao;
import org.seckill.dao.SuccessKilledDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SeckillStatEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

@Service
public class SeckillServiceImpl implements SeckillService{
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SeckillDao seckillDao;
	
	@Autowired
	private SuccessKilledDao successKilledDao;
	
	//md5��ֵ�ַ��������ڻ���md5
	private final String slat = "hwiorhjlksndfc'PK]QWI]QWOPJFASNFZLSKNFAOIHDF";
	
	@Override
	public List<Seckill> getSeckillList() {
		return seckillDao.queryAll(0, 4);
	}

	@Override
	public Seckill getById(long seckillId) {
		return seckillDao.queryById(seckillId);
	}

	@Override
	public Exposer exportSeckillUrl(long seckillId) {
		Seckill seckill = seckillDao.queryById(seckillId);
		//��ɱid�ж�
		if(seckill == null){
			return new Exposer(false, seckillId);
		}
		//��ɱʱ���ж�
		Date startTime = seckill.getStartTime();
		Date endTime = seckill.getEndTime();
		//��ǰϵͳʱ��
		Date now = new Date();
		//�жϵ�ǰʱ���Ƿ�����ɱ������
		if(now.getTime() < startTime.getTime() 
				|| now.getTime() > endTime.getTime()){
			return new Exposer(false,seckillId,now.getTime(),startTime.getTime(),endTime.getTime());
		}
		//ת���ض��ַ����Ĺ��̣�������
		String md5 = getMD5(seckillId);
		return new Exposer(true,md5,seckillId);
	}

	@Override
	@Transactional
	/**
	 * ʹ��ע��������񷽷����е�
	 * 1.�����ŶӴ��һ��Լ������ȷ��ע���񷽷��ķ��
	 * 2.��֤���񷽷���ִ��ʱ�価���̣ܶ���Ҫ�����������������RPC/HTTP������߰��뵽���񷽷��ⲿ
	 * 3.�������еķ�������Ҫ������ֻ��һ���޸Ĳ�����ֻ��������������Ҫ�������
	 */
	public SeckillExecution executeSeckill(long seckillId, long userPhone,
			String md5) throws SeckillException, RepeatKillException,
			SeckillCloseException {
		//�ж�md5ֵ�Ƿ�α��
		if(md5 == null || !md5.equals(getMD5(seckillId))){
			throw new SeckillException("seckill data rewrite!");
		}
		//ִ����ɱҵ���߼��������+��¼������Ϊ
		//�������Ϊ
		Date nowTime = new Date();
		int updateCount = seckillDao.reduceNumber(seckillId, nowTime);
		
		try{
			if(updateCount <=0 ){
				//û�и������ݿ��¼,��ɱ����
				throw new SeckillCloseException("seckill is Closed��");
			}else{
				//��¼������Ϊ
				int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
				//����������seckillId��userPhone
				if(insertCount <= 0){
					//�ظ���ɱ
					throw new RepeatKillException("seckill repeated!");
				}
				else{
					//��ɱ�ɹ�
					SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
					return new SeckillExecution(seckillId,SeckillStatEnum.SUCCESS,successKilled);
				}
			}
		}catch (SeckillCloseException e1){
			throw e1;
		}catch (RepeatKillException e2){
			throw e2;
		}catch (Exception e){
			logger.error(e.getMessage(),e);
			//���б������쳣ת��Ϊ�������쳣
			throw new SeckillException("seckill inner error:" + e.getMessage());
		}
	}

	
	/**
	 * ����md5�����ַ����ķ���
	 * @param seckillId
	 * @return
	 */
	private	String getMD5(long seckillId){
		String base = seckillId + "/" + slat;
		String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
		return md5;
	}
	
}