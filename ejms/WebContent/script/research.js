/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 설문조사
 * 설명:
 */

var globalObjStorage;
var selId;

/**
 * 사용자 정보를 담을 객체
 */
function examObject(){
	this.formseq = '';
	this.examseq = '';
	this.examcont = '';
	return this;
}

/**
 * 설명 : 사용자 객체에 특정 속성의 데이터를 설정한다.
 * 인자1 obj - 속성정보를 저장할 사용자 객체
 * 인자2 node - 속성정보를 갖고 있는 XML Node객체
 */
examObject.prototype.setData = function(obj, node){
	// obj의 속성(prop)에 대해 루프를 돌면서 
	for (prop in obj){
		if (prop == node.attributes[0].value){
			obj[prop] = node.text;
			break;
		}
	}
	return obj;
};

//IE7, IE8에서 getElementsByClassName 대신 사용할 수 있음
getElementsByClassNameCompatible = function(className) {
    if(document.getElementsByClassName) {
        return document.getElementsByClassName(className);
    }     
    var regEx = new RegExp('(^| )'+className+'( |$)');
    var nodes = new Array();
    var elements = document.body.getElementsByTagName("*");
    var len = elements.length;
    for(var i=0; i < len ; i++) {
        if(regEx.test(elements[i].className)) {
            nodes.push(elements[i]);
        }
    }
    elements = null;
    return nodes;
};

/* getElementsByClassName 을 IE8 이하에서 동작하게 하는 코드 */
if (!document.getElementsByClassName) {
    document.getElementsByClassName = function (cn) {
        var rx = new RegExp("(?:^|\\s)" + cn+ "(?:$|\\s)");
        var allT = document.getElementsByTagName("*"), allCN = [],ac="", i = 0, a;
 
        while (a = allT[i=i+1]) {
            ac=a.className;
            if ( ac && ac.indexOf(cn) !==-1) {
                if(ac===cn){ allCN[allCN.length] = a; continue;   }
                    rx.test(ac) ? (allCN[allCN.length] = a) : 0;
                }
        }
        return allCN;
    };
}

function changeFormseqCheck(obj) {
	var changeFormseqObj = document.getElementsByName(obj.name);
	for ( var i = 0; i < changeFormseqObj.length; i++ ) {
		if ( changeFormseqObj[i].oldvalue == obj.value ) {
			changeFormseqObj[i].value = obj.oldvalue;
			changeFormseqObj[i].oldvalue = changeFormseqObj[i].value;
			obj.oldvalue = obj.value;
		}
	}
}
 
function previewImage(divObj, imgObj, src, width, height) {
	var agent = navigator.userAgent.toLowerCase(); 
	var img = new Image();
	img.src = src;
	if ( typeof(src) != "undefined" ) {
		var filenm = src.toLowerCase().trim();
		if ( filenm.length > 0 &&
					(filenm.length < 8 ||
						!(filenm.substring(filenm.length - 4) == ".gif" ||
							filenm.substring(filenm.length - 4) == ".jpg" ||
							filenm.substring(filenm.length - 4) == ".png")) ) {
	
			alert("GIF, JPG, PNG 파일만 미리보기 하실 수 있습니다.");
			return;
		}
	}
	
	if ( divObj.style.display != "block" ) {
		var resizeObj = function() {
			divObj.style.width = width + 20;
			divObj.style.height = height + 20;
			//document.getElementById(divObj.id + "_f").width = width + 10;
			//document.getElementById(divObj.id + "_f").height = height + 10;
			//document.getElementById(divObj.id + "_t1").width = width + 20;
			//document.getElementById(divObj.id + "_t1").height = height + 20;
			//document.getElementById(divObj.id + "_t2").width = width + 20;
			document.getElementById(divObj.id + "_t2").border = 1;
			document.getElementById(divObj.id + "_t2").height = height + 20;
			document.getElementById(imgObj).width = width;
			document.getElementById(imgObj).height = height;
			document.getElementById(imgObj).src = src;
		}
		resizeObj();

		try {
			var setRealSize = function() {
				if ( !(img.width < width && img.height < height) ) {
					if(img.width > 590){
						var p_width = 590/img.width;
						width = 590;
						height = img.height*p_width;
					}else{
						width = img.width;
						height = img.height;
					}
					
				}
				resizeObj();
				//fullSizeWindow();
				window.resizeTo(710, screen.availHeight * 0.8);
			}
			
			
			
			if (agent.indexOf("chrome") != -1) {
					img.onload = function() {
						if ( img.readyState != "complete" ) {
							img.onreadystatechange = setRealSize;
							setRealSize();
						} else {
							setRealSize();
						}
					}
				}else{
					if ( img.readyState != "complete" ) {
						img.onreadystatechange = setRealSize;
						setRealSize();
					} else {
						setRealSize();
					}
				}

				
			
		} catch ( exception ) {}
		
		divObj.style.display = "block";
	} else {
		divObj.style.display = "none";
	}
	//fullSizeWindow();
	window.resizeTo(710, screen.availHeight * 0.8);
}
 
function checkFileType(fileObj) {
	return;	//모든 파일 첨부가능
	
	var filenm = fileObj.value.toLowerCase().trim();

	if ( filenm.length > 0 &&
				(filenm.length < 8 || filenm.substring(1, 3) != ":\\" ||
					!(filenm.substring(filenm.length - 4) == ".gif" ||
						filenm.substring(filenm.length - 4) == ".jpg" ||
						filenm.substring(filenm.length - 4) == ".png")) ) {

		alert("GIF, JPG, PNG 파일만 첨부할 수 있습니다.");
		resetFile(fileObj.name);
		return;
	}
}

function resetFile(objName) {
	document.getElementById(objName).outerHTML = document.getElementById(objName).outerHTML;
}
 
function showFileUpload(divObj, divObjIdx, examObj, examIdx) {
	divObj = document.getElementById(divObj + divObjIdx);

	if ( eval("document.forms[0]." + examObj).length == undefined ) {
		examObj = eval("document.forms[0]." + examObj);
	} else {
		examObj = eval("document.forms[0]." + examObj)[examIdx];
	}
	examValue = examObj.value;

	if ( divObj.style.display == "none" ) {
		divObj.style.display = "block";
		
		if ( examValue.length == 0 ) {
			examObj.value = "파일첨부";
		}
	} else {
		divObj.style.display = "none";
		if ( examValue.substring(examValue.length - 4) == "파일첨부" ) {
			examObj.value = examValue.substring(0, examValue.length - 4);
		}
	}
}
 
function setSearchDate() {
	var frm = document.forms[0];
	var a = frm.strdt;			//기간시작
	var b = frm.enddt;			//기간종료
	
	if (a.onpropertychange == null) {
		a.onpropertychange = setSearchDate;
		b.onpropertychange = setSearchDate;
	} else if (event.propertyName.toLowerCase() == "value") {
		var src = event.srcElement;
		if (src == a) {
			if (a.value > b.value) {
				b.onpropertychange = null;
				b.value = a.value;
				b.onpropertychange = setSearchDate;
			}
		} else if (src == b) {
			if (a.value > b.value || a.value == "") {
				a.onpropertychange = null;
				a.value = b.value;
				a.onpropertychange = setSearchDate;
			}
		}
	}
}

function showSwtichLayer(obj, option) {	
	if(option == "1" || option == "2") {		
		//(단일,중복)선택형
		eval("exam"+obj).style.display = "block";	
	} else if(option == "3" || option == "4") {
	  //단답형, 장문형
		eval("exam"+obj).style.display = "none";		
	}
	if(option == "1"){
		document.getElementById("require_"+obj).checked = true;
		exFrsqExsq(obj , option);
	}else{
		document.getElementById("require_"+obj).checked = false;
		exFrsqExsq(obj , option);
	}
	
	//보안처리
	if(option == "1" || option == "2" || option == "4"){
		eval("secu"+obj).style.visibility = "hidden";    	
	} else {
		eval("secu"+obj).style.visibility = "visible";  
	}
}
	
function showOpenType(option){
	var range = document.getElementsByName("range");
	var rangedetail = document.getElementsByName("rangedetail");
	
	if ( option == 1 ) {
		eval(opentype1).style.display = "block";
		eval(opentype2).style.display = "block";
		showTgtType('s');
		for ( var i = 0; i < rangedetail.length; i++ ) {
			rangedetail[i].checked = false;
		}
	} else {
		eval(opentype1).style.display = "block";
		eval(opentype2).style.display = "block";
		showTgtType('h');
		for ( var i = 0; i < rangedetail.length; i++ ) {
			if ( rangedetail[i].checked ) break;
			if ( i + 1 == rangedetail.length && range[1].checked ) rangedetail[0].checked = true;
		}
	}	
}

function showOpenTypeDetail(option){
	var range = document.getElementsByName("range");
	
	if ( option == 1 ) {
		eval(opentype1).style.display = "block";
		eval(opentype2).style.display = "block";
		showTgtType('s');
	} else {
		eval(opentype1).style.display = "none";
		eval(opentype2).style.display = "none";
		showTgtType('h');
		range[1].checked = true;
	}	
	
	showTgtType(option);
}

function showTgtType(option){
	var tgtdept1 = document.getElementById("tgtdept1");
	var tgtdept2 = document.getElementById("tgtdept2");
	var tgtdept3 = document.getElementById("tgtdept3");
	var tgtdept4 = document.getElementById("tgtdept4");
	var tgtdept5 = document.getElementById("tgtdept5");
	var tgtdept6 = document.getElementById("tgtdept6");
	var opentype = document.forms[0].opentype;
	
	if ( option == "s" && opentype[0].checked == true ) {
		tgtdept1.style.display = "block";
		tgtdept2.style.display = "block";
		tgtdept3.style.display = "block";
		tgtdept4.style.display = "block";
		tgtdept5.style.display = "block";
		tgtdept6.style.display = "block";
	} else {
		tgtdept1.style.display = "none";
		tgtdept2.style.display = "none";
		tgtdept3.style.display = "none";
		tgtdept4.style.display = "none";
		tgtdept5.style.display = "none";
		tgtdept6.style.display = "none";
	}
}

function makeForm(frm, mode){
	if(frm.title.value.trim() == "") {
		alert("제목을 입력하세요.");			
		frm.title.focus();
		return;
	}
	
	if(frm.formcount.value > 99 || frm.formcount.value < 1){
		alert("문항만들기는 1~99개 까지 만들 수 있습니다.");
		frm.formcount.focus();
		return;
	}
	
	if(frm.examcount.value > 50 || frm.examcount.value < 1){
		alert('한 문항당 보기는 1~50개 까지 만들 수 있습니다.');
		frm.examcount.focus();
		return;
	}
	if(frm.limit1chk.checked != ""){
		if(frm.tgtdeptnm.value == ""){
			alert('설문대상 제한여부를 확인해주세요.');
			return;
		}
	}
	if(frm.limit2chk.checked != ""){
		if(frm.othertgtnm.value == ""){
			alert('설문기타대상 제한여부를 확인해주세요.');
			return;
		}
	}
	
	if(mode == "add"){
		frm.posscroll.value = document.body.scrollTop + 85;
	}
	setEx_sqs();
	frm.mode.value = mode;
	processingShow();
	frm.submit();	
}	

//완료시에 확인해야 할 사항
function check_complete(frm, type){
	var obj = document.forms[0];
	var range;
	
	if(obj.title.value.trim() == "") {
		alert("제목을 입력하세요.");			
		obj.title.focus();
		return;
	}
	
	if(obj.formcount.value < 1){		
		alert('문항만들기를 먼저 수행하세요!');
		return;
	}

	if(typeof(obj.formseq) == 'undefined'){
		if(obj.formcount.value > 0){		
			alert('문항만들기 버튼을 누르세요!');
			return;
		}
	} else {
		if(typeof(obj.formseq.value) != 'undefined'){
			if(obj.formseq.value != obj.formcount.value){
				alert('문항만들기를 먼저 수행하세요!');
				return;
			}
			if(obj.formtitle.value.trim() == ""){
					alert("1번 문항의 제목을 입력하세요.");
					obj.formtitle.focus();
					return;
			}
		}	else {
			if(obj.formseq.length != obj.formcount.value){
				alert('문항만들기를 먼저 수행하세요!');
				return;
			}
			for(var i=0;i<obj.formtitle.length;i++){
				if(obj.formtitle[i].value.trim() == ""){
					alert(i+1+"번 문항의 제목을 입력하세요.");
					obj.formtitle[i].value = "";
					obj.formtitle[i].focus();
					return;
				}
			}
		}		
	}
	
	setEx_sqs();
	frm.mode.value = type;
	frm.submit();
}

//완료시에 확인해야 할 사항
function check_grpcomplete(frm, type){
	var obj = document.forms[0];
	
	if(obj.title.value.trim() == "") {
		alert("제목을 입력하세요.");			
		obj.title.focus();
		return;
	}
	
	if ( obj.rchnolist.value.trim() == "" || obj.rchname.value.trim() == "" ) {
		if ( type != "temp" ) {
			alert('설문조사를 지정하세요.');
			return;
		}
	}
	
	frm.mode.value = type;
	frm.submit();
}

//삭제시에 확인해야 할 사항
function check_delete(frm, seq, delseq){
	if(confirm(seq+"번 문항을 삭제합니다")){
		setEx_sqs();
		frm.mode.value = "del";
		frm.delseq.value = delseq;
		frm.submit();
	}
}

//미리보기에 확인해야 할 사항
function check_preview(frm){	
	var obj = frm;	

	if(obj.formcount.value < 1){		
		alert('문항만들기를 먼저 수행하세요!');
		return;
	}

	if(typeof(obj.formseq) == 'undefined'){
		if(obj.formcount.value > 0){		
			alert('문항만들기 버튼을 누르세요!');
			return;
		}
	} else {
		if(typeof(obj.formseq.value) != 'undefined'){
			if(obj.formseq.value != obj.formcount.value){
				alert('문항만들기를 먼저 수행하세요!');
				return;
			}
			if(obj.formtitle.value.trim() == ""){
					alert("1번 문항의 제목을 입력하세요.");
					obj.formtitle.focus();
					return;
			}
		}	else {
			if(obj.formseq.length != obj.formcount.value){
				alert('문항만들기를 먼저 수행하세요!');
				return;
			}
			for(var i=0;i<obj.formtitle.length;i++){
				if(obj.formtitle[i].value.trim() == ""){
					alert(i+1+"번 문항의 제목을 입력하세요.");
					obj.formtitle[i].value = "";
					obj.formtitle[i].focus();
					return;
				}
			}
		}		
	}
	setEx_sqs();
	frm.target = "actionFrame";
	frm.mode.value = "prev";				
	frm.submit();		
	frm.target = "";
}

//미리보기에 확인해야 할 사항
function check_grppreview(frm){	
	var obj = frm;	

	if ( obj.rchnolist.value.trim() == "" || obj.rchname.value.trim() == "" ) {		
		alert('설문조사를 지정하세요.');
		return;
	}

	frm.target = "actionFrame";
	frm.mode.value = "prev";				
	frm.submit();		
	frm.target = "";
}

//마감처리
function close_check(seq, range){
	if(confirm('설문을 마감처리 하시겠습니까?')){
		document.location.href="/researchClose.do?rchno="+seq+"&range="+range;
	}
}

//마감처리
function close_grpcheck(seq, range){
	if(confirm('설문을 마감처리 하시겠습니까?')){
		document.location.href="/researchGrpClose.do?rchgrpno="+seq+"&range="+range;
	}
}

function cancel() {
	if(confirm("취소하시겠습니까?")){
		golist();
	}else{
		return;
	}
}

function golist() {
		location.href = "/researchMyList.do";
}

function gogrplist() {
		location.href = "/researchGrpMyList.do";
}

//검색 버튼 클릭시
function getSearch(frm, isSysMgr){
  if(isSysMgr == '001'){
		if(frm.sch_deptnm.value == '' && frm.sch_usernm.value == '') frm.initentry.value = "first";
		else	frm.initentry.value = "second";
	}else{
		frm.initentry.value = "first";
	}
	frm.submit();
}

//조직도 페이지 호출시
var formationObj;
function fTree(frm, orggbn, orgid){
	if(orggbn == 'null') orggbn = "";
	if(orgid == 'null')  orgid = "";
	
	var url  = '/formation/formation.jsp?orggbn=' + orggbn + '&orgid=' + orgid;
	formationObj = window.open(url, "popup", "width=390,height=700");
}

//조직도에서 넘어오는 데이터 : 조직도 더블클릭시 호출 메소드 
function fTreeDblClick(orgid, orgnm, orgfullnm, upper_orgid, upper_orgnm, upper_orgfullnm, grpgbn) {
	if(formationObj != null) formationObj.close();
	if(grpgbn == 2){
		document.forms[0].sch_old_deptcd.value = orgid;
		document.forms[0].sch_deptnm.value 		 = orgfullnm;
		document.forms[0].sch_old_userid.value = "";
		document.forms[0].sch_usernm.value 		 = "";
	}else{
		document.forms[0].sch_old_deptcd.value = upper_orgid;
		document.forms[0].sch_deptnm.value 	 	 = upper_orgfullnm;
		document.forms[0].sch_old_userid.value = orgid;
		document.forms[0].sch_usernm.value 		 = orgnm;
	}
	getSearch(document.forms[0], '001');
}

/**
 * 문항 선택시 보기 목록 조회
 * */
function examSearch1(obj, idx){
	var params = '';
	var frm = document.researchForm;
	var obj_name = obj.name;
	var name = obj_name.replace("exFrsq", "exExsq");
	var opt = document.getElementsByName("formtype["+idx+"]");
	var sel = eval("document.forms[0]."+name);
	var formtype="";
	for(var r=0 ; r < opt.length ; r++){
		if(opt[r].checked == true){
			formtype= opt[r].value;
		}
	}
	if(formtype != "01"){
		alert("단일 선택형만 선택할 수 있습니다.");
		obj.value=0;
	}
	if(obj.value == 0){
		sel.value= 0;
	}else if(obj.value > 0){
		sel.value= 1;
		"require_"+idx
		document.getElementById("require_"+idx).checked = false;
	}
	
	//params = 'formseq=' + encodeURIComponent(obj.value)+"&sessionId="+ encodeURIComponent(frm.sessionId.value); 

	//var userList = new Ajax.Request('/researchViewSubAjax.do', {method: 'post', postBody: params, onComplete: parseGumToUser});
	
	return false;	
}

/**
 * 화면 ONLOAD시 보기 목록 조회
 * */
function examSearch2(obj){
	var params = '';
	var obj_name = obj.name;
	var formseq = obj_name.replace("exExsq_","");
	var sel = eval("document.forms[0].exFrsq_"+formseq);
	var ex_sel = eval("document.forms[0]."+obj_name);

for(var i = 0 ; i < obj.length ; i++){
	//alert(obj[i].selected);
}
	if(obj.value == 0){
		sel.value= 0;
	}
	if(sel.value == 0 && obj.value != 0){
		alert("질문을 선택해 주세요.");
		obj.value = 0;
	}
	var frm = document.researchForm;
	//selId= "exExsq_"+formseq;
	//params = 'formseq=' + encodeURIComponent(formseq)+"&sessionId="+ encodeURIComponent(frm.sessionId.value); 

	//var userList = new Ajax.Request('/researchViewSubAjax.do', {method: 'post', postBody: params, onComplete: parseGumToUser});
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
		
		var user = new examObject();
	
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

	var range = eval("document.forms[0]."+selId);
	range.innerHTML = "";

	if (globalObjStorage.length > 0){
		var opt;
		var opt1;
		for (var i = 0 ; i < globalObjStorage.length ; i++){
			user = globalObjStorage[i];
			opt = document.createElement('OPTION');
			opt.value = user.examseq;
			//opt.text = user.examcont;
			opt.text = "보기"+(i+1);
			range.add(opt);
		}
	
	}else{
		range.innerHTML = "";
		var opt=  document.createElement('OPTION');
		opt.value = "0";
		opt.text = "보기";
		range.add(opt);
	}
	//selId ="";
	var obj = new String(selId).replace("exExsq_","");
	exFrsqExsq(obj , '1');
	globalObjStorage = null;
}

function exFrsqExsq(obj , option){
	var exFrsqs = eval("window.document.getElementsByClassName('exFrsq')");
	var exExsqs = eval("window.document.getElementsByClassName('exExsq')");
	
	for(var i = parseInt(obj); i < exFrsqs.length; i++) {
		var opt = document.getElementsByName("formtype["+i+"]");
		var sel = eval("document.forms[0].exFrsq_"+(i+1));
		var exsel = eval("document.forms[0].exExsq_"+(i+1));
		var formcount = eval("document.forms[0].examcount.value");
		sel.innerHTML="";
		exsel.innerHTML="";
		var opt_value="";
		for(var r=0 ; r < opt.length ; r++){
			if(opt[r].checked == true){
				opt_value= opt[r].value;
			}
		}
	
			var dopt=  document.createElement('OPTION');
			dopt.value = "0";
			dopt.text = "질문";
			sel.add(dopt);
			
			var expt=  document.createElement('OPTION');
			expt.value = "0";
			expt.text = "보기";
			exsel.add(expt);
			
			if(opt_value == "01" && i > 0){
				for(var k = 0 ; k < i ; k++){
					var kopt = document.getElementsByName("formtype["+k+"]");
					var kopt_value = "";
					for(var kr=0 ; kr < kopt.length ; kr++){
						if(kopt[kr].checked == true){
							kopt_value= kopt[kr].value;
						}
					}
					var selk = eval("document.forms[0].exFrsq_"+(k+1));
					if(kopt_value == "01"){
						//if(selk.value == '0' || selk.value == ""){							
							dopt = document.createElement("OPTION"); 
							dopt.text = "질문"+(k+1)+"";
							dopt.value = (k+1);
							sel.add(dopt);
							//exsq.value=0;
						//}
					}
				}
				if (formcount > 0){
					var opt;
					var opt1;
					for (var j = 0 ; j < formcount ; j++){
						expt = document.createElement('OPTION');
						expt.value = (j+1);
						expt.text = "보기"+(j+1);
						exsel.add(expt);
					}				
				}
			   
			}else{
				//dopt=  document.createElement('OPTION');
				//dopt.value = "0";
				//dopt.text = "보기";
				//exsel.add(dopt);
			}
			exsel.value=0;
	}
}

function preexCheck(formseq, examseq, exformseq, elistsize){
	var exFrsqs = eval("window.document.getElementsByClassName('ex_"+formseq+"_"+examseq+"')");
	if(exFrsqs.length > 0){
		for(var i = 0 ; i < exFrsqs.length ; i++){
			exFrsqs[i].style.display="inline";
		}
	}else{
		for(var i = 0 ; i < elistsize ; i++){
			var exFrsqs1 = eval("window.document.getElementsByClassName('ex_"+formseq+"_"+(i+1)+"')");
			if(exFrsqs1.length > 0){				
				for(var j = 0 ; j < exFrsqs1.length ; j++){
					exFrsqs1[j].style.display="none";
				}
			}
		}
	}
}

/*설문에서 체크 안된 것은 모두 display none 처리한다.
 *  1. 현재 보여주지고 있는 설문중에 연계 코드가 있는 내역중에 정상적으로 체크여부를 확인한다.
 */
function setEx_display_none(){
	var frm = document.forms[0];
	var listcnt = frm.listcnt.value;	//설문 전체 개수
	for(var i=0; i<listcnt; i++){
		var formseq = eval("document.forms[0].formseq"+i+".value");
		var exsq = eval("document.forms[0].exsqs"+i+".value");//연계 번호
		var exexsq = eval("document.forms[0].exexsq"+i+".value").split("_");//연계 번호
		var exfrsq = eval("document.forms[0].exfrsq"+i+".value");
		var exsq_i = "ex_"+exfrsq+"_";
		var formtype = eval("document.forms[0].formtype"+i+".value");
		var ex= document.getElementsByClassName(exsq);
		var anscont = eval("document.forms[0].anscont"+i);
		if(ex[0].style.display != "none"){	
			if(exsq != "ex_0_0"){
				var none_yn = true;
				for(var j=0; j<listcnt; j++){
					var formseq_j = eval("document.forms[0].formseq"+j+".value");				
					var exexsq_j = eval("document.forms[0].exexsq"+j+".value").split("_");//연계 번호				
					var exsq_ = eval("document.forms[0].exsqs"+j+".value");
					var exsq_j = "ex_"+formseq_j+"_";
					var formtype_j = eval("document.forms[0].formtype"+j+".value");
					var ex_j= document.getElementsByClassName(exsq_);
					var anscont_j = eval("document.forms[0].anscont"+j);
					if(ex_j[0].style.display != "none" && formtype_j == "01"){	
						for(var k=0;k < anscont_j.length ; k++){
							if(anscont_j[k].checked){
								for(var z = 0 ; z < exexsq.length ; z++){
									var e_i = exsq_i+exexsq[z];
									var e_j = exsq_j+anscont_j[k].value;
									if(e_i == e_j){
										none_yn = false;
										break;
									}
									
							}
							}
						}
						
					}
				}	
				if(none_yn){
					ex[0].style.display="none";
				}
			}
		}
		if(ex[0].style.display == "none"){	
			for(var k=0;k < anscont.length ; k++){
				if(anscont[k].checked){
					exsq_j = exsq_j+anscont[k].value;
					anscont[k].checked = false;
				}
			}
		}
	}
	var ol_style= document.getElementsByClassName("ol_style");
	ol_style[0].style["list-style-type"] = "decimal";
}

function setEx_sqs(){
	var obj = document.forms[0];
	var array_frsqs = new Array();
	var array_exsqs = new Array();
	var exFrsqs = eval("window.document.getElementsByClassName('exFrsq')");
	 for(var i = 0; i < exFrsqs.length; i++) {
				var sel = eval("document.forms[0].exFrsq_"+(i+1));
				var exsel = eval("document.forms[0].exExsq_"+(i+1));
				var arrayExcel ="";
				for(var j=1 ; j< exsel.length ; j++){
					if(exsel[j].selected){
						if(arrayExcel != "")arrayExcel +="_";
						if(arrayExcel != "0")arrayExcel += exsel[j].value;
					}
				}
				if(arrayExcel == "")arrayExcel = "0";
				array_frsqs[i]= sel.value;	
				array_exsqs[i]=arrayExcel;	
		 }
	 obj.frsqs.value=array_frsqs;
	 obj.exsqs.value=array_exsqs;
	
}

function ex_research(formseq, eseq, ex_frsq, ex_exsq, elistsize, idx){//문항, 보기 , 연계문항, 연계보기, 문항개수, 문항index
	var exsq_now = eseq;
	if(eval("document.forms[0].ex1_"+(formseq+"_"+eseq)) != undefined){
		exsq_now = eval("document.forms[0].ex1_"+(formseq+"_"+eseq)+".value");
	}
	
	var contentData = window.document.getElementsByClassName("ex_"+formseq+"_"+exsq_now);
	//var contentData = document.querySelectorAll(".ex_"+formseq+"_"+exsq_now)
	if(contentData.length > 0){		
		//contentData[0].style.removeProperty("display");
		contentData[0].style.display = "";
	}
	for(var i = 1 ; i < elistsize ; i++){
		var exsq =  0;
		if(eval("document.forms[0].ex1_"+(formseq+"_"+i)) != undefined){
			exsq = eval("document.forms[0].ex1_"+(formseq+"_"+i)+".value");
		}
		
		if(exsq_now != exsq){
			var contentNone = window.document.getElementsByClassName("ex_"+formseq+"_"+exsq);
			if(contentNone.length > 0){
				contentNone[0].style.display = "none";	
			}
		}
	}	
	
	//alert(exsq_now);
	setEx_display_none();
}