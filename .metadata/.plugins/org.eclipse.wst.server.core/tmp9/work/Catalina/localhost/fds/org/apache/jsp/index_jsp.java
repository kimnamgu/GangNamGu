/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/8.0.24
 * Generated at: 2020-07-27 02:19:15 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(6);
    _jspx_dependants.put("/WEB-INF/include/include-body.jspf", Long.valueOf(1433227700000L));
    _jspx_dependants.put("jar:file:/C:/dev/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp9/wtpwebapps/fds/WEB-INF/lib/jstl-1.2.jar!/META-INF/fmt.tld", Long.valueOf(1153352682000L));
    _jspx_dependants.put("/WEB-INF/lib/jstl-1.2.jar", Long.valueOf(1595463255549L));
    _jspx_dependants.put("jar:file:/C:/dev/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp9/wtpwebapps/fds/WEB-INF/lib/jstl-1.2.jar!/META-INF/c.tld", Long.valueOf(1153352682000L));
    _jspx_dependants.put("jar:file:/C:/dev/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp9/wtpwebapps/fds/WEB-INF/lib/jstl-1.2.jar!/META-INF/fn.tld", Long.valueOf(1153352682000L));
    _jspx_dependants.put("/WEB-INF/include/include-header.jspf", Long.valueOf(1505457526000L));
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
      out.write("<title>확정일자 수기대장 열람시스템</title>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write(" \r\n");
      out.write("\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"/fds/css/common.css\" />\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"/fds/css/skin01.css\" />\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"/fds/css/import.css\" />\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"/fds/css/fds.css\" />\r\n");
      out.write("\r\n");
      out.write("<!-- jQuery -->\r\n");
      out.write("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js\"></script>\r\n");
      out.write("<script src=\"http://code.jquery.com/ui/1.11.2/jquery-ui.js\"></script>\r\n");
      out.write("<script src=\"/fds/js/calendar.js\"></script>\r\n");
      out.write("<script src=\"");
      if (_jspx_meth_c_005furl_005f0(_jspx_page_context))
        return;
      out.write("\" charset=\"utf-8\"></script>\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("\t\tvar gfv_count = 1;\r\n");
      out.write("\t\r\n");
      out.write("\t\t$(document).ready(function(){\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\tif(NvlStr($(\"#userid\").val()) != \"\")\r\n");
      out.write("\t\t\t{\t\r\n");
      out.write("\t\t\t\tdocument.location.href = \"/fds/daejang/fixedDateList.do\";\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t$(\"#login\").on(\"click\", function(e){ //목록으로 버튼\r\n");
      out.write("\t\t\t\te.preventDefault();\r\n");
      out.write("\t\t\t\tfn_login();\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t    $(\"input\").keydown(function (event) {\r\n");
      out.write("\t                if (event.which == 13) {    //enter\r\n");
      out.write("\t                \tfn_login();\r\n");
      out.write("\t                }\r\n");
      out.write("\t        });\r\n");
      out.write("\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t});\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tfunction fn_login(){\r\n");
      out.write("\t\t\tvar comSubmit = new ComSubmit(\"form1\");\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\tcomSubmit.setUrl(\"");
      if (_jspx_meth_c_005furl_005f1(_jspx_page_context))
        return;
      out.write("\");\r\n");
      out.write("\t\t\tcomSubmit.submit();\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t\r\n");
      out.write("\t</script>\r\n");
      out.write("</head>\r\n");
      out.write("\r\n");
      out.write("<body>\r\n");
      out.write("\r\n");
      out.write("<form id=\"form1\" name=\"form1\" method=\"post\">\r\n");
      out.write("<input type=\"hidden\" id=\"userid\" name=\"userid\" value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${sessionScope.userinfo.userId}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("\">\r\n");
      out.write("<table  border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"bodyframe_fixed\">\r\n");
      out.write("  <tr>\r\n");
      out.write("    <td class=\"pupup_frame\" style=\"padding-right:12px\">\r\n");
      out.write("\r\n");
      out.write("\t\t<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"btn\" id=\"tblAddMemoTempSave21\" style=\"filter:none;\">\r\n");
      out.write("\t     <tr>\r\n");
      out.write("\t        <td><!-- <img src=\"/fds/images/main/main_logo.jpg\"> --></td>\r\n");
      out.write("\t        <td width=\"30\">&nbsp;</td>\t        \r\n");
      out.write("\t      </tr>\r\n");
      out.write("\t     </table>\r\n");
      out.write("\t     \r\n");
      out.write("\t     <table  border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"bodyframe_full\">\r\n");
      out.write("\t\t  <tr>\r\n");
      out.write("\t\t    <td class=\"bodyframe\">\r\n");
      out.write("\t\t    <!--페이지 타이틀 시작 -->\r\n");
      out.write("\t\t    <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("\t\t      <tr>\r\n");
      out.write("\t\t        <td class=\"pupup_title\"><img src=\"/fds/images/popup_title.gif\" align=\"absmiddle\"><b>확정일자 수기대장 열람시스템</b></td>\r\n");
      out.write("\t\t      </tr>\r\n");
      out.write("\t\t      <tr>\r\n");
      out.write("\t\t        <td class=\"margin_title\"></td>\r\n");
      out.write("\t\t      </tr>\r\n");
      out.write("\t\t    </table>\r\n");
      out.write("\t\t    <!--페이지 타이틀 끝 -->\r\n");
      out.write("\t\t    </td>\r\n");
      out.write("\t\t  </tr>\r\n");
      out.write("\t\t\r\n");
      out.write("        <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("          <tr>\r\n");
      out.write("            <td class=\"tbl_line_s\"></td>\r\n");
      out.write("          </tr>\r\n");
      out.write("        </table>\r\n");
      out.write("        <!--리스트 시작 -->\r\n");
      out.write("\t      <table width=\"100%\" border=\"0\" cellspacing=\"1\" cellpadding=\"0\" class=\"tbl\" id=\"divMemoMainBody\" style=\"display:block;\">\r\n");
      out.write("\t          <tr>\r\n");
      out.write("\t            <td width=\"120\" class=\"tbl_field\">아이디<font class=\"font_star\">*</font></td>\r\n");
      out.write("\t            <td colspan=\"3\" width=\"607\" class=\"tbl_list_left\">\r\n");
      out.write("\t                <input type=\"text\" id=\"USER_ID\" name=\"USER_ID\" class=\"input\" style=\"width:150;\">\t               \r\n");
      out.write("\t            </td>\r\n");
      out.write("\t          </tr>\r\n");
      out.write("\t\t\t  <tr>\r\n");
      out.write("\t            <td width=\"120\" class=\"tbl_field\">패스워드 <font class=\"font_star\">*</font></td>\r\n");
      out.write("\t            <td colspan=\"3\" width=\"607\" class=\"tbl_list_left\">\r\n");
      out.write("\t             \t<input type=\"password\" id=\"PASS_WD\" name=\"PASS_WD\" class=\"input\" style=\"width:150;\">\t                \r\n");
      out.write("\t            </td>\r\n");
      out.write("\t          </tr>\r\n");
      out.write("\t\t\t \r\n");
      out.write("\t      </table>\r\n");
      out.write("\t      \r\n");
      out.write("        <!-- -------------- 버튼 시작 --------------  -->\r\n");
      out.write("        <table width=\"100%\" height=\"40\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("          <tr>\r\n");
      out.write("            <td></td>\r\n");
      out.write("            <td align=\"right\">\r\n");
      out.write("            <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("              <tr>\r\n");
      out.write("                <td>\r\n");
      out.write("                <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"btn\" id=\"tblAddMemo21\" style=\"filter:none;\">\r\n");
      out.write("                  <tr>\r\n");
      out.write("                    <td><img src=\"/fds/images/btn_type0_head.gif\"></td>\r\n");
      out.write("                    <td background=\"/fds/images/btn_type0_bg.gif\" class=\"btn_type1\" nowrap><a href=\"#this\" class=\"btn\" id=\"login\"><font color=\"white\">로그인</font></a></td>\r\n");
      out.write("                    <td><img src=\"/fds/images/btn_type0_end.gif\"></td>\r\n");
      out.write("                  </tr>\r\n");
      out.write("                </table>                \r\n");
      out.write("                </td>\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("               \r\n");
      out.write("              </tr>\r\n");
      out.write("            </table>\r\n");
      out.write("            </td>\r\n");
      out.write("          </tr>\r\n");
      out.write("          <tr>\r\n");
      out.write("            <td class=\"margin_btn\" colspan=\"2\"></td>\r\n");
      out.write("          </tr>\r\n");
      out.write("        </table>\r\n");
      out.write("        <!-- -------------- 버튼 끝 --------------  -->\r\n");
      out.write("    </td>    \r\n");
      out.write("  </tr>\r\n");
      out.write("</table>\r\n");
      out.write("</form>\r\n");
      out.write("\r\n");
      out.write("<form id=\"commonForm\" name=\"commonForm\"></form>");
      out.write("\r\n");
      out.write("</body>\r\n");
      out.write("</html>\r\n");
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
    // /WEB-INF/include/include-header.jspf(14,13) name = value type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005furl_005f0.setValue("/js/common.js");
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
    // /index.jsp(38,21) name = value type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005furl_005f1.setValue("/common/login.do");
    int _jspx_eval_c_005furl_005f1 = _jspx_th_c_005furl_005f1.doStartTag();
    if (_jspx_th_c_005furl_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005furl_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005furl_005f1);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005furl_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005furl_005f1);
    return false;
  }
}
