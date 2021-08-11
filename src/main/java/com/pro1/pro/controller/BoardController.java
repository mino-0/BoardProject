package com.pro1.pro.controller;

import com.pro1.pro.common.security.domain.CustomUser;
import com.pro1.pro.domain.Board;
import com.pro1.pro.domain.Member;
import com.pro1.pro.dto.PaginationDTO;
import com.pro1.pro.service.BoardService;
import com.pro1.pro.vo.PageRequestVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
public class BoardController {

    public final BoardService boardService;

    @GetMapping("register")
    @PreAuthorize("hasRole('MEMBER')")
    public void registerForm(Model model, Authentication authentication) throws Exception {
        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        Member member = customUser.getMember();

        Board board = new Board();

        board.setWriter(member.getUserId());
        model.addAttribute(board);
    }

    @PostMapping("/register")
    @PreAuthorize("hasRole('MEMBER')")
    public String register(Board board, RedirectAttributes rttr) throws Exception {
        boardService.register(board);

        rttr.addFlashAttribute("msg", "SUCCESS");
        return "redirect:/board/list";
    }

    @GetMapping("/list")
    //public void list(Model mode) throws Exception {
    public void list(@ModelAttribute("pgrq") PageRequestVO pageRequestVO, Model model) throws Exception {
    //    model.addAttribute("list", boardService.list());

        Page<Board> page = boardService.list(pageRequestVO);
        model.addAttribute("pgntn", new PaginationDTO<Board>(page));
    }

    @GetMapping("/read")
    public void read(Long boardNo,
                     @ModelAttribute("pgrq") PageRequestVO pageRequestVO, Model model) throws Exception{
    //public void read(Long boardNo, Model model) throws Exception {
        model.addAttribute(boardService.read(boardNo));
    }

//    @GetMapping("/modify")
//    @PreAuthorize("hasRole('MEMBER')")
//    public void modifyForm(Long boardNo, Model model) throws Exception {
//        model.addAttribute(boardService.read(boardNo));
//    }
    @GetMapping("/modify")
    @PreAuthorize("hasRole('MEMBER')")
    public void modifyForm(Long boardNo,
                           @ModelAttribute("pgrq") PageRequestVO pageRequestVO, Model model) throws Exception {
        model.addAttribute(boardService.read(boardNo));
    }

    @PostMapping("/modify")
    @PreAuthorize("(hasRole('MEMBER') and principal.username == #board.writer)")
//    public String modify(Board board, RedirectAttributes rttr) throws Exception {
//        boardService.modify(board);
//        rttr.addFlashAttribute("msg", "SUCCESS");
//        return "redirect:/board/list";
//    }
    public String modify(Board board, PageRequestVO pageRequestVO, RedirectAttributes rttr) throws Exception {
        boardService.modify(board);

        rttr.addAttribute("page", pageRequestVO.getPage());
        rttr.addAttribute("sizePerPage", pageRequestVO.getSizePerPage());

        rttr.addFlashAttribute("msg", "SUCCESS");
        return "redirect:/board/list";
    }

    @PostMapping("/remove")
    @PreAuthorize("(hasRole('MEMBER') and principal.username == #writer) or hasRole('ADMIN')")
//  public String remove(Long boardNo, RedirectAttributes rttr, String writer) throws Exception{
//        boardService.remove(boardNo);
//
//        rttr.addFlashAttribute("msg", "SUCCESS");
//        return "redirect:/board/list";
//  }
    public String remove(Long boardNo,PageRequestVO pageRequestVO,
                         RedirectAttributes rttr, String writer) throws Exception{
        boardService.remove(boardNo);

        rttr.addAttribute("page", pageRequestVO.getPage());
        rttr.addAttribute("sizePerPage", pageRequestVO.getSizePerPage());

        rttr.addFlashAttribute("msg", "SUCCESS");
        return "redirect:/board/list";
    }
}
