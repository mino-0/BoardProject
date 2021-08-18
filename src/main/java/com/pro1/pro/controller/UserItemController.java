package com.pro1.pro.controller;

import com.pro1.pro.common.exception.NotMyItemException;
import com.pro1.pro.common.security.domain.CustomUser;
import com.pro1.pro.domain.Item;
import com.pro1.pro.domain.Member;
import com.pro1.pro.domain.UserItem;
import com.pro1.pro.prop.ShopProperties;
import com.pro1.pro.service.ItemService;
import com.pro1.pro.service.MemberService;
import com.pro1.pro.service.UserItemService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

@Controller
@RequiredArgsConstructor
@RequestMapping("/useritem")
public class UserItemController {
    private final UserItemService service;

    private final ShopProperties shopProperties;


    @GetMapping("/list")
    @PreAuthorize("hasAnyRole('ADMIN','MEMBER')")
    public void list(Model model, Authentication authentication) throws Exception {
        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        Member member = customUser.getMember();

        Long userNo = member.getUserNo();

        model.addAttribute("list",service.list(userNo));
    }

    @GetMapping("/read")
    @PreAuthorize("hasAnyRole('ADMIN','MEMBER')")
    public void read(Long userItemNo, Model model) throws Exception {
        model.addAttribute(service.read(userItemNo));
    }

    @ResponseBody
    @GetMapping("/download")
    @PreAuthorize("hasAnyRole('ADMIN','MEMBER')")
    public ResponseEntity<byte[]> download(Long userItemNo, Authentication authentication) throws Exception {
        UserItem userItem = service.read(userItemNo);

        //구매한 상품이 사용자의 것인지 체크
        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        Member member = customUser.getMember();

        if (userItem.getUserNo() != member.getUserNo()) {
            throw new NotMyItemException("It is Not My Item");
        }

        String fullName = userItem.getPictureUrl();

        InputStream in = null;
        ResponseEntity<byte[]> entity = null;

        try {
            HttpHeaders headers = new HttpHeaders();

            in = new FileInputStream(shopProperties.getUploadPath() + File.separator + fullName);

            String fileName = fullName.substring(fullName.indexOf("_") + 1);

            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.add("Content-Disposition", "attachment; filename=\""
                    + new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + "\"");

            entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
        }finally {
            in.close();
        }
        return entity;
    }

    @GetMapping("/notMyItem")
    @PreAuthorize("hasAnyRole('ADMIN','MEMBER')")
    public void notMyItem(Model model) throws Exception {

    }
}
