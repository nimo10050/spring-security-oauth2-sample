//package com.nimo.oauth.config.service;
//
//import com.nimo.oauth.domain.TbUser;
//import com.nimo.oauth.service.TbPermissionService;
//import com.nimo.oauth.service.TbUserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.HashSet;
//import java.util.Set;
//
//@Service
//public class UserDetailsServiceImpl implements UserDetailsService {
//
//    @Autowired
//    private TbUserService tbUserService;
//
//    @Autowired
//    private TbPermissionService tbPermissionService;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        TbUser tbUser = tbUserService.queryUserByUsername(username);
//        if(tbUser!=null){
//            Set<GrantedAuthority> set = new HashSet<>();
//            //tbPermissionService
//            //GrantedAuthority grantedAuthority = new GrantedAuthority();
//            return new User(tbUser.getUsername(),tbUser.getPassword(),null);
//        }
//        return null;
//    }
//
//}
