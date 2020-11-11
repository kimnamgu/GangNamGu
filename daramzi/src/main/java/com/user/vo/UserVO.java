package com.user.vo;

import java.util.Date;

import com.common.vo.commonVO;

public class UserVO extends commonVO { 
	
    private int userIdx;
    private String id;
    private String email;
    private String name;
    private String password;
    private int grade;
    private Date ins_dt;
    private Date upt_dt;
    
    //비밀번호 확인
    public boolean matchPassword(String pw) {
        return this.password.equals(pw);
    }

	public int getUserIdx() {
		return userIdx;
	}

	public void setUserIdx(int userIdx) {
		this.userIdx = userIdx;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public Date getIns_dt() {
		return ins_dt;
	}

	public void setIns_dt(Date ins_dt) {
		this.ins_dt = ins_dt;
	}

	public Date getUpt_dt() {
		return upt_dt;
	}

	public void setUpt_dt(Date upt_dt) {
		this.upt_dt = upt_dt;
	}
    
    
    

}
