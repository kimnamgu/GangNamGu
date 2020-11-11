/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 관리자변경
 * 설명:
 */

// 부서 또는 사용자 검색을 통해 사용자 객체를 담을 전역 배열변수
var globalObjStorage;
/**
 * 부서를 새로 가져온다.
 *
 */
 
 /**
 * 사용자 정보를 담을 객체
 */
function userObject(){
	this.userId = '';
	this.userName = '';
	this.deptId = '';
	this.deptName = '';
	this.grpGbn = '';
	this.mainFl = '';
	return this;
}

/**
 * 설명 : 사용자 객체에 특정 속성의 데이터를 설정한다.
 * 인자1 obj - 속성정보를 저장할 사용자 객체
 * 인자2 node - 속성정보를 갖고 있는 XML Node객체
 */
userObject.prototype.setData = function(obj, node){
	// obj의 속성(prop)에 대해 루프를 돌면서 
	for (prop in obj){
		if (prop == node.attributes[0].value){
			obj[prop] = node.text;
			break;
		}
	}
	return obj;
};
 
 /*
function loadFormation(){
	var treeFormation = document.getElementById('treebox_formation');
	treeFormation.innerHTML = '';
	tree = new dhtmlxtreeObject("treebox_formation","100%","100%",0);
	tree.setImagePath("/dhtmlxtree/imgs/");
	  
	//tree.enableCheckBoxes(true);
	tree.setOnClickHandler(getUserList);
	tree.loadXML("/commsubdept/formationList.do");
}
*/

/**
 * 조직도를 표시한다.
 */
function loadFormation(orgid){
	var orggbn = document.forms[0].orggbn.value;
	setUrl("/formation.do");
	setType("DEPT");
	setViewObjectId(formationLayer);
	getFormation(true, orggbn, orgid);
}

//콤보박스에서 선택 
function orggbnChange(){
	loadFormation();
}

//조직도에서 넘어오는 데이터 : 조직도 더블클릭시 호출 메소드 
function fTreeClick(orgid, orgnm, orgfullnm, upper_orgid, upper_orgnm, upper_orgfullnm, grpgbn) {
	getUserList(orgid);
}

/**
 * 설명 : 역할_사용자 추가/삭제 시 사람이 선택되어 있는지 상태체크
 */
function chkDeptStatus(){
	var selectedDeptId = tree.getSelectedItemId();
	
	if (selectedDeptId == ''){
		alert('선택하신 부서가 없습니다.');
		return false;
	}
	return true;
}

/**
 * 설명 : 조직도에서 부서를 선택할 경우 해당부서의 사용자를 조회한다.
 * 인자 : 조직도에서 선택된 부서코드
 */
function getUserList(id){
	searchUser(id);
}

/**
 * 설명 : 부서 또는 사용자검색 시 사용자를 조회한다.
 * 인자 : 부서코드
 */
function searchUser(deptCode){
	
	var params = "";
	var vUserName = document.searchForm.userName;
	
	if (deptCode != null && deptCode != undefined){
		params += 'deptCode=' + encodeURIComponent(deptCode);
		vUserName.value = '';
	}else{
		if (vUserName.value == ''){
			alert('검색할 직원명을 입력해주세요');
			vUserName.focus();
			return false;
		}
		params += '&userName=' + encodeURIComponent(vUserName.value);
	}
	var userList = new Ajax.Request('/commsubdept/deptuserList.do', {method: 'post', postBody: params, onComplete: parseDeptUser});
	return false;
}

/**
 * 부서별 사용자 조회에 대한 결과XML을  파싱한다.
 */
function parseDeptUser(httpXML){

	var xmlDoc = httpXML.responseXML;
	
	var root = xmlDoc.documentElement;
	var dataList = root.childNodes;

	globalObjStorage = new Array();

	for (var i = 0 ; i < dataList.length; i++){
		
		var user = new userObject();
		for (var j = 0 ; j < dataList[i].childNodes.length; j++){
			user.setData(user, dataList[i].childNodes[j]);
		}
		
		globalObjStorage[i] = user;
		user = null;
	}
	
	writeDeptUser();
}

/**
 * 설명 : 파싱된 사용자 정보를 화면에 출력한다.
 */
function writeDeptUser(){

	var	htmlStr = '        <form name="userForm" onsubmit="return false">'
				+ '        <table width="340" border="0" align="ceneter" cellpadding="1" cellspacing="0">';
	
	if (globalObjStorage.length > 0){
		for (var i = 0 ; i < globalObjStorage.length ; i++){
			user = globalObjStorage[i];
			htmlStr += '          <tr>'
					+ '            <td width="40" align="center"><input type="hidden" name="deptName" value="' + user.deptName + '"><input type="hidden" name="userName" value="' + user.userName + '"><input type="checkbox" style="border:0;background-color:transparent;" name="userId" value="' + user.userId + '"></td>'
					+ '            <td width="200" align="center">' + user.deptName + '</td>'
					+ '            <td width="100" align="center">' + user.userName  + '</td>'
					+ '          </tr>';
					
			user = null;
		}
	}else{
		htmlStr += '          <tr>'
				+ '            <td height="25" align="center" colspan="3">직원이 존재하지 않습니다.</td>'
				+ '          </tr>';
	}
	
	htmlStr += '        </table>'
			+ '        </form>';
	
	$('userList').innerHTML = htmlStr;
	globalObjStorage = null;
}

/**
 * 설명 : 모두 선택에 따른 사용자 체크박스 적용
 *
 */
function allCheck(obj){
	var idList;
	var chk = false;
	
	if (obj.checked)
		chk = true;	
	
	if (document.forms('userForm')){

		idList = userForm.elements['userId'];

		if (idList){
			if (idList.length){
//				alert('사용자정보 2개이상');
				for (var i = 0 ; i < idList.length; i++){
					idList[i].checked = chk;
				}
			}else{
//				alert('사용자정보 1개');
				idList.checked = chk;
			}
		}

	}
}

/**
 * 선택된 사용자를 부모창에 값넘겨주기 
 */
function addUser(){
	var userId, userName, deptName;
	var message = "";
	var addUserList = new Array();

	if (document.forms('userForm')){
		
		userId = userForm.elements['userId'];
		deptName = userForm.elements['deptName'];
		userName = userForm.elements['userName'];
		
		if (userId){
			if (userId.length){

				for (var i = 0 ; i < userId.length ; i++){

					if (userId[i].checked){
						user = new userObject();
						user.userId = userId[i].value;
						user.userName = userName[i].value;
						user.deptName = deptName[i].value;
						
						addUserList.push(user);
						user = null;
						message = '';
					}
				}
			}else{
				if (userId.checked){
					user = new userObject();
					user.userId = userId.value;
					user.userName = userName.value;
					user.deptName = deptName.value;
					
					addUserList.push(user);
					message = '';
				}
			}// end (userId.length)
			
			if (addUserList.length == 0)
				message = '선택하신 직원이 없습니다.';
		}// end (userId)
	}

	if (message != ''){
		alert(message);
	}else{
		validation(addUserList);
	}
	return;
}

function validation(userList){
	var opener = window.dialogArguments; 
	
	var idList = new Array();
	var nameList = new Array();

	for (var i = 0 ; i < userList.length; i++){
		user = userList[i];
		idList.push(user.userId);
		nameList.push(user.userName);
	}
	
	opener.document.forms[0].user_id.value = idList;

  cancel();
}

function errMsg(e){
	alert(e);
}

function cancel(){
	self.close();
}
