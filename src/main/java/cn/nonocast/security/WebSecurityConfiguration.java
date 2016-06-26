package cn.nonocast.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .inMemoryAuthentication()
                .withUser("nonocast").password("123456").roles("USER").and()
                .withUser("admin").password("123456").roles("ADMIN");
    }

    @Configuration
    @Order(1)
    public static class AdminWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/admin/**")
                .authorizeRequests()
                .antMatchers("/admin/login").permitAll()
                .anyRequest().hasRole("ADMIN").and()
                .formLogin().loginPage("/admin/login").defaultSuccessUrl("/admin/console").permitAll().and()
                .logout().logoutSuccessUrl("/").permitAll().and()
                .rememberMe().tokenValiditySeconds(2419200).key("blackapr0n");
        }
    }

    @Configuration
    @Order(2)
    public static class UserWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring().antMatchers("/webjars/**", "/resources/**");
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                .antMatchers("/").permitAll()
                .anyRequest().authenticated().and()
                .formLogin().loginPage("/login").defaultSuccessUrl("/home").permitAll().and()
                .logout().logoutSuccessUrl("/").permitAll().and()
                .rememberMe().tokenValiditySeconds(2419200).key("blackapr0n");
        }
    }
}
