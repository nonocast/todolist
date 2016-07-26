package cn.nonocast.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import javax.servlet.http.HttpServletRequest;

public class ApiPreAuthenticationFilter extends AbstractPreAuthenticatedProcessingFilter {
	public ApiPreAuthenticationFilter(AuthenticationManager authenticationManager) {
		setAuthenticationManager(authenticationManager);
	}

	@Override
	protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
		return "nonocast@gmail.com";
	}

	@Override
	protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
		return "ignore";
	}
}
