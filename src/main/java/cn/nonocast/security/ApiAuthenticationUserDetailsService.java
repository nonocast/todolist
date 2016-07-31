package cn.nonocast.security;

import cn.nonocast.model.Token;
import cn.nonocast.repository.UserRepository;
import cn.nonocast.service.TokenService;
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
			try {
				Token p = tokenService.get(principal);
				result = userRepository.findByEmail(p.getEmail());
			} catch(Exception ex) {
				throw new UsernameNotFoundException("");
			}
		}

		return result;
	}
}
