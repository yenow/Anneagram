package com.anneagram.dao;

import java.util.List;

import com.anneagram.vo.ReplyVO;

public interface ReplyDAO {

	void replyInsert(ReplyVO re);
	
	public void replyInsert_no(ReplyVO re);

	List<ReplyVO> listReply(int bno);
}
