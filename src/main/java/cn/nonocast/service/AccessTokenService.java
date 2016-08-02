package cn.nonocast.service;

import cn.nonocast.model.AccessToken;
import cn.nonocast.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class AccessTokenService {
//	@Autowired
//	private UserService userService;
//
//	@Autowired
//	private PasswordEncoder passwordEncoder;

	@Autowired
	private RedisTemplate<Object, Object> redisTemplate;

	@Resource(name = "redisTemplate")
	private ValueOperations<String, AccessToken> vOps;

//	public AccessToken get(String email, String password) {
//		AccessToken token = null;
//		User user = userService.findByEmail(email);
//		if(user != null && passwordEncoder.matches(password, user.getPassword())) {
//			token = get(user);
//		}
//		return token;
//	}

	public AccessToken valid(String email, String token) {
		AccessToken result = null;

		AccessToken target = vOps.get(getKey(email));
		if(target != null && target.getSecret().equals(token)) {
			result = target;
		}

		return result;
	}

	public AccessToken get(User user) {
		AccessToken token;
		AccessToken target = this.findByEmail(user.getEmail());
		if(target != null) {
			token = target;
			this.invalidate(target);
		} else {
			token = new AccessToken(user);
			this.save(token);
		}
		return token;
	}

	private AccessToken findByEmail(String email) {
		return vOps.get(getKey(email));
	}

	private void save(AccessToken token) {
		vOps.set(getKey(token.getEmail()), token);
		this.invalidate(token);
	}

	private void invalidate(AccessToken token) {
		vOps.getOperations().expire(getKey(token.getEmail()), 1, TimeUnit.DAYS);
	}

	private String getKey(String email) {
		return "token:" + email;
	}
}
