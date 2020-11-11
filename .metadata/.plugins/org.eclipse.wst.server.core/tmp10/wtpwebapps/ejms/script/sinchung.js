/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ��û��
 * ����:
 */

var globalObjStorage;
var selId;

/**
 * ����� ������ ���� ��ü
 */
function examObject(){
	this.formseq = '';
	this.examseq = '';
	this.examcont = '';
	return this;
}

/**
 * ���� : ����� ��ü�� Ư�� �Ӽ��� �����͸� �����Ѵ�.
 * ����1 obj - �Ӽ������� ������ ����� ��ü
 * ����2 node - �Ӽ������� ���� �ִ� XML Node��ü
 */
examObject.prototype.setData = function(obj, node){
	// obj�� �Ӽ�(prop)�� ���� ������ ���鼭 
	for (prop in obj){
		if (prop == node.attributes[0].value){
			obj[prop] = node.text;
			break;
		}
	}
	return obj;
};

//IE7, IE8���� getElementsByClassName ��� ����� �� ����
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

/* getElementsByClassName �� IE8 ���Ͽ��� �����ϰ� �ϴ� �ڵ� */
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
	if ( typeof(src) != "undefined" ) {
		var filenm = src.toLowerCase().trim();
		if ( filenm.length > 0 &&
					(filenm.length < 8 ||
						!(filenm.substring(filenm.length - 4) == ".gif" ||
							filenm.substring(filenm.length - 4) == ".jpg" ||
							filenm.substring(filenm.length - 4) == ".png")) ) {
	
			alert("GIF, JPG, PNG ���ϸ� �̸����� �Ͻ� �� �ֽ��ϴ�.");
			return;
		}
	}

	if ( divObj.style.display != "block" ) {
		var resizeObj = function() {
			divObj.style.width = width + 20;
			divObj.style.height = height + 20;
			document.getElementById(divObj.id + "_f").width = width + 10;
			document.getElementById(divObj.id + "_f").height = height + 10;
			document.getElementById(divObj.id + "_t1").width = width + 20;
			document.getElementById(divObj.id + "_t1").height = height + 20;
			document.getElementById(divObj.id + "_t2").width = width + 20;
			document.getElementById(divObj.id + "_t2").height = height + 20;
			document.getElementById(imgObj).width = width;
			document.getElementById(imgObj).height = height;
			document.getElementById(imgObj).src = src;
		}
		resizeObj();

		try {
			var setRealSize = function() {
				if ( !(img.width < width && img.height < height) ) {
					width = img.width;
					height = img.height;
				}
				resizeObj();
				//fullSizeWindow();
			}
			
			var img = new Image();
			img.src = src;
			if ( img.readyState != "complete" ) {
				img.onreadystatechange = setRealSize;
			} else {
				setRealSize();
			}
		} catch ( exception ) {}
		
		divObj.style.display = "block";
	} else {
		divObj.style.display = "none";
	}
	//fullSizeWindow();
}
 
function checkFileType(fileObj) {
	return;	//��� ���� ÷�ΰ���
	
	var filenm = fileObj.value.toLowerCase().trim();

	if ( filenm.length > 0 &&
				(filenm.length < 8 || filenm.substring(1, 3) != ":\\" ||
					!(filenm.substring(filenm.length - 4) == ".gif" ||
						filenm.substring(filenm.length - 4) == ".jpg" ||
						filenm.substring(filenm.length - 4) == ".png")) ) {

		alert("GIF, JPG, PNG ���ϸ� ÷���� �� �ֽ��ϴ�.");
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
			examObj.value = "����÷��";
		}
	} else {
		divObj.style.display = "none";
		if ( examValue.substring(examValue.length - 4) == "����÷��" ) {
			examObj.value = examValue.substring(0, examValue.length - 4);
		}
	}
}
 
function setSearchDate() {
	var frm = document.forms[0];
	var a = frm.strdt;			//�Ⱓ����
	var b = frm.enddt;			//�Ⱓ����
	
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

//�Է� ��� Ȱ��ȭ ó��
function showSwtichLayer(obj, option) {	
	if(option == "1" || option == "2") {
		//(���ϼ���,�ߺ�����)������
		eval("sample"+obj).style.display = "block";
		eval("addcont"+obj).style.display = "none";		
		eval("req"+obj).style.visibility= "hidden"; 	
	} else if(option == "3" || option == "4") {
	  //�ܴ���, �幮��
		eval("sample"+obj).style.display = "none";
		eval("addcont"+obj).style.display = "block";	
		eval("req"+obj).style.visibility= "visible";
	}
	
	if(option == "1"){
		document.getElementById("require_"+obj).checked = true;
		exFrsqExsq(obj , option);
	}else{
		document.getElementById("require_"+obj).checked = false;
		exFrsqExsq(obj , option);
	}
	//����ó��, �ʼ��Է�ó��
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
		for ( var i = 0; i < rangedetail.length; i++ ) {
			rangedetail[i].checked = false;
		}
	} else {
		for ( var i = 0; i < rangedetail.length; i++ ) {
			if ( rangedetail[i].checked ) break;
			if ( i + 1 == rangedetail.length && range[1].checked ) rangedetail[0].checked = true;
		}
	}	
}

function showOpenTypeDetail(option) {
	var range = document.getElementsByName("range");
	
	if ( option == 1 ) {
	} else {
		range[1].checked = true;
	}	
}

//�Ϸ�ÿ� Ȯ���ؾ� �� ����
function check_complete(frm, type){
	
	if(frm.title.value.trim() == "") {
		alert("������ �Է��ϼ���.");			
		frm.title.focus();
		return;
	}
	
	if(frm.acnt.value < 1){	
		alert('���׸���⸦ ���� �����ϼ���!');
		return;
	}

	if(typeof(frm.formseq) == 'undefined'){
		if(frm.acnt.value > 0){		
			alert('���׸���� ��ư�� ��������!');
			return;
		}
	} else {
		if(typeof(frm.formseq.value) != 'undefined'){
			if(frm.formseq.value != frm.acnt.value){
				alert('���׸���⸦ ���� �����ϼ���!');
				return;
			}
			if(frm.formtitle.value.trim() == ""){
					alert("1�� ������ ������ �Է��ϼ���.");
					frm.formtitle.focus();
					return;
			}
		}	else {
			if(frm.formseq.length != frm.acnt.value){
				alert('���׸���⸦ ���� �����ϼ���!');
				return;
			}
			for(var i=0;i<frm.formtitle.length;i++){
				if(frm.formtitle[i].value.trim() == ""){
					alert(i+1+"�� ������ ������ �Է��ϼ���.");
					frm.formtitle[i].value = "";
					frm.formtitle[i].focus();
					return;
				}
			}
		}		
	}
	setEx_sqs();
	frm.mode.value = type;
	frm.submit();
}

//���׸���⿡�� Ȯ���ؾ� �� ����
function check_make(frm){
	frm.posscroll.value = document.body.scrollTop;
	
	if(frm.title.value.trim() == "") {
		alert("������ �Է��ϼ���.");			
		frm.title.focus();
		return;
	}

	if(frm.acnt.value > 60 || frm.acnt.value < 1){
		alert('���׸����� 1~60�� ���� ���� �� �ֽ��ϴ�.');
		frm.acnt.focus();
		return;
	}
	
	if(frm.examcount.value > 60 || frm.examcount.value < 1){
		alert('�� ���״� ����� 1~60�� ���� ���� �� �ֽ��ϴ�.');
		frm.acnt.focus();
		return;
	}
	
	frm.mode.value = "make";
	processingShow();
	frm.submit();
}

//�����ÿ� Ȯ���ؾ� �� ����
function check_delete(frm, seq, delseq){
	frm.posscroll.value = document.body.scrollTop;
	
	if(confirm(seq+"�� ������ �����մϴ�.")){
		setEx_sqs();
		frm.mode.value = "del";
		frm.delseq.value = delseq;
		frm.submit();
	}
}

//�߰��ÿ� Ȯ���ؾ� �� ����
function check_add(frm){
		frm.posscroll.value = document.body.scrollTop + 85;
		
		frm.mode.value = "add";		
		frm.submit();
}

//�̸����⿡ Ȯ���ؾ� �� ����
function check_preview(frm){			
	if(frm.acnt.value < 1){	
		alert('���׸���⸦ ���� �����ϼ���!');
		return;
	}

	if(typeof(frm.formseq) == 'undefined'){
		if(frm.acnt.value > 0){		
			alert('���׸���� ��ư�� ��������!');
			return;
		}
	} else {
		if(typeof(frm.formseq.value) != 'undefined'){
			if(frm.formseq.value != frm.acnt.value){
				alert('���׸���⸦ ���� �����ϼ���!');
				return;
			}
			if(frm.formtitle.value.trim() == ""){
					alert("1�� ������ ������ �Է��ϼ���.");
					frm.formtitle.focus();
					return;
			}
		}	else {
			if(frm.formseq.length != frm.acnt.value){
				alert('���׸���⸦ ���� �����ϼ���!');
				return;
			}
			for(var i=0;i<frm.formtitle.length;i++){
				if(frm.formtitle[i].value.trim() == ""){
					alert(i+1+"�� ������ ������ �Է��ϼ���.");
					frm.formtitle[i].value = "";
					frm.formtitle[i].focus();
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

//����ó��
function del_check(seq){
	if(confirm('��û�� ���� ��� �����Ͱ� ���� �˴ϴ�.\n�����Ͻðڽ��ϱ�?')){
		//document.location.href="/formDel.do?reqformno="+seq
		document.forms[0].action = "/formDel.do";
		document.forms[0].submit();
	}
}

//����ó��
function close_check(seq, range){
	if(confirm('��û���� ����ó�� �Ͻðڽ��ϱ�?')){
		document.location.href="/formClose.do?reqformno="+seq+"&range="+range;
	}
}

//��û���� ��� 
function cancel_check(reqformno, reqseq, gubun){
	if ( gubun == 1 ) {
		if(confirm('��û������ ��� �����˴ϴ�.\n����Ͻðڽ��ϱ�?') == false){	
	    return;
		}
	} else if ( gubun == 2 ) {
		if(confirm('������� �� �̰��翡�� ��û������ ����˴ϴ�.\n����Ͻðڽ��ϱ�?') == false){	
	    return;
		}
	}
	
	document.forms[0].action = '/myCancel.do?reqformno=' + reqformno + '&reqseq=' + reqseq + '&gubun=' + gubun;
	document.forms[0].submit();
	document.forms[0].target = '';
	document.forms[0].action = '/dataSave.do';
}

function unlimiteTerm() {
	var temp = document.forms[0].enddt.onpropertychange;
	document.forms[0].enddt.onpropertychange = null;
	document.forms[0].enddt.value='';
	document.forms[0].enddt.onpropertychange = temp;
}

function cancel(){
	if(confirm("����Ͻðڽ��ϱ�?")){
		golist();
	}else{
		return;
	}
}

function golist(){
		location.href = "/formList.do";
}

//��û �Ϸ��� �޼���
function comp(){
	alert('��û���� ��� �Ǿ����ϴ�.');
	return;
}

//��û���� ��� �޼���
function msgcancel(){
	alert('���� �Ǿ����ϴ�.');
	return;
}

/**
 * ���� ���ý� ���� ��� ��ȸ
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
	if(formtype != "1"){
		alert("���� �������� ������ �� �ֽ��ϴ�.");
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
 * ȭ�� ONLOAD�� ���� ��� ��ȸ
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
		alert("������ ������ �ּ���.");
		obj.value = 0;
	}
	var frm = document.researchForm;
	//selId= "exExsq_"+formseq;
	//params = 'formseq=' + encodeURIComponent(formseq)+"&sessionId="+ encodeURIComponent(frm.sessionId.value); 

	//var userList = new Ajax.Request('/researchViewSubAjax.do', {method: 'post', postBody: params, onComplete: parseGumToUser});
	return false;	
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
			dopt.text = "����";
			sel.add(dopt);
			
			var expt=  document.createElement('OPTION');
			expt.value = "0";
			expt.text = "����";
			exsel.add(expt);
			if(opt_value == "1" && i > 0){
				var sel_cnt=0;
				for(var k = 0 ; k < i ; k++){
					var kopt = document.getElementsByName("formtype["+k+"]");
					var kopt_value = "";
					for(var kr=0 ; kr < kopt.length ; kr++){
						if(kopt[kr].checked == true){
							kopt_value= kopt[kr].value;
						}
					}
					var selk = eval("document.forms[0].exFrsq_"+(k+1));
					if(kopt_value == "1"){
						//if(selk.value == '0' || selk.value == ""){							
							dopt = document.createElement("OPTION"); 
							dopt.text = "����"+(k+1)+"";
							dopt.value = (k+1);
							sel.add(dopt);
							//exsq.value=0;
						//}
							sel_cnt++;
					}
				}
				if (formcount > 0 &&  sel_cnt > 0){
					var opt;
					var opt1;
					for (var j = 0 ; j < formcount ; j++){
						expt = document.createElement('OPTION');
						expt.value = (j+1);
						expt.text = "����"+(j+1);
						exsel.add(expt);
					}				
				}
			   
			}else{
				//dopt=  document.createElement('OPTION');
				//dopt.value = "0";
				//dopt.text = "����";
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

/*�������� üũ �ȵ� ���� ��� display none ó���Ѵ�.
 *  1. ���� ���������� �ִ� �����߿� ���� �ڵ尡 �ִ� �����߿� ���������� üũ���θ� Ȯ���Ѵ�.
 */
function setEx_display_none(){
	var frm = document.forms[0];
	var listcnt = frm.listcnt.value;	//���� ��ü ����
	
	for(var i=0; i<listcnt; i++){
		var formseq = eval("document.forms[0].formseq"+i+".value");
		var exsq = eval("document.forms[0].exsqs"+i+".value");//���� ��ȣ
		var exexsq = eval("document.forms[0].exexsq"+i+".value").split("_");//���� ��ȣ
		var exfrsq = eval("document.forms[0].exfrsq"+i+".value");
		var exsq_i = "ex_"+exfrsq+"_";
		var formtype = eval("document.forms[0].formtype"+i+".value");
		var ex= document.getElementsByClassName(exsq);
		var anscont = document.getElementsByName("radioans["+i+"]");		
		//var anscont = eval("document.forms[0].radioans["+i+"]");		
		if(ex[0].style.display != "none"){
			if(exsq != "ex_0_0"){
				var none_yn = true;
				for(var j=0; j<listcnt; j++){
					var formseq_j = eval("document.forms[0].formseq"+j+".value");				
					var exexsq_j = eval("document.forms[0].exexsq"+j+".value").split("_");//���� ��ȣ				
					var exsq_ = eval("document.forms[0].exsqs"+j+".value");
					var exsq_j = "ex_"+formseq_j+"_";
					var formtype_j = eval("document.forms[0].formtype"+j+".value");
					var ex_j= document.getElementsByClassName(exsq_);
					//var anscont_j = eval("document.forms[0].radioans["+j+"]");
					var anscont_j = document.getElementsByName("radioans["+j+"]");	
					if(ex_j[0].style.display != "none" && formtype_j == "1"){	
						for(var k=0;k < anscont_j.length ; k++){
							if(anscont_j[k].checked){
								for(var z = 0 ; z < exexsq.length ; z++){
									var e_i = exsq_i+exexsq[z];
									var e_j = exsq_j+(parseInt(anscont_j[k].value)+1);
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

function ex_research(formseq, eseq, ex_frsq, elistsize, idx){//����, ���� , ���蹮��, ���躸��, ���װ���, ����index
	var exsq_now = eseq;
	if(eval("document.forms[0].ex1_"+(formseq+"_"+eseq)) != undefined){
		exsq_now = eval("document.forms[0].ex1_"+(formseq+"_"+eseq)+".value");
	}
	
	var contentData = window.document.getElementsByClassName("ex_"+formseq+"_"+exsq_now);	
	
	if(contentData.length > 0){				
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
	setEx_display_none();
}

