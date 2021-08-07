package com.pro1.pro.service;

import com.pro1.pro.domain.CodeGroup;

import java.util.List;

public interface CodeGroupService {
    public void register(CodeGroup codeGroup) throws Exception;
    public List<CodeGroup> list() throws Exception;
}
