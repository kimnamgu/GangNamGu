package service.common.logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import service.daejang.service.DaejangService;
 
public class EchoHandler extends TextWebSocketHandler {
	
	Logger log = Logger.getLogger(this.getClass());
	@Resource(name="daejangService")
	private DaejangService daejangService;
	
    private List<WebSocketSession> sessionList = new ArrayList<WebSocketSession>();
     
      // 클라이언트와 연결 이후에 실행되는 메서드
      @Override
      public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessionList.add(session);        
        log.debug("연결됨 \t:  " + session.getId());
      }
     
      // 클라이언트가 서버로 메시지를 전송했을 때 실행되는 메서드
      @Override
      protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {        
        log.debug(" 메세지 받음  \t:  " + session.getId()+ message.getPayload());
        
        Map<String, Object> rtmap = daejangService.getDocIssueReserveCount();
        
        //log.debug("rtmap cnt \t:  " + rtmap.get("TOTAL_COUNT"));
        
        for (WebSocketSession sess : sessionList) {
          sess.sendMessage(new TextMessage(session.getId() + " count=[" + rtmap.get("TOTAL_COUNT") + "] : " + message.getPayload()));
        }
      }
     
      // 클라이언트와 연결을 끊었을 때 실행되는 메소드
      @Override
      public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessionList.remove(session);
        log.debug("연결 끊김  \t:  " + session.getId());
      }
}