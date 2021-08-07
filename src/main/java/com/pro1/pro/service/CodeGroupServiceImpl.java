package com.pro1.pro.service;

import com.pro1.pro.domain.CodeGroup;
import com.pro1.pro.repository.CodeGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CodeGroupServiceImpl implements CodeGroupService{


    private final CodeGroupRepository repository;

    @Override
    public void register(CodeGroup codeGroup) throws Exception{
        repository.save(codeGroup);
    }

    @Override
    public List<CodeGroup> list() throws Exception {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "groupCode"));
    }
}
