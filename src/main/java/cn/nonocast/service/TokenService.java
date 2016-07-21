package cn.nonocast.service;

import cn.nonocast.model.Token;
import cn.nonocast.model.User;
import cn.nonocast.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

// Token 24h有效
@Service
public class TokenService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	private Map<String, Token> tokenMap = new HashMap<>();

	public Collection<Token> findAll() {
		List<Token> tokens = new ArrayList<>(this.tokenMap.values());
		Collections.sort(tokens);
		return tokens;
	}

	public Token create(String email, String password) {
		Token token = null;
		User user = userRepository.findByEmail(email);
		if(user!=null && passwordEncoder.matches(password, user.getPassword())) {
			Optional<Token> target = this.findByEmail(email);
			if(target.isPresent()) {
				(token = target.get()).invalidate();
			}else {
				token = new Token(user);
				this.tokenMap.put(token.getKey(), token);
			}
		}
		return token;
	}

	public Token get(String token) {
		Token result = null;
		Token p = tokenMap.get(token);
		if(p.isValid()) {
			result = p;
			result.invalidate();
		}
		return result;
	}

	public Optional<Token> findByEmail(String email) {
		List<Token> tokens = new ArrayList<>(this.tokenMap.values());
		Optional<Token> result = tokens.stream().filter(p->email.equals(p.getEmail())).findFirst();
		return result;

	}

	public void reset() {
		this.tokenMap.clear();
	}

	public void clean() {
		for(String key : this.tokenMap.keySet()) {
			Token p = this.tokenMap.get(key);
			if(!p.isValid()) {
				this.tokenMap.remove(key);
			}
		}
	}
}
