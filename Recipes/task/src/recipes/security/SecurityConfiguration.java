package recipes.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import recipes.User;
import recipes.data.UserRepository;

import java.util.Optional;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().and()
                  .csrf()
                  .disable()
                  .headers()
                  .frameOptions()
                  .disable()
                .and()
                .authorizeRequests()
                  .antMatchers("/api/register").permitAll()
                .antMatchers("/actuator/shutdown").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                  .antMatchers("/api/recipe/**").hasRole("USER")
                  .anyRequest().authenticated()
                .and()
                  .formLogin().defaultSuccessUrl("/success", true)
                .successHandler((request, response, authentication) -> response.setStatus(200));


                //.permitAll()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .authorizeRequests()
//                .antMatchers("/api").hasRole("USER")
//                .antMatchers("/", "/**").permitAll()


    }


    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepo) {
        return email -> {
            Optional<User> user = userRepo.findByUsername(email);
            if (user.isPresent()) return user.get();

            throw new UsernameNotFoundException("User '" + email + "' not found");
        };
    }
}
