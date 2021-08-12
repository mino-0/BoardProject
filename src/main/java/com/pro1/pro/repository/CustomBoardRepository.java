package com.pro1.pro.repository;

import com.pro1.pro.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomBoardRepository {
    public Page<Board> getSearchPage(String type, String keyword, Pageable pageable);
}
