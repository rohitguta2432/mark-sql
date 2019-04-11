package com.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * @author RITESH SINGH
 * @since JDK 1.8
 * @version 1.0
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("markAuthenticationProvider")
	private AuthenticationProvider authenticationProvider;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/*http.csrf().disable().headers().frameOptions().disable()
				.and().authorizeRequests()
				.antMatchers("/", "/favicon.ico", "/resources/**").permitAll()
				.antMatchers("/index1/**").hasAnyRole("ADMIN").anyRequest().authenticated()
				.and()
				.formLogin()
				.loginPage("/").permitAll().loginProcessingUrl("/authenticate")
				.usernameParameter("j_username").passwordParameter("j_password").defaultSuccessUrl("/index").and()
				.logout().logoutUrl("/logout").logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.clearAuthentication(true).invalidateHttpSession(true).logoutSuccessUrl("/logoutsuccessfully")
				.permitAll().and().exceptionHandling().accessDeniedPage("/403");*/
		
		http.
		authorizeRequests()
			.antMatchers("/css/", "/favicon.ico", "/resources/**").permitAll()		
			.antMatchers("/login","/user/get-user/**").permitAll()
			.antMatchers("/web/**").hasAuthority("ROLE_SUPER_ADMIN")
			.antMatchers("/company/create").hasAuthority("ROLE_SUPER_ADMIN")
			.antMatchers("/user/driver-list").hasAuthority("ROLE_FLEET_MANAGER")
			.anyRequest()
			.authenticated().and().csrf().disable().formLogin()
			.loginPage("/login").permitAll().loginProcessingUrl("/authenticate")
			.usernameParameter("j_username").passwordParameter("j_password")
			.defaultSuccessUrl("/dashboard")
			.failureUrl("/login?error=true")
			.and().logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/login").and().exceptionHandling()
			.accessDeniedPage("/access-denied");
		
		http.sessionManagement().maximumSessions(1).expiredUrl("/login?expired");	
		
	}

	@Override
	@Bean(name = "authenticationManager")
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Bean
	public RequestContextListener requestContextListener(){
	    return new RequestContextListener();
	}
	
	@Bean 
	public MultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(); 
		multipartResolver.setMaxUploadSize(50*1024*1024); 
		return multipartResolver; 
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web
	       .ignoring()
	       .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
	}	
}