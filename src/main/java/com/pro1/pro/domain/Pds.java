package com.pro1.pro.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name="pds")
public class Pds {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    private String itemName;
    private String description;

    //자료파일과 연관관계 매핑
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "item_id")
    private List<PdsFile> pdsFiles = new ArrayList<PdsFile>();

    @Transient
    private String[] files;

    private Integer viewCnt = 0;

    @CreationTimestamp
    private LocalDateTime regDate;

    @UpdateTimestamp
    private LocalDateTime updDate;

    public void addItemFile(PdsFile itemFile) {
        pdsFiles.add(itemFile);
    }

    public void clearItemFile() {
        pdsFiles.clear();
    }

}
