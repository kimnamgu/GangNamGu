package com.board.vo;

import com.common.vo.commonVO;

public class ReplyVO extends commonVO {

	private int replyIdx;
	private int replySeq;
	private String replyCont;
	private int boardIdx;
	private String id;
	private int upperReplyIdx;
	private String ins_dt;
	private String upt_dt;
	
	private String level;
	
	
	public int getReplyIdx() {
		return replyIdx;
	}
	public void setReplyIdx(int replyIdx) {
		this.replyIdx = replyIdx;
	}
	public int getReplySeq() {
		return replySeq;
	}
	public void setReplySeq(int replySeq) {
		this.replySeq = replySeq;
	}
	public String getReplyCont() {
		return replyCont;
	}
	public void setReplyCont(String replyCont) {
		this.replyCont = replyCont;
	}
	public int getBoardIdx() {
		return boardIdx;
	}
	public void setBoardIdx(int boardIdx) {
		this.boardIdx = boardIdx;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getUpperReplyIdx() {
		return upperReplyIdx;
	}
	public void setUpperReplyIdx(int upperReplyIdx) {
		this.upperReplyIdx = upperReplyIdx;
	}
	public String getIns_dt() {
		return ins_dt;
	}
	public void setIns_dt(String ins_dt) {
		this.ins_dt = ins_dt;
	}
	public String getUpt_dt() {
		return upt_dt;
	}
	public void setUpt_dt(String upt_dt) {
		this.upt_dt = upt_dt;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	
	
}
