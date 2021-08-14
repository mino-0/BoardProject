package com.pro1.pro.service;

import com.pro1.pro.domain.Item;
import com.pro1.pro.domain.Member;
import com.pro1.pro.domain.PayCoin;
import com.pro1.pro.domain.UserItem;
import com.pro1.pro.repository.MemberRepository;
import com.pro1.pro.repository.PayCoinRepository;
import com.pro1.pro.repository.UserItemRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import javax.persistence.Transient;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserItemServiceImpl implements UserItemService{
    private final UserItemRepository userItemRepository;
    private final PayCoinRepository payCoinRepository;
    private final MemberRepository memberRepository;

    @Transient
    @Override
    public void register(Member member, Item item) throws Exception {
        Long userNo = member.getUserNo();

        Long itemId = item.getItemId();
        int price = item.getPrice();

        UserItem userItem = new UserItem();
        userItem.setUserNo(userNo);
        userItem.setItemId(itemId);

        PayCoin paycoin = new PayCoin();
        paycoin.setUserNo(userNo);
        paycoin.setItemId(itemId);
        paycoin.setAmount(price);

        Member memberEntity = memberRepository.getOne(userNo);

        int coin = memberEntity.getCoin();
        int amount = paycoin.getAmount();

        memberEntity.setCoin(coin - amount);

        memberRepository.save(memberEntity);
        payCoinRepository.save(paycoin);
        userItemRepository.save(userItem);
    }

    @Override
    public List<UserItem> list(Long userNo) throws Exception {
        List<Object[]> valueArrays = userItemRepository.listUserItem(userNo);

        List<UserItem> userItemList = new ArrayList<UserItem>();
        for (Object[] valueArray : valueArrays) {
            UserItem userItem = new UserItem();

            userItem.setUserItemNo((Long)valueArray[0]);
            userItem.setUserNo((Long) valueArray[1]);
            userItem.setItemId((Long) valueArray[2]);
            userItem.setRegDate((LocalDateTime) valueArray[3]);
            userItem.setItemName((String) valueArray[4]);
            userItem.setPrice((int)valueArray[5]);
            userItem.setDescription((String) valueArray[6]);
            userItem.setPictureUrl((String) valueArray[7]);

            userItemList.add(userItem);
        }
        return userItemList;
    }

    @Override
    public UserItem read(Long userItemNo) throws Exception {
        List<Object[]> valueArrays = userItemRepository.readUserItem(userItemNo);

        Object[] valueArray = valueArrays.get(0);

        UserItem userItem = new UserItem();

        userItem.setUserItemNo((Long) valueArray[0]);
        userItem.setUserNo((Long) valueArray[1]);
        userItem.setItemId((Long) valueArray[2]);
        userItem.setRegDate((LocalDateTime) valueArray[3]);
        userItem.setItemName((String) valueArray[4]);
        userItem.setPrice((int)valueArray[5]);
        userItem.setDescription((String) valueArray[6]);
        userItem.setPictureUrl((String) valueArray[7]);

        return userItem;
    }

}
