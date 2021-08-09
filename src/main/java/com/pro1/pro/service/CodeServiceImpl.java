package com.pro1.pro.service;

import com.pro1.pro.domain.CodeDetail;
import com.pro1.pro.domain.CodeGroup;
import com.pro1.pro.dto.CodeLabelValue;
import com.pro1.pro.repository.CodeDetailRepository;
import com.pro1.pro.repository.CodeGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CodeServiceImpl implements CodeService {

    private final CodeGroupRepository repository;
    private final CodeDetailRepository repository1;


    @Override
    public List<CodeLabelValue> getCodeGroupList() throws Exception {
        List<CodeGroup> codeGroups = repository.findAll(Sort.by(Sort.Direction.ASC, "groupCode"));
        List<CodeLabelValue> codeGroupList = new ArrayList<CodeLabelValue>();

        for (CodeGroup codeGroup : codeGroups) {
            codeGroupList.add(new CodeLabelValue(codeGroup.getGroupCode(), codeGroup.getGroupName()));
        }
        return codeGroupList;
    }

    @Override
    public List<CodeLabelValue> getCodeList(String classCode) throws Exception {
        List<CodeDetail> codeDetails = repository1.findByGroupCode(classCode);//classCode로 찾은 리스트
        List<CodeLabelValue> jobList = new ArrayList<CodeLabelValue>();

        for (CodeDetail codeDetail : codeDetails) {
            jobList.add(new CodeLabelValue(codeDetail.getCodeValue(), codeDetail.getCodeName()));
        }
        return jobList;
    }

}
