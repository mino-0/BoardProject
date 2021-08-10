package com.pro1.pro.service;

import com.pro1.pro.domain.Board;
import com.pro1.pro.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService{
    public final BoardRepository boardRepository;
    @Override
    public void register(Board board) throws Exception {
        boardRepository.save(board);
    }

    @Override
    public List<Board> list() throws Exception {
        return boardRepository.findAll(Sort.by(Sort.Direction.DESC, "boardNo"));
    }

    @Override
    public Board read(Long boardNo) throws Exception {
        return boardRepository.getOne(boardNo);
    }

    @Override
    public void modify(Board board) throws Exception {
        Board boardEntity = boardRepository.getOne(board.getBoardNo());

        boardEntity.setTitle(board.getTitle());
        boardEntity.setContent(board.getContent());

        boardRepository.save(boardEntity);
    }

    @Override
    public void remove(Long boardNo) throws Exception {
        boardRepository.deleteById(boardNo);
    }
}