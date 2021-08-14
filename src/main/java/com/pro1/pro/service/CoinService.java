package com.pro1.pro.service;

import com.pro1.pro.domain.ChargeCoin;
import com.pro1.pro.domain.PayCoin;

import java.util.List;

public interface CoinService {
    public void charge(ChargeCoin chargeCoin) throws Exception;

    public List<ChargeCoin> list(Long userNo) throws Exception;

    public List<PayCoin> listPayHistory(Long userNo) throws Exception;
}
