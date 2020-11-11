/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 로그인
 * 설명:
 */

// 앞뒤로 빈공백을 없앤다.
String.prototype.trim = function() {
	return this.replace(/(^\s*)|(\s*$)/gi, "");
}

//엔터키 후 다음 아이템(nextItem)으로 포커스 이동
function enterKey_focus(nextItem) {
	if(event.keyCode == 13) {
		eval("document." + nextItem + ".focus()");
	}
}

function check_submit(){
	document.loginForm.userId.value = document.loginForm.userId.value.trim();
	if(document.loginForm.userId.value == ""){
		alert('아이디를 입력하세요');
		document.loginForm.userId.focus();
		return;
	}
	if(document.loginForm.password.value == ""){
		alert('패스워드를  입력하세요');
		document.loginForm.password.focus();
		return;
	}
	
	document.loginForm.submit();
}

function loadFocus(val){
	if(val.trim() == ""){	
		//아이디 값이 없으면 아이디에 포커스
		document.loginForm.userId.focus();
	} else {
		//아이디값이 있으면 패스워드에 포커스	                           
		document.loginForm.password.focus();
	}
}

//로그인 실패에러 메세지
function loginerror()
{
	alert('아이디/비밀번호가 일치하는 사용자가 없습니다.');			

	document.loginForm.password.value = '';
	document.loginForm.password.focus();
}