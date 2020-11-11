<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>	
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/WEB-INF/include/include-header2.jspf" %>
		<meta http-equiv="X-UA-Compatible" content="IE=Edge">
		<meta charset="utf-8">
		<title>자가진단시스템</title>
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
		<link rel="stylesheet" href="http://www.gangnam.go.kr/assets/vendor/bootstrap/css/bootstrap.min.css">
		<link rel="stylesheet" href="http://www.gangnam.go.kr/assets/css/survey/style.css">
		<link rel="stylesheet" href="http://www.gangnam.go.kr/assets/vendor/fontawesome/css/font-awesome.min.css">
		
		<!--[if lt IE 9]>
	      <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
	      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
	    <![endif]-->
	    

	<script type="text/javascript">
		var user_right = "${sessionScope.userinfo.userright}";
		
		$(document).ready(function(){
			
		$('input:radio[name=SORT_GB]:input[value="A"]').attr("checked", true);	
		$("#SHORTEN_YN3").attr("checked", true);
	
			
			$("#apply").on("click", function(e){ //진단하기
				e.preventDefault();
				fn_apply();
			});
		
			$("#list1").on("click", function(e){ //목록으로1
				e.preventDefault();				
				fn_DgnsMastList();							
			});
			
			$("#list2").on("click", function(e){ //목록으로2
				e.preventDefault();				
				fn_DgnsMastList();							
			});
			
			$("#print").on("click", function(e){ //프린트화면
				e.preventDefault();
				fn_printList();
			});			
		
		});
		
		
		
		function fn_apply(){			
			
			var cnts = Number("${fn:length(lists)}");
			var cntq = 0;
			var dgns_no = $("#dgns_no").val();			
			var i, j;
			var tmp1=0, sum=0, chk=0, tsum=0;
			var cntq_cnt = new Array();
						
			<c:forEach var="rowq_cnt" items="${listq_cnt}" varStatus="status5">
			cntq_cnt[Number("${status5.count}")] = Number("${rowq_cnt.CNT}");			
			</c:forEach>
			
			
			for(i=1; i<= cnts; i++){
				sum = 0;
				chk = 0;
				
				if(cnts > 1)
				{
					cntq = cntq_cnt[i];
				}
				else{
					cntq = Number("${fn:length(listq)}");	
				}									
				
				for(j=1; j<= cntq; j++){
										
					tmp1 = $("input:radio[name='example_" + i + j + "']:checked").val();
					//alert("i=[" + i + "] j=[" + j + "] tmp=[" + tmp1 + "]");
					if(tmp1 == undefined || tmp1 == "" || tmp1 == null){
						if(cnts > 1)
						{
							alert(i + "그룹 " + j + "번의 질문에 응답을 하시기 바랍니다.");
						}
						else{
							alert( j + "번의 질문에 응답을 하시기 바랍니다.");
						}
						
						chk = 1;
						break;
					}
									
					sum =  sum + Number(tmp1);									
				}
				
				if(dgns_no == 2 && i == 3){ //부부관계 측정 3그룹 만 총점을 2로 나눈다
					tsum = (sum / 2) + tsum;
				}
				else{
					tsum = sum + tsum;
				}
					
				if(chk == 1)
					break;
				
				if(chk == 0){
					
					if(dgns_no == 2 && i == 3){ //부부관계 측정 3그룹 만 총점을 2로 나눈다
						$("#total" + i).html(sum / 2);
						//alert('end1!!');
						setval_cookie(dgns_no);
					}
					else{					
						$("#total" + i).html(sum);
						//alert('end2!!');
						setval_cookie(dgns_no);
					}
										
					if( Number("${fn:length(lists)}") > 2)						
						$("#total_sum").html(tsum);	
				}					
			}							
		}
		
		
		
		function setval_cookie(idx){	
			var cn = $.cookie('apply'+idx);
			var param = "DGNS_NO=" + idx;
						
			if(cn == null || cn == undefined)
			{
			    $.cookie('apply'+idx, 'Y',  { expires : 1, path : '/' });
								
				  $.ajax({
					  /*
		              url : "/sds/common/applyCntUpdateAjax.do",
		              type: "post",		           
		              dataType : "json",
		              data : param,
		              error : function(xhr, status, e){
		   			  	alert("error " + xhr.message);
		              },
		              success : function(data){           
		              	//alert("success " + data.message);
		              }
		              */
		              
		              type:"POST", 		              
		              url: '/sds/common/applyCntUpdateAjax.do',
		              dataType: "json",
		              data : param,
		              cache : false, 
		              success : function(resData)
		              {
		            	  //alert("success " + resData.message); 점수");
		            	  alert("점수가 계산되었습니다.");
		              },
		              error : function(xhr, status, e)
		              { 
		            	  alert("error " + xhr.message);
		              }
		              
		          });
		  		       		
			}
			else{
				//alert('이미 실시하셨습니다!!');
			}
		}
		
		
		function fn_DgnsMastList(){			
			var comSubmit = new ComSubmit();
			
			comSubmit.setUrl("<c:url value='/diagnose/selfDgnsMastList.do' />");			
			comSubmit.submit();
		}	    
	    
	 </script>   
	    
	</head>
<body>
<form id="form1" name="form1">
<input type="hidden" id="dgns_no" name="dgns_no" value="${param.DGNS_NO}">
	<div class="wrap">
		<div class="container">
			<div class="survey-wrap">
				<h2>자가진단표</h2>
				<div class="survey-info">
					<table class="table">
						<caption>자가진단표</caption>
						<colgroup>
							<col style="width:10%">
							<col style="width:90%">
						</colgroup>
						<tbody>
							<tr>
								<th colspan="2" class="text-center survey-title">
									${map.TITLE}
								</th>								
							</tr>
							<tr>								
								<td colspan="2" align="right"><a href="#this" class="btn-join" id="list1">목록으로</a></td>
							</tr>
							<c:if test="${ null ne map.HEADCONT }">	
							<tr>
								<th scope="row">&nbsp;</th>
								<td>
									 ${map.HEADCONT }
								</td>
							</tr>
							</c:if>											
						</tbody>
					</table>
				</div>
				
				<c:set var="sNum" value="${fn:length(lists)}"/>
				<c:set var="qNum" value="${fn:length(listq)}"/>
				<c:set var="eNum" value="${fn:length(liste)}"/>
				
				<c:forEach var="rows" items="${lists}" varStatus="status1">
				<c:if test="${ sNum > 1}">					
				<div class="survey-write-top-area">
					<div class="write-info">
						<p><b>${status1.count}. ${rows.SUBJECT }</b></p>						
					</div>
				</div>
				</c:if>
				
				<div class="survey-list">
					<ol>
						<c:forEach var="rowq" items="${listq}" varStatus="status2">
						<c:if test="${rowq.SBJ_SEQ eq rows.SBJ_SEQ}">						
						<li>
							<c:choose>
							<c:when test="${null ne rowq.CONT}">
							<div class="survey-list-title">
								${rowq.CONT }								
							</div>
							</c:when>
							<c:otherwise>
							<div class="survey-list-title">
								&nbsp;								
							</div>
							</c:otherwise>
							</c:choose>
							<div class="survey-list-form">
								<ul><!-- 보기의 갯수는 문항수 보다는 크다. 보기는 예/아니오 등 최소 두개이상 -->	
									<c:forEach var="rowe" items="${liste}" varStatus="status3">									
									<c:choose>
									<c:when  test="${(qNum * 2) <= eNum }">							
									<c:if test="${rowe.SBJ_SEQ eq rows.SBJ_SEQ and rowe.QST_SEQ eq rowq.QST_SEQ}">									
									<li>
										<input type="radio" id="example_${rowe.SBJ_SEQ}${rowe.QST_SEQ}${rowe.EXP_SEQ}" name="example_${rowe.SBJ_SEQ}${rowe.QST_SEQ}" value="${rowe.POINT }">
										<label for="example_${rowe.SBJ_SEQ}${rowe.QST_SEQ}${rowe.EXP_SEQ}">${rowe.CONT }</label>
									</li>
									</c:if>
									</c:when>
									<c:otherwise>
									<li>
										<input type="radio" id="example_${status1.count}${rowq.QST_SEQ}${rowe.EXP_SEQ}" name="example_${status1.count}${rowq.QST_SEQ}" value="${rowe.POINT }">
										<label for="example_${status1.count}${rowq.QST_SEQ}${rowe.EXP_SEQ}">${rowe.CONT }</label>
									</li>
									</c:otherwise>
									</c:choose>																	
									</c:forEach>																	
								</ul>
							</div>
						</li>
						</c:if>
						</c:forEach>						
					</ol>
				</div>
				</c:forEach>
				
				
				<div class="text-center mb20">
					<a href="#this" class="btn-join" id="apply">진단하기</a> <a href="#this" class="btn-join" id="list2">목록으로</a>
				</div>
				
				<c:if test="${ null ne map.TAILCONT }">				
				<div class="survey-info">
					<table class="table">
						<caption>자가진단표</caption>
						<colgroup>
							<col style="width:10%">
							<col style="width:90%">
						</colgroup>
						<tbody>								
							<tr>
								<td>&nbsp;</td>
								<td>
									 ${map.TAILCONT }
								</td>
							</tr>							
							<tr>
								<th colspan="2" class="text-center survey-title">
								&nbsp;
								</th>								
							</tr>						
						</tbody>
					</table>
				</div>
				</c:if>
				
				
				
				
				
				<div class="survey-sp-txt">
					<c:forEach var="rows" items="${lists}" varStatus="status4">
					<p><b><h3>점수 <c:if test="${ sNum > 1}">${status4.count}</c:if> : <font color="red"><span id="total${status4.count }"></span></font></h3></b></p>
					<br/>
					</c:forEach>					
					<c:if test="${ sNum > 2  and sNum < 10 }"> <!-- 6번 질문지 일때만, 2번은 제외  -->
					<br><br>
						<h1>총 점 :  <font color="blue"><span id="total_sum"></span></font></h1>
					</c:if>										
				</div>
			</div>
		</div>
	</div>
	<br/>
	</form>
	<c:if test="${ !empty param.BOT_IMG}">
	<table border=1 align=center>
	<tr>
		<td>
		<img src="/sdsfile/${param.BOT_IMG}">	
		</td>
	</tr>	
	</table>	
	<br/>
	</c:if>
	<%@ include file="/WEB-INF/include/include-body.jspf" %>
	
</body>
</html>