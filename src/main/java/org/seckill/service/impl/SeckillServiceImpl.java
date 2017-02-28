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
	
	//md5验值字符串，用于混淆md5
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
		//秒杀id判断
		if(seckill == null){
			return new Exposer(false, seckillId);
		}
		//秒杀时间判断
		Date startTime = seckill.getStartTime();
		Date endTime = seckill.getEndTime();
		//当前系统时间
		Date now = new Date();
		//判断当前时间是否在秒杀过程中
		if(now.getTime() < startTime.getTime() 
				|| now.getTime() > endTime.getTime()){
			return new Exposer(false,seckillId,now.getTime(),startTime.getTime(),endTime.getTime());
		}
		//转化特定字符串的过程，不可逆
		String md5 = getMD5(seckillId);
		return new Exposer(true,md5,seckillId);
	}

	@Override
	@Transactional
	/**
	 * 使用注解控制事务方法的有点
	 * 1.开发团队达成一致约定，明确标注事务方法的风格。
	 * 2.保证事务方法的执行时间尽可能短，不要穿插其他的网络操作RPC/HTTP请求或者剥离到事务方法外部
	 * 3.不是所有的方法都需要事务，如只有一条修改操作或只读操作，并不需要事务控制
	 */
	public SeckillExecution executeSeckill(long seckillId, long userPhone,
			String md5) throws SeckillException, RepeatKillException,
			SeckillCloseException {
		//判断md5值是否伪造
		if(md5 == null || !md5.equals(getMD5(seckillId))){
			throw new SeckillException("seckill data rewrite!");
		}
		//执行秒杀业务逻辑：减库存+记录购买行为
		//减库存行为
		Date nowTime = new Date();
		int updateCount = seckillDao.reduceNumber(seckillId, nowTime);
		
		try{
			if(updateCount <=0 ){
				//没有更新数据库记录,秒杀结束
				throw new SeckillCloseException("seckill is Closed！");
			}else{
				//记录购买行为
				int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
				//联合主键：seckillId，userPhone
				if(insertCount <= 0){
					//重复秒杀
					throw new RepeatKillException("seckill repeated!");
				}
				else{
					//秒杀成功
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
			//所有编译期异常转换为运行期异常
			throw new SeckillException("seckill inner error:" + e.getMessage());
		}
	}

	
	/**
	 * 生成md5加密字符串的方法
	 * @param seckillId
	 * @return
	 */
	private	String getMD5(long seckillId){
		String base = seckillId + "/" + slat;
		String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
		return md5;
	}
	
}
