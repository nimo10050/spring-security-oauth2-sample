package com.nimo.oauth.service;

import com.nimo.oauth.domain.TbUser;

import java.util.List;

public interface TbUserService{

    List<TbUser> listUser();

    TbUser queryUserByUsername(String username);

}
