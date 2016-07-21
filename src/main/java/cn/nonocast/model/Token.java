package cn.nonocast.model;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class Token implements Comparable<Token> {
	private String key;
	private Long id;
	private String email;
	private String name;
	private LocalDateTime createdAt;
	private LocalDateTime expiredAt;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getExpiredAt() {
		return expiredAt;
	}

	public void setExpiredAt(LocalDateTime expiredAt) {
		this.expiredAt = expiredAt;
	}

	public Token() {
		this.key = Long.toHexString(Double.doubleToLongBits(Math.random()));
	}

	public Token(User user) {
		this();
		this.id = user.getId();
		this.email = user.getEmail();
		this.name = user.getName();
		this.createdAt = LocalDateTime.now();
		this.expiredAt = LocalDateTime.now();
		this.invalidate();
	}

	@Override
	public int compareTo(Token p) {
		return this.createdAt.compareTo(p.createdAt);
	}

	@Override
	public String toString() {
		return this.key;
	}

	public boolean isValid() {
		return this.expiredAt.compareTo(LocalDateTime.now()) > 0;
	}

	public void invalidate() {
		this.expiredAt = LocalDateTime.now().plus(1, ChronoUnit.DAYS);
	}

}
