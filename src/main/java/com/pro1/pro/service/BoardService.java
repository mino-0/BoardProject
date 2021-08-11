package com.pro1.pro.service;

import com.pro1.pro.domain.Board;
import com.pro1.pro.vo.PageRequestVO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BoardService {
    public void register(Board board) throws Exception;

    public List<Board> list() throws Exception;

    public Page<Board> list(PageRequestVO pageRequestVO) throws Exception;

    public Board read(Long boardNo) throws Exception;

    public void modify(Board board) throws Exception;

    public void remove(Long boardNo) throws Exception;

}
