package com.pro1.pro.domain;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Table(name = "code_group")
@Entity
public class CodeGroup {
    @Id
    @Column(length = 3)
    private String groupCode;

    @Column(length = 30, nullable = false)
    private String groupName;

    @Column(length = 1)
    private String useYn = "Y";

    @CreationTimestamp
    private LocalDateTime regDate;

    @CreationTimestamp
    private LocalDateTime updDate;


}
