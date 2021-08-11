package com.pro1.pro.service;

import com.pro1.pro.domain.Board;
import com.pro1.pro.repository.BoardRepository;
import com.pro1.pro.vo.PageRequestVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public Page<Board> list(PageRequestVO pageRequestVO) throws  Exception{
        int pageNumber = pageRequestVO.getPage() -1;
        int sizePerPage = pageRequestVO.getSizePerPage();

        Pageable pageRequest = PageRequest.of(pageNumber, sizePerPage, Sort.Direction.DESC, "boardNo");

        Page<Board> page = boardRepository.findAll(pageRequest);

        return page;
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
