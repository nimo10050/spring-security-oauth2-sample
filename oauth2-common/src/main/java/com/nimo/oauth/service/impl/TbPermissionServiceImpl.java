package com.nimo.oauth.service.impl;

import com.nimo.oauth.mapper.TbPermissionMapper;
import com.nimo.oauth.service.TbPermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TbPermissionServiceImpl implements TbPermissionService{

    @Resource
    private TbPermissionMapper tbPermissionMapper;

}
