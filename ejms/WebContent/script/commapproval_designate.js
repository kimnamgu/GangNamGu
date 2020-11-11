/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 결재선지정
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
	this.gubun = '';
	this.sancYn = '';
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
 
function loadDept(){
	var treeUser = document.getElementById('treebox_user');
	treeUser.innerHTML = '';
	tree=new dhtmlxtreeObject("treebox_user","100%","100%",0);
	tree.setImagePath("/dhtmlxtree/imgs/");
	  
	//tree.enableCheckBoxes(true);
	tree.setOnDblClickHandler(addUser);
	tree.loadXML("/commapproval/designateList.do");
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
	
	if (userId){
		user = new userObject();
		user.userId = userId;
		user.userName = userName;
		user.className = className;
		user.deptName = deptName;
		
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
	var gubun;
	var flag;

	if(document.frm.gubun[0].checked == true){
		 gubun= document.frm.gumTo;
		 flag = '1';
	}else{
		 gubun= document.frm.sungIn;
		 flag = '2';
	}
	
	var opt;
	var user;
	
	if(flag == '1'){
		if(stateChk()){
			return; 
		}
	}
	
	for (var i = 0; i < userList.length ; i++){
		user = userList[i];
		// 기존에 존재하지 않는 사용자에 대해서만 추가
		if (!existUserChk(user.userId, flag)){
		
			opt = document.createElement('OPTION');
			opt.value = user.userId + '(0)';
			opt.text = user.userName;
			
			gubun.add(opt);
			
			user = null;
			opt = null;
		}
	}
}

/**
 * 설명 : 사용자가 존재하는지 여부를 체크
 * 인자 : 사용자ID, 구
 */
function existUserChk(userId,flag){
	var UserList;
	var localId;
	var startIdx = 0;
	if(flag == '1'){
		UserList = document.frm.gumTo.options;
	}else{
		UserList = document.frm.sungIn.options;
	}

	if (UserList.length > 0){
		for (var i = 0 ; i < UserList.length ; i++){
			localId = UserList[i].value;
			startIdx = localId.indexOf('(');
			if (localId.substring(0,(startIdx>-1)? startIdx: localId.length) == userId){
				return true;
				break;
			}
		}
	}
	return false;
}

/**
 * 설명 : 사용자가 문서 상태 및 결재 상태에 따른 결재자 추가 삭제  여부를 체크
 * 인자 : 사용자ID, 구
 */
function stateChk(){
	var type = document.frm.type.value;
	var state = document.frm.state.value;

	if(type == '1'){
		if(state == '03'){
			alert("승인대기 상태입니다 검토자를 추가 및 삭제 할수 없습니다.");
			return true;
		}
	}else{
		if(state == '04'){
			alert("승인대기 상태입니다 검토자를 추가 및 삭제 할수 없습니다.");
			return true;
		}
	}
	return false;
}


/**
 * 선택된 사용자를 삭제한다.
 */
function delUser(userList){
	var userList;
	var startIdx;
	var endIdx;

	//더블클릭으로 삭제하기 위하여 수정 (080206 장덕용)
	if (userList == null) {
		if(document.frm.gubun[0].checked == true){
			if(stateChk()){
				return;
			}else{
				userList = document.frm.gumTo;
			}
		}else{
			userList = document.frm.sungIn;
		}
	}
		
	var UserCnt =userList.options.length;
	for (var i = (UserCnt - 1) ; i > -1 ; i--){
		if (userList.options[i].selected){
			var selectId = userList.options[i].value;
			startIdx = selectId.indexOf('('); 
			endIdx = selectId.indexOf(')');

			var yn = selectId.substring((startIdx + 1),endIdx);

			if(yn == '1'){
				alert('결재가완료되었습니다 삭제할 수 없습니다.');
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
 * 설명 : 결제라인 기존등록내용보기.
 * 인자 : 시스템문서번호, 구분(취합부서결제:1, 제출부서결제:2)
 */
function viewUser(){

	var params = '';
	var sysdocno = document.frm.sysdocno;
	var type = document.frm.type;
	
	params = 'sysdocno=' + encodeURIComponent(sysdocno.value)+ '&type=' + encodeURIComponent(type.value); 

	var userList = new Ajax.Request('/commapproval/designateView.do', {method: 'post', postBody: params, onComplete: parseGumToUser});
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
	var gumToUser = document.frm.gumTo;
	var sungInUser = document.frm.sungIn;
	gumToUser.innerHTML = '';	
	sungInUser.innerHTML = '';
	
	if (globalObjStorage.length > 0){
		var opt;
		var opt1;
		for (var i = 0 ; i < globalObjStorage.length ; i++){
			user = globalObjStorage[i];
			
			if(user.gubun == '1'){
				opt = document.createElement('OPTION');
				opt.value = user.userId + '('+ user.sancYn + ')';
				opt.text = user.userName + '(' + user.className + ')';
				gumToUser.add(opt);	
				user = null;
			}else{
				opt1 = document.createElement('OPTION');
				opt1.value = user.userId + '('+ user.sancYn + ')';
				opt1.text = user.userName + '(' + user.className + ')';
				sungInUser.add(opt1);	
				user = null;
			}
		}
	
	}else{
		gumToUser.innerHTML = '';
		sungInUser.innerHTML = '';
	}
	
	globalObjStorage = null;
}

function validation(){
	var opener = window.dialogArguments; 
	var url = '/commapproval/designateInsert.do';
	var params = '';
	var gumToUser = document.frm.gumTo;
	var sungInUser = document.frm.sungIn;
	var isMaking = document.frm.isMaking.value;
	var sysdocno = document.frm.sysdocno.value;
	var type = document.frm.type.value;
	var retid = document.frm.retid.value;
	
	var idList = new Array();
	var nameList = new Array();
	var nameList1 = new Array();
	var gubunList = new Array();
	
	var gumtoList = new Array();
	var sunginList = new Array();
	
	var tmpId = '';
	var tmpName = '';
	var startIdx = 0;
	
	if(gumToUser.options.length > 0){
		if(sungInUser.options.length<=0){
			alert("최종승인자를 입력하여야 합니다.")
			return;
		}
	}
	
	for (var i = 0 ; i < gumToUser.options.length; i++){
		user = new userObject();
		tmpId = gumToUser.options[i].value;
		tmpName = gumToUser.options[i].text;
		
		startIdx = tmpId.indexOf('(');

		user.sancYn = tmpId.substring((startIdx + 1),(startIdx +2));
		if(tmpId.substring((startIdx + 1),(startIdx +2)) == '0'){
			idList.push(tmpId.substring(0,(startIdx>-1)? startIdx: tmpId.length));
		
			startIdx = tmpName.indexOf('(');
			nameList.push(tmpName.substring(0, (startIdx > -1)? startIdx : tmpName.length));
			gubunList.push('1');
		}
		startIdx = tmpName.indexOf('(');
		user.userName = tmpName.substring(0, (startIdx > -1)? startIdx : tmpName.length);

		gumtoList.push(user);
		user = null;
		startIdx = 0;
	}
	if(retid ==""){
		opener.document.forms[0].sancusrnm1.value = nameList ;
	}
	
	for (var i = 0 ; i < sungInUser.options.length; i++){
		user1 = new userObject();
		tmpId = sungInUser.options[i].value;
		tmpName = sungInUser.options[i].text;
		
		startIdx = tmpId.indexOf('(');
		user1.sancYn = tmpId.substring((startIdx + 1),(startIdx +2));
		if(tmpId.substring((startIdx + 1),(startIdx +2)) == '0'){
			idList.push(tmpId.substring(0,(startIdx>-1)? startIdx: tmpId.length));
		
			startIdx = tmpName.indexOf('(');
			nameList.push(tmpName.substring(0, (startIdx > -1)? startIdx : tmpName.length));
			nameList1.push(tmpName.substring(0, (startIdx > -1)? startIdx : tmpName.length));
			gubunList.push('2');
		}
		startIdx = tmpName.indexOf('(');
		user1.userName = tmpName.substring(0, (startIdx > -1)? startIdx : tmpName.length);
		
		sunginList.push(user1);
		user1 = null;
		startIdx = 0;
	}
	
	
	if(retid == ""){
		opener.document.forms[0].sancusrnm2.value= nameList1;
	}else{
		opener.designateset(gumtoList, sunginList, retid);
	}

	
	params = 'cmd=INSERT' + '&isMaking=' + isMaking + '&sysdocno='+ sysdocno + '&type=' + type + '&idList=' + encodeURIComponent(idList.join(':')) 
			+ '&nameList=' + encodeURIComponent(nameList.join(':')) + '&gubunList=' + encodeURIComponent(gubunList.join(':'));
	
	var update = new Ajax.Request(url, {method: 'post', postBody: params, onComplete: callBack, onFailure: errMsg});
}

function callBack(resXML){
	var isMaking = document.frm.isMaking.value;
	var xmlDoc = resXML.responseXML;
	var cmd, retCode, msg;
	
	var resultNode = xmlDoc.getElementsByTagName('result');

	cmd = resultNode[0].childNodes[0].text;
	retCode = resultNode[0].childNodes[1].text;
	msg = resultNode[0].childNodes[2].text;

	if (cmd == 'INSERT') {
		if (retCode == "true") {
			if(msg != "") {
				window.dialogArguments.alert(msg);
			}
			self.close();
		} else {
			alert('결재선지정 저장시 에러가 발생하였습니다.');
		}
	}

	if(isMaking != 1) {
		window.dialogArguments.location.reload();
	}	
}

function errMsg(e){
	alert(e);
}

function cancel(){
	self.close();
}