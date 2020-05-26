package com.yyh.entity;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yangyuhao.common.utils.DateUtil;
import com.yangyuhao.common.utils.RandomUtil;
import com.yangyuhao.common.utils.StringUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring-redis.xml" })
public class UserTest {

	@Autowired
	RedisTemplate<String, User> redisTemplate;

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void test() {
		Date date = new Date();
		Map<String, User> m = new HashMap<>();
		for (int i = 1; i <= 100000; i++) {
			User user = new User();
			user.setId(i);
			user.setName(StringUtil.generateChineseName());
			int random = RandomUtil.random(0, 1);
			if(random==0){
				user.setSex("男");
			}else{
				user.setSex("女");
			}
			user.setPhone("13"+RandomUtil.random(9));
			String userEmail = RandomUtil.randomString(RandomUtil.random(3, 20));
			
			int random2 = RandomUtil.random(0, 5);
			if(random2==0){
				userEmail+="@qq.com";
			}else if(random2==1){
				userEmail+="@163.com";
			}else if(random2==2){
				userEmail+="@sian.com";
			}else if(random2==3){
				userEmail+="@gmail.com";
			}else if(random2==4){
				userEmail+="@sohu.com";
			}else{
				userEmail+="@hotmail.com";
			}
			user.setEmail(userEmail);
			Calendar c = Calendar.getInstance();
			c.set(1950, 4, 26, 19, 54, 30);
			Date time = c.getTime();
			c.add(Calendar.YEAR,52 );
			Date time2 = c.getTime();
			user.setBirthday(DateUtil.random(time, time2));
			
			m.put("user_"+i, user);
			
			
		}
		redisTemplate.opsForHash().putAll("user", m);
		Date date2 = new Date();
		long l2 = date2.getTime();
		long l1 = date.getTime();
		long l = l2-l1;
		System.out.println(l);
}

	
	
	
	
	
	
	
}
