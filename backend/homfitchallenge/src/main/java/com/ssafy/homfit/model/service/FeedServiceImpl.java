package com.ssafy.homfit.model.service;

import java.util.List;

import com.ssafy.homfit.model.Feed;
import com.ssafy.homfit.model.dao.FeedDAO;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedServiceImpl implements FeedService {

    @Autowired
    SqlSession sqlSession;

    @Override
    public boolean create(Feed feed) throws Exception {
        try {
            sqlSession.getMapper(FeedDAO.class).create(feed);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean updateImg(int feed_id, String feed_picture) throws Exception {
        Feed feed = null;
        try {
            // 멀티파트폼 저장하는 프로세스 필요
            feed.setFeed_id(feed_id);
            feed.setFeed_picture(feed_picture);
            sqlSession.getMapper(FeedDAO.class).updateImg(feed);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public List<Feed> searchByTag(String keyword) throws Exception {
        List<Feed> list = null;
        List<Integer> item = null;

        item = sqlSession.getMapper(FeedDAO.class).searchCidKeyword(keyword);
        list = sqlSession.getMapper(FeedDAO.class).searchByTag(item);

        return list;
    }

    @Override
    public Feed searchByFeedId(int feed_id) throws Exception {
        Feed data = null;

        data = sqlSession.getMapper(FeedDAO.class).searchByFeedId(feed_id);
        return data;
    }

    @Override
    public List<Feed> searchByUser(String uid) throws Exception {
        List<Feed> list = null;
        list = sqlSession.getMapper(FeedDAO.class).searchByUser(uid);
        return list;
    }

    @Override
    public List<Feed> searchByChallenge(int challenge_id) throws Exception {
        List<Feed> list = null;
        list = sqlSession.getMapper(FeedDAO.class).searchByChallenge(challenge_id);
        return list;
    }

}