package com.example.myoceanproject.domain;

import com.example.myoceanproject.entity.CommunityPost;
import com.example.myoceanproject.entity.Period;
import com.example.myoceanproject.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
public class CommunityPostDTO {
    private User user; //FK
    private String communityCategory;
    private String communityTitle;
    private String communityContent;
    private String communityViewNumber;

//  게시글 작성 시 게시글 제목, 카테고리, 내용이 처음으로 저장된다.
    public CommunityPost toEntity(){
        return CommunityPost.builder()
                .communityTitle(communityTitle)
                .communityCategory(communityCategory)
                .communityContent(communityContent)
                .communityViewNumber(communityViewNumber)
                .build();
    }
}
