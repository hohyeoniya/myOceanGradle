package com.example.myoceanproject.controller.host;

import com.example.myoceanproject.domain.Criteria;
import com.example.myoceanproject.domain.GroupDTO;
import com.example.myoceanproject.entity.Group;
import com.example.myoceanproject.service.GroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/host/*")
public class HostController {

    private final GroupService groupService;

    // 게시글 목록
    @GetMapping("/group-list")
    public String list(Model model){
        model.addAttribute("groupDTOs", groupService.show());
        return "app/bulletin_board/bulletin_board";
    }


    // 소모임 작성 페이지
    @GetMapping("/index")
    public String host(Model model){
        model.addAttribute("groupDTO", new GroupDTO());
        return "app/host/host";
    }

    // 게시글 상세보기
    @GetMapping("/read")
    public String read(Long groupId, Model model, Model model2, Model model3, Model model4, Model model5, Model model6, HttpServletRequest request) throws UnsupportedEncodingException{
        HttpSession session= request.getSession();
        Long userId = (Long) session.getAttribute("userId");

        model.addAttribute("groupDTO", groupService.find(groupId));
        model2.addAttribute("groupTop5DTOs", groupService.findTop5BygroupId(groupId));
        model3.addAttribute("groupScheduleDTO", groupService.findAllByGroupId(groupId));
        model4.addAttribute("localDateTime", LocalDateTime.now());
        if(userId != null){
            model5.addAttribute("groupUserCheck", groupService.findGroupUser(userId, groupId));
        } else{
            model5 = null;
        }

        model6.addAttribute("groupJoinMember", groupService.countGroupMember(groupId));


        return "app/bulletin_board/bulletin_board_detail";
    }

    // 게시글 수정하기
    @GetMapping("update")
    public String update(Long groupId, Model model){
        model.addAttribute("groupDTO", groupService.find(groupId));
        return "app/host/host";
    }

    // 게시글 삭제하기
    @GetMapping("/deleteGroup")
    public RedirectView delete(Long groupId){
        groupService.delete(groupId);
        return new RedirectView("/host/group-list");
    }
}