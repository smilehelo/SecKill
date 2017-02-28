package org.seckill.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml",
					   "classpath:spring/spring-service.xml"})
public class SeckillServiceTest {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SeckillService seckillService;
	
	@Test
	public void testGetSeckillList() throws Exception{
		List<Seckill> lists = seckillService.getSeckillList();
		logger.info("list={}",lists);
	}
	
	@Test
	public void testGetById() throws Exception{
		long id = 2L;
		Seckill seckill = seckillService.getById(id);
		logger.info("Seckill={}",seckill);
	}
	
	@Test
	public void testExportSeckillUrl() throws Exception{
		long id = 3L;
		Exposer exposer = seckillService.exportSeckillUrl(id);
		logger.info("********" + exposer);
	}
	
	@Test
	public void testExecuteSeckill() throws Exception{
		long id = 3L;
		long userPhone = 13817228493L;
		String md5 = "2d675869331fc343a10df1af76fa01ef";
		try{
			SeckillExecution seckillExecution = seckillService.executeSeckill(id, userPhone, md5);
			logger.info("*******" + seckillExecution);
		}catch (RepeatKillException e){
			logger.error(e.getMessage());
		}catch (SeckillCloseException e1){
			logger.error(e1.getMessage());
		}
	}
	
	
	/**
	 * √Î…±µƒ»´π˝≥Ã£¨√Î…±Ω”ø⁄±©¬∂£¨÷¥––√Î…±
	 * @throws Exception
	 */
	@Test
	public void testSeckillLogic() throws Exception{
		long id = 2L;
		Exposer exposer = seckillService.exportSeckillUrl(id);
		if(exposer.isExposed()){
			logger.info("***√Î…±µÿ÷∑’˝»∑±©¬∂£∫" + exposer);
			String md5 = exposer.getMd5();
			long userPhone = 13817228493L;
			//÷¥––√Î…±
			try{
				SeckillExecution execution = seckillService.executeSeckill(id, userPhone, md5);
				logger.info("*******" + execution);
			}catch (RepeatKillException e){
				logger.error(e.getMessage());
			}catch (SeckillCloseException e1){
				logger.error(e1.getMessage());
			}
		}else{
			//√Î…±Œ¥ø™∆Ù
			logger.warn("***√Î…±µÿ÷∑¥ÌŒÛ£∫" + exposer);
		}
		
	}
	
	
}


