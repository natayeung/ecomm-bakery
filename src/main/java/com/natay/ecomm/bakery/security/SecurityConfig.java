package com.natay.ecomm.bakery.security;

import com.natay.ecomm.bakery.account.AccountService;
import com.natay.ecomm.bakery.account.Account;
import com.natay.ecomm.bakery.security.authentication.UserCredentials;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
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

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean(name = BeanIds.USER_DETAILS_SERVICE)
    protected UserDetailsService userDetailsService() {
        return username -> {
            Optional<Account> account = accountService.findAccountByEmail(username);
            return account
                    .map(acct -> UserCredentials.of(acct.email(), acct.password()))
                    .orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
        };
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
                .antMatchers("/order/**", "/account/**")
                .hasRole("USER")
                .antMatchers("/", "/**")
                .permitAll()

                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .loginProcessingUrl("/authenticate")
                .usernameParameter("email")
                .passwordParameter("password")
                .failureHandler(authenticationFailureHandler)

                .and()
                .oauth2Login()
                .loginPage("/login")
                .defaultSuccessUrl("/");
    }
}
