package gift.daejang.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface DaejangService {

	List<Map<String, Object>> selectGiftAcceptList(Map<String, Object> map) throws Exception;
	
	List<Map<String, Object>> selectGiftPrintList(Map<String, Object> map) throws Exception;

	void insertGiftAccept(Map<String, Object> map, HttpServletRequest request) throws Exception;

	Map<String, Object> giftAcceptDetail(Map<String, Object> map) throws Exception;
	
	void updateGiftAccept(Map<String, Object> map, HttpServletRequest request) throws Exception;

	void deleteGiftAccept(Map<String, Object> map) throws Exception;
	
	
	Map<String, Object> giftMngStatus(Map<String, Object> map) throws Exception;
	
	void insertGiftMng(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	void updateGiftMng(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	void deleteGiftMng(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	
	Map<String, Object> giftSellStatus(Map<String, Object> map) throws Exception;
	
	void insertGiftSell(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	void updateGiftSell(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	void deleteGiftSell(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
}
