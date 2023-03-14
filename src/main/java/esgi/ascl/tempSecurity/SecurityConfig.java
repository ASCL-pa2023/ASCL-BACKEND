package esgi.ascl.tempSecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    /*
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests

                                //API entière
                                .requestMatchers(HttpMethod.GET, "/api/v1/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/v1/**").permitAll() //KO
                                //.requestMatchers(HttpMethod.POST, "/api/v1/**").hasAnyAuthority("ADMIN") //KO
                                //.requestMatchers(HttpMethod.POST, "/api/v1/**").hasAnyRole("ADMIN") //KO


                                //Directement sur create
                                .requestMatchers(HttpMethod.POST, "/api/v1/message/**").hasRole("ADMIN")
                                //.requestMatchers(HttpMethod.POST, "/api/v1/message/create").hasRole("ADMIN")

                )
                .httpBasic(withDefaults());
        return http.build();
    }

     */

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("ADMIN")
                .authorities("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user);
    }



}
