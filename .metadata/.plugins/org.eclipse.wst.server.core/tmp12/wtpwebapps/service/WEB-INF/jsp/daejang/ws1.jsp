<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>주요사업 관리시스템</title>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
<script type="text/javascript">
	
	var ws;
	
	$(document).ready(function(){
		
		$("#enterBtn").on("click", function(e){ //		
			ws = new WebSocket("ws://98.23.113.55/service/ws");
			
			ws.onerror = onError;
			ws.onmessage = onMessage;
			ws.onopen = onOpen;
			ws.onClose = onClose;
			
		});
		
		$("#exitBtn").on("click", function(e){			
			ws.Close();			
		});
		
		$("#sendBtn").on("click", function(e){			
			sendMessage();			
		});
		
	});
	
	
	function sendMessage(){
		ws.send(
			$("#message").val()	
		);		
	}
	
	function onMessage(event){
		var data = event.data;
		$("#data").append(data + "<br>");
	}
	
	function onOpen(event){		
		$("#data").append("클라이언트측 onOpen() : Connection Opened!<br>");
	}
	
	function onClose(event){		
		$("#data").append("클라이언트측 onClose() : Connection Closed!<br>");
	}
	
	function onError(event){		
		$("#data").append("클라이언트측 onError() : Connection error!<br>");
	}
				
</script>
</head>

<body>
<input type="button" id="enterBtn" value="ENTER"/>
<input type="button" id="exitBtn" value="EXIT"/>

<p>
session ID : <input type="text" id="sessionid" value="" size="50"/>
<p>

<input type="text" id="message" value="50"/>
<input type="button" id="sendBtn" value="SEND"/>
<div id="data"></div>
</body>
</html>