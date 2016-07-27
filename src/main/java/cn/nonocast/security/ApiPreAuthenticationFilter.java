package cn.nonocast.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import javax.servlet.http.HttpServletRequest;

public class ApiPreAuthenticationFilter extends AbstractPreAuthenticatedProcessingFilter {
	private static final Logger logger = LoggerFactory.getLogger(ApiPreAuthenticationFilter.class);

	public ApiPreAuthenticationFilter(AuthenticationManager authenticationManager) {
		setAuthenticationManager(authenticationManager);
	}

	@Override
	protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
//		logger.debug("TOKEN: " + request.getHeader("TOKEN"));
		return request.getHeader("Token");
	}

	@Override
	protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
		return "ignore";
	}
}
