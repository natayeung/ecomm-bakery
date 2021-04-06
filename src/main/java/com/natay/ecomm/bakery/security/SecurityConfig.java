package com.natay.ecomm.bakery.security;

import com.natay.ecomm.bakery.user.AccountService;
import com.natay.ecomm.bakery.user.UserAccount;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.util.Optional;

/**
 * @author natayeung
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final AccountService accountService;
    private final AuthenticationFailureHandler authenticationFailureHandler;

    public SecurityConfig(PasswordEncoder passwordEncoder, AccountService accountService, AuthenticationFailureHandler authenticationFailureHandler) {
        this.passwordEncoder = passwordEncoder;
        this.accountService = accountService;
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests()
                .antMatchers("/order")
                .hasRole("USER")
                .antMatchers("/", "/**")
                .permitAll()

                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/authenticate")
                .usernameParameter("email")
                .passwordParameter("password")
                .failureHandler(authenticationFailureHandler);
    }

    @Bean
    protected UserDetailsService userDetailsService() {
        return username -> {
            Optional<UserAccount> account = accountService.findAccountByEmail(username);
            return account
                    .map(acct -> UserCredentials.of(acct.email(), acct.password()))
                    .orElseThrow(() -> new UsernameNotFoundException(username + " not found"));

        };
    }
}