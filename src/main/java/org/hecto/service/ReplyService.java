package org.hecto.service;

import java.util.List;

import org.hecto.domain.ReplyVO;

public interface ReplyService {
	public int register(ReplyVO vo);
	public ReplyVO get(Long rno);
	public int modify(ReplyVO vo);
	public int remove(Long rno);
	
	public List<ReplyVO> getListByBno(Long bno);
}
