package org.geekhub.andrij.course_project.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/", "/signin", "/signup/**", "/about", "/contacts", "/reset_password").permitAll()
                .antMatchers("/css/**", "/js/**", "/images/**").permitAll()
                .antMatchers("/admin/*").hasRole("ADMIN")
                .antMatchers("/user/*").hasRole("USER")
                .anyRequest().authenticated()
            .and()
                .formLogin()
                .loginPage("/signin")
                .defaultSuccessUrl("/")
                .permitAll()
            .and()
                .rememberMe()
                .key("remember-me-key")
                .tokenValiditySeconds(86400)
            .and()
                .logout()
                .logoutUrl("/exit")
//                .logoutRequestMatcher(new AntPathRequestMatcher("/exit"))
                .logoutSuccessUrl("/")
                .permitAll()
            .and()
                .exceptionHandling().accessDeniedPage("/access_denied");;
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth, UserDetailsService userDetailsService) throws Exception {
        auth
            .userDetailsService(userDetailsService)
            .passwordEncoder(new BCryptPasswordEncoder());
    }
}