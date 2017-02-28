package org.seckill.Test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class HeloTest {

	@Test
	public void test1(){
		Map<String,String> maps = new HashMap<String,String>();
		
		maps.put("1", "2");
		maps.put("2", "3");
		
		System.out.println(maps.containsKey("1"));
		System.out.println(maps.toString());
	}
}
