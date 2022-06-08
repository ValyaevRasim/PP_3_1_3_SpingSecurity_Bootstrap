package spring_boot.configs;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity(debug = true)
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(securedEnabled = true) //защищаем отдельные методы
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final SuccessUserHandler successUserHandler;
    public WebSecurityConfig(@Qualifier("userDetailServiceImpl") UserDetailsService userDetailsService,
                             SuccessUserHandler successUserHandler) {
        this.successUserHandler = successUserHandler;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
//        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/admin/**").hasAuthority("ADMIN")
                .antMatchers("/user/**").hasAnyAuthority("ADMIN", "USER")
//                .antMatchers("/admin/**").hasRole("ADMIN") // в б.д надо чтобы роли начинались на ROLE_
//                .antMatchers("/user/**").hasAnyRole("ADMIN", "USER")
                .antMatchers("/", "/index").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().successHandler(successUserHandler) //переадресовывает пользователя на соответствующую страницу
        ;
    }


// аутентификация inMemory
//******************************
//authorities - без приставки ROLE_. D configure(HttpSecurity http) д.б открыт доступ к url .antMatchers("/admin/**").hasAuthority("ADMIN")


//******************************
// roles - без приставки ROLE_. D configure(HttpSecurity http) д.б открыт доступ к url .antMatchers("/admin/**").hasRole("ROLE_ADMIN")

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("admin")
//                .password(passwordEncoder().encode("admin"))
//                .roles("ADMIN")
//                .and()
//                .withUser("user")
//                .password(passwordEncoder().encode("user"))
//                .roles("USER")
//        ;
//    }


//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        PasswordEncoder encoder = new BCryptPasswordEncoder();
//        auth.inMemoryAuthentication()
//                .withUser("admin")
//                .password(encoder.encode("admin"))
//                .authorities("ADMIN","USER") // в configure(HttpSecurity http) д.б открыт доступ к url .antMatchers("/admin/**").hasAuthority("ADMIN")
//                .and()
//                .withUser("user")
//                .password(passwordEncoder().encode("user"))
//                .authorities("USER") // в configure(HttpSecurity http) д.б открыт доступ к url .antMatchers("/user/**").hasRole("USER")
//        ;
//    }


}
