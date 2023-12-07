package com.dam.uasz.sbcar3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;


import com.dam.uasz.sbcar3.service.UserDetailsServiceImpl;


@Configuration
@EnableWebSecurity
public class SecurityConfig{
	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;

    @Bean
    SecurityFilterChain configureSecurity(HttpSecurity http) throws Exception {
    	http.csrf().disable()
    	.cors().and()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
		.authorizeHttpRequests().requestMatchers(HttpMethod.POST, "/login").permitAll()
		.anyRequest().authenticated()
		.and()
		.httpBasic(withDefaults());
        return http.build();
    }
    
    @Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception  {
		auth.userDetailsService(userDetailsService)
		.passwordEncoder(new BCryptPasswordEncoder());
	}

    @Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) 
			throws Exception {
	    return authenticationConfiguration.getAuthenticationManager();
	}
}