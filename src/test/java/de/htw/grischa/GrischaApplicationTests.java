package de.htw.grischa;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.DefaultStringRedisConnection;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;

import static org.hamcrest.core.IsCollectionContaining.hasItems;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GrischaApplicationTests {

	@Autowired
	RedisConnectionFactory redisConnectionFactory;

	@SpringBootApplication
	static class Config{}

	private RedisConnection connection;
	private StringRedisConnection stringConnection;

	@Before
	public void setUp(){
		connection = redisConnectionFactory.getConnection();
		stringConnection = new DefaultStringRedisConnection(connection);
	}

	@After
	public void after(){
		connection.close();
	}

	@Test
	public void collectAllKeys() {
		stringConnection.set("key-1", "test1");
		stringConnection.set("key-2", "test2");

		Collection<String> keys = stringConnection.keys("*");

		Assert.assertThat(keys, hasItems("key-1", "key-2"));
	}

	@Test
	public void getFirstValue(){
		String val = stringConnection.get("key-1");

		Assert.assertEquals(val,  "test1");
	}
}
