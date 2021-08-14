package com.pro1.pro.service;

import com.pro1.pro.domain.Item;
import com.pro1.pro.domain.Member;
import com.pro1.pro.domain.UserItem;

import java.util.List;

public interface UserItemService {
    public void register(Member member, Item item) throws Exception;

    public List<UserItem> list(Long userNo) throws Exception;

    public UserItem read(Long userItemNo) throws Exception;



}
