package com.ssafy.homfit.controller;

import com.ssafy.homfit.model.Feed;
import com.ssafy.homfit.model.service.FeedService;
import com.ssafy.homfit.util.UploadImg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

@Api("FeedController V1")
@RestController
@RequestMapping("/feed")
public class FeedController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    @Autowired
    private FeedService feedService;

    @ApiOperation(value = "피드 생성", notes = "입력된 데이터(maked_uid = 작성자 uid, challenge_id = 챌린지 식별번호, feed_contents = 피드 내용, imgFile = 이미지 파일)를 토대로 피드를 생성")
    @PostMapping(value = "/create")
    public ResponseEntity<String> create(@ModelAttribute Feed feed) {
        HttpStatus status = null;
        String msg = null;

        try {
            feed.setFeed_picture(UploadImg.writeImg(feed.getImgFile()));
            if (feedService.create(feed)) {
                msg = SUCCESS;
                status = HttpStatus.ACCEPTED;
            } else {
                msg = FAIL;
                status = HttpStatus.ACCEPTED;
            }
        } catch (Exception e) {
            logger.error("피드 생성 실패 : {}", e);
            msg = e.getMessage();
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<String>(msg, status);
    }

    @ApiOperation(value = "인증샷 수정", notes = "기존 인증샷을 지우고, 새로운 인증샷을 등록한다")
    @PutMapping(value = "/update/img")
    public ResponseEntity<String> putMethodName(@RequestPart int feed_id, @RequestPart MultipartFile imgFile) {
        HttpStatus status = null;
        String msg = null;

        try {
            if (feedService.updateImg(feed_id, UploadImg.writeImg(imgFile))) {
                msg = SUCCESS;
            } else {
                msg = FAIL;
            }
            status = HttpStatus.ACCEPTED;
        } catch (Exception e) {
            logger.error("피드 이미지 수정 실패 : {}", e);
            msg = e.getMessage();
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<String>(msg, status);
    }

}