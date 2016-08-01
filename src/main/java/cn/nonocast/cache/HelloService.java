package cn.nonocast.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class HelloService {
	private static final Logger logger = LoggerFactory.getLogger(HelloService.class);

	@Cacheable(value="token", key="T(String).valueOf(#p0)")
	public Hello findHello(Long id) {
		return new Hello(1L, "hello");
	}


	@Cacheable(value="tasks", key="T(String).valueOf(#p0)")
	public World findWorld(Long id) {
		return new World(1L, "world");
	}
}
