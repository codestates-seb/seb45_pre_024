package com.day24.preProject.config;

import com.day24.preProject.auth.filter.JwtAuthenticationFilter;
import com.day24.preProject.auth.filter.JwtVerificationFilter;
import com.day24.preProject.auth.handler.MemberAccessDeniedHandler;
import com.day24.preProject.auth.handler.MemberAuthenticationEntryPoint;
import com.day24.preProject.auth.handler.MemberAuthenticationFailureHandler;
import com.day24.preProject.auth.handler.Oauth2memberSuccessHandler;
import com.day24.preProject.auth.jwt.JwtTokenizer;
import com.day24.preProject.auth.utils.AuthorityUtils;
import com.day24.preProject.member.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.net.MalformedURLException;
import java.util.Arrays;

@Configuration
@EnableWebMvc
public class SecurityConfiguration {
    private final JwtTokenizer jwtTokenizer;
    private final AuthorityUtils authorityUtils;
    private final MemberService memberService;
    private final OAuth2AuthorizedClientService auth2AuthorizedClientService;

    public SecurityConfiguration(JwtTokenizer jwtTokenizer, AuthorityUtils authorityUtils, MemberService memberService, OAuth2AuthorizedClientService auth2AuthorizedClientService) {
        this.jwtTokenizer = jwtTokenizer;
        this.authorityUtils = authorityUtils;
        this.memberService = memberService;
        this.auth2AuthorizedClientService = auth2AuthorizedClientService;
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
                        .antMatchers("member/signin").permitAll()
                        .antMatchers(HttpMethod.GET, "/question").permitAll()
                        .antMatchers(HttpMethod.POST, "/question").hasRole("USER")
                        .antMatchers(HttpMethod.POST, "/question/**").hasRole("USER")
                        .antMatchers(HttpMethod.PATCH, "/question/**").hasRole("USER")
                        .antMatchers(HttpMethod.DELETE, "/question/**").hasRole("USER")
                        .antMatchers(HttpMethod.POST, "/answer/**").hasRole("USER")
                        .antMatchers(HttpMethod.PATCH, "/answer/**").hasRole("USER")
                        .antMatchers(HttpMethod.DELETE, "/answer/**").hasRole("USER")
                        .anyRequest().permitAll()
                )
                .oauth2Login(oauth2 -> {
                            try {
                                oauth2
                                        .successHandler(new Oauth2memberSuccessHandler(jwtTokenizer, memberService, auth2AuthorizedClientService));
                            } catch (MalformedURLException e) {
                                throw new RuntimeException(e);
                            }
                        }
                );
        return http.build();
    }
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://pre-project.s3-website.ap-northeast-2.amazonaws.com","*"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST","PATCH","DELETE"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder) throws Exception {
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);

            JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, jwtTokenizer, new ObjectMapper());
            jwtAuthenticationFilter.setFilterProcessesUrl("/member/signin");
            jwtAuthenticationFilter.setAuthenticationFailureHandler(new MemberAuthenticationFailureHandler());

            JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtTokenizer, authorityUtils, memberService);
            builder.addFilter(jwtAuthenticationFilter)
                    .addFilterAfter(jwtVerificationFilter, OAuth2LoginAuthenticationFilter.class);
        }
    }
}
