var select_dept_id	  = "";
var select_dept_name  = "";
var select_dept_level = 0;

var select_type = "";
var select_code = "";

//사용자와 부서 선택
function setbtndel(typ,data){
	if(typ=="USR") desc="사용자";
	else if(typ=="DEPT") desc="부서";
	else desc = "";
	
	select_type = typ;
	select_code = data;
	
	if(desc=="") {
		select_type = "";
		select_code = "";
		btn_del.innerText = "[부서/사용자를 선택하세요]";
	
	} else {
		btn_del.innerText = "["+desc+"] "+data+"";
	}
}

//이전페이지로 이동(p_err:구분코드)
function goBack(p_err){
	var s_msg="";
	if (p_err.toUpperCase() =="USR_I") {
		s_msg = "이미 존재하는 사용자아이디 입니다.";
	} else if (p_err.toUpperCase()=="DEPT_I") {
		s_msg = "이미 존재하는 부서코드 입니다.";
	}
  	alert(s_msg);
	history.back();
}

/******************** 첫번재 iframe START *******************************/

//첫번째 iframe
function click_deptname(dept_id, dept_name, dept_level){
	var orggbn = document.forms[0].orggbn.value;
	window.open('/usrMgrFrame2.do?dept_id='+dept_id+'&orggbn='+orggbn,'ifrm2');
	select_dept.innerHTML = "선택부서 :<B> "+dept_name+"</B>" ;
	select_dept_id 		= dept_id;
	select_dept_name 	= dept_name;
	select_dept_level = dept_level;
	select_orggbn 	  = document.forms[0].orggbn.value;
	setbtndel('','');
	window.open("about:blank","ifrm3");
}

function orggbnChange(frm){
  window.open('/usrMgrFrame1.do?orggbn='+frm.orggbn.value,'ifrm1');

}
/********************* 첫번재 iframe END ********************************/

/******************** 두번째 iframe START *******************************/

//하위부서명 
function click_edit_dept(dept_id, dept_name){
	setbtndel('DEPT',dept_id);
	window.open('/usrMgrFrame3.do?mode=dept_M&dept_id='+dept_id,'ifrm3');
}

//하위사용자명
function click_edit_usr(user_id, user_name, dept_id){
	setbtndel('USR',user_id);
	window.open('/usrMgrFrame3.do?mode=usr_M&user_id='+user_id+'&dept_id='+dept_id,'ifrm3');
}

//사용자 검색
function clickSearch(p_dept_id, p_dept_name, p_user_id){
	window.open('/usrMgrFrame2.do?dept_id='+p_dept_id,'ifrm2');
	select_dept.innerHTML = "현재선택부서: <B>("+p_dept_id+") "+p_dept_name+"</B>" ;
	select_dept_id    = p_dept_id;
	select_dept_name  = p_dept_name;

	window.open('/usrMgrFrame3.do?mode=usr_M&user_id='+p_user_id+'&dept_id='+p_dept_id,'ifrm3');
	setbtndel('USR',p_user_id);
}

//삭제
function click_del(){
	if(select_type!="USR" && select_type!="DEPT") {
		alert("부서/사용자를 선택하셔야 합니다.");
		return;
	}
	if(select_type=="USR") {
		if(!confirm('사용자 '+select_code+'을(를) 삭제하시겠습니까?')) return;				
		window.open('/deptManageDB.do?mode=usr_d&id='+select_code,'ifrm3');
	}
	if(select_type=="DEPT") {
		if(!confirm('부서 '+select_code+'을(를) 삭제하시겠습니까?')) return;
		window.open('/deptManageDB.do?mode=dept_d&id='+select_code,"ifrm3");
	}
}

//사용자 이동
function ifrm2_refresh(gubun){
	if(gubun == "I") alert("저장되었습니다.");
	if(gubun == "U") alert("수정되었습니다.");
	if(gubun == "D") alert("삭제되었습니다.");
	click_deptname(select_dept_id, select_dept_name);
}

//부서 이동
function ifrm1_refresh(gubun){
	if(gubun == "I") alert("저장되었습니다.");
	if(gubun == "U") alert("수정되었습니다.");
	if(gubun == "D") alert("삭제되었습니다.");
	if(gubun == "P") alert("암호가 초기화 되었습니다.");
  window.open('/usrMgrFrame1.do','ifrm1');
}
/********************* 두번째 iframe END ********************************/

/******************** 세번째  iframe START *******************************/

//신규부서
function click_new_dept(){
	if(select_dept_id.trim()=="") { alert("부서를 선택하세요"); return; }
	setbtndel('','');
	if(select_dept_level == 6) {
		window.open("about:blank","ifrm3");
		alert("더이상 하위부서를 만들 수 없습니다.");
		return;
	}
	window.open('/usrMgrFrame3.do?mode=dept_I&dept_id='+select_dept_id,'ifrm3');
}

//신규사용자
function click_new_usr(){
	if(select_dept_id.trim()=="") { alert("부서를 선택하세요"); return; }
	setbtndel('','');
	window.open('/usrMgrFrame3.do?mode=usr_I&dept_id='+select_dept_id,'ifrm3');
}

//사용자 패스워드 디폴트 Submit
function UsrDefaultPasswdSumbit(){
	if (confirm("암호를 초기화하면 1로 셋팅이 됩니다. \n암호를 초기화하시겠습니까?")) {
		ifrm3.document.forms[0].mode.value="defaultPasswd";
		ifrm3.document.forms[0].submit();
	} else {
		return false;
	}
}

//부서 Submit
function DeptSumbit(){
	if(ifrm3.document.forms[0].upper_dept_id.value.trim() == "") {
		alert("상위부서코드가 없습니다.");
		ifrm3.document.forms[0].upper_dept_id.focus();
		return;
	}
	if(ifrm3.document.forms[0].dept_id.value.trim() == "") {
		alert("부서코드가 없습니다.");
		ifrm3.document.forms[0].dept_id.focus();
		return;
	}
	if(getCharLen(ifrm3.document.forms[0].dept_id.value.trim())>40) {
		alert("부서코드 입력 최대 길이를 초과하였습니다.\n영문+숫자 40자, 한글 20자까지 입력가능합니다.");
		ifrm3.document.forms[0].dept_id.focus();
		return;
	}
	if(ifrm3.document.forms[0].dept_name.value.trim() == "") {
		alert("부서명이 없습니다.");
		ifrm3.document.forms[0].dept_name.focus();
		return;
	}
	if(getCharLen(ifrm3.document.forms[0].dept_name.value.trim())>40) {
		alert("부서명 입력 최대 길이를 초과하였습니다.\n영문+숫자 40자, 한글 20자까지 입력가능합니다.");
		ifrm3.document.forms[0].dept_name.focus();
		return;
	}
	
	if(ifrm3.document.forms[0].dept_rank.value.trim()!="" && ifrm3.document.forms[0].dept_rank.value.trim()>999999999) {
		alert("부서순번 입력 최대 값을 초과하였습니다.\n999,999,999이하로 입력하시기 바랍니다.");
		ifrm3.document.forms[0].dept_rank.focus();
		return;
	}
	ifrm3.document.forms[0].submit();
}

//사용자 Submit
function UsrSumbit(){
	if(ifrm3.document.forms[0].user_id.value.trim() == "") {
		alert("사용자 아이디가 없습니다.");
		ifrm3.document.forms[0].user_id.focus();
		return;
	}
	if(getCharLen(ifrm3.document.forms[0].user_id.value.trim())>40) {
		alert("사용자 아이디 입력 최대 길이를 초과하였습니다.\n영문+숫자 40자, 한글 20자까지 입력가능합니다.");
		ifrm3.document.forms[0].user_id.focus();
		return;
	}
	if(ifrm3.document.forms[0].user_name.value.trim() == "") {
		alert("사용자명이 없습니다.");
		ifrm3.document.forms[0].user_name.focus();
		return;
	}
	if(getCharLen(ifrm3.document.forms[0].user_name.value.trim())>80) {
		alert("사용자명 입력 최대 길이를 초과하였습니다.\n영문+숫자 80자, 한글 40자까지 입력가능합니다.");
		ifrm3.document.forms[0].user_name.focus();
		return;
	}
	if(ifrm3.document.forms[0].dept_id.value.trim() == "") {
		alert("부서를 지정하지 않았습니다.");
		ifrm3.document.forms[0].dept_id.focus();
		return;
	}
	
	//패스워드입력
	if(ifrm3.document.forms[0].passwd.value.trim() == "") {
		alert("암호를 입력해 주시기 바랍니다.");
		ifrm3.document.forms[0].passwd.focus();
		return;
	}
	ifrm3.document.forms[0].submit();
}
/********************* 세번째 iframe END ********************************/
