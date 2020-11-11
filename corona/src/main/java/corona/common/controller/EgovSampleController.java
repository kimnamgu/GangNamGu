package corona.common.controller;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class EgovSampleController {
	
    @RequestMapping(value="/sample/getAddrApi.do")
    public void getAddrApi(HttpServletRequest req, ModelMap model, HttpServletResponse response) throws Exception {
    	
	    String currentPage = "1";    //??�? �??? ?��?? (???? ???��?. currentPage : n > 0)
		String countPerPage = "1";  //??�? �??? ?��?? (???��??? �??? �???. countPerPage �??? : 0 < n <= 100)
		String resultType = "json";      //??�? �??? ?��?? (�???결과???? ?��??, json)
		String confmKey = "devU01TX0FVVEgyMDIwMDgxNDA4MzU0MzExMDA2NDU=";          //??�? �??? ?��?? (?��?��??)
		String keyword = "신길로";            //??�? �??? ?��?? (?��????)

		String apiUrl = "http://www.juso.go.kr/addrlink/addrLinkApi.do?currentPage="+currentPage+"&countPerPage="+countPerPage+"&keyword="+URLEncoder.encode(keyword,"UTF-8")+"&confmKey="+confmKey+"&resultType="+resultType;
		URL url = new URL(apiUrl);
    	BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(),"UTF-8"));
    	StringBuffer sb = new StringBuffer();
    	String tempStr = null;

    	while(true){
    		tempStr = br.readLine();
    		if(tempStr == null) break;
    		sb.append(tempStr);								// ???�결�? JSON ????
    	}
    	
    	
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(sb.toString());
        JSONObject JSONObject2 = (JSONObject) jsonObject.get("results");
        JSONArray jusoArray = (JSONArray) JSONObject2.get("juso");
        JSONObject jusoObject = (JSONObject) jusoArray.get(0);
        System.out.println("* jusoObject *" + jusoObject.get("emdNm"));
        
    	br.close();
    	response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml");
		response.getWriter().write(sb.toString());			// ???�결�? �???
    }
}
