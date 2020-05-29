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
		String director = "director";
		String worker = "worker";
		String client = "client";

		http.authorizeRequests().antMatchers("/resources/**", "/webjars/**", "/h2-console/**").permitAll()
				.antMatchers(HttpMethod.GET, "/", "/oups").permitAll()
				// users
				.antMatchers("/users/new").permitAll()
				// clients
				.antMatchers("/clients").hasAnyAuthority(worker, director).antMatchers("/clients/{clientId}/**")
				.hasAnyAuthority(worker, director).antMatchers("/clients/find").hasAnyAuthority(worker, director)
				.antMatchers("/clients/new").hasAnyAuthority(director)
				// credit card app
				.antMatchers("/creditcardapps").hasAnyAuthority(director, worker)
				.antMatchers("/creditcardapps/{creditcardappsId}/**").hasAnyAuthority(director, worker, client)
				.antMatchers("/creditcardapps/created").hasAnyAuthority(client)
				.antMatchers("/creditcardapps/limit-reached").hasAnyAuthority(client)
				.antMatchers("/mycreditcardapps/**").hasAnyAuthority(client)
				// employees
				.antMatchers("/employees/**").hasAnyAuthority(director)
				// accounts
				.antMatchers("/accounts/**").hasAnyAuthority(client)
				// credit card
				.antMatchers("/cards/**").hasAnyAuthority(client)
				// personal data
				.antMatchers("/personalData/**").hasAnyAuthority(client).antMatchers("/personalDataEmployee/**")
				.hasAnyAuthority(worker, director)
				// transfer app
				.antMatchers("/transferapps").hasAnyAuthority(worker, director)
				.antMatchers("/transferapps/{transferappsId}/accept/{bankAccountId}").hasAnyAuthority(worker, director)
				.antMatchers("/transferapps/{transferappsId}/refuse/{bankAccountId}").hasAnyAuthority(worker, director)
				.antMatchers("/transferapps/{bankAccountId}/new").hasAnyAuthority(client)
				.antMatchers("/transferapps_mine/**").hasAnyAuthority(client)
				.antMatchers("/transferapps/{transferappsId}").permitAll()
				// loan app
				.antMatchers("/loanapps/collect").hasAnyAuthority(director).antMatchers("/loanapps")
				.hasAnyAuthority("director", worker).antMatchers("/loanapps/{loanappsId}/accept")
				.hasAnyAuthority(director, worker).antMatchers("/loanapps/{loanappsId}/refuse")
				.hasAnyAuthority(director, worker).antMatchers("/loanapps/{loanId}/new/{bankAccountId}")
				.hasAnyAuthority(client).antMatchers("/myloanapps").hasAnyAuthority(client)
				.antMatchers("/loanapps/{loanappsId}").permitAll()
				// exchanges
				.antMatchers("/exchanges/**").permitAll()
				// loans
				.antMatchers("/loans/**").hasAnyAuthority(client).antMatchers("/grantedLoans/**")
				.hasAnyAuthority(director)
				// debts
				.antMatchers("/debts/***").hasAnyAuthority(director, worker)
				// dashboard
				.antMatchers("/dashboard").hasAnyAuthority(client).antMatchers("/dashboard/{clientId}/statics")
				.hasAnyAuthority(director).anyRequest().denyAll().and().formLogin()
				/* .loginPage("/login") */
				.failureUrl("/login-error").and().logout().logoutSuccessUrl("/");
		// Configuración para que funcione la consola de administración
		// de la BD H2 (deshabilitar las cabeceras de protección contra
		// ataques de tipo csrf y habilitar los framesets si su contenido
		// se sirve desde esta misma página.
		http.csrf().ignoringAntMatchers("/h2-console/**");
		http.headers().frameOptions().sameOrigin();
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource)
				.usersByUsernameQuery("select username,password,enabled " + "from users " + "where username = ?")
				.authoritiesByUsernameQuery("select username, authority " + "from authorities " + "where username = ?")
				.passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

}
