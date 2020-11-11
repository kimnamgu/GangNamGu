package com.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class RegisterRequestValidator implements Validator{
    
    private static final String emailRegExp =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
            "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private Pattern pattern;
 
    public RegisterRequestValidator() {
        pattern = Pattern.compile(emailRegExp);
    }
 
    @Override
    public boolean supports(Class<?> clazz) {
        return RegisterRequest.class.isAssignableFrom(clazz);
    }
 
    @Override
    public void validate(Object target, Errors errors) {
        RegisterRequest regReq = (RegisterRequest) target;
        
        if(regReq.getEmail() == null || regReq.getEmail().trim().isEmpty()) {
            errors.rejectValue("email", "required", "�ʼ� ���� �Դϴ�.");
        } else {
            Matcher matcher = pattern.matcher(regReq.getEmail());
            if(!matcher.matches()) {
                errors.rejectValue("email", "bad", "�ùٸ��� �ʴ� �����Դϴ�.");
            }
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "required", "�ʼ� ���� �Դϴ�.");
        ValidationUtils.rejectIfEmpty(errors, "pw", "required", "�ʼ� ���� �Դϴ�.");
        ValidationUtils.rejectIfEmpty(errors, "checkPw", "required", "�ʼ� ���� �Դϴ�.");
        if(!regReq.getPw().isEmpty()) {
            if(!regReq.isPwEqualToCheckPw()) {
                errors.rejectValue("checkPw", "nomatch", "��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
            }
        }
    }
 
}
