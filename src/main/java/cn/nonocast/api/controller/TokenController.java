package cn.nonocast.api.controller;

import cn.nonocast.service.*;
import cn.nonocast.model.*;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// TODO: move to redis LATER
@RestController("apiTokenController")
@RequestMapping("/api")
public class TokenController {
	@Autowired
	private TokenService tokenService;

	// curl -H "Authorization: Basic bm9ub2Nhc3RAZ21haWwuY29tOjEyMzQ1Ngo="  http://localhost:8080/api/token
	@RequestMapping("token")
	public ResponseEntity<?> token(@RequestHeader(value="Authorization", defaultValue="") String authorization, HttpServletResponse response) {
		if(Strings.isNullOrEmpty(authorization)) {
			response.setHeader("WWW-authenticate", "basic");
			return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
		}

		Token token = null;

		Pattern p = Pattern.compile("basic\\s+(.*)$", Pattern.CASE_INSENSITIVE);
		Matcher matcher = p.matcher(authorization);
		if(matcher.matches()) {
			String auth = new String(Base64.getDecoder().decode(matcher.group(1))).trim();
			String[] slices = auth.split(":");
			token = tokenService.create(slices[0], slices[1]);
		}

		if(token == null) {
			response.setHeader("WWW-authenticate", "basic");
			return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
		}

		return new ResponseEntity<>(token.getKey(), HttpStatus.OK);
	}
}
