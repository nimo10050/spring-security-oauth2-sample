package com.nimo.oauth.controller;

import com.nimo.oauth.model.LoginUserReq;
import com.nimo.oauth.utils.CryptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@RestController
public class UserController {

    @RequestMapping("/getUser")
    public Object user() {
        HashMap<String, Object> user = new HashMap<>();
        user.put("username", "zhangsan");
        return user;
    }

    @Autowired(required = false)
    private AuthenticationManager authenticationManager;

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private AuthorizationServerTokenServices authorizationServerTokenServices;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping(value = "/login")
    @ResponseBody
    public Object doLogin(
            HttpServletRequest request,
            @RequestBody LoginUserReq req) throws Exception {

        // 对请求头进行 base64 解码, 获取 client id 和 client secret
        String[] tokens = CryptUtils.decodeBasicHeader(request.getHeader("Authorization"));
        String clientId = tokens[0];
        String clientSecret = tokens[1];

        // 通过 clientId 获取客户端详情
        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);

        // 校验 ClientDetails
        if (clientDetails == null) {
            throw new UnapprovedClientAuthenticationException("Unknown client id : " + clientId);
        }

        if (!passwordEncoder.matches(clientSecret, clientDetails.getClientSecret())) {
            throw new UnapprovedClientAuthenticationException("Invalid client secret for client id : " + clientId);
        }

        // 通过 username 和 password 构建一个 Authentication 对象
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(req.getUsername(),
                req.getPassword());

        // 验证用户信息
        Authentication auth = authenticationManager.authenticate(authRequest);
        // 放入 Secirty 的上下文
        SecurityContextHolder.getContext().setAuthentication(auth);

        // 通过 Client 信息和 请求参数, 获取一个 TokenRequest 对象
        TokenRequest tokenRequest = new TokenRequest(new HashMap<String, String>(), clientId,
                clientDetails.getScope(), "password");

        // 通过 TokenRequest 和 ClientDetails 构建 OAuthRequest
        OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);

        // 通过 OAuth2Request 和 Authentication 构建OAuth2Authentication
        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, auth);

        // 通过 OAuth2Authentication 构建 OAuth2AccessToken
        OAuth2AccessToken token = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);

        return token;


    }


}
