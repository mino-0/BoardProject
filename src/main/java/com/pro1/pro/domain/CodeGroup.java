package com.pro1.pro.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString(exclude = "codeDetails")
@EqualsAndHashCode(of = "groupCode")
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

    @UpdateTimestamp
    private LocalDateTime updDate;

    @OneToMany
    @JoinColumn(name = "groupCode")
    private List<CodeDetail> codeDetails;
}
