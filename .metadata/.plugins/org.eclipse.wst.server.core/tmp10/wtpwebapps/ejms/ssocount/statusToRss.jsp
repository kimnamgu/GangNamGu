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
	String chTitle = ""; //ä���� ����
	String chLink = ""; //ä���� URL
	String chDescription = ""; //ä���� ���� 
	//���� �����Ǵ� Ÿ���� 4�����̴�.
	//1. LIST : �Ϲ� ����Ʈ ����
	//2. NEWS_BOARD : ���� ����Ʈ�� ���·� �Ϲݰ� ���������, �̹����� �������
	//		  ù��° �������� �̹����� ȭ�鿡 ����.
	//3. ALBUM_BOARD : �̹��� �Խ����� ���·� �̹����� ȭ�鿡 ����.
	//4. COUNT : ī��Ʈ������ ī���ð��� �����ش�.
	String chDate = ""; //��¥(�� : ��, 03 14 2008 10:01:41 +0900 => ���� ���� �״�� �����־���Ѵ�.)
	String chLanguage = ""; //ä���� Language
	String itemTitle = ""; //�� �Խó����� ����
	String itemLink = ""; //�� �Խó����� �󼼳��� URL(�� �ý��ۿ� ���缭 ���������� �������� �κ��� ����������.)
	String itemDescription = ""; //�� �Խó����� ������ ����.
	String itemDomain = ""; //�� �Խó����� �̹����� ���� ��� �̹��� URL
	String itemImgLength = ""; //�� �Խó����� �̹����� ���� ��� �̹����� ũ��
	String itemImgType = ""; //�� �Խó����� �̹����� ���� ��� �̹����� Ÿ��(image/Ȯ���ڸ�)
	String itemAuthor = ""; //�� �Խó����� �ۼ���(�Է� �ʼ� �ƴ�����, null���� �ʵȴ�.)
	String itemPubDate = ""; //�� �Խó����� �ۼ���(rss 2.0���忡 ������ ��¥)

	/**
	 * TIP.
	 * �̹����� ������� ��Ż������ �̹��� �Ľ̼�����
	 * 1. enclosure �±��� �� ����
	 * 2. category �±��� �� ����
	 * ���� �±��� ���� ���� ���� �ʾҴٸ� noImg������ �����ش�.
	 */

	String userid = Utils.nullToEmptyString((String)request.getParameter("userid"));
	String deptcd = "";
	
	userid = Base64.decodeString(userid);
	UserBean ub = UserManager.instance().getUserInfo(userid);
	if ( ub != null ) deptcd = ub.getDept_id();
	
	//��δ��
	DeliveryManager deliMgr = DeliveryManager.instance();
	int deliCount = deliMgr.deliTotCnt(deptcd);
	
	//�Է´�� �Ǽ� ��������
	InputingManager inMgr = InputingManager.instance();
	int inputCount = inMgr.inputingTotCnt(userid, deptcd, 1);
	
	//��û��� �Ǽ� ��������
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
	chDescription = "��Ȳ";
	chDate = now;
	chLanguage = "ko";	
	
	ArrayList list = new ArrayList();
	HashMap hm = new HashMap();
	hm.put("title", "��δ��");
	hm.put("link", ejmsUrl + "/ssocount.do?act=deliv&userid=" + userid);
	hm.put("desc", Integer.toString(deliCount));
	hm.put("auth", chTitle);
	hm.put("date", now);
	list.add(hm);
	hm = new HashMap();
	hm.put("title", "�Է´��");
	hm.put("link", ejmsUrl + "/ssocount.do?act=input&userid=" + userid);
	hm.put("desc", Integer.toString(inputCount));
	hm.put("auth", chTitle);
	hm.put("date", now);
	list.add(hm);
	hm = new HashMap();
	hm.put("title", "��û���");
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
		<!--<category domain="<%=itemDomain%>">�̹���</category>-->
		<author><![CDATA[<%=itemAuthor%>]]></author>
		<pubDate><%=itemPubDate%></pubDate>
		<!--<enclosure url="<%=itemDomain%>" length="<%=itemImgLength%>" type="<%=itemImgType%>" />-->
	</item>
<%		
	}
%>
</channel>
</rss>