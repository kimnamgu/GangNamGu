package nexti.ejms.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import nexti.ejms.common.appInfo;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.util.EncodingUtil;
import org.apache.log4j.Logger;

public class NewHttpClient {
	private static Logger logger = Logger.getLogger(NewHttpClient.class);
	
    private String urlStr = ""; 
    private String content = ""; 
    private String errStat = "";
    private int methodType = 0;
    private List paramList = null;

    public final static int GETTYPE = 0; 
    public final static int POSTTYPE = 1; 
    
    public NewHttpClient(String urlStr){ 
		this.urlStr = urlStr; 
		paramList = new ArrayList(); 
	}

    public String getContent() { 
        return content; 
    }
    
    public int getMethodType() { 
        return methodType; 
    }
    
    public void setMethodType(int methodType) { 
        this.methodType = methodType; 
    }

    public void setParam(String key, String value) { 
        paramList.add(new NameValuePair(key,value)); 
    }
    
    private String getErrStat() {
		return errStat;
	}
    
	private void setErrStat(String errStat) {
		this.errStat = errStat;
	}
    
    public int execute() throws Exception{
    	int timeOut = 1000 * 10;	//1000 = 1ÃÊ
        int iGetResultCode = 0;
        HttpClient client = null;
        HttpMethod method = null;
        NameValuePair[] paramArray = null;

        try {            
        	client = new HttpClient();
        	client.getHttpConnectionManager().getParams().setConnectionTimeout(timeOut);
            client.getHttpConnectionManager().getParams().setSoTimeout(timeOut);
            client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "euc-kr");            

            if(methodType == GETTYPE){ 
                GetMethod getMethod = new GetMethod(urlStr); 
                
                getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, 
                		new DefaultHttpMethodRetryHandler(3, false));
                setParam("serverkey", nexti.ejms.agent.AgentUtil.encryptSeed(appInfo.getServerkey()));
                paramArray = new NameValuePair[paramList.size()]; 
                paramList.toArray(paramArray);
                if(paramArray.length > 0) 
                    getMethod.setQueryString(EncodingUtil.formUrlEncode(paramArray,"euc-kr")); 
                method = getMethod;
            }else if(methodType == POSTTYPE){ 
                PostMethod postMethod = new PostMethod(urlStr);
                paramArray = new NameValuePair[paramList.size()]; 
                paramList.toArray(paramArray);
                for(int k = 0; k < paramArray.length; k++){ 
                    postMethod.addParameter(paramArray[k].getName(), paramArray[k].getValue()); 
                }
                method = postMethod;
            } 
            
            iGetResultCode = client.executeMethod(method);

            if (iGetResultCode != HttpStatus.SC_OK) {
            	setErrStat("Method failed: " + method.getStatusLine());
            	logger.error(getErrStat());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream()));
            StringBuffer buf = new StringBuffer();
            String s = null;
            while (null != (s = br.readLine())) {
              buf.append(s);
            }
            
            content = buf.toString();
     
        } catch ( Exception e ) { 
        	setErrStat("Fatal protocol violation: " + e.getMessage());
        	logger.error(getErrStat());
            iGetResultCode = 0; 
        } finally {
        	try { method.releaseConnection(); } catch ( Exception e ) {}
        }
        return iGetResultCode; 
    }
}