package com.example.myoceanproject.repository.community.reply;

import com.example.myoceanproject.domain.*;
import com.example.myoceanproject.entity.CommunityPost;
import com.example.myoceanproject.type.CommunityCategory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.example.myoceanproject.entity.QCommunityPost.communityPost;
import static com.example.myoceanproject.entity.QCommunityReply.communityReply;

@Repository
@RequiredArgsConstructor
public class CommunityReplyRepositoryImpl implements CommunityReplyCustomRepository{
//사용자 지정 레파지토리 Impl(구현)

    private final JPAQueryFactory queryFactory;



//    해당포스트의 전체 댓글 삭제
    @Override
    public void deleteByCommunityPost(CommunityPost communityPost) {
        queryFactory.delete(communityReply)
                .where(communityReply.communityPost.communityPostId.eq(communityPost.getCommunityPostId()))
                .execute();
    }

    @Override
    public Integer CountReplyByCommunityPost(Long communityPostId){
        return Math.toIntExact(queryFactory.select(communityReply.communityReplyId.count())
                .from(communityReply)
                .where(communityReply.communityPost.communityPostId.eq(communityPostId))
                .fetchFirst());
    }

    @Override
    public List<CommunityReplyDTO> findByCommunityPost(CommunityPost communityPost){
        return queryFactory.select(new QCommunityReplyDTO(
                        communityReply.user.userId,
                        communityReply.user.userNickname,
                        communityReply.user.userFileName,
                        communityReply.user.userFilePath,
                        communityReply.user.userFileSize,
                        communityReply.user.userFileUuid,
                        communityReply.communityPost.communityPostId,
                        communityReply.communityPost.communityTitle,
                        communityReply.communityReplyId,
                        communityReply.communityReplyContent,
                        communityReply.createDate,
                        communityReply.updatedDate
                ))
                .from(communityReply)
                .where(communityReply.communityPost.communityPostId.eq(communityPost.getCommunityPostId()))
                .orderBy(communityReply.communityReplyId.desc()).fetch();
    }

    @Override
    public Page<CommunityReplyDTO> findAll(Pageable pageable){
        List<CommunityReplyDTO> replies = queryFactory.select(new QCommunityReplyDTO(
                    communityReply.user.userId,
                    communityReply.user.userNickname,
                    communityReply.user.userFileName,
                    communityReply.user.userFilePath,
                    communityReply.user.userFileSize,
                    communityReply.user.userFileUuid,
                    communityReply.communityPost.communityPostId,
                    communityReply.communityPost.communityTitle,
                    communityReply.communityReplyId,
                    communityReply.communityReplyContent,
                    communityReply.createDate,
                    communityReply.updatedDate
                ))
                .from(communityReply)
                .where(communityReply.communityPost.communityCategory.ne(CommunityCategory.COUNSELING))
                .orderBy(communityReply.communityPost.communityPostId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()).fetch();

        long total = queryFactory.selectFrom(communityReply)
                .where(communityReply.communityPost.communityCategory.ne(CommunityCategory.COUNSELING))
                .fetch().size();

        return new PageImpl<>(replies, pageable, total);
    }
    @Override
    public Page<CommunityReplyDTO> findAll(Pageable pageable, Criteria criteria){
        List<CommunityReplyDTO> replies = queryFactory.select(new QCommunityReplyDTO(
                        communityReply.user.userId,
                        communityReply.user.userNickname,
                        communityReply.user.userFileName,
                        communityReply.user.userFilePath,
                        communityReply.user.userFileSize,
                        communityReply.user.userFileUuid,
                        communityReply.communityPost.communityPostId,
                        communityReply.communityPost.communityTitle,
                        communityReply.communityReplyId,
                        communityReply.communityReplyContent,
                        communityReply.createDate,
                        communityReply.updatedDate
                ))
                .from(communityReply)
                .where(communityReply.communityPost.communityTitle.contains(criteria.getKeyword()).and(communityReply.communityPost.communityCategory.ne(CommunityCategory.COUNSELING)))
                .orderBy(communityReply.communityPost.communityPostId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()).fetch();

        long total = queryFactory.selectFrom(communityReply)
                .where(communityReply.communityPost.communityTitle.contains(criteria.getKeyword()).and(communityReply.communityPost.communityCategory.ne(CommunityCategory.COUNSELING)))                .fetch().size();

        return new PageImpl<>(replies, pageable, total);
    }
    @Override
    public Page<CommunityReplyDTO> findAllByCategory(Pageable pageable){
        List<CommunityReplyDTO> replies = queryFactory.select(new QCommunityReplyDTO(
                    communityReply.user.userId,
                    communityReply.user.userNickname,
                    communityReply.user.userFileName,
                    communityReply.user.userFilePath,
                    communityReply.user.userFileSize,
                    communityReply.user.userFileUuid,
                    communityReply.communityPost.communityPostId,
                    communityReply.communityPost.communityTitle,
                    communityReply.communityReplyId,
                    communityReply.communityReplyContent,
                    communityReply.createDate,
                    communityReply.updatedDate
                ))
                .from(communityReply)
                .where(communityReply.communityPost.communityCategory.eq(CommunityCategory.COUNSELING))
                .orderBy(communityReply.communityPost.communityPostId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()).fetch();

        long total = queryFactory.selectFrom(communityReply)
                .where(communityReply.communityPost.communityCategory.eq(CommunityCategory.COUNSELING))
                .fetch().size();

        return new PageImpl<>(replies, pageable, total);
    }
    @Override
    public Page<CommunityReplyDTO> findAllByCategory(Pageable pageable, Criteria criteria){
        List<CommunityReplyDTO> replies = queryFactory.select(new QCommunityReplyDTO(
                        communityReply.user.userId,
                        communityReply.user.userNickname,
                        communityReply.user.userFileName,
                        communityReply.user.userFilePath,
                        communityReply.user.userFileSize,
                        communityReply.user.userFileUuid,
                        communityReply.communityPost.communityPostId,
                        communityReply.communityPost.communityTitle,
                        communityReply.communityReplyId,
                        communityReply.communityReplyContent,
                        communityReply.createDate,
                        communityReply.updatedDate
                ))
                .from(communityReply)
                .where(communityReply.communityPost.communityTitle.contains(criteria.getKeyword()).and(communityReply.communityPost.communityCategory.eq(CommunityCategory.COUNSELING)))
                .orderBy(communityReply.communityPost.communityPostId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()).fetch();

        long total = queryFactory.selectFrom(communityReply)
                .where(communityReply.communityPost.communityTitle.contains(criteria.getKeyword()).and(communityReply.communityPost.communityCategory.eq(CommunityCategory.COUNSELING)))                .fetch().size();

        return new PageImpl<>(replies, pageable, total);
    }
}
