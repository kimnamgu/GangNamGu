/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/8.0.24
 * Generated at: 2020-11-06 04:05:33 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF.jsp.manage;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class noticeMng_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(6);
    _jspx_dependants.put("jar:file:/C:/dev/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp13/wtpwebapps/manpower/WEB-INF/lib/jstl-1.2.jar!/META-INF/fn.tld", Long.valueOf(1153352682000L));
    _jspx_dependants.put("/WEB-INF/include/include-body.jspf", Long.valueOf(1561017636000L));
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
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody;

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
    _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fc_005furl_0026_005fvalue_005fnobody.release();
    _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.release();
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
      out.write("\t$(document).ready(function() {\r\n");
      out.write("\t\tfn_selectNoticeList(1);\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t$(\"#TITLE\").keydown(function (key) {\r\n");
      out.write("\r\n");
      out.write("\t\t\tif(key.keyCode == 13){//키가 13이면 실행 (엔터는 13)\r\n");
      out.write("\t\t\t\t$(\"#PAGE_INDEX\").val(\"1\");\r\n");
      out.write("\t\t\t\tfn_selectNoticetList(1);\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t});\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t$(\"#NAME\").keydown(function (key) {\r\n");
      out.write("\r\n");
      out.write("\t\t\tif(key.keyCode == 13){//키가 13이면 실행 (엔터는 13)\r\n");
      out.write("\t\t\t\t$(\"#PAGE_INDEX\").val(\"1\");\r\n");
      out.write("\t\t\t\tfn_selectNoticetList(1);\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t});\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t$(\"#search\").on(\"click\", function(e){ //검색 버튼\r\n");
      out.write("\t\t\te.preventDefault();\r\n");
      out.write("\t\t\tfn_selectNoticeList(1);\r\n");
      out.write("\t\t});\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t$(\"#initial\").on(\"click\", function(e){ //초기화 버튼\r\n");
      out.write("\t\t\te.preventDefault();\r\n");
      out.write("\t\t\t$(\"#form1\").each(function() {\t\t\t\t \r\n");
      out.write("\t\t\t\tfn_initial_setting();\r\n");
      out.write("\t\t    });\r\n");
      out.write("\t\t});\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t//전체 선택시\r\n");
      out.write("\t\t$('input[name=_selected_all_]').on('change', function(){\r\n");
      out.write("\t\t\t$('input[name=_selected_]').prop('checked', this.checked); \r\n");
      out.write("\t\t}); \r\n");
      out.write("\t\t\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t$(\"#delete\").on(\"click\", function(e){ //초기화 버튼\r\n");
      out.write("\t\t\te.preventDefault();\r\n");
      out.write("\t\t\tdelChkRow();\r\n");
      out.write("\t\t});\r\n");
      out.write("\t\t\t\r\n");
      out.write("\r\n");
      out.write("\t});\r\n");
      out.write("\t\r\n");
      out.write("\t//선택 로우 삭제\r\n");
      out.write("    function delChkRow() {\r\n");
      out.write("\t\t\r\n");
      out.write("    \tvar arr = $('input[name=_selected_]:checked').serializeArray().map(function(item) { return item.value });\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tvar checkedValue = [];\r\n");
      out.write("\t\t$('input[name=_selected_]:checked').each(function(index, item){\r\n");
      out.write("\t\t\tcheckedValue.push($(item).val());   \r\n");
      out.write("\t\t});\r\n");
      out.write("   \t\r\n");
      out.write("\r\n");
      out.write("\t\tif (arr.length ==0 ) {\r\n");
      out.write("\t\t\talert(\"선택된 로우가 없습니다.\");\r\n");
      out.write("\t\t\treturn false;\r\n");
      out.write("\t\t} \r\n");
      out.write("\t\t\r\n");
      out.write("\t\tif (confirm(\"선택 항목을 삭제 하시겠습니까?\")) {\r\n");
      out.write("\r\n");
      out.write("\t\t\t//견적서 파일 삭제\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t$.ajax({\r\n");
      out.write("\t\t        type: \"post\",\r\n");
      out.write("\t\t        url : \"/manpower/manage/deleteNoticeChk.do\",\r\n");
      out.write("\t\t        data: {\r\n");
      out.write("\t\t        \tval:checkedValue\r\n");
      out.write("\t\t        },\r\n");
      out.write("\t\t        contentType : \"application/x-www-form-urlencoded; charset=utf-8\",\r\n");
      out.write("\r\n");
      out.write("\t\t        success:function (data) {//추천성공\r\n");
      out.write("\t\t        \talert(\"삭제가 완료되었습니다.\");\r\n");
      out.write("\t\t        \tfn_selectNoticeList(1);\r\n");
      out.write("\t            },\r\n");
      out.write("\r\n");
      out.write("\t\t        error: function (data) {//추천실패\r\n");
      out.write("\t\t        \talert(\"삭제가 실패 하였습니다.관리자에게 문의 바랍니다.\");\r\n");
      out.write("\t            }\r\n");
      out.write("\t     \t});\r\n");
      out.write("\r\n");
      out.write("\t\t} \r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\t//수정화면가기\r\n");
      out.write("\tfunction fn_updateNotice(UID) {\r\n");
      out.write("\t\tvar comSubmit = new ComSubmit();\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tcomSubmit.setUrl(\"");
      if (_jspx_meth_c_005furl_005f5(_jspx_page_context))
        return;
      out.write("\");\r\n");
      out.write("\t\tcomSubmit.addParam(\"ID\", UID);\r\n");
      out.write("\t\tcomSubmit.submit();\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tfunction fn_selectNoticeList(pageNo){\r\n");
      out.write("\t\tvar comAjax = new ComAjax();\r\n");
      out.write("\t\tcomAjax.setUrl(\"");
      if (_jspx_meth_c_005furl_005f6(_jspx_page_context))
        return;
      out.write("\");\r\n");
      out.write("\t\tcomAjax.addParam(\"PAGE_INDEX\",pageNo);\r\n");
      out.write("\t\tcomAjax.addParam(\"PAGE_ROW\", $(\"#RECORD_COUNT\").val());\t\t\t\r\n");
      out.write("\t\tcomAjax.addParam(\"name\", $(\"#NAME\").val());\r\n");
      out.write("\t\tcomAjax.addParam(\"title\", $(\"#TITLE\").val());\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tcomAjax.setCallback(\"fn_selectNoticeListCallback\");\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tcomAjax.ajax();\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\t\r\n");
      out.write("\tfunction fn_selectNoticeListCallback(data){\r\n");
      out.write("\t\tvar total = data.total;\t\t\t\r\n");
      out.write("\t\tvar body = $(\"#mytable\");\r\n");
      out.write("\t\tvar recordcnt = $(\"#RECORD_COUNT\").val();\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t$(\"#tcnt\").text(comma(total));\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tbody.empty();\r\n");
      out.write("\t\tif(total == 0){\r\n");
      out.write("\t\t\tvar str = \"<tr>\" + \r\n");
      out.write("\t\t\t\t  \"<td colspan='32' style='text-align:center;'>조회된 결과가 없습니다.</td>\" +\r\n");
      out.write("\t\t\t\t  \"</tr>\";\r\n");
      out.write("\t\t\tbody.append(str);\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\telse{\r\n");
      out.write("\t\t\tvar params = {\r\n");
      out.write("\t\t\t\tdivId : \"PAGE_NAVI\",\r\n");
      out.write("\t\t\t\tpageIndex : \"PAGE_INDEX\",\r\n");
      out.write("\t\t\t\ttotalCount : total,\r\n");
      out.write("\t\t\t\trecordCount : recordcnt,\r\n");
      out.write("\t\t\t\teventName : \"fn_selectNoticeList\"\r\n");
      out.write("\t\t\t};\r\n");
      out.write("\t\t\tgfn_renderPaging(params);\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\tvar str = \"\";\r\n");
      out.write("\t\t\tvar t_str = \"\";\r\n");
      out.write("\t\t\tvar tr_class = \"\";\r\n");
      out.write("\t\t\tvar jsonData = null;\r\n");
      out.write("\t\t\tvar jsonStr = null;\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t$.each(data.list, function(key, value){\r\n");
      out.write("\t\t\t\t       t_str = \"\";\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\t\t\t       if ((value.RNUM % 2) == 0 )\r\n");
      out.write("\t\t\t\t\t\t\ttr_class = \"tr1\";\r\n");
      out.write("\t\t\t\t\t   else\r\n");
      out.write("\t\t\t\t\t\t\ttr_class = \"tr2\";\r\n");
      out.write("\t\t\t\t      \r\n");
      out.write("\t\t\t\t       \r\n");
      out.write("\t\t\t\t       str += \"<tr class='\" + tr_class + \"'>\" + \r\n");
      out.write("\t\t\t\t       \t\t\t   \"<td><input type='checkbox' name='_selected_' value='\"+value.ID+\"'></td>\" +\r\n");
      out.write("\t\t\t\t\t\t\t\t   \"<td align='center' style='color:#444444;'>\" + value.RNUM + \"</td>\" +\r\n");
      out.write("\t\t\t\t\t\t\t\t   \"<td align='center' style='color:blue;'>\" +\r\n");
      out.write("\t\t\t\t\t\t\t\t   \"\t<a href='javascript:void(0);' onclick='fn_updateNotice(\"+ value.ID +\")' name='NAME'>\" + NvlStr(value.TITLE) + \"</a>\"+\r\n");
      out.write("\t\t\t\t\t\t\t\t   \"</td>\" +\r\n");
      out.write("\t\t\t\t\t\t\t\t   \"<td align='center' style='color:#444444;'>\" + NvlStr(value.NAME) + \"</td>\" +\r\n");
      out.write("\t\t\t\t\t\t\t\t   \"<td align='center' style='color:#444444;'>\" + NvlStr(value.INS_DATE) + \"</td>\" +\r\n");
      out.write("\t\t\t\t\t\t\t\t   \"<td align='center' style='color:#444444;'>\" + NvlStr(value.READ_COUNT) + \"</td>\" +\r\n");
      out.write("\t\t\t\t\t\t\t   \"</tr>\";\r\n");
      out.write("\t\t\t\t\t\t\t\t   \r\n");
      out.write("\t\t\t});\r\n");
      out.write("\t\t\tbody.append(str);\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\t\r\n");
      out.write("\t\r\n");
      out.write("</script>\r\n");
      out.write("</head>\r\n");
      out.write("\r\n");
      out.write("<body>\r\n");
      out.write("\r\n");
      out.write("<form id=\"form1\" name=\"form1\">\r\n");
      out.write("<input type=\"hidden\" id=\"USER_ID\" name=\"USER_ID\" value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${sessionScope.userinfo.userId}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("\">\r\n");
      if (_jspx_meth_c_005fset_005f0(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<div style=\"background-color:#170B3B;color:white; margin-bottom:10px; width: 100%;\">\r\n");
      out.write("\t<a href=\"#this\" class=\"btn\" id=\"logout\">\r\n");
      out.write("\t\t<img src=\"/manpower/images/popup_title.gif\" align=\"absmiddle\">\r\n");
      out.write("\t</a>공지사항\t\r\n");
      out.write("</div>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<div style=\"width:100%;padding-left:20px;padding-right:10px;\">\r\n");
      out.write("\t<div class=\"card\" style=\"width:100%;display: inline-block;padding-top:10px;\">\r\n");
      out.write("\t\t<form action=\"\" method=\"post\">\r\n");
      out.write("\t\t\t<div class=\"card-body card-block\">\r\n");
      out.write("\t\t\t\t<div>\r\n");
      out.write("\t\t\t\t\t<div class=\"form-group\" style=\"width: 360;float:left;margin-left:5px;\">\r\n");
      out.write("\t\t\t\t\t\t<div class=\"input-group\">\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"input-group-addon\" style=\"width: 80px;\">제목</div>\r\n");
      out.write("\t\t\t\t\t\t\t<input type=\"text\" id=\"TITLE\" name=\"TITLE\" value=\"\" class=\"form-control\">\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\r\n");
      out.write("\t\t\t\t\t<div class=\"form-group\" style=\"width: 360;float:left;margin-left:5px;\">\r\n");
      out.write("\t\t\t\t\t\t<div class=\"input-group\">\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"input-group-addon\" style=\"width: 80px;\">작성자</div>\r\n");
      out.write("\t\t\t\t\t\t\t<input type=\"text\" id=\"NAME\" name=\"NAME\" value=\"\" class=\"form-control\">\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t\t<div class=\"form-actions form-group\">\r\n");
      out.write("\t\t\t\t<div style=\"float: left;margin-left:15px;\">\r\n");
      out.write("\t\t\t\t\t<a href=\"#this\" class=\"btn btn-danger\" id=\"initial\"><i class=\"fa fa-ban\"></i> 검색초기화</a> \r\n");
      out.write("\t\t\t\t\t<a href=\"#this\" class=\"btn btn-primary\" id=\"search\"><i class=\"fa fa-search\"></i>검색</a>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</form>\r\n");
      out.write("\t</div>\r\n");
      out.write("</div>\r\n");
      out.write("\t\t\r\n");
      out.write("\t<div style=\"float: left;margin-left:20px;\">\r\n");
      out.write("\t\t<div style=\"float: left;\">\r\n");
      out.write("\t\t\t<b>Total : <span id=\"tcnt\"></span> 건</b> &nbsp;\r\n");
      out.write("\t\t</div> \r\n");
      out.write("\t\t<div style=\"float: left;\">\r\n");
      out.write("\t\t\t<select id=\"DISP_CNT\" name=\"DISP_CNT\" class=\"form-control\" style=\"width:60px!important;height:30px;padding:3px;\">\r\n");
      out.write("\t           \t<option value=\"10\">10</option>\r\n");
      out.write("\t           \t<option value=\"20\">20</option>\r\n");
      out.write("\t\t\t\t<option value=\"50\">50</option>\r\n");
      out.write("\t\t\t</select>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("\r\n");
      out.write("</form>\r\n");
      out.write("\r\n");
      out.write("<div class=\"contents\" style=\"float:right;margin-right:22px;margin-bottom:10px;\">\r\n");
      out.write("\t<a href=\"/manpower/write/noticeWrite.do\" class=\"btn btn-primary btn-sm\" id=\"insert\">등록</a>\r\n");
      out.write("\t<a href=\"#this\" class=\"btn btn-primary btn-sm\" id=\"delete\"><i class=\"fa fa-eraser\"></i> 선택삭제</a>\r\n");
      out.write("</div>\r\n");
      out.write("<div align=\"left\" style=\"width:100%;padding-right:20px;\">\r\n");
      out.write("\t<div  style=\"margin-left:20px;\" align=\"center\">\r\n");
      out.write("\t\t<div class=\"table-responsive m-b-40\">\r\n");
      out.write("\t\t\t <table class=\"table table-borderless table-data3\" id=\"selectable\">\r\n");
      out.write("\t\t\t     <thead style=\"background-color:#0B2161;\">\r\n");
      out.write("\t\t\t        <tr align=\"center\">\r\n");
      out.write("\t\t\t        \t\t<th style=\"width: 40px;\"><input type=\"checkbox\" name=\"_selected_all_\"></th>\r\n");
      out.write("\t\t\t\t\t\t\t<th style=\"width: 80px;\">NO</th>\r\n");
      out.write("\t\t\t\t\t\t\t<th>제목</th>\r\n");
      out.write("\t\t\t\t\t\t\t<th style=\"width: 150px;\">작성자</th>\r\n");
      out.write("\t\t\t\t\t\t\t<th style=\"width: 200px;\">작성일</th>\r\n");
      out.write("\t\t\t\t\t\t\t<th style=\"width: 150px;\">조회수</th>\r\n");
      out.write("\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t      </thead>\r\n");
      out.write("\t\t\t      <tbody id='mytable'>     \r\n");
      out.write("\t\t\t      </tbody>\r\n");
      out.write("\t\t      </table>\r\n");
      out.write("\t      </div>\r\n");
      out.write("\t</div>\r\n");
      out.write("\r\n");
      out.write("\t<div id=\"PAGE_NAVI\" align='center'></div>\r\n");
      out.write("\t<input type=\"hidden\" id=\"PAGE_INDEX\" name=\"PAGE_INDEX\"/>\r\n");
      out.write("\t<input type=\"hidden\" id=\"RECORD_COUNT\" name=\"RECORD_COUNT\" value=\"20\"/>\t\r\n");
      out.write("\r\n");
      out.write("</div>\t\r\n");
      out.write("\r\n");
      out.write("\t");
      out.write("\r\n");
      out.write("<form id=\"commonForm\" name=\"commonForm\"></form>");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
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
    // /WEB-INF/jsp/manage/noticeMng.jsp(100,20) name = value type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005furl_005f5.setValue("/write/updateNoticeView.do");
    int _jspx_eval_c_005furl_005f5 = _jspx_th_c_005furl_005f5.doStartTag();
    if (_jspx_th_c_005furl_005f5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005furl_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005furl_005f5);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005furl_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005furl_005f5);
    return false;
  }

  private boolean _jspx_meth_c_005furl_005f6(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  c:url
    org.apache.taglibs.standard.tag.rt.core.UrlTag _jspx_th_c_005furl_005f6 = (org.apache.taglibs.standard.tag.rt.core.UrlTag) _005fjspx_005ftagPool_005fc_005furl_0026_005fvalue_005fnobody.get(org.apache.taglibs.standard.tag.rt.core.UrlTag.class);
    _jspx_th_c_005furl_005f6.setPageContext(_jspx_page_context);
    _jspx_th_c_005furl_005f6.setParent(null);
    // /WEB-INF/jsp/manage/noticeMng.jsp(107,18) name = value type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005furl_005f6.setValue("/manage/selectNoticeList.do");
    int _jspx_eval_c_005furl_005f6 = _jspx_th_c_005furl_005f6.doStartTag();
    if (_jspx_th_c_005furl_005f6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005furl_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005furl_005f6);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005furl_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005furl_005f6);
    return false;
  }

  private boolean _jspx_meth_c_005fset_005f0(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  c:set
    org.apache.taglibs.standard.tag.rt.core.SetTag _jspx_th_c_005fset_005f0 = (org.apache.taglibs.standard.tag.rt.core.SetTag) _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(org.apache.taglibs.standard.tag.rt.core.SetTag.class);
    _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
    _jspx_th_c_005fset_005f0.setParent(null);
    // /WEB-INF/jsp/manage/noticeMng.jsp(182,0) name = var type = java.lang.String reqTime = false required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fset_005f0.setVar("u_rt");
    // /WEB-INF/jsp/manage/noticeMng.jsp(182,0) name = value type = javax.el.ValueExpression reqTime = true required = false fragment = false deferredValue = true expectedTypeName = java.lang.Object deferredMethod = false methodSignature = null
    _jspx_th_c_005fset_005f0.setValue(new org.apache.jasper.el.JspValueExpression("/WEB-INF/jsp/manage/noticeMng.jsp(182,0) '${sessionScope.userinfo.userright}'",_el_expressionfactory.createValueExpression(_jspx_page_context.getELContext(),"${sessionScope.userinfo.userright}",java.lang.Object.class)).getValue(_jspx_page_context.getELContext()));
    int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
    if (_jspx_th_c_005fset_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f0);
    return false;
  }
}
