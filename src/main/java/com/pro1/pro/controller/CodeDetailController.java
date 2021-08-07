package com.pro1.pro.controller;

import com.pro1.pro.domain.CodeDetail;
import com.pro1.pro.dto.CodeLabelValue;
import com.pro1.pro.service.CodeDetailService;
import com.pro1.pro.service.CodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/codedetail")
public class CodeDetailController {
    private final CodeDetailService codeDetailService;
    private final CodeService codeService;

    @GetMapping("/register")
    public void registerForm(Model model) throws Exception {
        CodeDetail codeDetail = new CodeDetail();
        model.addAttribute(codeDetail);

        List<CodeLabelValue> groupCodeList = codeService.getCodeGroupList();
        model.addAttribute("groupCodeList", groupCodeList);
    }

    @PostMapping("/register")
    public String register(CodeDetail codeDetail, RedirectAttributes rttr) throws Exception {
        codeDetailService.register(codeDetail);

        rttr.addFlashAttribute("msg", "SUCCESS");
        return "redirect:/codedetail/list";
    }

    @GetMapping("/list")
    public void list(Model model) throws Exception {
        model.addAttribute("list", codeDetailService.list());
    }

    @GetMapping("/read")
    public void read(CodeDetail codeDetail, Model model) throws Exception {
        model.addAttribute(codeDetailService.read(codeDetail));

        List<CodeLabelValue> groupCodeList = codeService.getCodeGroupList();
        model.addAttribute("groupCodeList", groupCodeList);
    }

    @GetMapping("/modify")
    public void modifyForm(CodeDetail codeDetail, Model model) throws Exception {
        model.addAttribute(codeDetailService.read(codeDetail));
        List<CodeLabelValue> groupCodeList = codeService.getCodeGroupList();
        model.addAttribute("groupCodeList", groupCodeList);
    }

    @PostMapping("/modify")
    public String modify(CodeDetail codeDetail, RedirectAttributes rttr) throws Exception {
        codeDetailService.modify(codeDetail);
        rttr.addFlashAttribute("msg", "SUCCESS");
        return "redirect:/codedetail/list";
    }

    @PostMapping("/remove")
    public String remove(CodeDetail codeDetail, RedirectAttributes rttr) throws  Exception{
        codeDetailService.remove(codeDetail);

        rttr.addFlashAttribute("msg", "SUCCESS");

        return"redirect:/codedetail/list";
    }
}