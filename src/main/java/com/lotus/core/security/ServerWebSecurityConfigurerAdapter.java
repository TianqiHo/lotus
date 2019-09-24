package com.lotus.core.security;
/*
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class ServerWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/contol/**","/css/**","/images/**","/js/**");
		super.configure(web);
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		/*auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
        .withUser("hetianqi")
        .password(new BCryptPasswordEncoder().encode("123"))
        .roles("UP1", "UP2")
        .and()
        .withUser("root")
        .password(new BCryptPasswordEncoder().encode("123"))
        .roles("UP2", "UP3");
		super.configure(auth);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		 http
		 .authorizeRequests()
		 .antMatchers("/signIn","/contol/**","/css/**","/images/**","/js/**")
		 .permitAll()
		 .anyRequest()
		 .authenticated()
		 .and()
		 .formLogin()
		 .loginPage("/signIn")
		 .loginProcessingUrl("/user/login")
		 // .defaultSuccessUrl("/index")
		  .and()
         .authorizeRequests()
         .antMatchers("/signIn")
         .permitAll()
         .anyRequest()
         .authenticated()
         .and()
         .csrf()
         .disable();

		 
		 
		/* http.formLogin()
         .loginPage("/signIn")
         .loginProcessingUrl("/user/login")
         .and()
         .authorizeRequests()
         .antMatchers("/signIn")
         .permitAll()
         .anyRequest()
         .authenticated()
         .and()
         .csrf()
         .disable();
		//super.configure(http);
	}
}*/
