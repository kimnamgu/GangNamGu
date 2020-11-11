package com.user.service;

import com.common.util.AuthInfo;
import com.common.util.LoginCommand;
import com.common.util.RegisterRequest;
import com.common.vo.FolderVO;

public interface UserService {
	void register(RegisterRequest regReq) throws Exception;
	
	AuthInfo loginAuth(LoginCommand loginCommand) throws Exception;
	 
	void mkDir(FolderVO folderVo) throws Exception; //로그인시 기본 아이디 폴더 생성
}
