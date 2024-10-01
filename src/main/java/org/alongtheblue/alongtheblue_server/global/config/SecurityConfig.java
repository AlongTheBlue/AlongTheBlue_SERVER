package org.alongtheblue.alongtheblue_server.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private static final String[] WHITE_LIST = {
		"/v3/api-docs/**", "/swagger-ui/**", "/swagger-resources/**", "/", "/api/**", "/error/**", "/swagger", "/swagger/**", "/oauth/**"
	};

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests(request -> request
				.requestMatchers(WHITE_LIST).permitAll()
				.anyRequest().authenticated()
			)
			.formLogin(withDefaults());

		return http.build();
	}
}
