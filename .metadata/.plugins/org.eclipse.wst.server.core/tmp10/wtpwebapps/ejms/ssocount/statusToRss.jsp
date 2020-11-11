<%@page contentType="text/xml;charset=EUC-KR" pageEncoding="EUC-KR"%><?xml version="1.0" encoding="euc-kr"?>
<rss version="2.0">
<jsp:directive.page import="nexti.ejms.delivery.model.DeliveryManager"/>
<jsp:directive.page import="nexti.ejms.inputing.model.InputingManager"/>
<jsp:directive.page import="nexti.ejms.sinchung.model.SinchungManager"/>
<jsp:directive.page import="nexti.ejms.sinchung.model.SearchBean"/>
<jsp:directive.page import="nexti.ejms.user.model.UserManager"/>
<jsp:directive.page import="nexti.ejms.user.model.UserBean"/>
<jsp:directive.page import="nexti.ejms.common.appInfo"/>
<jsp:directive.page import="nexti.ejms.util.Base64"/>
<jsp:directive.page import="nexti.ejms.util.Utils"/>
<jsp:directive.page import="java.text.SimpleDateFormat"/>
<jsp:directive.page import="java.util.ArrayList"/>
<jsp:directive.page import="java.util.Calendar"/>
<jsp:directive.page import="java.util.HashMap"/>
<%
	String chTitle = ""; //채널의 제목
	String chLink = ""; //채널의 URL
	String chDescription = ""; //채널의 형태 
	//현제 제공되는 타입은 4가지이다.
	//1. LIST : 일반 리스트 형태
	//2. NEWS_BOARD : 뉴스 리스트의 형태로 일반과 비슷하지만, 이미지가 있을경우
	//		  첫번째 아이템의 이미지가 화면에 노출.
	//3. ALBUM_BOARD : 이미지 게시판의 형태로 이미지가 화면에 노출.
	//4. COUNT : 카운트형태의 카운팅값을 보여준다.
	String chDate = ""; //날짜(예 : 금, 03 14 2008 10:01:41 +0900 => 예의 형태 그대로 보내주어야한다.)
	String chLanguage = ""; //채널의 Language
	String itemTitle = ""; //각 게시내용의 제목
	String itemLink = ""; //각 게시내용의 상세내용 URL(각 시스템에 마춰서 상세페이지가 보여지는 부분을 만들어줘야함.)
	String itemDescription = ""; //각 게시내용의 가략한 내용.
	String itemDomain = ""; //각 게시내용의 이미지가 있을 경우 이미지 URL
	String itemImgLength = ""; //각 게시내용의 이미지가 있을 경우 이미지의 크기
	String itemImgType = ""; //각 게시내용의 이미지가 있을 경우 이미지의 타입(image/확장자명)
	String itemAuthor = ""; //각 게시내용의 작성자(입력 필수 아니지만, null값은 않된다.)
	String itemPubDate = ""; //각 게시내용의 작성일(rss 2.0스펙에 마춰진 날짜)

	/**
	 * TIP.
	 * 이미지가 있을경우 포탈에서의 이미지 파싱순서는
	 * 1. enclosure 태그의 값 유무
	 * 2. category 태그의 값 유무
	 * 위의 태그의 값이 셋팅 되지 않았다면 noImg파일을 보여준다.
	 */

	String userid = Utils.nullToEmptyString((String)request.getParameter("userid"));
	String deptcd = "";
	
	userid = Base64.decodeString(userid);
	UserBean ub = UserManager.instance().getUserInfo(userid);
	if ( ub != null ) deptcd = ub.getDept_id();
	
	//배부대기
	DeliveryManager deliMgr = DeliveryManager.instance();
	int deliCount = deliMgr.deliTotCnt(deptcd);
	
	//입력대기 건수 가져오기
	InputingManager inMgr = InputingManager.instance();
	int inputCount = inMgr.inputingTotCnt(userid, deptcd, 1);
	
	//신청대기 건수 가져오기
	SinchungManager sMgr = SinchungManager.instance();
	SearchBean search = new SearchBean();
	search.setDeptid(appInfo.getRootid());		
	search.setStart_idx(1);
	search.setEnd_idx(10000);
	search.setUnlimited(false);
	int reqCount = sMgr.doSinchungTotCnt(search);
	
	String ejmsUrl = nexti.ejms.common.appInfo.getWeb_address();
	String now = new SimpleDateFormat("EEE, MM dd yyyy hh:mm:ss Z").format(Calendar.getInstance().getTime());
	chTitle = nexti.ejms.common.appInfo.getAppName();
	chLink = ejmsUrl + "/ssocount/statusToRss.jsp?userid=" + userid;
	chDescription = "현황";
	chDate = now;
	chLanguage = "ko";	
	
	ArrayList list = new ArrayList();
	HashMap hm = new HashMap();
	hm.put("title", "배부대기");
	hm.put("link", ejmsUrl + "/ssocount.do?act=deliv&userid=" + userid);
	hm.put("desc", Integer.toString(deliCount));
	hm.put("auth", chTitle);
	hm.put("date", now);
	list.add(hm);
	hm = new HashMap();
	hm.put("title", "입력대기");
	hm.put("link", ejmsUrl + "/ssocount.do?act=input&userid=" + userid);
	hm.put("desc", Integer.toString(inputCount));
	hm.put("auth", chTitle);
	hm.put("date", now);
	list.add(hm);
	hm = new HashMap();
	hm.put("title", "신청대기");
	hm.put("link", ejmsUrl + "/ssocount.do?act=reqlist&userid=" + userid);
	hm.put("desc", Integer.toString(reqCount));
	hm.put("auth", chTitle);
	hm.put("date", now);	
	list.add(hm);
%>

<channel>
	<title><![CDATA[<%=chTitle%>]]></title>
	<link><![CDATA[<%=chLink%>]]></link>
	<description><%=chDescription%></description>
	<pubDate><%=chDate%></pubDate>
	<language><%=chLanguage%></language>
<%
	for ( int i=0; i < list.size(); i++ ) {
		hm = (HashMap)list.get(i);
		itemTitle				=	(String)hm.get("title");
		itemLink  			=	(String)hm.get("link");
		itemDescription	=	(String)hm.get("desc");
		itemDomain			=	(String)hm.get("domain");
		itemImgLength   = (String)hm.get("imglen");
		itemImgType			= (String)hm.get("imgtype");
		itemAuthor			=	(String)hm.get("auth");
		itemPubDate			=	(String)hm.get("date");
%>
	<item>
		<title><![CDATA[<%=itemTitle%>]]></title>
		<link><![CDATA[<%=itemLink%>]]></link>
		<description><![CDATA[<%=itemDescription%>]]></description>
		<!--<category domain="<%=itemDomain%>">이미지</category>-->
		<author><![CDATA[<%=itemAuthor%>]]></author>
		<pubDate><%=itemPubDate%></pubDate>
		<!--<enclosure url="<%=itemDomain%>" length="<%=itemImgLength%>" type="<%=itemImgType%>" />-->
	</item>
<%		
	}
%>
</channel>
</rss>