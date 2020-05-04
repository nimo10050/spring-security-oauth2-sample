package com.nimo.oauth.service.impl;

import com.nimo.oauth.domain.TbUser;
import com.nimo.oauth.mapper.TbUserMapper;
import com.nimo.oauth.service.TbUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TbUserServiceImpl implements TbUserService{

    @Resource
    private TbUserMapper tbUserMapper;

    @Override
    public List<TbUser> listUser(){
        return tbUserMapper.selectList(null);
    }

    @Override
    public TbUser queryUserByUsername(String username) {
//        Example example = new Example(TbUser.class);
//        example.createCriteria().andEqualTo("username", username);
//        return tbUserMapper.selectOneByExample(example);
        return null;
    }
}
