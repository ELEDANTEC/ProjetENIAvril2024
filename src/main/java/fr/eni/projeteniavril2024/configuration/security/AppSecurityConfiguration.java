package fr.eni.projeteniavril2024.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class AppSecurityConfiguration {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers("/", "/auctions", "/signin", "/css/**", "/images/**").permitAll()

                                .requestMatchers(HttpMethod.GET, "/*").hasRole("MEMBER")
                                .requestMatchers(HttpMethod.POST, "/*").hasRole("MEMBER")

                                .requestMatchers(HttpMethod.GET, "/*").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/*").hasRole("ADMIN")

                                .anyRequest().authenticated())

                .formLogin(form ->
                        form
                                .loginPage("/login").permitAll()
                                .defaultSuccessUrl("/"))

                .logout(logout ->
                        logout
                                .invalidateHttpSession(true)
                                .clearAuthentication(true)
                                .deleteCookies("JSESSIONID")
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .logoutSuccessUrl("/")
                                .permitAll())
        ;

        return http.build();
    }

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        String sqlUsername = "SELECT username, password, 1 FROM USERS WHERE username = ?;";
        String sqlAuthorities = "SELECT username, administrator FROM USERS WHERE username = ?;";
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.setUsersByUsernameQuery(sqlUsername);
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(sqlAuthorities);

        return jdbcUserDetailsManager;
    }
}
