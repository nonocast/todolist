package cn.nonocast.security;

import cn.nonocast.repository.UserRepository;
import cn.nonocast.service.TokenService;
import cn.nonocast.model.Token;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class ApiAuthenticationUserDetailsService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TokenService tokenService;

	@Override
	public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token) throws UsernameNotFoundException {
		String principal = (String) token.getPrincipal();

		UserDetails result = null;
		if(!Strings.isNullOrEmpty(principal)) {
			Token p = tokenService.get(principal);
			result = userRepository.findOne(p.getId());
		}

		return result;
	}
}
