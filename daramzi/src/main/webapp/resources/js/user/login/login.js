
//sql injection 막기위한 검사
function validateLogin(){

 var id = document.forms[0].id; 

 var pw = document.forms[0].pw;

 if(id.value == "") {

  alert("id is null");

  id.focus();

  return false;

 }else if(id.value.indexOf("'") != -1){
      alert("you can't use single qutation!");
      id.value = "";
      id.focus();
      return false;
 }

 if(pw.value == "") {

  alert("pw is null");

  pw.focus();

      return false;

 }else if(pw.value.indexOf("'") != -1){
      alert("you can't use single qutation!");
      pw.value = "";
      pw.focus();
      return false;
 }

}


//로그인
function login(){
    $.ajax({
        type: "post",
        url : "/loginConfirm",
        data: {
        	loginID : $('#loginID').val(),
        	loginPW : $('#loginPW').val()
        },

        success:function (data) {//로그인성공
        	location.reload();
        },

        error: function (data) {//로그인실패
        	alert("아이디나 비밀번호가 맞지 않습니다.\n다시 입력해주시기 바랍니다.");
        }

 	});
	
	
}