package org.springframework.samples.acmerico.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

@SuppressWarnings("deprecation")
/**
 * @author japarejo
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/resources/**","/webjars/**","/h2-console/**").permitAll()
				.antMatchers(HttpMethod.GET, "/","/oups").permitAll()
				.antMatchers("/users/new").permitAll()
				.antMatchers("/admin/**").hasAnyAuthority("director")
				.antMatchers("/clients/**").hasAnyAuthority("worker","director")	
				.antMatchers("/creditcardapps/**").permitAll()
				.antMatchers("/employees/**").hasAnyAuthority("director")
				.antMatchers("/accounts/**").hasAnyAuthority("client","director")
				.antMatchers("/cards/**").hasAnyAuthority("client")
				.antMatchers("/personalData/**").hasAnyAuthority("client")
				.antMatchers("/personalDataEmployee/**").hasAnyAuthority("worker", "director")
				.antMatchers("/transferapps/**").hasAnyAuthority("client","worker","director")
				.antMatchers("/transferapps_mine/**").hasAnyAuthority("client", "worker", "director")
				.antMatchers("/mycreditcardapps/**").hasAnyAuthority("client")
				.antMatchers("/myloanapps/**").hasAnyAuthority("client")
				.antMatchers("/exchanges/**").hasAnyAuthority("client","worker","director")
				.antMatchers("/loans/**").hasAnyAuthority("client")
				.antMatchers("/loanapps/**").hasAnyAuthority("director", "worker", "client")
				.antMatchers("/grantedLoans/**").hasAnyAuthority("director")
				.antMatchers("/director/loans/**").hasAnyAuthority("director")
				.antMatchers("/debts/***").hasAnyAuthority("director","worker")
				.antMatchers("/dashboard/**").hasAnyAuthority("client", "director")
				.anyRequest().denyAll()
				.and()
				 	.formLogin()
				 	/*.loginPage("/login")*/
				 	.failureUrl("/login-error")
				.and()
					.logout()
						.logoutSuccessUrl("/"); 
                // Configuración para que funcione la consola de administración 
                // de la BD H2 (deshabilitar las cabeceras de protección contra
                // ataques de tipo csrf y habilitar los framesets si su contenido
                // se sirve desde esta misma página.
                http.csrf().ignoringAntMatchers("/h2-console/**");
                http.headers().frameOptions().sameOrigin();
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
	      .dataSource(dataSource)
	      .usersByUsernameQuery(
	       "select username,password,enabled "
	        + "from users "
	        + "where username = ?")
	      .authoritiesByUsernameQuery(
	       "select username, authority "
	        + "from authorities "
	        + "where username = ?")	      	      
	      .passwordEncoder(passwordEncoder());	
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {	    
		PasswordEncoder encoder =  NoOpPasswordEncoder.getInstance();
	    return encoder;
	}
	
}


