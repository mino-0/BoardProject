package com.pro1.pro.service;

import com.pro1.pro.domain.CodeDetail;
import com.pro1.pro.domain.CodeDetailId;
import com.pro1.pro.repository.CodeDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Service
@RequestMapping("/codeDetail")
public class CodeDetailServiceImpl implements CodeDetailService {
    private final CodeDetailRepository repository;

    @Override
    public void register(CodeDetail codeDetail) throws Exception {
        String groupCode= codeDetail.getGroupCode();
        List<Object[]> rsList = repository.getMaxSortSeq(groupCode);

        int maxSortSeq = 0;
        if(rsList.size()>0){
            Object[] maxValues = rsList.get(0);
            System.out.println(maxValues);
            if (maxValues != null && maxValues.length > 0) {
                maxSortSeq = (Integer) maxValues[0];
            }
        }
        codeDetail.setSortSeq(maxSortSeq + 1);
        repository.save(codeDetail);
    }

    @Override
    public List<CodeDetail> list() throws Exception {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "groupCode", "codeValue"));
    }

    @Override
    public CodeDetail read(CodeDetail codeDetail) throws Exception {
        CodeDetailId codeDetailId = new CodeDetailId();
        codeDetailId.setGroupCode(codeDetail.getGroupCode());
        codeDetailId.setCodeValue(codeDetail.getCodeValue());
        return repository.getOne(codeDetailId);
    }

    @Override
    public void modify(CodeDetail codeDetail) throws Exception {
        CodeDetailId codeDetailId = new CodeDetailId();
        codeDetailId.setGroupCode(codeDetail.getGroupCode());
        codeDetailId.setCodeValue(codeDetail.getCodeValue());

        CodeDetail codeDetailEntity = repository.getOne(codeDetailId);

        codeDetailEntity.setCodeValue(codeDetail.getCodeValue());
        codeDetailEntity.setCodeName(codeDetail.getCodeName());

        repository.save(codeDetailEntity);
    }

    @Override
    public void remove(CodeDetail codeDetail) throws Exception {
        CodeDetailId codeDetailId = new CodeDetailId();
        codeDetailId.setGroupCode(codeDetail.getGroupCode());
        codeDetailId.setCodeValue(codeDetail.getCodeValue());
        repository.deleteById(codeDetailId);
    }
}
