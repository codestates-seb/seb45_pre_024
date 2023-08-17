package com.day24.preProject.config;

import com.day24.preProject.auth.filter.JwtAuthenticationFilter;
import com.day24.preProject.auth.filter.JwtVerificationFilter;
import com.day24.preProject.auth.handler.MemberAccessDeniedHandler;
import com.day24.preProject.auth.handler.MemberAuthenticationEntryPoint;
import com.day24.preProject.auth.handler.MemberAuthenticationFailureHandler;
import com.day24.preProject.auth.jwt.JwtTokenizer;
import com.day24.preProject.auth.userdetails.MemberDetailsService;
import com.day24.preProject.auth.utils.AuthorityUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class SecurityConfiguration {
    private final JwtTokenizer jwtTokenizer;
    private final AuthorityUtils authorityUtils;
    private final MemberDetailsService memberDetailsService;

    public SecurityConfiguration(JwtTokenizer jwtTokenizer, AuthorityUtils authorityUtils, MemberDetailsService memberDetailsService) {
        this.jwtTokenizer = jwtTokenizer;
        this.authorityUtils = authorityUtils;
        this.memberDetailsService = memberDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .headers().frameOptions().disable()
                .and()
                .csrf().disable()
                .cors(Customizer.withDefaults())
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                .httpBasic().disable()
                .exceptionHandling()
                .authenticationEntryPoint(new MemberAuthenticationEntryPoint())
                .accessDeniedHandler(new MemberAccessDeniedHandler())
                .and()
                .apply(new CustomFilterConfigurer())
                .and()
//                .authorizeRequests()
//                .antMatchers("/h2/**").permitAll();
                .authorizeHttpRequests(auth -> auth
                        .antMatchers("/h2/**").permitAll()
                        .antMatchers(HttpMethod.GET, "/question").permitAll()
                        .antMatchers(HttpMethod.POST, "/question").hasRole("USER")
                        .antMatchers(HttpMethod.POST, "/question/**").hasRole("USER")
                        .antMatchers(HttpMethod.PATCH, "/question/**").hasRole("USER")
                        .antMatchers(HttpMethod.DELETE, "/question/**").hasRole("USER")
                        .antMatchers(HttpMethod.POST, "/answer/**").hasRole("USER")
                        .antMatchers(HttpMethod.PATCH, "/answer/**").hasRole("USER")
                        .antMatchers(HttpMethod.DELETE, "/answer/**").hasRole("USER")
                        .anyRequest().permitAll()
                );
        return http.build();
    }
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST","PATCH","DELETE"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder) throws Exception {
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);

            JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, jwtTokenizer, new ObjectMapper());
            jwtAuthenticationFilter.setFilterProcessesUrl("/member/signin");
            jwtAuthenticationFilter.setAuthenticationFailureHandler(new MemberAuthenticationFailureHandler());

            JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtTokenizer, authorityUtils, memberDetailsService);
            builder.addFilter(jwtAuthenticationFilter)
                    .addFilterAfter(jwtVerificationFilter, JwtAuthenticationFilter.class);
        }
    }
}
