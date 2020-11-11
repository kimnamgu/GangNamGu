package com.user.dao;

import org.springframework.stereotype.Repository;

import com.common.dao.AbstractDAO;
import com.common.util.LoginCommand;
import com.common.util.RegisterRequest;
import com.common.vo.FolderVO;
import com.user.vo.UserVO;

@Repository("userDAO")
public class UserDAO extends AbstractDAO {
    public UserVO selectUser(LoginCommand loginCommand) {
        return (UserVO)selectOne("user.selectUser", loginCommand);
    }
	
    public UserVO selectByEmail(String email) {
        return (UserVO)selectOne("user.selectByEmail", email);
    }
 
    public UserVO selectById(String id) {
        return (UserVO)selectOne("user.selectById", id);
    }
  
    public void insertUser(RegisterRequest regReq) {
        insert("user.register", regReq);
    }
     
    public void mkDir(FolderVO folderVo) {
        insert("user.mkDir", folderVo);
    }
}
