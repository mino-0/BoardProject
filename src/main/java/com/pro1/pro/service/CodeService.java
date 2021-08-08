package com.pro1.pro.service;

import com.pro1.pro.domain.CodeDetail;
import com.pro1.pro.dto.CodeLabelValue;

import java.util.List;

public interface CodeService {
    public List<CodeLabelValue>getCodeGroupList()throws Exception;

    public List<CodeLabelValue> getCodeList(String CodeName) throws Exception;

}
