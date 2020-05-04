package com.nimo.oauth2demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Map;

@Controller
public class AuthController {

    @Autowired
    RestTemplate restTemplate;

    /**
     * 接收 qq 认证返回的授权码
     *
     * @param code
     * @return
     */
    @RequestMapping("oauth/authorize/callback")
    public ModelAndView getAuthorizeCode(String code) {
        // 1.通过授权码，获取 token
        String accessToken = sendGetTokenRequest(code);
        // 2. 通过 token，获取用户信息
        HttpHeaders headers1 = new HttpHeaders();
        headers1.add("Authorization", "Bearer " + accessToken);
        ResponseEntity<Map> userInfo = restTemplate.exchange("http://localhost:8082/qq/getUser", HttpMethod.GET,
                new HttpEntity<>(null, headers1), Map.class);
        // 3. 携带用户信息，返回首页
        return new ModelAndView("index", "user", userInfo.getBody());
    }

    /**
     * 发送请求，获取 token
     * @param code
     * @return
     */
    private String sendGetTokenRequest(String code) {
        // 通过授权码，获取 token
        String oauth_token_url = "http://localhost:8082/qq/oauth/token";
        // 请求参数
        MultiValueMap<String, String> params = buildRequestParam(code);
        // 请求头
        HttpHeaders headers = buildRequestHeader();
        // send request
        ResponseEntity<Map> response = restTemplate.postForEntity(oauth_token_url, new HttpEntity<>(params, headers), Map.class);
        System.out.println("获取到的 token ： " + response.getBody());
        return response.getBody().get("access_token").toString();
    }


    /**
     * 获取 token 的请求参数
     * @param code
     * @return
     */
    private MultiValueMap<String, String> buildRequestParam(String code) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "authorization_code");
        map.add("scope", "user_info:read");
        map.add("code", code);
        map.add("redirect_uri", "http://localhost:8081/weibo/oauth/authorize/callback");
        return map;
    }

    /**
     * 获取 token 的请求头
     * @return
     */
    private HttpHeaders buildRequestHeader() {

        HttpHeaders headers = new HttpHeaders();
        // 请求头
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        String auth = "qq" + ":" + "123456";
        byte[] encodedAuth = Base64.getEncoder().encode(
                auth.getBytes(Charset.forName("US-ASCII")));
        String authHeader = "Basic " + new String(encodedAuth);
        headers.add("Authorization", authHeader);
        return headers;
    }


}
