/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/8.0.24
 * Generated at: 2020-09-03 23:03:32 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF.jsp.main;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class visitor_005fmsgOut_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(5);
    _jspx_dependants.put("jar:file:/C:/dev/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp12/wtpwebapps/service/WEB-INF/lib/jstl-1.2.jar!/META-INF/fn.tld", Long.valueOf(1153352682000L));
    _jspx_dependants.put("/WEB-INF/lib/jstl-1.2.jar", Long.valueOf(1595463255549L));
    _jspx_dependants.put("/WEB-INF/include/include-header.jspf", Long.valueOf(1591778872000L));
    _jspx_dependants.put("jar:file:/C:/dev/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp12/wtpwebapps/service/WEB-INF/lib/jstl-1.2.jar!/META-INF/c.tld", Long.valueOf(1153352682000L));
    _jspx_dependants.put("jar:file:/C:/dev/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp12/wtpwebapps/service/WEB-INF/lib/jstl-1.2.jar!/META-INF/fmt.tld", Long.valueOf(1153352682000L));
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
      out.write("<title>주요사업 관리시스템</title>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<!-- \r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"/service/css/common.css\" />\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"/service/css/skin01.css\" />\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"/service/css/import.css\" />\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"/service/css/service.css\" />\r\n");
      out.write(" -->\r\n");
      out.write(" \r\n");
      out.write("<!-- Fontfaces CSS-->\r\n");
      out.write("<link href=\"/service/bootstrap/css/font-face.css\" rel=\"stylesheet\" media=\"all\">\r\n");
      out.write("<link href=\"/service/bootstrap/vendor/font-awesome-4.7/css/font-awesome.min.css\" rel=\"stylesheet\" media=\"all\">\r\n");
      out.write("<link href=\"/service/bootstrap/vendor/font-awesome-5/css/fontawesome-all.min.css\" rel=\"stylesheet\" media=\"all\">\r\n");
      out.write("<link href=\"/service/bootstrap/vendor/mdi-font/css/material-design-iconic-font.min.css\" rel=\"stylesheet\" media=\"all\">\r\n");
      out.write("\r\n");
      out.write("<!-- Bootstrap CSS-->\r\n");
      out.write("<link href=\"/service/bootstrap/vendor/bootstrap-4.1/bootstrap.min.css\" rel=\"stylesheet\" media=\"all\">\r\n");
      out.write("\r\n");
      out.write("<!-- Vendor CSS-->\r\n");
      out.write("<link href=\"/service/bootstrap/vendor/animsition/animsition.min.css\" rel=\"stylesheet\" media=\"all\">\r\n");
      out.write("<link href=\"/service/bootstrap/vendor/bootstrap-progressbar/bootstrap-progressbar-3.3.4.min.css\" rel=\"stylesheet\" media=\"all\">\r\n");
      out.write("<link href=\"/service/bootstrap/vendor/wow/animate.css\" rel=\"stylesheet\" media=\"all\">\r\n");
      out.write("<link href=\"/service/bootstrap/vendor/css-hamburgers/hamburgers.min.css\" rel=\"stylesheet\" media=\"all\">\r\n");
      out.write("<link href=\"/service/bootstrap/vendor/slick/slick.css\" rel=\"stylesheet\" media=\"all\">\r\n");
      out.write("<link href=\"/service/bootstrap/vendor/select2/select2.min.css\" rel=\"stylesheet\" media=\"all\">\r\n");
      out.write("<link href=\"/service/bootstrap/vendor/perfect-scrollbar/perfect-scrollbar.css\" rel=\"stylesheet\" media=\"all\">\r\n");
      out.write("\r\n");
      out.write("<!-- Main CSS-->\r\n");
      out.write("<link href=\"/service/bootstrap/css/theme.css\" rel=\"stylesheet\" media=\"all\">\r\n");
      out.write(" \r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<!-- Jquery JS-->\r\n");
      out.write("<script src=\"/service/bootstrap/vendor/jquery-3.2.1.min.js\"></script>\r\n");
      out.write("<!-- Bootstrap JS-->\r\n");
      out.write("<script src=\"/service/bootstrap/vendor/bootstrap-4.1/popper.min.js\"></script>\r\n");
      out.write("<script src=\"/service/bootstrap/vendor/bootstrap-4.1/bootstrap.min.js\"></script>\r\n");
      out.write("<!-- Vendor JS       -->\r\n");
      out.write("<script src=\"/service/bootstrap/vendor/slick/slick.min.js\">\r\n");
      out.write("</script>\r\n");
      out.write("<script src=\"/service/bootstrap/vendor/wow/wow.min.js\"></script>\r\n");
      out.write("<script src=\"/service/bootstrap/vendor/animsition/animsition.min.js\"></script>\r\n");
      out.write("<script src=\"/service/bootstrap/vendor/bootstrap-progressbar/bootstrap-progressbar.min.js\">\r\n");
      out.write("</script>\r\n");
      out.write("<script src=\"/service/bootstrap/vendor/counter-up/jquery.waypoints.min.js\"></script>\r\n");
      out.write("<script src=\"/service/bootstrap/vendor/counter-up/jquery.counterup.min.js\">\r\n");
      out.write("</script>\r\n");
      out.write("<script src=\"/service/bootstrap/vendor/circle-progress/circle-progress.min.js\"></script>\r\n");
      out.write("<script src=\"/service/bootstrap/vendor/perfect-scrollbar/perfect-scrollbar.js\"></script>\r\n");
      out.write("<script src=\"/service/bootstrap/vendor/chartjs/Chart.bundle.min.js\"></script>\r\n");
      out.write("<script src=\"/service/bootstrap/vendor/select2/select2.min.js\">\r\n");
      out.write("</script>\r\n");
      out.write("\r\n");
      out.write("<!-- Main JS-->\r\n");
      out.write("<script src=\"/service/bootstrap/js/main.js\"></script>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<!-- jQuery -->\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"/service/css/jquery-ui.css\" />\r\n");
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
      out.write("<!-- alert -->\r\n");
      out.write("<script src=\"/service/jquery.modal-master/js/DesignAlert.js\"></script>\r\n");
      out.write("<script src=\"/service/jquery.modal-master/js/jquery.modal.js\"></script>\r\n");
      out.write("<script src=\"/service/jquery.modal-master/js/jquery.modal.min.js\"></script>\r\n");
      out.write("\r\n");
      out.write("<link href=\"/service/jquery.modal-master/css/jquery.modal.css\" rel=\"stylesheet\">\r\n");
      out.write("<link href=\"/service/jquery.modal-master/css/jquery.modal.theme-atlant.css\" rel=\"stylesheet\">\r\n");
      out.write("<link href=\"/service/jquery.modal-master/css/jquery.modal.theme-xenon.css\" rel=\"stylesheet\">\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("\t\tvar flag = \"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${flag}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("\";\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t$(document).ready(function(){\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\tif(flag == '1')\r\n");
      out.write("\t\t\t{\r\n");
      out.write("\t\t\t\t$(\"#msg\").html('사용승인이 안되어 있습니다!!<br><br>관리자에게 연락하여 [사용승인] 받고 이용하시길 바랍니다.(☎ 5452)');\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\telse if(flag == '2'){\r\n");
      out.write("\t\t\t\t$(\"#msg\").html('등록이 완료되었습니다.<br><br>관리자에게 연락하여 [사용승인] 받고  다시 접속하시길 바랍니다.(☎ 5452)');\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\telse if(flag == '3'){\r\n");
      out.write("\t\t\t\t$(\"#msg\").html('아이디가 틀립니다!!<br><br>다시 확인하시고 로그인 하시길 바랍니다.');\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\telse if(flag == '4'){\r\n");
      out.write("\t\t\t\t$(\"#msg\").html('패스워드가 틀립니다!!<br><br>다시 확인하시고 로그인 하시길 바랍니다.');\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\telse if(flag == '5'){\r\n");
      out.write("\t\t\t\t$(\"#msg\").html('해당 시스템은 지정 컴퓨터를 통해서만 접근이 가능합니다.!!<br><br>다시 확인하시고 로그인 하시길 바랍니다.');\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t$(\"#click\").on(\"click\", function(e){ //작성하기 버튼\r\n");
      out.write("\t\t\t\tlocation.href = \"/service/visitor/visitorLogin.do\"\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\t\t});\r\n");
      out.write("\t\t\r\n");
      out.write("\t</script>\r\n");
      out.write("</head>\r\n");
      out.write("\r\n");
      out.write("<body>\r\n");
      out.write("\t<div class=\"page-wrapper\">\r\n");
      out.write("        <div class=\"page-content--bge5\">\r\n");
      out.write("            <div class=\"container\">\r\n");
      out.write("                <div class=\"login-wrap\">\r\n");
      out.write("                    <div class=\"login-content\">\r\n");
      out.write("                        <div class=\"login-logo\">\r\n");
      out.write("\t\t\t\t\t\t\t<img src=\"/service/images/popup_title.gif\" align=\"absmiddle\">결과메세지\r\n");
      out.write("                        </div>\r\n");
      out.write("                        <div class=\"login-form\">\r\n");
      out.write("                        \t<div class=\"form-group\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<img src=\"/service/images/title_ico.gif\" align=\"absmiddle\">메세지\r\n");
      out.write("\t\t\t\t\t\t\t\t<br>\r\n");
      out.write("\t\t\t\t\t\t\t\t<br>\r\n");
      out.write("\t\t\t\t\t\t\t\t<b><span id=\"msg\"></span></b>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t<a href=\"#this\" width=\"100%\" id=\"click\"><button class=\"au-btn au-btn--block au-btn--blue m-b-20\" type=\"button\" >확인</button></a>\r\n");
      out.write("                        </div>\r\n");
      out.write("                    </div>\r\n");
      out.write("                </div>\r\n");
      out.write("            </div>\r\n");
      out.write("        </div>\r\n");
      out.write("    </div>\r\n");
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
}
