package com.administracion_empleados.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import com.administracion_empleados.services.CustomUserDetailsService;

// import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        @Autowired
        private CustomUserDetailsService userDetailsService;

        @Autowired
        private PasswordEncoder passwordEncoder;

        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
                auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        }

        public void configure(WebSecurity web) throws Exception {
                web.ignoring().requestMatchers("/assets/css/**", "/assets/js/**", "/assets/img/**", "/assets/scss/**",
                                "/assets//**");
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                        .authorizeHttpRequests(authorize -> authorize
                                        .requestMatchers("/profile").hasRole("admin")
                                        .anyRequest().authenticated())
                        .formLogin(login -> login
                                        .loginPage("/login")
                                        .permitAll()
                                        .usernameParameter("username")
                                        .passwordParameter("password")
                                        .successHandler((request, response, authentication) -> response.sendRedirect("/home"))
                                        .failureUrl("/login?error=true"))
                        .logout(logout -> logout
                                        .logoutSuccessUrl("/login")
                                        .invalidateHttpSession(true)
                                        .deleteCookies("JSESSIONID")
                                        .permitAll())
                        .sessionManagement(session -> session
                                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                                        .invalidSessionUrl("/login?expired=true")
                                        .maximumSessions(1)
                                        .maxSessionsPreventsLogin(true)
                                        .expiredUrl("/login?expired=true")
                                        .sessionRegistry(sessionRegistry())
                                        .and()
                                        .sessionFixation().migrateSession())
                        .addFilterBefore(new SessionExpiredFilter(), ChannelProcessingFilter.class)
                        .exceptionHandling(exceptionHandling -> exceptionHandling
                                        .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login?expired=true"))
                                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.NOT_FOUND)));

                return http.build();
        }

        @Bean
        public SessionRegistry sessionRegistry() {
                return new SessionRegistryImpl();
        }

        @Bean
        public HttpSessionEventPublisher httpSessionEventPublisher() {
                return new HttpSessionEventPublisher();
        }
}
