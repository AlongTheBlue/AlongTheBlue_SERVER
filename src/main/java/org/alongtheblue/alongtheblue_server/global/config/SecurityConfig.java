package com.example.promotionpage.global.config;

import static org.springframework.security.config.Customizer.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private static final String[] WHITE_LIST = {
		"/v3/api-docs/**", "/swagger-ui/**", "/swagger-resources/**", "/api/**", "/error/**", "/", "/swagger", "/swagger/**"
	};

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests(request -> request
				.requestMatchers(new AntPathRequestMatcher("/admin/**")).authenticated()
				.requestMatchers(WHITE_LIST).permitAll()
			)
			.formLogin(withDefaults());

		return http.build();
	}
}
