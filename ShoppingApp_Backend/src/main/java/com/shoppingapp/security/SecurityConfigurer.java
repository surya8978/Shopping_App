package com.shoppingapp.security;

import com.shoppingapp.security.JWT.JwtEntryPoint;
import com.shoppingapp.security.JWT.JwtRequestFilter;
import com.shoppingapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;

@EnableWebSecurity
@Slf4j
@Configuration
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {
	
	@Autowired
	UserService userDetailsService;
	@Autowired
	private JwtEntryPoint accessDenyHandler;
	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		log.debug("Entering AuthenticationManagerBuider configure method!!!");
		auth.authenticationProvider(authProvider());
		log.debug("Exiting AuthenticationManagerBuider configure method!!!");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		log.debug("Entering HttpSecurity configure method!!!");
		http.csrf().disable().authorizeRequests()
				.antMatchers("/shopping/**","/**").permitAll().anyRequest().authenticated()
				.and()
				.exceptionHandling().authenticationEntryPoint(accessDenyHandler)
				.and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.cors().disable().addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		log.debug("Exiting HttpSecurity configure method!!!");
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		log.debug("Creation of Authentication Manager Bean successful!!!");
		return super.authenticationManagerBean();
	}

	@Override
	public void configure(WebSecurity webSecurity) throws Exception {
		webSecurity.ignoring().antMatchers(HttpMethod.POST, "/refresh")
				.antMatchers(HttpMethod.OPTIONS, "/**")
				.and().ignoring()
				.antMatchers(HttpMethod.GET, "/" // Other Stuff You want to Ignore
				).and().ignoring()
				.antMatchers("/swagger-ui.html","/v2/api-docs","/configuration/ui",
		"/swagger-resources/**","/configuration/security","/webjars/**","/auth/swagger","/elk","/exception");
	}
	

	
	@Bean
	public DaoAuthenticationProvider authProvider() {

	  DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	  authProvider.setUserDetailsService(userDetailsService);
	  authProvider.setPasswordEncoder(passwordEncoder);
	  return authProvider;

	}

}
