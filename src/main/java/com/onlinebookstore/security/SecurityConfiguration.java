package com.onlinebookstore.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;




@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	private final AuthenticationProvider authenticationProvider;
	private final JwtService jwtService;
	private final UserDetailsService userDetailsService;

//    @Qualifier("HandlerExceptionResolver")
	private HandlerExceptionResolver exceptionResolver;
	private BlacklistService blacklistService;


    @Autowired
    public SecurityConfiguration(AuthenticationProvider authenticationProvider, JwtService jwtService,
                                 UserDetailsService userDetailsService, @Qualifier("handlerExceptionResolver") HandlerExceptionResolver exceptionResolver) {
        this.authenticationProvider = authenticationProvider;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.exceptionResolver = exceptionResolver;
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtService, userDetailsService, exceptionResolver,blacklistService);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer :: disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("api/auth/**",
                                "/v2/api-docs",
                                "/v3/api-docs",
                                "/v3/api-docs/**",
                                "/swagger-resources",
                                "/swagger-resources/**",
                                "/swagger-ui/**",
                                "/configuration/ui",
                                "/configuration/security",
                                "/webjars/**",
                                "/swagger-ui.html").permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


    protected void configure(HttpSecurity http) throws Exception {
        http
        .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                		.requestMatchers("/api/admin/**").hasRole("ADMIN")
                		.requestMatchers("/api/employee/**").hasRole("EMPLOYEE")
                		.requestMatchers("/api/attendance/**").hasAnyRole("ADMIN","EMPLOYEE")
                		.requestMatchers("/api/notification/**").hasRole("ADMIN")
                		.requestMatchers("/api/attendance/report**").hasAnyRole("ADMIN","EMPLOYEE")
                		.requestMatchers("/api/overall**").hasAnyRole("ADMIN")
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .permitAll())
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll());
    }
}
