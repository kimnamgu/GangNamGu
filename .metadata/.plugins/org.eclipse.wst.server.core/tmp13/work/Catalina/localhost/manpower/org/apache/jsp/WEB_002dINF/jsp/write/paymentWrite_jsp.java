/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/8.0.24
 * Generated at: 2020-11-04 11:52:31 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF.jsp.write;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class paymentWrite_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(5);
    _jspx_dependants.put("jar:file:/C:/dev/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp13/wtpwebapps/manpower/WEB-INF/lib/jstl-1.2.jar!/META-INF/fn.tld", Long.valueOf(1153352682000L));
    _jspx_dependants.put("jar:file:/C:/dev/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp13/wtpwebapps/manpower/WEB-INF/lib/jstl-1.2.jar!/META-INF/fmt.tld", Long.valueOf(1153352682000L));
    _jspx_dependants.put("jar:file:/C:/dev/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp13/wtpwebapps/manpower/WEB-INF/lib/jstl-1.2.jar!/META-INF/c.tld", Long.valueOf(1153352682000L));
    _jspx_dependants.put("/WEB-INF/lib/jstl-1.2.jar", Long.valueOf(1595463255549L));
    _jspx_dependants.put("/WEB-INF/include/include-header.jspf", Long.valueOf(1604290358114L));
  }

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = null;
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005furl_0026_005fvalue_005fnobody;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fc_005furl_0026_005fvalue_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fc_005furl_0026_005fvalue_005fnobody.release();
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

final java.lang.String _jspx_method = request.getMethod();
if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method) && !javax.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSPs only permit GET POST or HEAD");
return;
}

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write(" \r\n");
      out.write("<!-- \r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"/manpower/css/common.css\" />\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"/manpower/css/skin01.css\" />\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"/manpower/css/import.css\" />\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"/manpower/css/manpower.css\" />\r\n");
      out.write(" -->\r\n");
      out.write(" \r\n");
      out.write(" \r\n");
      out.write(" \r\n");
      out.write("<!-- Fontfaces CSS-->\r\n");
      out.write("<link href=\"/manpower/bootstrap/css/font-face.css\" rel=\"stylesheet\" media=\"all\">\r\n");
      out.write("<link href=\"/manpower/bootstrap/vendor/font-awesome-4.7/css/font-awesome.min.css\" rel=\"stylesheet\" media=\"all\">\r\n");
      out.write("<link href=\"/manpower/bootstrap/vendor/font-awesome-5/css/fontawesome-all.min.css\" rel=\"stylesheet\" media=\"all\">\r\n");
      out.write("<link href=\"/manpower/bootstrap/vendor/mdi-font/css/material-design-iconic-font.min.css\" rel=\"stylesheet\" media=\"all\">\r\n");
      out.write("\r\n");
      out.write("<!-- Bootstrap CSS-->\r\n");
      out.write("<link href=\"/manpower/bootstrap/vendor/bootstrap-4.1/bootstrap.min.css\" rel=\"stylesheet\" media=\"all\">\r\n");
      out.write("\r\n");
      out.write("<!-- Vendor CSS-->\r\n");
      out.write("<link href=\"/manpower/bootstrap/vendor/animsition/animsition.min.css\" rel=\"stylesheet\" media=\"all\">\r\n");
      out.write("<link href=\"/manpower/bootstrap/vendor/bootstrap-progressbar/bootstrap-progressbar-3.3.4.min.css\" rel=\"stylesheet\" media=\"all\">\r\n");
      out.write("<link href=\"/manpower/bootstrap/vendor/wow/animate.css\" rel=\"stylesheet\" media=\"all\">\r\n");
      out.write("<link href=\"/manpower/bootstrap/vendor/css-hamburgers/hamburgers.min.css\" rel=\"stylesheet\" media=\"all\">\r\n");
      out.write("<link href=\"/manpower/bootstrap/vendor/slick/slick.css\" rel=\"stylesheet\" media=\"all\">\r\n");
      out.write("<link href=\"/manpower/bootstrap/vendor/select2/select2.min.css\" rel=\"stylesheet\" media=\"all\">\r\n");
      out.write("<link href=\"/manpower/bootstrap/vendor/perfect-scrollbar/perfect-scrollbar.css\" rel=\"stylesheet\" media=\"all\">\r\n");
      out.write("\r\n");
      out.write("<!-- Main CSS-->\r\n");
      out.write("<link href=\"/manpower/bootstrap/css/theme.css\" rel=\"stylesheet\" media=\"all\">\r\n");
      out.write(" \r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<!-- Jquery JS-->\r\n");
      out.write("<script src=\"/manpower/bootstrap/vendor/jquery-3.2.1.min.js\"></script>\r\n");
      out.write("<!-- Bootstrap JS-->\r\n");
      out.write("<script src=\"/manpower/bootstrap/vendor/bootstrap-4.1/popper.min.js\"></script>\r\n");
      out.write("<script src=\"/manpower/bootstrap/vendor/bootstrap-4.1/bootstrap.min.js\"></script>\r\n");
      out.write("<!-- Vendor JS       -->\r\n");
      out.write("<script src=\"/manpower/bootstrap/vendor/slick/slick.min.js\">\r\n");
      out.write("</script>\r\n");
      out.write("<script src=\"/manpower/bootstrap/vendor/wow/wow.min.js\"></script>\r\n");
      out.write("<script src=\"/manpower/bootstrap/vendor/animsition/animsition.min.js\"></script>\r\n");
      out.write("<script src=\"/manpower/bootstrap/vendor/bootstrap-progressbar/bootstrap-progressbar.min.js\">\r\n");
      out.write("</script>\r\n");
      out.write("<script src=\"/manpower/bootstrap/vendor/counter-up/jquery.waypoints.min.js\"></script>\r\n");
      out.write("<script src=\"/manpower/bootstrap/vendor/counter-up/jquery.counterup.min.js\">\r\n");
      out.write("</script>\r\n");
      out.write("<script src=\"/manpower/bootstrap/vendor/circle-progress/circle-progress.min.js\"></script>\r\n");
      out.write("<script src=\"/manpower/bootstrap/vendor/perfect-scrollbar/perfect-scrollbar.js\"></script>\r\n");
      out.write("<script src=\"/manpower/bootstrap/vendor/chartjs/Chart.bundle.min.js\"></script>\r\n");
      out.write("<script src=\"/manpower/bootstrap/vendor/select2/select2.min.js\">\r\n");
      out.write("</script>\r\n");
      out.write("\r\n");
      out.write("<!-- Main JS-->\r\n");
      out.write("<script src=\"/manpower/bootstrap/js/main.js\"></script>\r\n");
      out.write(" \r\n");
      out.write("<!-- jQuery -->\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"/manpower/css/jquery-ui.css\" />\r\n");
      out.write("<!-- <script src=\"http://code.jquery.com/jquery.min.js\"></script>-->\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      if (_jspx_meth_c_005furl_005f0(_jspx_page_context))
        return;
      out.write("\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      if (_jspx_meth_c_005furl_005f1(_jspx_page_context))
        return;
      out.write("\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      if (_jspx_meth_c_005furl_005f2(_jspx_page_context))
        return;
      out.write("\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      if (_jspx_meth_c_005furl_005f3(_jspx_page_context))
        return;
      out.write("\"></script>\r\n");
      out.write("<script src=\"");
      if (_jspx_meth_c_005furl_005f4(_jspx_page_context))
        return;
      out.write("\" charset=\"utf-8\"></script>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("\tvar user_right = \"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${sessionScope.userinfo.userright}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("\";\r\n");
      out.write("\r\n");
      out.write("\t$(document).ready(\r\n");
      out.write("\t\tfunction() {\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t\t//입력일\r\n");
      out.write("\t\t\t$(\"#WRITE_DATE\").datepicker({\r\n");
      out.write("\t\t\t\tdateFormat: 'yy-mm-dd',\r\n");
      out.write("\t\t\t\tmonthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],\r\n");
      out.write("\t\t\t    dayNamesMin: ['일','월','화','수','목','금','토'],\r\n");
      out.write("\t\t\t\tchangeMonth: true, //월변경가능\r\n");
      out.write("\t\t\t    changeYear: true, //년변경가능\r\n");
      out.write("\t\t\t\tshowMonthAfterYear: true //년 뒤에 월 표시\t\t\t\t\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t//검색초기화\r\n");
      out.write("\t\t\tfn_initial_setting();\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t//정보입력\r\n");
      out.write("\t\t\t$(\"#insert\").on(\"click\", function(e){ //등록하기 버튼\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\te.preventDefault();\t\t\t\t\r\n");
      out.write("\t\t\t\tfn_write();\t\t\t\t\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t});\r\n");
      out.write("\t\r\n");
      out.write("\r\n");
      out.write("\t\r\n");
      out.write("\t//검색초기화\r\n");
      out.write("\tfunction fn_initial_setting(){\r\n");
      out.write("\t    //입력일자 현재\r\n");
      out.write("\t    $('#WRITE_DATE').datepicker('setDate', 'today'); //(-1D:하루전, -1M:한달전, -1Y:일년전), (+1D:하루후, -1M:한달후, -1Y:일년후)\r\n");
      out.write("\r\n");
      out.write("\t    $('input[name=\"SEX\"]').val(['M']);\r\n");
      out.write("\t    $('input[name=\"JUYA_GUBUN\"]').val(['주간']);\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\t//정보 입력\r\n");
      out.write("\tfunction fn_write(){\r\n");
      out.write("\t\tvar comSubmit = new ComSubmit(\"form1\");\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tcomSubmit.addParam(\"WRITE_DATE\", $(\"#WRITE_DATE\").val().replace(/-/gi, \"\"));\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tif($('#WRITE_DATE').val() == \"\")\r\n");
      out.write("\t\t{\r\n");
      out.write("\t\t\talert('입력일자를 입력하세요!!');\r\n");
      out.write("\t\t\t$('#WRITE_DATE').focus();\r\n");
      out.write("\t\t\treturn false;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tif($('#CONSULT_GUBUN').val() == \"\")\r\n");
      out.write("\t\t{\r\n");
      out.write("\t\t\talert('상담구분을 입력하세요!!');\r\n");
      out.write("\t\t\t$('#CONSULT_GUBUN').focus();\r\n");
      out.write("\t\t\treturn false;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tif($('#MINWON_CONTENT').val() == \"\")\r\n");
      out.write("\t\t{\r\n");
      out.write("\t\t\talert('민원내용을 입력하세요!!');\r\n");
      out.write("\t\t\t$('#MINWON_CONTENT').focus();\r\n");
      out.write("\t\t\treturn false;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tif(confirm('데이터를 등록하시겠습니까?'))\r\n");
      out.write("\t\t{\t\t\t\t\t\r\n");
      out.write("\t\t\tcomSubmit.setUrl(\"");
      if (_jspx_meth_c_005furl_005f5(_jspx_page_context))
        return;
      out.write("\");\t\t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\tcomSubmit.submit();\r\n");
      out.write("\t\t\talert('등록완료 되었습니다.!!');\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t\r\n");
      out.write("\tfunction trim(str) {\r\n");
      out.write("\t    return str.replace(/(^\\s*)|(\\s*$)/gi, '');\r\n");
      out.write("\t}\t\r\n");
      out.write("\r\n");
      out.write("\t\r\n");
      out.write("\t//기존주소 jusoPopupOrgCallBack\r\n");
      out.write("\tfunction jusoPopupOrgCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn,detBdNmList,bdNm,bdKdcd,siNm,sggNm,emdNm,liNm,rn,udrtYn,buldMnnm,buldSlno,mtYn,lnbrMnnm,lnbrSlno,emdNo){\r\n");
      out.write("\t\t// 팝업페이지에서 주소입력한 정보를 받아서, 현 페이지에 정보를 등록합니다.\r\n");
      out.write("\t\t$('#ORG_ADDRESS').val(roadAddrPart1);\r\n");
      out.write("\t\t$('#ORG_ADDRESS_DTL').val(addrDetail);\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\t//자가격리주소 jusoPopupIsoCallBack\r\n");
      out.write("\tfunction jusoPopupIsoCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn,detBdNmList,bdNm,bdKdcd,siNm,sggNm,emdNm,liNm,rn,udrtYn,buldMnnm,buldSlno,mtYn,lnbrMnnm,lnbrSlno,emdNo){\r\n");
      out.write("\t\t// 팝업페이지에서 주소입력한 정보를 받아서, 현 페이지에 정보를 등록합니다.\r\n");
      out.write("\t\t$('#SELF_ISO_AREA_ADDRESS').val(roadAddrPart1);\r\n");
      out.write("\t\t$('#SELF_ISO_AREA_ADDRESS_DTL').val(addrDetail);\r\n");
      out.write("\t}\r\n");
      out.write("</script>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<style>\r\n");
      out.write("  table {\r\n");
      out.write("    border: 1px solid #444444;\r\n");
      out.write("     border-collapse: collapse;\r\n");
      out.write("  }\r\n");
      out.write("  th, td {\r\n");
      out.write("    border: 1px solid #444444;\r\n");
      out.write("    padding : 10px;\r\n");
      out.write("  }\r\n");
      out.write("</style>\r\n");
      out.write("</head>\r\n");
      out.write("\r\n");
      out.write("<body>\r\n");
      out.write("\r\n");
      out.write("<div style=\"background-color:#170B3B;color:white; margin-bottom:10px; width: 100%;\">\r\n");
      out.write("\t<a href=\"#this\" class=\"btn\" id=\"logout\">\r\n");
      out.write("\t\t<img src=\"/manpower/images/popup_title.gif\" align=\"absmiddle\">\r\n");
      out.write("\t</a>사업 등록\r\n");
      out.write("\t\r\n");
      out.write("\t<div style=\"padding-right:10px;padding-top:7px;float:right;\">\r\n");
      out.write("\t\t<a href=\"/manpower/manage/businessMng.do\" class=\"btn btn-success\" id=\"statistics\"><i class=\"fa fa-reply\"></i> 목록보기</a>\r\n");
      out.write("\t</div>\r\n");
      out.write("</div>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<form id=\"form1\" name=\"form1\" enctype=\"multipart/form-data\">\r\n");
      out.write("\t<input type=\"hidden\" id=\"INS_ID\" name=\"INS_ID\" value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${sessionScope.userinfo.userId}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("\">\r\n");
      out.write("\t\r\n");
      out.write("\t\t<!--리스트 시작 -->\r\n");
      out.write("\t\t<table style=\"margin:20px;\">\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td>처리년월</td>\r\n");
      out.write("\t\t\t\t<td><input type=\"text\" class=\"form-control\" id=\"MINWON_NAME\" name=\"MINWON_NAME\"></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td>사업명</td>\r\n");
      out.write("\t\t\t\t<td><input type=\"text\" class=\"form-control\" id=\"MINWON_NAME\" name=\"MINWON_NAME\"></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td>인력선택</td>\r\n");
      out.write("\t\t\t\t<td><input type=\"text\" class=\"form-control\" id=\"MINWON_NAME\" name=\"MINWON_NAME\"></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t개인정보\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td>이름(생년월일)</td>\r\n");
      out.write("\t\t\t\t<td><input type=\"text\" class=\"form-control\" id=\"MINWON_NAME\" name=\"MINWON_NAME\"></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td>연락처</td>\r\n");
      out.write("\t\t\t\t<td><input type=\"text\" class=\"form-control\" id=\"MINWON_NAME\" name=\"MINWON_NAME\"></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td>주소</td>\r\n");
      out.write("\t\t\t\t<td><input type=\"text\" class=\"form-control\" id=\"MINWON_NAME\" name=\"MINWON_NAME\"></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td>부양가족수</td>\r\n");
      out.write("\t\t\t\t<td><input type=\"text\" class=\"form-control\" id=\"MINWON_NAME\" name=\"MINWON_NAME\"></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td>은행계좌</td>\r\n");
      out.write("\t\t\t\t<td><input type=\"text\" class=\"form-control\" id=\"MINWON_NAME\" name=\"MINWON_NAME\"></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t근태정보\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td>월차</td>\r\n");
      out.write("\t\t\t\t<td><input type=\"text\" class=\"form-control\" id=\"MINWON_NAME\" name=\"MINWON_NAME\"></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td>유급휴일</td>\r\n");
      out.write("\t\t\t\t<td><input type=\"text\" class=\"form-control\" id=\"MINWON_NAME\" name=\"MINWON_NAME\"></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t입금정보\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td>급여총액</td>\r\n");
      out.write("\t\t\t\t<td><input type=\"text\" class=\"form-control\" id=\"MINWON_NAME\" name=\"MINWON_NAME\"></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td>과세대상</td>\r\n");
      out.write("\t\t\t\t<td><input type=\"text\" class=\"form-control\" id=\"MINWON_NAME\" name=\"MINWON_NAME\"></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td>인건비</td>\r\n");
      out.write("\t\t\t\t<td><input type=\"text\" class=\"form-control\" id=\"MINWON_NAME\" name=\"MINWON_NAME\"></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td>근무일수</td>\r\n");
      out.write("\t\t\t\t<td><input type=\"text\" class=\"form-control\" id=\"MINWON_NAME\" name=\"MINWON_NAME\"></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td>반일근무일수</td>\r\n");
      out.write("\t\t\t\t<td><input type=\"text\" class=\"form-control\" id=\"MINWON_NAME\" name=\"MINWON_NAME\"></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td>주차일수</td>\r\n");
      out.write("\t\t\t\t<td><input type=\"text\" class=\"form-control\" id=\"MINWON_NAME\" name=\"MINWON_NAME\"></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td>일당</td>\r\n");
      out.write("\t\t\t\t<td><input type=\"text\" class=\"form-control\" id=\"MINWON_NAME\" name=\"MINWON_NAME\"></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td>월차수당</td>\r\n");
      out.write("\t\t\t\t<td><input type=\"text\" class=\"form-control\" id=\"MINWON_NAME\" name=\"MINWON_NAME\"></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td>교통비 등(비과세)</td>\r\n");
      out.write("\t\t\t\t<td><input type=\"text\" class=\"form-control\" id=\"MINWON_NAME\" name=\"MINWON_NAME\"></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td>기타금액</td>\r\n");
      out.write("\t\t\t\t<td><input type=\"text\" class=\"form-control\" id=\"MINWON_NAME\" name=\"MINWON_NAME\"></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td>기타 금액 내역</td>\r\n");
      out.write("\t\t\t\t<td><input type=\"text\" class=\"form-control\" id=\"MINWON_NAME\" name=\"MINWON_NAME\"></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t4대보험 정보 기입\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td>공제총액</td>\r\n");
      out.write("\t\t\t\t<td><input type=\"text\" class=\"form-control\" id=\"MINWON_NAME\" name=\"MINWON_NAME\"></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td>건강보험</td>\r\n");
      out.write("\t\t\t\t<td><input type=\"text\" class=\"form-control\" id=\"MINWON_NAME\" name=\"MINWON_NAME\"></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td>장기요양보험</td>\r\n");
      out.write("\t\t\t\t<td><input type=\"text\" class=\"form-control\" id=\"MINWON_NAME\" name=\"MINWON_NAME\"></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td>고용보험</td>\r\n");
      out.write("\t\t\t\t<td><input type=\"text\" class=\"form-control\" id=\"MINWON_NAME\" name=\"MINWON_NAME\"></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td>보험외공제</td>\r\n");
      out.write("\t\t\t\t<td><input type=\"text\" class=\"form-control\" id=\"MINWON_NAME\" name=\"MINWON_NAME\"></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td>소득세</td>\r\n");
      out.write("\t\t\t\t<td><input type=\"text\" class=\"form-control\" id=\"MINWON_NAME\" name=\"MINWON_NAME\"></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td>주민세</td>\r\n");
      out.write("\t\t\t\t<td><input type=\"text\" class=\"form-control\" id=\"MINWON_NAME\" name=\"MINWON_NAME\"></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t<tr align=\"right\" >\r\n");
      out.write("\t\t\t\t<td colspan=\"2\">\r\n");
      out.write("\t\t\t\t\t<a href=\"#this\" class=\"btn btn-info\" id=\"insert\">등록</a>\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t</table>\r\n");
      out.write("\t</form>\r\n");
      out.write("\r\n");
      out.write("</body>\r\n");
      out.write("</html>");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }

  private boolean _jspx_meth_c_005furl_005f0(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  c:url
    org.apache.taglibs.standard.tag.rt.core.UrlTag _jspx_th_c_005furl_005f0 = (org.apache.taglibs.standard.tag.rt.core.UrlTag) _005fjspx_005ftagPool_005fc_005furl_0026_005fvalue_005fnobody.get(org.apache.taglibs.standard.tag.rt.core.UrlTag.class);
    _jspx_th_c_005furl_005f0.setPageContext(_jspx_page_context);
    _jspx_th_c_005furl_005f0.setParent(null);
    // /WEB-INF/include/include-header.jspf(64,36) name = value type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005furl_005f0.setValue("/js/jquery.min.js");
    int _jspx_eval_c_005furl_005f0 = _jspx_th_c_005furl_005f0.doStartTag();
    if (_jspx_th_c_005furl_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005furl_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005furl_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005furl_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005furl_005f0);
    return false;
  }

  private boolean _jspx_meth_c_005furl_005f1(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  c:url
    org.apache.taglibs.standard.tag.rt.core.UrlTag _jspx_th_c_005furl_005f1 = (org.apache.taglibs.standard.tag.rt.core.UrlTag) _005fjspx_005ftagPool_005fc_005furl_0026_005fvalue_005fnobody.get(org.apache.taglibs.standard.tag.rt.core.UrlTag.class);
    _jspx_th_c_005furl_005f1.setPageContext(_jspx_page_context);
    _jspx_th_c_005furl_005f1.setParent(null);
    // /WEB-INF/include/include-header.jspf(65,36) name = value type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005furl_005f1.setValue("/js/jquery-ui.js");
    int _jspx_eval_c_005furl_005f1 = _jspx_th_c_005furl_005f1.doStartTag();
    if (_jspx_th_c_005furl_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005furl_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005furl_005f1);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005furl_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005furl_005f1);
    return false;
  }

  private boolean _jspx_meth_c_005furl_005f2(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  c:url
    org.apache.taglibs.standard.tag.rt.core.UrlTag _jspx_th_c_005furl_005f2 = (org.apache.taglibs.standard.tag.rt.core.UrlTag) _005fjspx_005ftagPool_005fc_005furl_0026_005fvalue_005fnobody.get(org.apache.taglibs.standard.tag.rt.core.UrlTag.class);
    _jspx_th_c_005furl_005f2.setPageContext(_jspx_page_context);
    _jspx_th_c_005furl_005f2.setParent(null);
    // /WEB-INF/include/include-header.jspf(66,36) name = value type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005furl_005f2.setValue("/js/jquery.cookie.js");
    int _jspx_eval_c_005furl_005f2 = _jspx_th_c_005furl_005f2.doStartTag();
    if (_jspx_th_c_005furl_005f2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005furl_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005furl_005f2);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005furl_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005furl_005f2);
    return false;
  }

  private boolean _jspx_meth_c_005furl_005f3(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  c:url
    org.apache.taglibs.standard.tag.rt.core.UrlTag _jspx_th_c_005furl_005f3 = (org.apache.taglibs.standard.tag.rt.core.UrlTag) _005fjspx_005ftagPool_005fc_005furl_0026_005fvalue_005fnobody.get(org.apache.taglibs.standard.tag.rt.core.UrlTag.class);
    _jspx_th_c_005furl_005f3.setPageContext(_jspx_page_context);
    _jspx_th_c_005furl_005f3.setParent(null);
    // /WEB-INF/include/include-header.jspf(67,36) name = value type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005furl_005f3.setValue("/js/jquery.form.js");
    int _jspx_eval_c_005furl_005f3 = _jspx_th_c_005furl_005f3.doStartTag();
    if (_jspx_th_c_005furl_005f3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005furl_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005furl_005f3);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005furl_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005furl_005f3);
    return false;
  }

  private boolean _jspx_meth_c_005furl_005f4(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  c:url
    org.apache.taglibs.standard.tag.rt.core.UrlTag _jspx_th_c_005furl_005f4 = (org.apache.taglibs.standard.tag.rt.core.UrlTag) _005fjspx_005ftagPool_005fc_005furl_0026_005fvalue_005fnobody.get(org.apache.taglibs.standard.tag.rt.core.UrlTag.class);
    _jspx_th_c_005furl_005f4.setPageContext(_jspx_page_context);
    _jspx_th_c_005furl_005f4.setParent(null);
    // /WEB-INF/include/include-header.jspf(68,13) name = value type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005furl_005f4.setValue("/js/common.js");
    int _jspx_eval_c_005furl_005f4 = _jspx_th_c_005furl_005f4.doStartTag();
    if (_jspx_th_c_005furl_005f4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005furl_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005furl_005f4);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005furl_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005furl_005f4);
    return false;
  }

  private boolean _jspx_meth_c_005furl_005f5(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  c:url
    org.apache.taglibs.standard.tag.rt.core.UrlTag _jspx_th_c_005furl_005f5 = (org.apache.taglibs.standard.tag.rt.core.UrlTag) _005fjspx_005ftagPool_005fc_005furl_0026_005fvalue_005fnobody.get(org.apache.taglibs.standard.tag.rt.core.UrlTag.class);
    _jspx_th_c_005furl_005f5.setPageContext(_jspx_page_context);
    _jspx_th_c_005furl_005f5.setParent(null);
    // /WEB-INF/jsp/write/paymentWrite.jsp(75,21) name = value type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005furl_005f5.setValue("/write/insertConsultWrite.do");
    int _jspx_eval_c_005furl_005f5 = _jspx_th_c_005furl_005f5.doStartTag();
    if (_jspx_th_c_005furl_005f5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005furl_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005furl_005f5);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005furl_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005furl_005f5);
    return false;
  }
}
