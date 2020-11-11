/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 입력담당자지정
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
	this.className = '';
	this.deptName = '';
	this.sancYn = '';
	this.chrgunitNm ='';
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
 
function loadUser(){
	var treeUser = document.getElementById('treebox_user');
	treeUser.innerHTML = '';
	tree=new dhtmlxtreeObject("treebox_user","100%","100%",0);
	tree.setImagePath("/dhtmlxtree/imgs/");
	  
	//tree.enableCheckBoxes(true);
	tree.setOnDblClickHandler(addUser);
	tree.loadXML("/commapproval/inputusrList.do");
}

/**
 * 선택된 사용자를 역할그룹에 추가한다.
 */
function addUser(){
	var addUserList = new Array();
	
	// 사용자를 선택 했는지 여부 확인
	if (!chkUserStatus()){
		return;
	}	
	
	var userId = tree.getSelectedItemId();
	var level = tree.getLevel(userId);
	var userName = tree.getItemText(userId);
	var className = tree.getUserData(userId, 'className');
	var deptName = tree.getUserData(userId, 'deptName');
	var sancYn = tree.getUserData(userId, 'sancYn');
	var chrgunitNm = tree.getUserData(userId, 'chrgunitNm');
	
	if (userId){
		user = new userObject();
		user.userId = userId;
		user.userName = userName;
		user.className = className;
		user.deptName = deptName;
		user.chrgunitNm = chrgunitNm;

		addUserList.push(user);
		user = null;
	}
		
	addList(addUserList);
	
	return;
}

/**
 * 설명 : 역할_사용자 목록에 선택된 사용자 목록을 추가한다.
 * 인자1 userObject객체를 담고 있는 Array
 */
function addList(userList){
	var opt;
	var user;
	
	for (var i = 0; i < userList.length ; i++){
		user = userList[i];
		// 기존에 존재하지 않는 사용자에 대해서만 추가
		if (!existUserChk(user.userId)){
		
			opt = document.createElement('OPTION');
			opt.value = user.userId + '(' + '01'+ '|'+ user.chrgunitNm + ')';
			opt.text = user.userName ;
			
			document.frm.userList.add(opt);
			
			user = null;
			opt = null;
		}
	}
}

/**
 * 설명 : 사용자가 존재하는지 여부를 체크
 * 인자 : 사용자ID, 구
 */
function existUserChk(userId){
	var startIdx = 0;
	var localId = "";
	var UserList= document.frm.userList.options;
	
	if (UserList.length > 0){
		for (var i = 0 ; i < UserList.length ; i++){
			localId = UserList[i].value;
			startIdx = localId.indexOf('(');	
			if (localId.substring(0,(startIdx>-1)? startIdx: localId.length)== userId){
				return true;
				break;
			}
		}
	}
	return false;
}

/**
 * 선택된 사용자를 삭제한다.
 */
function delUser(){
	var userList= document.frm.userList;
	var UserCnt =userList.options.length;
	
	for (var i = (UserCnt - 1) ; i > -1 ; i--){
		if (userList.options[i].selected){
			var selectId = userList.options[i].value;
			startIdx = selectId.indexOf('('); 
			endIdx = selectId.indexOf('|');

			var yn = selectId.substring((startIdx + 1),endIdx);	
			if(yn != '01'){
				alert('입력된 내용이 있습니다 삭제할 수 없습니다.');
			}else{
				userList.remove(i);
			}	
		}
	}
}

/**
 * 설명 : 역할_사용자 추가/삭제 시 사람이 선택되어 있는지 상태체크
 */
function chkUserStatus(){
	var selectedUserId = tree.getSelectedItemId();
	
	if (selectedUserId == ''){
		alert('선택하신 사람이 없습니다.');
		return false;
	}
	return true;
}

/**
 * 설명 : 입력담당자 기존등록내용보기.
 * 인자 : 시스템문서번호
 */
function viewUser(){

	var params = '';
	var sysdocno = document.frm.sysdocno;
	
	params += 'sysdocno=' + encodeURIComponent(sysdocno.value);

	var userList = new Ajax.Request('/commapproval/inputusrView.do', {method: 'post', postBody: params, onComplete: parseGumToUser});
	return false;
}
/**
 * 사용자에 대한 결과(XML)를 파싱한다.
 */
function parseGumToUser(httpXML){
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
	
	writeGumToUser();
}
/**
 * 결제선 지정  사용자를 화면에 출력한다.
 */
function writeGumToUser(){
	var userList = document.frm.userList;
	userList.innerHTML = '';	
	
	if (globalObjStorage.length > 0){
		var opt;
		var opt1;
			  
		for (var i = 0 ; i < globalObjStorage.length ; i++){
			user = globalObjStorage[i];
			opt = document.createElement('OPTION');
			opt.value = user.userId + '('+user.sancYn + '|'+ user.chrgunitNm + ')';
			opt.text = user.userName + '(' + user.className + ')';
			userList.add(opt);	
			user = null;
		}
	}else{
		userList.innerHTML = '';
	}
	
	globalObjStorage = null;
	
	var user_id = document.forms[0].user_id.value;
	var user_name = document.forms[0].user_name.value;
	var dept_code = document.forms[0].dept_code.value;
	var class_name = document.forms[0].class_name.value;
	var origindeptcd = document.forms[0].origindeptcd.value;
	
	if ( dept_code == origindeptcd ) {
		
		if ( !existUserChk(user_id) && confirm("본인을 입력담당자로 지정하시겠습니까?") ) {
			var addUserList = new Array();
			var user = new userObject();
			user.userId = user_id;
			user.userName = user_name + "(" + class_name + ")";
			addUserList.push(user);
			addList(addUserList);
			//validation();
		}
	}	
}
/**
 * 입력  사용자를 등록한다. 
 */
function validation(){
	var opener = window.dialogArguments; 
	var url = '/commapproval/inputusrInsert.do';
	var params = '';
	var retid = document.frm.retid.value;
	var approvalUser = document.frm.userList;
	var sysdocno = document.frm.sysdocno.value;
	
	var idList = new Array();
	var nameList = new Array();
	var nameList1 = new Array();
	var setList = new Array();
	
	var tmpId = '';
	var tmpName = '';
	var startIdx = 0;
	var startIdx1 = 0;
	
	if(approvalUser.length  < 1) {
		alert("입력담당자가 지정이 되지 않았습니다.\n입력담당자를 지정하십시오.");
		return;
	}
	
	for (var i = 0 ; i < approvalUser.options.length; i++){
		user = new userObject();
		tmpId 	= approvalUser.options[i].value;
		tmpName = approvalUser.options[i].text;
		
	  startIdx = tmpId.indexOf('(');
	  startIdx1 = tmpId.indexOf('|');
	  user.sancYn = tmpId.substring((startIdx + 1), startIdx1);
	  user.chrgunitNm = tmpId.substring((startIdx1 + 1 ), (tmpId.length - 1));
		if(user.sancYn == '01'){
			idList.push(tmpId.substring(0, (startIdx > -1)? startIdx : tmpId.length));
		}
		startIdx = tmpName.indexOf('(');
		user.userName = tmpName.substring(0, (startIdx > -1)? startIdx : tmpName.length);
		if(user.sancYn == '01'){
			nameList.push(tmpName.substring(0, (startIdx > -1)? startIdx : tmpName.length));
		}
		
		nameList1.push(tmpName.substring(0, (startIdx > -1)? startIdx : tmpName.length));
		setList.push(user);
		user = null;
		
	}
	
	if(retid == ""){
		opener.document.forms[0].appntusrnm.value = nameList1;
	}else{
		opener.inputusrset(setList, retid);
	}

	params = 'cmd=INSERT' + '&sysdocno=' + sysdocno + '&idList=' + encodeURIComponent(idList.join(':')) 
			+ '&nameList=' + encodeURIComponent(nameList.join(':'));
	var insert = new Ajax.Request(url, {method: 'post', postBody: params, onComplete: callBack, onFailure: errMsg});
}


function callBack(resXML){
	var xmlDoc = resXML.responseXML;
	var cmd, retCode, msg;
	
	var resultNode = xmlDoc.getElementsByTagName('result');

	cmd = resultNode[0].childNodes[0].text;
	retCode = resultNode[0].childNodes[1].text;
	msg = resultNode[0].childNodes[2].text;
	
	if (cmd == 'INSERT'){
		if (parseInt(retCode, 10) > 0){
			//alert('입력담당자가  저장이 완료되었습니다.');
			if(msg != "") {
				window.dialogArguments.alert(msg);
			}
			self.close();
			window.dialogArguments.location.reload();
		} else if (parseInt(retCode, 10) == 0){
			alert('입력담당자 저장시 에러가 발생하였습니다.');
		}
	}
}

function errMsg(e){
	alert(e);
}

function cancel(){
	self.close();
}