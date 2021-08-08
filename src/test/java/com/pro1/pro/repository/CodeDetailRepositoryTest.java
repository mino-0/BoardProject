package com.pro1.pro.repository;

import com.pro1.pro.domain.CodeDetail;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class CodeDetailRepositoryTest {
    @Autowired
    CodeDetailRepository codeDetailRepository;

    @Test
    void codeList() throws Exception{

        List<CodeDetail> codeList = codeDetailRepository.findByGroupCode("A01");

        Assertions.assertThat(codeList.size()).isEqualTo(4);
    }
}