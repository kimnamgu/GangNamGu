/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 취합대상지정
 * 설명:
 */

// 부서 또는 사용자 검색을 통해 사용자 객체를 담을 전역 배열변수
var globalObjStorage;
var treeformation1=null;
var treeformation1State=false;
var treeformation2=null;
var treeformation2State=false;

/**
 * 부서를 새로 가져온다.
 *
 */
 
 /**
 * 사용자 정보를 담을 객체
 */
function deptObject(){
	this.deptId = '';
	this.deptName = '';
	this.deptFullName = '';
	this.grpGbn = '';
	this.mainyn = '';
	return this;
}

/**
 * 설명 : 사용자 객체에 특정 속성의 데이터를 설정한다.
 * 인자1 obj - 속성정보를 저장할 사용자 객체
 * 인자2 node - 속성정보를 갖고 있는 XML Node객체
 */
deptObject.prototype.setData = function(obj, node){
	// obj의 속성(prop)에 대해 루프를 돌면서 
	for (prop in obj){
		if (prop == node.attributes[0].value){
			obj[prop] = node.text;
			break;
		}
	}
	return obj;
};

function loadFormation(orgid) {
	var orggbn = document.forms[0].sch_orggbn.value;
	setTree("treeformation1");
	setUrl("/formation.do");
	setType("DEPTUSER");
	setViewObjectId(treebox_formation);
	setSearchData(document.forms[0].searchType.value, document.forms[0].searchValue.value);
	if ( treeformation1State == false) {
		getFormation(true, orggbn, orgid);
		treeformation1State = true;
	}
}

function searchFormation() {
	if ( document.forms[0].searchValue.value.trim() != "" ) {		
		setTree("treeformation1");
		setUrl("/formation.do");
		setType("DEPTUSER");
		setViewObjectId(treebox_formation);
		setSearchData(document.forms[0].searchType.value, document.forms[0].searchValue.value);
		getFormation(true, "", "");
	} else {
		setSearchData("", "");
		treeformation1State = false;
		loadFormation();
	}
}

function loadDistribute() {
	var orggbn = document.forms[0].grp_orggbn.value;
	setTree("treeformation2");
	setUrl("/formation.do");
	setType("GROUP");
	setViewObjectId(treebox_distribute);
	setSearchData("", "");
	if ( treeformation2State == false ) {
		getFormation(true, orggbn, "");
		treeformation2State = true;
	}
}

// 콤보박스에서 선택 
function orggbnChange(gubun){
	if ( gubun == 0 ) {
		treeformation1State = false;
		loadFormation();
	} else {
		treeformation2State = false;
		loadDistribute();
	}
}

/**
 * 배포목록에 대한 결과(XML)를 파싱한다.
 */
function parseDetail(httpXML){
	var xmlDoc = httpXML.responseXML;
	
	var root = xmlDoc.documentElement;
	var dataList = root.childNodes;

	globalObjStorage = new Array();

	for (var i = 0 ; i < dataList.length; i++){
		
		var dept = new deptObject();
		for (var j = 0 ; j < dataList[i].childNodes.length; j++){
			dept.setData(dept, dataList[i].childNodes[j]);
		}
		
		globalObjStorage[i] = dept;
		dept = null;
	}
	
	writeDetail();
}

/**
 * 배포목록에 대한 정보를 출력한다.
 * 인자1 - id : 선택한 배포목록에 대한 부서코드
 */
function getDetailList(id){
	var params = 'grpId=' + encodeURIComponent(id);
	var detailList = new Ajax.Request('/commsubdept/grpdetailList.do', {method: 'post', postBody: params, onComplete: parseDetail});
}

/**
 * 배포목록 선택 시 해당하는부서목록을 화면에 출력한다.
 */
function writeDetail(){
	var grpDetail = document.forms[0].grpdetail;
	grpDetail.innerHTML = '';	
	if (globalObjStorage.length > 0){
		var opt;
		for (var i = 0 ; i < globalObjStorage.length ; i++){
			dept = globalObjStorage[i];
			opt = document.createElement('OPTION');
			opt.value = dept.deptId;
			opt.text = dept.deptFullName;
			opt.originText = dept.deptName;
			
			grpDetail.add(opt);
			dept = null;
		}
	}else{
		grpDetail.innerHTML = '';
	}
	
	globalObjStorage = null;
}

/**
 * 선택된 부서를  추가한다.
 */
function addDept() {
	if ( SELECTED_ITEM == null ) return;

	var addDeptList = new Array();
	var level = SELECTED_ITEM.level;
	var grpgbn = SELECTED_ITEM.grpgbn;
	var mainyn = SELECTED_ITEM.main_yn;
	var deptId = SELECTED_ITEM.dept_id;
	var deptName = SELECTED_ITEM.dept_name;
	var deptFullName = SELECTED_ITEM.dept_fullname;
	
	if ( mainyn != "Y" && !document.getElementById("subdeptId").checked ) {
		alert("대상부서가 아닙니다");
		return;
	}
	
	if ( deptId ) {
		if ( document.getElementById("subdeptId").checked ) {
			try {
				var url = "/researchSubDeptList.do?deptid=" + deptId;
				var subdeptCallBack = function(resultXML) {
					var xmlDoc = resultXML.responseXML;
					var resultNode = xmlDoc.getElementsByTagName('result');
					
					for ( var i = 0; i < resultNode[0].childNodes.length; i++ ) {
						dept = new deptObject();
						dept.grpGbn = 2
						dept.deptId = resultNode[0].childNodes[i].childNodes[0].text;
						dept.deptName = resultNode[0].childNodes[i].childNodes[1].text;
						dept.deptFullName = resultNode[0].childNodes[i].childNodes[2].text;
				
						addDeptList.push(dept);
						dept = null;
					}
					addList(addDeptList);
				}
				new Ajax.Request(url, {onComplete:subdeptCallBack});
			} catch (exception) {}
		} else {
			dept = new deptObject();
			dept.grpGbn = grpgbn;
			dept.deptId = deptId;
			dept.deptName = deptName;
			dept.deptFullName = deptFullName;
	
			addDeptList.push(dept);
			dept = null;
			
			addList(addDeptList);
		}
	}
}

function addDept1(){
	if ( SELECTED_ITEM == null ) return;
	
	var addDeptList = new Array();
	var level = SELECTED_ITEM.level;
	var grpgbn = SELECTED_ITEM.grpgbn;
	var mainyn = SELECTED_ITEM.main_yn;
	var deptId = SELECTED_ITEM.dept_id;
	var deptName = SELECTED_ITEM.dept_name;
	
	var findIndex;
	if ( (findIndex = deptName.indexOf(" (공통)")) != -1 ) {
		deptName = deptName.substring(0, findIndex);
	}
	if ( (findIndex = deptName.toLowerCase().indexOf(" <img")) != -1 ) {
		deptName = deptName.substring(0, findIndex);
	}
	
	if (deptId){
		dept1 = new deptObject();
		dept1.grpGbn = grpgbn;
		dept1.deptId = deptId;
		dept1.deptName = deptName;
		dept1.deptFullName = deptName;
		
		addDeptList.push(dept1);
		dept1 = null;
	}
		
	addList(addDeptList);
}

/**
 * 설명 : 선택된 부서  추가한다.
 * 인자1 userObject객체를 담고 있는 Array
 */
function addList(deptList){
	var opt;
	var dept;
	
	for (var i = 0; i < deptList.length ; i++){
		dept = deptList[i];
		// 기존에 존재하지 않는 부서에 대해서만 추가
		if (!existDeptChk(dept.deptId)){
		
			opt = document.createElement('OPTION');
			opt.value = dept.deptId + '('+ dept.grpGbn + ')';
			opt.text = dept.deptFullName;
			opt.originText = dept.deptName;

			document.forms[0].deptList.add(opt);
			dept = null;
			opt = null;
		}
	}
}

function fTreeClick(orgid, orgnm, orgfullnm, upper_orgid, upper_orgnm, upper_orgfullnm, grpgbn) {
	if ( grpgbn == 1 ) {
		getDetailList(orgid);
	}
}

function fTreeDblClick(orgid, orgnm, orgfullnm, upper_orgid, upper_orgnm, upper_orgfullnm, grpgbn) {
	if ( grpgbn == 1 ) {
		addDept1();
	} else if ( grpgbn == 2 ) {
		if ( SELECTED_ITEM.main_yn == "Y" ) {
			addDept();
			event.cancelBubble = true;
		}
	} else if ( grpgbn == 3 ) {
		if ( SELECTED_ITEM.main_yn == "Y" ) {
			addDept();
			event.cancelBubble = true;
		}
	}
}

function saveDistribute(type, value) {
	var action = "/grpSave.do";
	var deptList = document.forms[0].deptList;
	
	if ( type == "insert" ) {
		var groupName = document.forms[0].groupName;
		
		if ( groupName.value.trim() == "" ) return;
	
		var wrongList = "";
		var idList = new Array();
		var grpgbnList = new Array();
		var nameList = new Array();
		var tmpId = '';
		var startIdx = 0;
		for (var i = 0 ; i < deptList.options.length; i++){
			tmpId = deptList.options[i].value ;
			startIdx = tmpId.indexOf('(');
			
			idList.push(tmpId.substring(0, (startIdx > -1)? startIdx : tmpId.length));
			grpgbnList.push(tmpId.substring((startIdx + 1),(startIdx + 2)));
			nameList.push(deptList.options[i].originText);
			
			if ( tmpId.substring((startIdx + 1),(startIdx + 2)) == "1" ) {
				if ( wrongList != "" ) wrongList += ", ";
				wrongList += deptList.options[i].text;
			}
		}
		
		if ( deptList.length == 0 ) {
			alert("등록된 설문대상이 없습니다.");
			return;
		}
		if ( wrongList != "" ) {
			alert("목록 저장시 배포목록은 포함할 수 없습니다.\n\n- 배포목록 : " + wrongList);
			return;
		}
		
		bgProcFrame.location.href = action + "?type=" + type + "&groupName=" + groupName.value +
																"&codeList=" + idList + "&codeGbnList=" + grpgbnList + "&nameList=" + nameList;
	} else if ( type = "delete" ) {
		var deptId = value;
		var deptName = event.srcElement.parentNode.innerHTML;
		
		var findIndex;
		if ( (findIndex = deptName.indexOf(" (공통)")) != -1 ) {
			deptName = deptName.substring(0, findIndex);
		}
		if ( (findIndex = deptName.toLowerCase().indexOf(" <img")) != -1 ) {
			deptName = deptName.substring(0, findIndex);
		}
	
		if ( confirm("[" + deptName + "] 배포목록을 삭제하시겠습니까?") == false ) {
			return;
		}
		
		for ( var i = 0; i < deptList.length; i++ ) {
			if ( deptList[i].value == deptId + "(1)" ) {
				deptList[i] = null;
			}
		}
		
		bgProcFrame.location.href = action + "?type=" + type + "&deptId=" + deptId;
	}
}

/**
 * 설명 : 존재하는지 여부를 체크
 * 인자 : 부서코드
 */
function existDeptChk(deptId){
	var DeptList= document.forms[0].deptList.options;
	
	if (DeptList.length > 0){
		for (var i = 0 ; i < DeptList.length ; i++){
		
			localId = DeptList[i].value;
			startIdx = localId.indexOf('(');
			if (localId.substring(0,(startIdx>-1)? startIdx: localId.length) == deptId){
				return true;
				break;
			}
		}
	}
	return false;
}

/**
 * 선택된 부서를 삭제한다.
 */
function delDept(){
	var DeptList= document.forms[0].deptList;
	
	var DeptCnt =DeptList.options.length;
	for (var i = (DeptCnt - 1) ; i > -1 ; i--){
		if (DeptList.options[i].selected){
			DeptList.remove(i);
		}
	}
}

/**
 * 설명 : 입력담당자 기존등록내용보기.
 * 인자 : 시스템문서번호
**/
function viewDept(){

	var params = '';
	var rchno = document.forms[0].rchno;
	var rchgrpno = document.forms[0].rchgrpno;
	
	params += 'rchno=' + encodeURIComponent(rchno.value);
	params += '&rchgrpno=' + encodeURIComponent(rchgrpno.value);

	var userList = new Ajax.Request('/commrchdeptView.do', {method: 'post', postBody: params, onComplete: parseDept});
	return false;
}
/**
 * 사용자에 대한 결과(XML)를 파싱한다.
 */
function parseDept(httpXML){
	var xmlDoc = httpXML.responseXML;	
	var root = xmlDoc.documentElement;
	var dataList = root.childNodes;

	globalObjStorage = new Array();

	for (var i = 0 ; i < dataList.length; i++){
		
		var dept = new deptObject();
		for (var j = 0 ; j < dataList[i].childNodes.length; j++){
			dept.setData(dept, dataList[i].childNodes[j]);
		}
		
		globalObjStorage[i] = dept;
		dept = null;
	}
	
	writeDept();
}
/**
 * 취합대상 지정  사용자를 화면에 출력한다.
 */
function writeDept(){
	var DeptList = document.forms[0].deptList;
	DeptList.innerHTML = '';	
	
	if (globalObjStorage.length > 0){
		var opt;
		var opt1;
		for (var i = 0 ; i < globalObjStorage.length ; i++){
			dept = globalObjStorage[i];
			opt = document.createElement('OPTION');
			opt.value = dept.deptId + '('+ dept.grpGbn + ')';
			opt.text = dept.deptFullName;
			opt.originText = dept.deptName;
			
			DeptList.add(opt);	
			user = null;
		}
	}else{
		DeptList.innerHTML = '';
	}
	
	globalObjStorage = null;
}

function divDisplay(n){
		var div = "";
		for(i=0; i<2; i++)
		{
			div = eval("div_display"+i+".style");

			if(i == n ){
				div.display ="block";
			}else{
				div.display ="none";
			}		
		}
	}

function validation(){
	var opener = window.dialogArguments; 
	var url = '/commrchdeptInsert.do';
	var params = '';
	var deptList = document.forms[0].deptList;
	var rchno = document.forms[0].rchno.value;
	var rchgrpno = document.forms[0].rchgrpno.value;
	
	var idList = new Array();
	var grpgbnList = new Array();
	var nameList = new Array();
	
	var tmpId = '';
	var startIdx = 0;
	for (var i = 0 ; i < deptList.options.length; i++){
		tmpId = deptList.options[i].value ;
		startIdx = tmpId.indexOf('(');
		
		idList.push(tmpId.substring(0, (startIdx > -1)? startIdx : tmpId.length));
		grpgbnList.push(tmpId.substring((startIdx + 1),(startIdx + 2)));
		nameList.push(deptList.options[i].originText);
	}
	
	params = 'cmd=INSERT' + '&rchno=' + rchno + '&rchgrpno=' + rchgrpno + '&idList=' + encodeURIComponent(idList.join(';')) + '&grpgbnList=' + encodeURIComponent(grpgbnList.join(';')) 
			+ '&nameList=' + encodeURIComponent(nameList.join(';'));
	
	var insert = new Ajax.Request(url, {method: 'post', postBody: params, onComplete: callBack, onFailure: errMsg});
}


function callBack(resXML){
	var opener = window.dialogArguments; 
	var xmlDoc = resXML.responseXML;
	var cmd, retCode;
	
	var resultNode = xmlDoc.getElementsByTagName('result');

	cmd = resultNode[0].childNodes[0].text;
	retCode = resultNode[0].childNodes[1].text;
	subdeptList = resultNode[0].childNodes[2].text;

	if (cmd == 'INSERT'){
		
		if (parseInt(retCode, 10) > 0){
			opener.document.forms[0].tgtdeptnm.value = subdeptList;
			self.close();
		} else if (parseInt(retCode, 10) == 0){
			alert('설문대상지정 에러가 발생하였습니다.');
		}
	}
}

function errMsg(e){
	alert(e);
}

function cancel(){
	self.close();
}