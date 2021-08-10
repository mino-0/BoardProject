package com.pro1.pro.service;

import com.pro1.pro.domain.Board;

import java.util.List;

public interface BoardService {
    public void register(Board board) throws Exception;

    public List<Board> list() throws Exception;

    public Board read(Long boardNo) throws Exception;

    public void modify(Board board) throws Exception;

    public void remove(Long boardNo) throws Exception;

}
