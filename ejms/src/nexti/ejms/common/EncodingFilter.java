/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 서블릿인코딩필터
 * 설명:
 */

package nexti.ejms.common;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * ServletRequest객체에 인코딩을 설정하는 Filter클래스.
 * 
 * @web.filter name="Encoding Filter" 
 * 			   display-name="Encoding Filter"
 * 	
 * @web.filter-init-param name="encoding"
 * 						  value="EUC-KR"		    
 * 
 * @web.filter-mapping url-pattern="/*"
 * 					   
 */
public class EncodingFilter implements Filter {
	private String encoding = null;

	protected FilterConfig filterConfig = null;

	public void destroy() {

		this.encoding = null;
		this.filterConfig = null;

	}

	/**
	 * ServletRequest객체에 web.xml에서 전달된 인코딩을 설정하는 메써드.
	 */
	public void doFilter(
		ServletRequest request,
		ServletResponse response,
		FilterChain chain)
		throws IOException, ServletException {
//		encoding="UTF-8";
//		System.out.println("****************IN ENCODING FILTER 0*****************"+encoding);
		request.setCharacterEncoding(encoding);
		//파라미터 확인
//		Enumeration en = request.getParameterNames();
//		System.out.println("****************IN ENCODING FILTER 1*****************");
//		while(en.hasMoreElements()){
//			String key = (String)en.nextElement();
//			System.out.println(key+" : "+request.getParameter(key));
//		}
//		System.out.println("****************IN ENCODING FILTER 2*****************"+request.getCharacterEncoding());
//		if (request.getCharacterEncoding() == null) {
//			if (encoding != null) {
//				System.out.println("****************IN ENCODING FILTER 3*****************"+encoding);
//				request.setCharacterEncoding(encoding);
//				System.out.println("****************IN ENCODING FILTER 4*****************"+request.getCharacterEncoding());
//			}
//		}
//		/***********************************/
//		en = request.getParameterNames();
//		System.out.println("****************IN ENCODING FILTER 3*****************");
//		while(en.hasMoreElements()){
//			String key = (String)en.nextElement();
//			System.out.println(key+" : "+request.getParameter(key));
//		}
//		System.out.println("****************IN ENCODING FILTER 4*****************");
//		/***********************************/

		//		try{
			chain.doFilter(request, response);
//		}catch(Exception e){
//			System.out.println("exception : "+e);
//			for(int i=0;i< e.getStackTrace().length;i++){
//				StackTraceElement el = e.getStackTrace()[i];
//				System.out.println(el.toString());
//			}
//		}
//		System.out.println("****************IN ENCODING FILTER 5*****************");
	}

	/**
	 * web.xml에서 전달된 인코딩 값을 초기화하는 메써드.
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		this.encoding = filterConfig.getInitParameter("encoding");
	}

	public FilterConfig getFilterConfig() {
		return filterConfig;
	}

	public void setFilterConfig(FilterConfig cfg) {
		filterConfig = cfg;
	}
}
