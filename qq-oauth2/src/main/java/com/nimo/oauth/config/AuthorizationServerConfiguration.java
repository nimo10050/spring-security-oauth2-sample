package com.nimo.oauth.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    //@Autowired
    //private AuthenticationManager authenticationManager;

//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//        // 密码模式必填
//        endpoints.authenticationManager(authenticationManager);
//    }

    /**
     * 基于内存的 client 和 token
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()// 基于内存存储令牌
                .withClient("qq")// 表示客户端
                .secret(passwordEncoder.encode("123456")) // app_secret
                .authorizedGrantTypes("authorization_code")// 授权码模式
                .scopes("user_info:read")// 授权范围
                .redirectUris("http://localhost:8081/weibo/oauth/authorize/callback")// 重定向地址
                .accessTokenValiditySeconds(600)
                //.refreshTokenValiditySeconds(3600);
        // 配置 n 多个客户端.and().withClient().secret()
        ;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients()
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
                .passwordEncoder(this.passwordEncoder);
    }

//-------------------------------- 以下是基于 jdbc 的令牌存储方式---------------------------------------

//    /**
//     * 基于 jdbc 的 client
//     * @param clients
//     * @throws Exception
//     */
//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.withClientDetails(clientDetailsService());// clientDetailsService
//    }
//
//    /**
//     * 需要告诉 oauth2 令牌存储也要通过 jdbc 的方式
//     * @param endpoints
//     * @throws Exception
//     */
//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
//        endpoints.tokenStore(tokenStore());
//    }
//
//    @Bean
//    @Primary// 覆盖数据源
//    @ConfigurationProperties("spring.datasource")
//    public DataSource dataSource(){
//        return DataSourceBuilder.create().build();
//    }
//
//    @Bean
//    public JdbcTokenStore tokenStore(){
//        return new JdbcTokenStore(dataSource());
//    }
//
//    @Bean
//    public JdbcClientDetailsService clientDetailsService(){
//        return new JdbcClientDetailsService(dataSource());
//    }

}
