package com.pro1.pro.controller;

import com.pro1.pro.common.security.domain.CustomUser;
import com.pro1.pro.domain.Item;
import com.pro1.pro.domain.Member;
import com.pro1.pro.prop.ShopProperties;
import com.pro1.pro.service.ItemService;
import com.pro1.pro.service.MemberService;
import com.pro1.pro.service.UserItemService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.aspectj.bridge.Message;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@RequiredArgsConstructor
@Controller
@RequestMapping("/item")
public class ItemController {
    private final ItemService itemService;

    private final ShopProperties shopProperties;

    private final MemberService memberService;

    private final UserItemService userItemService;

    private final MessageSource messageSource;

    @GetMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public String registerForm(Model model) throws Exception {
        model.addAttribute(new Item());
        return "item/register";
    }

    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public String register(Item item, RedirectAttributes rttr) throws Exception {
        MultipartFile pictureFile = item.getPicture();
        MultipartFile previewFile = item.getPreview();
        String createdPictureFilename = uploadFile(pictureFile.getOriginalFilename(), pictureFile.getBytes());
        String createdPreviewFilename = uploadFile(previewFile.getOriginalFilename(), previewFile.getBytes());

        item.setPictureUrl(createdPictureFilename);
        item.setPreviewUrl(createdPreviewFilename);

        itemService.register(item);

        rttr.addFlashAttribute("msg", "SUCCESS");
        return "redirect:/item/list";
    }

    @GetMapping("/list")
    public void list(Model model) throws Exception {
        List<Item> itemList = itemService.list();

        model.addAttribute("itemList", itemList);
    }

    @GetMapping("/read")
    public String read(Long itemId, Model model) throws Exception {
        Item item = itemService.read(itemId);

        model.addAttribute(item);
        return "item/read";
    }

    @GetMapping("/modify")
    @PreAuthorize("hasRole('ADMIN')")
    public String modifyForm(Long itemId, Model model) throws Exception {
        Item item = itemService.read(itemId);
        model.addAttribute(item);

        return "item/modify";
    }

    @PostMapping("/modify")
    @PreAuthorize("hasRole('ADMIN')")
    public String modify(Item item, RedirectAttributes rttr) throws Exception {
        MultipartFile pictureFile = item.getPicture();

        if (pictureFile != null && pictureFile.getSize() > 0) {
            String createdFilename = uploadFile(pictureFile.getOriginalFilename(), pictureFile.getBytes());
            item.setPictureUrl(createdFilename);
        }

        MultipartFile previewFile = item.getPreview();

        if (previewFile != null && previewFile.getSize() > 0) {
            String createdFilename = uploadFile(previewFile.getOriginalFilename(), previewFile.getBytes());

            item.setPreviewUrl(createdFilename);
        }
        itemService.modify(item);

        rttr.addFlashAttribute("msg", "SUCCESS");
        return "redirect:/item/list";
    }

    @GetMapping("/remove")
    @PreAuthorize("hasRole('ADMIN')")
    public String removeForm(Long itemId, Model model) throws Exception {
        Item item = itemService.read(itemId);

        model.addAttribute(item);
        return "item/remove";
    }

    @PostMapping("/remove")
    @PreAuthorize("hasRole('ADMIN')")
    public String remove(Item item, RedirectAttributes rttr) throws Exception {
        itemService.remove(item.getItemId());

        rttr.addFlashAttribute("msg", "SUCCESS");

        return "redirect:/item/list";
    }

    @ResponseBody
    @GetMapping("/display")
    public ResponseEntity<byte[]> displayFile(Long itemId) throws Exception {
        InputStream in = null;
        ResponseEntity<byte[]> entity = null;
        String fileName = itemService.getPreview(itemId);

        try {
            String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);

            MediaType mType = getMediaType(formatName);

            HttpHeaders headers = new HttpHeaders();

            String uploadPath = shopProperties.getUploadPath();

            in = new FileInputStream(uploadPath + File.separator + fileName);

            if (mType != null) {
                headers.setContentType(mType);
            }
            entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
        }catch(Exception e){
            e.printStackTrace();
            entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
        }finally {
            in.close();
        }
        return entity;
    }

    private String uploadFile(String originalName, byte[] fileData) throws Exception {
        UUID uid = UUID.randomUUID();

        String createdFileName = uid.toString() + "_" + originalName;

        String uploadPath = shopProperties.getUploadPath();
        File target = new File(uploadPath, createdFileName);

        FileCopyUtils.copy(fileData, target);
        return createdFileName;
    }

    private MediaType getMediaType(String formatName) {
        if (formatName != null) {
            if (formatName.equals("JPG")) {
                return MediaType.IMAGE_JPEG;
            }
            if (formatName.equals("GIF")) {
                return MediaType.IMAGE_GIF;
            }
            if (formatName.equals("PNG")) {
                return MediaType.IMAGE_PNG;
            }
        }
        return null;
    }
    @PostMapping("/buy")
    public String buy(Long itemId, RedirectAttributes rttr, Authentication authentication) throws Exception {
        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        Member member = customUser.getMember();

        Long userNo = member.getUserNo();

        member.setCoin(memberService.getCoin(userNo));

        Item item = itemService.read(itemId);

        userItemService.register(member, item);


        String message = messageSource.getMessage("item.purchaseComplete", null, Locale.KOREAN);


        rttr.addFlashAttribute("msg", message);
        return "redirect:/item/success";
    }
    @GetMapping("/success")
    public String success()throws Exception {
        return "item/success";
    }
}
