package com.pro1.pro.controller;

import com.pro1.pro.domain.Member;
import com.pro1.pro.dto.CodeLabelValue;
import com.pro1.pro.service.CodeService;
import com.pro1.pro.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class MemberController {
    private final MemberService service;
    private final CodeService codeService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public void registerForm(Member member, Model model) throws Exception {
        String classCode = "A01";
        List<CodeLabelValue> jobList = codeService.getCodeList(classCode);

        model.addAttribute("jobList", jobList);
    }

    @PostMapping("/register")
    public String register(@Validated Member member, BindingResult result,
                           Model model, RedirectAttributes rttr) throws Exception {
        if (result.hasErrors()) {
            String classCode = "A01";
            List<CodeLabelValue> jobList = codeService.getCodeList(classCode);

            model.addAttribute("jobList", jobList);

            return "user/register";
        }
        String inputPassword = member.getUserPw();
        member.setUserPw(passwordEncoder.encode(inputPassword));

        service.register(member);

        rttr.addFlashAttribute("userName", member.getUserName());
        return "redirect:/user/registerSuccess";
    }

    @GetMapping("/registerSuccess")
    public void registerSuccess(Model model) throws Exception {

    }

    @GetMapping("/list")
    public void list(Model model) throws Exception{
        model.addAttribute("list", service.list());
    }
}