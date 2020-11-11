/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 공통
 * 설명:
 */
 
function getCookieVal(offset) {
	var endstr = document.cookie.indexOf (";", offset);
	if (endstr == -1) endstr = document.cookie.length;
	return unescape(document.cookie.substring(offset, endstr)); 
}

function getCookie(name) {
	var arg = name + "=";
	var alen = arg.length;
	var clen = document.cookie.length;
	var i = 0;
	while (i < clen) {
		var j = i + alen;
		if (document.cookie.substring(i, j) == arg) return getCookieVal(j);
		i = document.cookie.indexOf(" ", i) + 1;
		if (i == 0) break;
	}
	return null;
}

function setCookie (name, value) {
	var argv = setCookie.arguments;
	var argc = setCookie.arguments.length;
	var expires = (2 < argc) ? argv[2] : null; var path = (3 < argc) ? argv[3] : null;
	var domain = (4 < argc) ? argv[4] : null; var secure = (5 < argc) ? argv[5] : false;
	document.cookie = name + "=" + escape(value) +
										((expires == null) ? "" : ("; expires=" + expires.toGMTString())) +
										((path == null) ? "" : ("; path=" + path)) +
										((domain == null) ? "" : ("; domain=" + domain)) +
										((secure == true) ? "; secure" : "");
}

function delCookie( key ) {
  var expireDate = new Date();
  expireDate.setDate( expireDate.getDate() - 1 );
  document.cookie = key + "= " + "; expires=" + expireDate.toGMTString() + "; path=/";
}

function maxlength_check(obj, len) {
	if(obj.value.length > len) {
		obj.blur();
		obj.focus();
		obj.value = obj.value.substring(0, len);
	}
}

function convertSize(size) {
	if(size > 1000) {
		var size = parseInt(size / 1000).toString();
		var conSize = "";

		if(size.length <= 3) {
			conSize = size;
		} else {
			var spot = parseInt(size.length % 3);
			if(spot > 0) {
				conSize = size.substr(0, spot) + ","
			}
			for(var i = spot; i < size.length; i+=3) {
				conSize += size.substr(i, 3) + ",";
			}
			conSize = conSize.substr(0, conSize.length - 1);
		}
		document.write(conSize + " KByte");
	} else  {
		document.write("1 KByte");
	}
}

function numberFormat(num) {
	var left = "", right = "", connum = "";

	if (num < 0) left = "-";

	num = String(Math.abs(num));
	if (num.indexOf(".") != -1) {
		right = num.substring(num.indexOf("."));
		num = num.substring(0, num.indexOf("."));
	}
			
	if(num.length <= 3) {
		connum = num;
	} else {
		var spot = parseInt(num.length % 3);
		if(spot > 0) connum = num.substr(0, spot) + ",";

		for(var i = spot; i < num.length; i+=3) {
			connum += num.substr(i, 3) + ",";
		}
		connum = connum.substr(0, connum.length - 1);
	}

	return left + connum + right;
}

function getAbsoluteLeft(htmlObject) {
	var xPos = htmlObject.offsetLeft;
	var temp = htmlObject.offsetParent;
	while (temp != null) {
		xPos += temp.offsetLeft;
		temp = temp.offsetParent;
	}
	return xPos;
}

function getAbsoluteTop(htmlObject) {
	var yPos = htmlObject.offsetTop;
	var temp = htmlObject.offsetParent;
	while (temp != null) {
		yPos += temp.offsetTop;
		temp = temp.offsetParent;
	}
	return yPos;
}

function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}

function MM_showHideLayers() { //v3.0
  var i,p,v,obj,args=MM_showHideLayers.arguments;
  for (i=0; i<(args.length-2); i+=3) if ((obj=MM_findObj(args[i]))!=null) { v=args[i+2];
    if (obj.style) { obj=obj.style; v=(v=='show')?'visible':(v='hide')?'hidden':v; }
    obj.visibility=v; }
}

function MM_reloadPage(init) {  //reloads the window if Nav4 resized
  if (init==true) with (navigator) {if ((appName=="Netscape")&&(parseInt(appVersion)==4)) {
    document.MM_pgW=innerWidth; document.MM_pgH=innerHeight; onresize=MM_reloadPage; }}
  else if (innerWidth!=document.MM_pgW || innerHeight!=document.MM_pgH) location.reload();
}

/*
 * 주어진 문자열의 왼쪽을 padding 문자로 채운다
 */
function lpad(str, n, padding)
{
	str = String(str);
    if (str.length >= n)
        return str;
    else
    {
        var len = n - str.length;
        var pad_str = str;
        for (var i=0; i<len; i++)
            pad_str = padding + pad_str;

        return pad_str;
    }
}

/*
 * 주어진 문자열의 오른쪽을 padding 문자로 채운다
 */
function rpad(str, n, padding)
{
	str = String(str);
    if (str.length >= n)
        return str;
    else
    {
        var len = n - str.length;
        var pad_str = str;
        for (var i=0; i<len; i++)
            pad_str = pad_str + padding;

        return pad_str;
    }
}


// 엔터 입력시 스크립트 실행
function isEnter(script) {
	if ( event.keyCode == "13" ) eval(script);
}

// 엔터키가 들어오면 텝키를 입력한것처럼 한다.
function key_entertotab()
{
	if (event.keyCode == '13') event.keyCode = '9';
}

// 숫자만 입력가능 하도록 하였다. 역시 엔터키는 텝키다.
function key_num()	// BackSpace, DEL, TAB, 0 .. 9
{
	key_entertotab();
	if (!
		(event.keyCode == '8' || event.keyCode == '9' || event.keyCode == '46' ||
		 event.keyCode == '39' || event.keyCode == '37' ||
		(event.keyCode >= '48' && event.keyCode <= '57') ||
		(event.keyCode >= '96' && event.keyCode <= '105'))
	   )
	{ event.returnValue = false; }
}

// 숫자에다가 보너스로 '-'까지 입력되도록 하였다.
// 자판의 '-'는 코드가 189고 계산기패드의 '-'코드는 109다. 짜증나서 둘다 넣었다.
function key_num_minus()	// BackSpace, DEL, TAB, -, 0 .. 9
{
	key_entertotab();
	if (!
		(event.keyCode == '8' || event.keyCode == '9' || event.keyCode == '46' ||
		 event.keyCode == '109' || event.keyCode == '189' ||		// '-'가 키가 먼지 모름.
  		 event.keyCode == '39' || event.keyCode == '37' ||
		(event.keyCode >= '48' && event.keyCode <= '57') ||
		(event.keyCode >= '96' && event.keyCode <= '105') )
	   )
	{ event.returnValue = false; }
}

function showModalPopup(actionpath, width, height, leftOffset, topOffset) {
	var left = (screen.width - width) / 2 + leftOffset;
	var top = (screen.height - height) / 2 + topOffset;
	var option =
		"dialogWidth:" + width + "px; " +
		"dialogHeight:" + height + "px; " +
		"dialogLeft:" + left + "px; " +
		"dialogTop:" + top + "px; " +
		"resizable:yes; scroll:yes; status:yes; center:no; " +
		"dialogHide:no; edge:raised; help:no; unadorned:no";
		
	try {
		window.showModalDialog(actionpath , window, option);
	} catch(exception) {
		alert("미리보기 기능을 사용하시려면 팝업 차단 사용을 꺼주시거나\n" +
					"팝업 허용 사이트로 등록해주시기 바랍니다.\n\n" +
					"제어판 → 인터넷옵션 → 개인정보 → 팝업차단사용(체크해제)\n" +
					"또는 설정(버튼클릭) → 허용할 웹 사이트 주소 입력 후 추가");
		try {processingHide();} catch(ex) {}
		try {parent.processingHide();} catch(ex) {}
	}
}

function showPopup(actionpath, width, height, leftOffset, topOffset) {
	var left = (screen.width - width) / 2 + leftOffset;
	var top = (screen.height - height) / 2 + topOffset;
	var option =
		"width=" + width + "," +
		"height="  + height + "," +
		"left=" + left + "," +
		"top=" + top + "," +
		"location=no, menubar=no, resizable=yes, scrollbars=yes, status=yes, " +
		"titlebar=no, toolbar=no, channelmode=no, directories=no, fullscreen=no";

	window.open(actionpath , "popup", option);
}

function showPopup2(actionpath, width, height, leftOffset, topOffset) {
	var left = (screen.width - width) / 2 + leftOffset;
	var top = (screen.height - height) / 2 + topOffset;
	var option =
		"width=" + width + "," +
		"height="  + height + "," +
		"left=" + left + "," +
		"top=" + top + "," +
		"location=no, menubar=no, resizable=yes, scrollbars=yes, status=yes, " +
		"titlebar=no, toolbar=no, channelmode=no, directories=no, fullscreen=no";

	window.open(actionpath , "popup2", option);
}

function fullSizeWindow(WIDTH, HEIGHT) {
	//<body onload="fullSizeWindow()">
	try {	
		window.dialogWidth = "0px";
		window.dialogHeight = "0px";
		try {window.resizeTo(0, 0);} catch (exception) {}
		
		var scrWidth = document.body.scrollWidth;		//실제화면 넓이
		var scrHeight = document.body.scrollHeight;	//실제화면 높이
		var diaWidth = 0;
		var diaHeight = 0;
		var gapWidth = 0;
		var gapHeight = 0;
				
		//window.open()
		gapWidth = scrWidth - document.body.clientWidth;
		gapHeight = scrHeight - document.body.clientHeight;
		try {window.resizeBy(gapWidth, gapHeight);} catch (exception) {}

		gapWidth = scrWidth - document.body.clientWidth;
		gapHeight = scrHeight - document.body.clientHeight;
		try {window.resizeBy(gapWidth, gapHeight);} catch (exception) {}

		if ( WIDTH != undefined && WIDTH != -1 ) {
			if ( document.body.clientWidth+30 > parseInt(WIDTH) ) window.resizeTo(parseInt(WIDTH), document.body.clientHeight+30);
		}
		if ( HEIGHT != undefined && HEIGHT != -1 ) {
			if ( document.body.clientHeight+30 > parseInt(HEIGHT) ) window.resizeTo(document.body.clientWidth+30, parseInt(HEIGHT));
		}
		
		//alert(scrWidth + " : " + scrHeight + "\n" + document.body.scrollWidth + " : " + document.body.scrollHeight + "\n");
		
		//window.showModalDialog()
		diaWidth = String(window.dialogWidth);
		if ( diaWidth.substring(diaWidth.length - 2, diaWidth.length).toLowerCase() == "px" ) {
			gapWidth = scrWidth - document.body.clientWidth;
			diaWidth = parseInt(diaWidth.substring(0, diaWidth.length - 2), 10) + gapWidth;
			window.dialogWidth = diaWidth + "px";
		}
		diaHeight = String(window.dialogHeight);		
		if ( diaHeight.substring(diaHeight.length - 2, diaHeight.length).toLowerCase() == "px" ) {
			gapHeight = scrHeight - document.body.clientHeight;
			diaHeight = parseInt(diaHeight.substring(0, diaHeight.length - 2), 10) + gapHeight;
			window.dialogHeight = diaHeight + "px";
		}
		
		diaWidth = String(window.dialogWidth);
		if ( diaWidth.substring(diaWidth.length - 2, diaWidth.length).toLowerCase() == "px" ) {
			gapWidth = scrWidth - document.body.clientWidth;
			diaWidth = parseInt(diaWidth.substring(0, diaWidth.length - 2), 10) + gapWidth;
			window.dialogWidth = diaWidth + "px";
		}
		diaHeight = String(window.dialogHeight);		
		if ( diaHeight.substring(diaHeight.length - 2, diaHeight.length).toLowerCase() == "px" ) {
			gapHeight = scrHeight - document.body.clientHeight;
			diaHeight = parseInt(diaHeight.substring(0, diaHeight.length - 2), 10) + gapHeight;
			window.dialogHeight = diaHeight + "px";
		}

		if ( WIDTH != undefined && WIDTH != -1 ) {
			if ( diaWidth > parseInt(WIDTH)+30 ) window.dialogWidth = parseInt(WIDTH)+30 + "px";
		}
		if ( HEIGHT != undefined && HEIGHT != -1 ) {
			if ( diaHeight > parseInt(HEIGHT)+30 ) window.dialogHeight = parseInt(HEIGHT)+30 + "px";
		}
		
	} catch (exception) {}
}

//결재선 지정
//팝업창이 닫힐때 메인창의 결재선을 Replace 시켜준다.
function designateset(gumtoList, sunginList, retid){
		var user;
		var tmp;
		var tmp_set1  = '';
		var tmp_set2 = '';

		for (var i = 0; i < gumtoList.length; i++){
			user = gumtoList[i];
			/*
			//	userName 이름
			//	sancYn   입력완료구분
			*/
			if(user.sancYn == '0'){
				tmp_set1	 = tmp_set1 + user.userName + ", ";
			}else{
				tmp_set1	 = tmp_set1 + user.userName + "<span class='style4'>(결제완료)</span>" + ", ";
			}
		}

		for (var i = 0; i < sunginList.length; i++){
			user = sunginList[i];
			/*
			//	userName 이름
			//	sancYn   입력완료구분
			*/
			if(user.sancYn == '0'){
				tmp_set2	 = tmp_set2 + user.userName + ", ";
			}else{
				tmp_set2	 = tmp_set2 + user.userName + "<span class='style4'>(결제완료)</span>" + ", ";
			}
		}

    eval(retid+"1").innerHTML = tmp_set1.substring(0,tmp_set1.length-2);
    eval(retid+"2").innerHTML = tmp_set2.substring(0,tmp_set2.length-2);
}

//입력담당자 지정
function inputusrset(setList,retid){
	var user;
	var tmp_set1 = '';
	var tmp_set2 = '';
	var chrgunitnm = '';

	for (var i = 0; i < setList.length; i++){
		user = setList[i];
		/*
		//	userName 이름
		//	sancYn   입력완료구분
		//	chrgunitNm 담당단위구분
		*/
		if(user.sancYn == '03' || user.sancYn == '04') {
			if(chrgunitnm == user.chrgunitNm) {
				tmp_set1 = tmp_set1 + ", " + user.userName;
			} else {
				if(i > 0) {
					tmp_set1 = "";
				}
				tmp_set1 = user.chrgunitNm + " : " + user.userName;
			}

			chrgunitnm = user.chrgunitNm;
		} else {
			if(tmp_set2 == '') {
				tmp_set2 = tmp_set2 + user.userName;
			} else {
				tmp_set2 = tmp_set2 + ", " + user.userName;
			}
		}
	}

	var tmp = document.getElementById(retid);

	eval(retid+"1").innerHTML = tmp_set1;
  eval(retid+"2").innerHTML = tmp_set2;
}

function filterKey(filter) {
  if(filter){
      var sKey = String.fromCharCode(event.keyCode);
      var re = new RegExp(filter);
      if(!re.test(sKey)) event.returnValue=false;

      event.keyCode = sKey.toUpperCase().charCodeAt();
  }
}

function inputFilterKey(filter) {
  if(filter){
      var sKey = String.fromCharCode(event.keyCode);
      var re = new RegExp(filter);
      if(!re.test(sKey)) event.returnValue=false;

      var srcValue = event.srcElement.value;

      if(sKey == "-" && srcValue.length > 0) {
      	 event.returnValue=false;
      }

      if((sKey == "." && srcValue.length == 0)
          || (sKey == "." && srcValue.length == 1 && srcValue == "-")
      		|| (sKey == "." && srcValue.indexOf(".") != -1)) {
      	 event.returnValue=false;
      }
  }
}

function IsNumeric(target) {	//입력값이 숫자만 입력되도록 체크
	e = window.event;	// window의 이벤트 Catch

	//숫자열 0 ~ 9 : 48 ~ 57, 키패드 0 ~ 9 : 96 ~ 105 ,8 : backspace, 46 : delete -->키코드값을 구분, 9 : TAB키, 110 : 소수점(숫자키패트), 190 : 소수점
	if(e.keyCode >= 48 && e.keyCode <= 57 || e.keyCode >= 96 && e.keyCode <= 105 || e.keyCode == 8 || e.keyCode == 46 || e.keyCode == 9 || e.keyCode == 110 || e.keyCode == 190)
	{
		return;
  }
  else {		// 숫자가 아니면 경고창 띄움
		alert("숫자만 입력할 수 있습니다.");
		target.value = "";
		e.returnValue = false;
 	}
}

function comma(target) {
	var num = target.value;

        if (num.length >= 4) {
            re = /^$|,/g;
            num = num.replace(re, "");
            fl=""
        if(isNaN(num)) {
        	alert("문자는 사용할 수 없습니다.");
        	target.value = "";
        	return 0;
        }
        if(num==0) return num
        if(num<0){
            num=num*(-1)
            fl="-"
        }
        else{
            num=num*1 //처음 입력값이 0부터 시작할때 이것을 제거한다.
        }
            num = new String(num)
            temp=""
            co=3
            num_len=num.length
    while (num_len>0){
        num_len=num_len-co
        if(num_len<0){co=num_len+co;num_len=0}
        temp=","+num.substr(num_len,co)+temp
        }
    target.value =  fl+temp.substr(1);
    }
}

function num_check() {
    var keyCode = event.keyCode
        if (keyCode < 48 || keyCode > 57){
            alert("문자는 사용할 수 없습니다."+"["+keyCode+"]")
            event.returnValue=false
        }
    }

// 앞뒤로 빈공백을 없앤다.
String.prototype.trim = function()
{
	return this.replace(/(^\s*)|(\s*$)/gi, "");
}

//엔터키 후 다음 아이템(nextItem)으로 포커스 이동
function enterKey_focus(nextItem){
	if(event.keyCode ==13){
		eval("document." + nextItem + ".focus()");
	}
}

//한글,영문,숫자 길이 구하기
function getCharLen(str){
  var tcount = 0;
  var tmpStr = new String(str);
  var temp = tmpStr.length;

  var onechar;
  for ( k=0; k<temp; k++ ){
    onechar = tmpStr.charAt(k);
    if (escape(onechar).length > 4){
      tcount += 2;
    }else{
      tcount += 1;
    }
  }
  return tcount;
}

function loadJavascript(url, charset) {
    var head= document.getElementsByTagName('head')[0];
    var script= document.createElement('script');
    script.type= 'text/javascript';
    if (charset != null) {
        script.charset = "euc-kr";
    }
    var loaded = false;
    script.onreadystatechange= function () {
        if (this.readyState == 'loaded' || this.readyState == 'complete') {
            if (loaded) {
                return;
            }
            loaded = true;
        }
    }
    script.src = url;
    head.appendChild(script);
}