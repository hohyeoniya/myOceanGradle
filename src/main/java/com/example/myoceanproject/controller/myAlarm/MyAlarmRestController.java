package com.example.myoceanproject.controller.myAlarm;

import com.example.myoceanproject.domain.AlarmDTO;
import com.example.myoceanproject.domain.Criteria;
import com.example.myoceanproject.domain.QuestDTO;
import com.example.myoceanproject.service.QuestService;
import com.example.myoceanproject.service.alarm.AlarmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/alarm/*")
public class MyAlarmRestController {
    private final AlarmService alarmService;

    @GetMapping("/{page}")
    public AlarmDTO getQuest(@PathVariable int page, HttpServletRequest request){
        //        0부터 시작,
        Pageable pageable = PageRequest.of(page == 0 ? 0 : page-1, 10);
        Long userId = (Long)request.getSession().getAttribute("userId");

        Page<AlarmDTO> alarmDTOPage= alarmService.showAlarm(pageable, userId);
        int endPage = (int)(Math.ceil(alarmDTOPage.getNumber()+1 / (double)10)) * 10;
        if(alarmDTOPage.getTotalPages() < endPage){
            endPage = alarmDTOPage.getTotalPages() == 0 ? 1 : alarmDTOPage.getTotalPages();
        }
        log.info(endPage + "end");

        AlarmDTO alarmDTO = new AlarmDTO();

        alarmDTO.setAlarmList(alarmDTOPage.getContent());
        alarmDTO.setEndPage(endPage);

        return alarmDTO;
    }

    @PatchMapping(value = "/update/{alarmId}")
    public String updateQuest(@PathVariable Long alarmId){
        log.info("-----------------------------------------------------");
        log.info("업데이트");
        log.info("-----------------------------------------------------");
        alarmService.modify(alarmId);

        return "update success";
    }

}