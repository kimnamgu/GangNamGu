package com.user.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.common.exception.AlreadyExistingEmailException;
import com.common.exception.AlreadyExistingIdException;
import com.common.exception.IdPasswordNotMatchingException;
import com.common.util.AuthInfo;
import com.common.util.LoginCommand;
import com.common.util.RegisterRequest;
import com.common.vo.FolderVO;
import com.user.dao.UserDAO;
import com.user.vo.UserVO;

@Service("userService")
public class UserServiceImpl implements UserService {
  
    @Resource(name="userDAO")
    private UserDAO userDAO;
  
    @Override
    public void register(RegisterRequest regReq) throws Exception {
        UserVO email = userDAO.selectByEmail(regReq.getEmail());
        if(email!=null) {
            throw new AlreadyExistingEmailException(regReq.getEmail()+" is duplicate email.");
        }
        UserVO userInfo = userDAO.selectById(regReq.getId());
        if(userInfo!=null) {
            throw new AlreadyExistingIdException(regReq.getId()+" is duplicate id.");
        }
        userDAO.insertUser(regReq);
    }
    
    @Override
    public AuthInfo loginAuth(LoginCommand loginCommand) throws Exception {
        UserVO user = userDAO.selectUser(loginCommand);
        if(user == null) {
            throw new IdPasswordNotMatchingException();
        }
        if(!user.matchPassword(loginCommand.getPw())) {
            throw new IdPasswordNotMatchingException();
        }
        return new AuthInfo(user.getId(), user.getName(), user.getGrade());
    }
    
    @Override
    public void mkDir(FolderVO folderVo) throws Exception {
        userDAO.mkDir(folderVo);
    }
    
}


