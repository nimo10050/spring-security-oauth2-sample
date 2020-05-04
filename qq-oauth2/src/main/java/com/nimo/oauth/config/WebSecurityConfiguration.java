package com.nimo.oauth.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity
//@Order(-1)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {


    /**
     * 高版本以后必须配置这个
     * @return
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    @Override
//    protected AuthenticationManager authenticationManager() throws Exception {
//        return super.authenticationManager();
//    }
//
    /**
     * 基于内存的用户权限配置
     * @return
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin").password(passwordEncoder().encode("123456")).roles("ADMIN")
                .and()
                .withUser("xiaoming").password(passwordEncoder().encode("123456")).roles("VISITOR")
        ;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/oauth/token").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .csrf().disable()
                .httpBasic();
//        http.cors().and()
//                .csrf().disable()
//                .httpBasic();
//        http.formLogin().and()
//                .antMatchers("/login","/oauth/*").permitAll()
//                .authorizeRequests()
//                .anyRequest()
//                .authenticated()
                ;//设置符合条件的端点通过，不被保护
    }

//    @Bean
//    @Override
//    public UserDetailsService userDetailsService(){
//        return new UserDetailsServiceImpl();
//    }
//
//    /**
//     * 基于 jdbc 权限配置
//     * @param auth
//     * @throws Exception
//     */
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService());
//    }
}
