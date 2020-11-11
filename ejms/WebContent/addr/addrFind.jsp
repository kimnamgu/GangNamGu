<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 주소검색
 * 설명:
--%>
<%@ page contentType="text/html;charset=EUC-KR" %>
<%@ page import="java.util.List" %>
<%@ page import="org.json.JSONObject" %>
<%@ page import="org.json.JSONArray" %>
<%@ page import="nexti.ejms.addr.model.AddrBean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%
	response.setDateHeader("Expires", 0);
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
%>
<%
	String retid = "";
	
	if(request.getAttribute("retid")!=null){
		retid = request.getAttribute("retid").toString();
	}
	List provList = (List)request.getAttribute("provList");
	JSONArray json = (JSONArray)request.getAttribute("json");
	Object obj = request.getAttribute("gbn");
	String gbn="1";
	if(obj!=null){
		gbn = (String)request.getAttribute("gbn");
	}
	List addrList2 = (List)request.getAttribute("addrList2");
	
	String prov = (String)request.getAttribute("prov");
	String city = (String)request.getAttribute("city");
	String street = (String)request.getAttribute("street");
	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8">
<meta http-equiv="Content-Script-Type" content="text/javascript">
<meta http-equiv="Content-Style-Type" content="text/css">
<title>주소찾기</title>
<base target="_self">
<link rel="stylesheet" type="text/css" href="/css/style.css">
<style type="text/css">
.tab_ul {
border-bottom-width:1px;
border-bottom-style:solid;
border-bottom-color:#a3b6d6;
text-align:center;
vertical-align: bottom;
}
</style>
<script type="text/javascript" src="/script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="/script/base64.js"></script>
<script type="text/javascript" src="/script/common.js"></script>
<script type="text/javascript">
var json = $.parseJSON('<%=json%>');
function openDong(){
	$("#divStreetSearch").hide();
	$("#divDongSearch").show();
	
	$("#ment").html("읍/면/동을 입력하세요");
	
	$("#dongTdL").attr('class','');
	$("#dongTdM").attr('class','');
	$("#dongTdR").attr('class','');
	$("#dongTdM").attr('background','/images/tab/bg_tab2_c_on.gif');
	$("#dongImgL").attr('src','/images/tab/bg_tab2_l_on.gif');
	$("#dongImgR").attr('src','/images/tab/bg_tab2_r_on.gif');
	
	$("#streetTdL").attr('class','tab_ul');
	$("#streetTdM").attr('class','tab_ul');
	$("#streetTdR").attr('class','tab_ul');
	$("#streetTdM").attr('background','/images/tab/bg_tab2_c.gif');
	$("#streetImgL").attr('src','/images/tab/bg_tab2_l.gif');
	$("#streetImgR").attr('src','/images/tab/bg_tab2_r.gif');
}
function openStreet(){

	$("#divDongSearch").hide();
	$("#divStreetSearch").show();
	
	$("#ment").html("지역 및 시군구를 선택후 도로명을 입력하세요");
	
	$("#streetTdL").attr('class','');
	$("#streetTdM").attr('class','');
	$("#streetTdR").attr('class','');
	$("#streetTdM").attr('background','/images/tab/bg_tab2_c_on.gif');
	$("#streetImgL").attr('src','/images/tab/bg_tab2_l_on.gif');
	$("#streetImgR").attr('src','/images/tab/bg_tab2_r_on.gif');
	
	$("#dongTdL").attr('class','tab_ul');
	$("#dongTdM").attr('class','tab_ul');
	$("#dongTdR").attr('class','tab_ul');
	$("#dongTdM").attr('background','/images/tab/bg_tab2_c.gif');
	$("#dongImgL").attr('src','/images/tab/bg_tab2_l.gif');
	$("#dongImgR").attr('src','/images/tab/bg_tab2_r.gif');
}
function setAddr(zipcd, addr, bungi){
	var opener = window.dialogArguments;
	var retid = document.forms[0].retid.value;
	var addr1 = "["+zipcd+"]"+addr;
	if(retid == ""){
		opener.document.forms[0].addr1.value= addr1;
		opener.document.forms[0].addr2.value= bungi;
		opener.document.forms[0].addr2.focus();
	}else{
		opener.setAddr(addr1, bungi, retid);
	}
	
	window.close();
}
function setAddr2(zipcd, addr,dong, bdnm, bdno){
	var opener = window.dialogArguments;
	var retid = document.forms[0].retid.value;
	var addr1 = "["+zipcd+"]"+addr+(bdnm?" "+bdno:"");
	var tk = dong?", ":"";
	var addr2 = "("+dong+(bdnm?tk+bdnm:"")+")";
	if(retid == ""){
		opener.document.forms[0].addr1.value= addr1;
		opener.document.forms[0].addr2.value= (dong || bdnm)?addr2:"";
		opener.document.forms[0].addr2.focus();
	}else{
		opener.setAddr(addr1, addr2, retid);
	}
	
	window.close();
}
function fnProvCng(prov){
  	 //var city = document.getElementById("city");
     $("#city").html("<option value=\"\">-시군구선택-</option>");
     var list;
     for(var i=0;i<json.length;i++){
     	if(json[i].name == prov){
     		list=json[i].data;
     		break;
     	}
     }
     for(var i=0;i<list.length;i++){
     	var option = document.createElement("option");
     	$(option).val(list[i]);
     	$(option).html(list[i]);
     	$("#city").append(option);
     }
}
function fnSubmit(){
	if($("#city option:selected").val().trim().length==0)return;
	//$("#pProv").val($("#prov").val()));
	//$("#pCity").val($("#city").val()));
	$("#pProv").val(Base64.encode($("#prov").val()));
	$("#pCity").val(Base64.encode($("#city").val()));
	$("#pStreet").val(Base64.encode($("#street").val()));
	document.streetFrm.submit();	
}
function fnSubmit2(){
	if($("#city option:selected").val().trim().length==0)return;
	//$("#pProv").val($("#prov").val()));
	//$("#pCity").val($("#city").val()));
	$("#pProv").val(Base64.encode($("#prov").val()));
	$("#pCity").val(Base64.encode($("#city").val()));
	$("#pStreet").val(Base64.encode($("#street").val()));
	
	return true;	
}

var fnAddrList = function(event){
	//alert(event.which);		
	//alert($("#city") );
	//alert($("#city option:selected") );
	//alert($("#city option:selected").val() );
	if($("#city option:selected").val().trim().length==0)return;
	//if(event)if($("#street").val().trim().length==0)return;
	
	var p1 = Base64.encode($("#prov").val());
	var p2 = Base64.encode($("#city").val());
	var p3 = Base64.encode($("#street").val());
	$.ajax({
          type:"POST",
          encoding:"EUC-KR",
          data: { prov : p1
          		, city : p2
          		, street : p3 },
          url:"/streetAddrListAction.do",
          dataType:"json", // 옵션이므로 JSON으로 받을게 아니면 안써도 됨
          success : function(data) {
          		 var list = data.json;
          		 if(list.length==0)return;
          		 var tbody = $("#tbody");
          		 tbody.html("");
          		 for(var i=0;i<list.length;i++){
          		 	//alert(list[i].zipcode+"/"+list[i].addr);
          		 	var tr = document.createElement("tr");
                	var td1 = document.createElement("td");
                	var td2 = document.createElement("td");
                	var a = document.createElement("a");
                	$(a).data("zipcode",list[i].zipcode);
                	$(a).data("addr",list[i].addr);
                	$(a).data("bdnm",list[i].bdnm);
                	$(a).data("bdno",list[i].bdno);
                	$(a).data("dong",list[i].dong);

                	$(a).html(list[i].addr+" "+(list[i].dong?" "+list[i].dong:"")+(list[i].bdnm?" "+list[i].bdnm:""));
                	$(a).css("cursor","pointer");
                	$(a).css("color","#7777ff");
                	$(a).click(function(data){
                		setAddr2($(this).data("zipcode"),$(this).data("addr"),$(this).data("dong"),$(this).data("bdnm"),$(this).data("bdno"));
                	});
                	$(td1).html(list[i].zipcode);
                	$(td1).css("text-align","center");
                	$(td1).css("border-bottom-style","solid");
                	$(td1).css("border-bottom-width","1px");
                	$(td1).css("border-bottom-color","#ddc89d");
                	$(td2).append(a);
                	$(td2).css("text-align","left");
                	$(td2).css("margin-left","18px");
                	$(td2).css("border-bottom-style","solid");
                	$(td2).css("border-bottom-width","1px");
                	$(td2).css("border-bottom-color","#ddc89d");
                	$(tr).append(td1);
                	$(tr).append(td2);
                	$(tbody).append(tr);
                }
          		 
                //	alert('success '+list.length);
                /*var city = document.streetFrm.city;
                city.innerHTML="<option value=\"\">-시군구선택-</option>";
                for(var i=0;i<list.length;i++){
                	var option = document.createElement("option");
                	$(option).val(list[i].city);
                	$(option).html(list[i].city);
                	city.appendChild(option);
                }*/
          },
          complete : function(data) {
                // 통신이 실패했어도 완료가 되었을 때 이 함수를 타게 된다.
                // TODO
                //alert('complete '+data);
          },
          error : function(xhr, status, error) {
                //alert("에러발생");
          }
   	});
	
}
$(document).ready(function(){
	//$("#street").keyup(fnAddrList);
	if("<%=gbn%>"=="2"){
		openStreet();
	}
});
</script>
</head>

<body bgcolor="#FFFFFF" text="#000000" style="margin: 0px 0px 0px 0px">
<table width="442" border="0" cellspacing="0" cellpadding="0">
  <!-- <tr>
    <td colspan="2"><img src="/images/address01.gif" width="442" height="57" alt=""></td>
  </tr> -->
  <tr>
  	<td>
  		<table width=100%  border="0" cellspacing="0" cellpadding="0" style="margin-bottom: 20px;">
  			<tr>
  				<td id="dongTdL" width=7><img id="dongImgL" width=7 height=22 src="/images/tab/bg_tab2_l_on.gif" /></td>
  				<td id="dongTdM" onclick="openDong();" style="cursor:pointer;text-align:center;vertical-align: bottom;" width=60 background="/images/tab/bg_tab2_c_on.gif">지번주소</td>
  				<td id="dongTdR" width=4><img id="dongImgR" width=4 height=22 src="/images/tab/bg_tab2_r_on.gif" /></td>
  				<td id="streetTdL" class="tab_ul" width=7><img id="streetImgL" width=7 height=21 src="/images/tab/bg_tab2_l.gif" /></td>
  				<td id="streetTdM" onclick="openStreet();" class="tab_ul" style="cursor:pointer;text-align:center;vertical-align: bottom;"  width=70 background="/images/tab/bg_tab2_c.gif">도로명주소</td>
  				<td id="streetTdR" class="tab_ul" width=4><img id="streetImgR" width=4 height=21 src="/images/tab/bg_tab2_r.gif" /></td>
  				<td class="tab_ul"></td>
  			</tr>
  		</table>
  	</td>
  </tr>
  <tr>
    <td colspan="2" height="25">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="/images/common/dot.gif" alt="">&nbsp;&nbsp;<b id="ment">읍/면/동을 입력하세요</b></td>
  </tr>
  <tr id="divDongSearch" style="display:">
    <td height="49" colspan="2" style="background-image: url('/images/address03_1.gif')">
    	<table width="442" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td width="37">&nbsp;</td>
	        <td width="326" align="center">
	        	<html:form action="/addrFindList.do">
	        			<input type="hidden" name="gbn" value="1">
			    		<input type="hidden" name="retid" value="<%=retid %>">
		        	<html:text name="addrListForm" property="sch_addr" title="검색어" styleClass="style1" style="width:100%;background-color:white"/>
		        </html:form>
	        </td>
	        <td width="79" style="padding-left:5px"><a href="javascript:document.forms[0].submit()"><img src="/images/common/btn_search.gif" border="0" alt="검색"></a></td>
	      </tr>
    	</table>
    </td>
  </tr>
  <tr id="divStreetSearch" style="display:none">
  	<td height="49" colspan="2" style="background-image: url('/images/address03_1.gif')">
  		<form name="streetFrm" action="/addrFindList.do" onsubmit="return fnSubmit2();">
  		<input type="hidden" name="gbn" value="2">
  		<input type="hidden" id="pProv" name="prov" value="">
  		<input type="hidden" id="pCity" name="city" value="">
  		<input type="hidden" id="pStreet" name="street" value="">
  		<input type="hidden" name="retid" value="<%=retid %>">
    	<table width="442" border="0" cellspacing="0" cellpadding="0">
    		<tr>
		        <td width="37">&nbsp;</td>
		        <td align="left">
		        	<select id="prov" onchange="fnProvCng(this.value);">
		        		<option value="">-지역선택-</option>
		        	<%
		        	AddrBean bean = null;
		        	for(int i=0;i<provList.size();i++){
		        		bean = (AddrBean)provList.get(i);
		        	%><option value="<%=bean.getProvince()%>" <%=bean.getProvince().equals(prov)?"selected":""%>><%=bean.getProvince()%></option><%
					}
		        	%>
		        	</select>
		        	<select id="city" onchange="//fnAddrList();">
		        		<option value="">-시군구선택-</option>
		        	</select>
		        	<input type=text id=street />
		        	<a href="javascript:fnSubmit();"><img src="/images/common/btn_search.gif" border="0" alt="검색"></a>
		        </td>
		        <td width="37" style="padding-left:5px"></td>
		        <td>&nbsp;</td>
		      </tr>
		</table>
		</form>
  	</td>
  </tr>
  <tr>
    <td height="10" colspan="2"></td>
  </tr>
  <tr>
    <td colspan="2" align="center">
    	<table width="400" border="0" cellspacing="0" cellpadding="0">
    		<thead>
    			<tr>
			        <td height="2" colspan="2" bgcolor="#DDC89D"></td>
			      </tr>
				  	<tr>
			        <td width="100" height="21" align="center" valign="middle" class="style4">우편번호</td>
			        <td width="300" align="center" valign="middle" class="style4">주 소 </td>
			      </tr>
			      <tr>
			        <td height="1" colspan="2" bgcolor="DDC89D"></td>
			      </tr>
    		</thead>
	      <tbody id="tbody">
		      <logic:notEmpty name="addrListForm" property="addrList">
		      <logic:iterate id="list" name="addrListForm" property="addrList" >
		      <tr>
		        <td height="21" align="center" class="style1"><bean:write name="list" property="zipcode"/></td>
		        <td style="padding-left:5px" class="style1"><a href="#" onclick="setAddr('<bean:write name="list" property="zipcode"/>','<bean:write name="list" property="addr"/>','<bean:write name="list" property="bungi"/>')"><bean:write name="list" property="addr"/>&nbsp;<bean:write name="list" property="bungi"/></a></td>
		      </tr>
		      <tr>
		        <td height="1" colspan="2" bgcolor="DDC89D"></td>
		      </tr>
		      </logic:iterate>
		      </logic:notEmpty>
		      <%
		      AddrBean ab = null;
		      for(int i=0;i<addrList2.size();i++){
		      	ab = (AddrBean)addrList2.get(i);
		      %>
		      		<tr>
				        <td height="21" align="center" class="style1"><%=ab.getZipcode()%> </td>
				        <td style="padding-left:5px" class="style1"><a href="#" onclick="setAddr2('<%=ab.getZipcode()==null?"":ab.getZipcode()%>','<%=ab.getAddr()==null?"":ab.getAddr()%>','<%=ab.getDong()==null?"":ab.getDong()%>','<%=ab.getBdnm()==null?"":ab.getBdnm()%>','<%=ab.getBdno()==null?"":ab.getBdno()%>');">
				        <%=ab.getAddr()+" "+((ab.getDong().length()>0)?" "+ab.getDong():"")+((ab.getBdnm()!=null && ab.getBdnm().length()>0 )?" "+ab.getBdnm():"") %>
				        </a></td>
				      </tr>
				      <tr>
				        <td height="1" colspan="2" bgcolor="DDC89D"></td>
				      </tr>
		      <%} %>
		     <%-- <logic:empty name="addrListForm" property="addrList">
		      	<logic:empty name="addrBean" property="addrList2">
					<tr>
			           <td colspan="2" height="23" align="center">조회된 목록이 없습니다.</td>
			         </tr>
			        <tr>
			        	<td colspan="2" height="1" bgcolor="DDC89D"></td>
			        </tr>
			      </logic:empty>
		      </logic:empty>--%>
	      </tbody>
    	</table>
    </td>
  </tr>
  <tr>
    <td colspan="2" align="center">&nbsp;</td>
  </tr>
</table>
</body>
</html>