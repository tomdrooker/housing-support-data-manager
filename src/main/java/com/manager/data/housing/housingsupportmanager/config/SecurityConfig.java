package com.manager.data.housing.housingsupportmanager.config;

import com.manager.data.housing.housingsupportmanager.FlashMessage;
import com.manager.data.housing.housingsupportmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {

        //Always allow access to assets
        web.ignoring().antMatchers("../resources/static/assets/**");
        web.ignoring().antMatchers("../resources/static/stylesheets/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Ensure all requests have admin role
        http
                .authorizeRequests()
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    .and()
                .formLogin()
                    .loginPage("/sign-in")
                    .permitAll()
                    .successHandler(loginSuccessHandler())
                    .failureHandler(loginFailureHandler())
                    .and()
                .logout()
                    .permitAll()
                    .logoutSuccessUrl("/home");
    }

    public AuthenticationSuccessHandler loginSuccessHandler() {
        return (request, response, authentication) ->
                response.sendRedirect("./home");
    }

    public AuthenticationFailureHandler loginFailureHandler() {
        return (request, response, authentication) -> {
            // Display message when credentials are incorrect - also need to add to template
            request.getSession().setAttribute("flash", new FlashMessage("Please enter a valid username and password", FlashMessage.Status.FAILURE));
            response.sendRedirect("./sign-in");
        };
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

}
