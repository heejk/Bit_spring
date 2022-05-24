package org.hecto.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.hecto.domain.ReplyVO;

public interface ReplyMapper {
	public int insert(ReplyVO vo);
	public ReplyVO read(Long rno);
	public int delete(Long rno);
	public int update(ReplyVO reply);
	
	// 특정한 게시물의 댓글만을 대상으로 하기 때문에 게시글 번호 필요 
	// MyBatis의 파라미터는 1개만 허용 >> 2개 이상 전달하기 위한 방법: @Param 이용 >> #{} 사용 가능 
	public List<ReplyVO> getListByBno(@Param("bno") Long bno);
}
