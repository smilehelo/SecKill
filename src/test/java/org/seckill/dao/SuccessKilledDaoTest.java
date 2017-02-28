package org.seckill.dao;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SuccessKilled;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * ����spring��junit���ϣ�junit����ʱ����springIOC����
 * @author Administrator
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
//����junit spring�����ļ�
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledDaoTest {
	
	@Resource
	private SuccessKilledDao successKilledDao;
	
	@Test
	public void testInsertSuccessKiled() throws Exception{
		int res = successKilledDao.insertSuccessKilled(3L, 13817228493L);
		System.out.println("��������" + res);
	}
	
	@Test
	public void testQueryByIdWithSeckill() throws Exception{
		long seckillId = 3L;
		long userPhone = 13817228493L;
		SuccessKilled res = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
		System.out.println(res.getSeckill());
		System.out.println(res);
		
	}
}
