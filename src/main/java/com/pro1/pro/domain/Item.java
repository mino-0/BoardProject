package com.pro1.pro.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    @Column(length = 50, nullable = false)
    private String itemName;

    private Integer price;

    @Column(length = 250)
    private String description;

    @Transient
    private MultipartFile picture;

    @Column(length = 200)
    private String pictureUrl;

    @Transient
    private MultipartFile preview;

    @Column(length = 200)
    private String previewUrl;

    @CreationTimestamp
    private LocalDateTime regDate;
    @UpdateTimestamp
    private LocalDateTime updDate;
}
