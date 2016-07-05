package cn.nonocast.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Bean
    UserDetailsService customUserService() {
        return new CustomUserService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserService())
            .passwordEncoder(passwordEncoder());
    }

    @Configuration
    @Order(1)
    public static class AdminWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/admin/**")
                .authorizeRequests()
                .antMatchers("/admin/login").permitAll()
                .anyRequest().hasRole("ADMIN").and()
                .formLogin().loginPage("/admin/login").defaultSuccessUrl("/admin/users").permitAll().and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/").permitAll();
        }
    }

    @Configuration
    @Order(2)
    public static class UserWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
        @SuppressWarnings("SpringJavaAutowiringInspection")
        @Autowired
        private UserDetailsService service;

        @Bean
        public RememberMeServices rememberMeServices() throws Exception {
            TokenBasedRememberMeServices result = new TokenBasedRememberMeServices("hell0w0r1d", service);
            result.setTokenValiditySeconds(2419200);
            return result;
        }

        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring().antMatchers("/webjars/**", "/resources/**");
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                .antMatchers("/", "/error", "/wechat/*").permitAll()
                .anyRequest().authenticated().and()
                .formLogin().loginPage("/login").defaultSuccessUrl("/home").permitAll().and()
                .rememberMe().rememberMeServices(rememberMeServices()).tokenValiditySeconds(2419200).key("hell0w0r1d").and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/").permitAll();
        }
    }
}
