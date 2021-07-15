package com.example.boutique;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity

                // URL autorizations:
                .authorizeRequests()
                // The order of the rules matters and the more specific rules should go first.
                // ne pas oublier le / devant les URLs
                .antMatchers("/", "/index.html","/user/registration").permitAll()
                .anyRequest().authenticated()

                // Authentication mode:
                .and().formLogin()  //  redirect to /login HTML page and then to the resource after successfull authentication

                // Authentication mode:
                .and().httpBasic()        // for web API auth

                //disable csrf protection
                .and().csrf().disable()
        ;
    }

   /* @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication()
                .withUser("admin").password(getPasswordEncoder().encode("secret")).authorities("ADMIN","USER").and()
                .withUser("user").password(getPasswordEncoder().encode("user")).authorities("USER").and()
                .withUser("jdoe").password(getPasswordEncoder().encode("unknown")).disabled(true).authorities("USER");
    }*/

    @Autowired
    private DataSource dataSource;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().passwordEncoder(getPasswordEncoder()).dataSource(dataSource)
                .usersByUsernameQuery("select username, password, enabled from users where username=?")
                .authoritiesByUsernameQuery("select username, role from users where username=?");
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        // return NoOpPasswordEncoder.getInstance(); //Pour mettre les password en clair
        return new BCryptPasswordEncoder();
    }


}
