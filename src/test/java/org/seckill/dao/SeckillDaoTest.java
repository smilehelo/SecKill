package org.seckill.dao;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.Seckill;
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
public class SeckillDaoTest {
	
	//ע��Daoʵ��������
	@Resource
	private SeckillDao seckillDao;
	
	@Test
	public void testQueryById() throws Exception{
		long id = 2;
		Seckill seckill = seckillDao.queryById(id);
		
		System.out.println(seckill.getCommodityName());
		System.out.println(seckill);
		
	}
	
	
	@Test
	public void testQueryAll() throws Exception{
		List<Seckill> lists = seckillDao.queryAll(0, 5);
		for(Seckill one : lists){
			System.out.println(one);
		}
	}
	
	
	@Test
	public void testReduceNumber() throws Exception{
		Date killtime = new Date();
		int res = seckillDao.reduceNumber(2L, killtime);
		System.out.println(res);
	}
	

}
