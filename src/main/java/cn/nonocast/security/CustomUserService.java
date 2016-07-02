package cn.nonocast.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import cn.nonocast.repository.UserRepository;
import cn.nonocast.model.User;

public class CustomUserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User result = userRepository.findByEmail(username);
        if(result == null) throw new UsernameNotFoundException("user not found");
        return result;
    }
}
