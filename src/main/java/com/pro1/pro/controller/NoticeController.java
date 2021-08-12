package com.pro1.pro.controller;

import com.pro1.pro.domain.Notice;
import com.pro1.pro.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Not;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {
    private final NoticeService noticeService;

    @GetMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public void registerForm(Model model) throws Exception {
        Notice notice = new Notice();
        model.addAttribute(notice);
    }

    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public String register(Notice notice, RedirectAttributes rttr) throws Exception {
        noticeService.register(notice);
        rttr.addFlashAttribute("msg", "SUCCESS");
        return "redirect:/notice/list";
    }

    @GetMapping("/list")
    public void list(Model model) throws Exception {
        model.addAttribute("list", noticeService.list());
    }

    @GetMapping("/read")
    public void read(Long noticeNo, Model model) throws Exception {
        model.addAttribute(noticeService.read(noticeNo));
    }

    @GetMapping("/modify")
    @PreAuthorize("hasRole('ADMIN')")
    public void modifyFomr(Long noticeNo, Model model) throws Exception {
        model.addAttribute(noticeService.read(noticeNo));
    }

    @PostMapping("/modify")
    @PreAuthorize("hasRole('ADMIN')")
    public String modify(Notice notice, RedirectAttributes rttr) throws Exception {
        noticeService.modify(notice);
        rttr.addFlashAttribute("msg", "SUCCESS");

        return "redirect:/notice/list";
    }

    @PostMapping("/remove")
    @PreAuthorize("hasRole('ADMIN')")
    public String remove(Long noticeNo, RedirectAttributes rttr) throws Exception {
        noticeService.remove(noticeNo);
        rttr.addFlashAttribute("msg","SUCCESS");
        return "redirect:/notice/list";
    }
}
