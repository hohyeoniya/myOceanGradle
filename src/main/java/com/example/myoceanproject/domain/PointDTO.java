package com.example.myoceanproject.domain;

import com.example.myoceanproject.entity.Period;
import com.example.myoceanproject.entity.Point;
import com.example.myoceanproject.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
public class PointDTO {

    private User user;
    private String pointAmountHistory;

//  포인트의 변화 추이가 계속 새롭게 저장이 된다.
    public Point toEntity(){
        return Point.builder()
                .pointAmountHistory(pointAmountHistory)
                .build();
    }
}
