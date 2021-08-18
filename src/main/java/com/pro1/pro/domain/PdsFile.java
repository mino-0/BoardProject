package com.pro1.pro.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Table(name = "pds_file")
public class PdsFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pdsFileId;

    @Column(length = 200)
    private String fullName;

    private Integer downCnt = 0;

    @CreationTimestamp
    private LocalDateTime regDate;

    @UpdateTimestamp
    private LocalDateTime updDate;

}
