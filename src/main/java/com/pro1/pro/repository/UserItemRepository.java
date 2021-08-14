package com.pro1.pro.repository;

import com.pro1.pro.domain.UserItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserItemRepository extends JpaRepository<UserItem,Long> {
    public List<UserItem> findByUserNo(Long userNO);

    //사용자 구매 상품 목록
    @Query("select a.userItemNo, a.userNo, a.itemId, a.regDate, b.itemName, b.price, b.description, b.pictureUrl "
            + "from UserItem a inner join Item b On a.itemId = b.itemId "
            + "where a.userNo = ?1 "
            + "order by a.userItemNo Desc, a.regDate desc")
    public List<Object[]> listUserItem(Long userNo);

    //구매 상품 보기
    @Query("select a.userItemNo, a.userNo, a.itemId, a.regDate, b.itemName, b.price, b.description,b.pictureUrl "
            + "from UserItem a inner join Item b on a.itemId = b.itemId "
            + "where a.userItemNo = ?1")
    public List<Object[]> readUserItem(Long userItemNo);

}
