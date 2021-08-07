package com.pro1.pro.controller;

import com.pro1.pro.domain.CodeGroup;
import com.pro1.pro.service.CodeGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/codegroup")
public class codeGroupController {

    private final CodeGroupService service;


    @GetMapping("/register")
    public void registerForm(Model model) throws Exception {
        CodeGroup codeGroup = new CodeGroup();

        model.addAttribute(codeGroup);
    }

    @PostMapping("/register")
    public String register(CodeGroup codeGroup, RedirectAttributes rttr) throws Exception {
        service.register(codeGroup);

        rttr.addFlashAttribute("msg", "SUCCESS");
        return "redirect:/codegroup/list";
    }

    @GetMapping("/list")
    public void list(Model model) throws Exception {
        model.addAttribute("list", service.list());
    }

}
