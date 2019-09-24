package com.lotus.backstage.comment.mapper;

import java.util.List;

import com.lotus.backstage.comment.model.Comment;

public interface CommentMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(Comment record);

    Comment selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Comment record);

	List<Comment> selectComments(Comment comment);
	
	int fabulousSum(Comment comment);

	int cancleFabulousSum(Comment comment);
	
	List<Comment> selectClientComments(Comment comment);
	
	int selectTodayCountEveryBody(Comment comment);
}