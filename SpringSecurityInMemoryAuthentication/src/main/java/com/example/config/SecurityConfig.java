package com.example.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) {
        return httpSecurity.authorizeHttpRequests(registry -> {
            registry.requestMatchers("/", "/login", "/css/**").permitAll();
            registry.requestMatchers("/user").hasRole("USER");
            registry.requestMatchers("/admin").hasRole("ADMIN");
            registry.anyRequest().authenticated();
        })
                /*.formLogin(Customizer.withDefaults())
                .logout(Customizer.withDefaults())*/
                /*.formLogin(form->{
                    form
                            .loginPage("/login")
                            .defaultSuccessUrl("/",true)
                            .permitAll();
                })*/.formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer.loginPage("/login").successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

                        if (isAdmin) {
                            response.sendRedirect("/admin");
                        } else {
                            response.sendRedirect("/user");
                        }
                    }
                }).permitAll()).logout(logout -> {
                    logout.logoutSuccessUrl("/logout-success").permitAll();
                }).exceptionHandling(exception->{
                    exception.accessDeniedPage("/403");
                })
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        UserDetails normalUser = User.builder().username("user").password("$2a$12$nNuL3.cpWKWaAD0vrk0Npe7t8HcqixS2YEOnpqLUhBEgVpx91QCyS").roles("USER").build();
        UserDetails adminUser = User.builder().username("admin").password("$2a$12$1q2yedt0yGlZzXbsT7aFv.NMKqjHS8ZY.IzZqNwmscWRhtvvaFQHa").roles("USER", "ADMIN").build();
        UserDetails adminUser1 = User.builder().username("rahul").password("$2a$12$6cwy9Wale2EP0Id5nVBaFOQkA5Bl.qRbICsdSrJXnifmSWGGoGq7.").roles("USER", "ADMIN").build();
        return new InMemoryUserDetailsManager(normalUser, adminUser, adminUser1);
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
