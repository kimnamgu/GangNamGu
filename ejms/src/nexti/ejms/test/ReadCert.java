/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: GPKI 서버인증서 Base64Encode
 * 설명:
 */
package nexti.ejms.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.gpki.gpkiapi.GpkiApi;
import com.gpki.gpkiapi.cert.X509Certificate; 
import com.gpki.gpkiapi.exception.GpkiApiException;
import com.gpki.gpkiapi.storage.Disk;
import com.gpki.gpkiapi.util.Base64;

public class ReadCert {
	public static void main(String[] args)
    {		
		X509Certificate x509Cert = null;
	    byte[] cert = null;
	    String base64cert = null;
	    try {
			GpkiApi.init("D:/Program/MyEclipse5.1.1GA/Workspace/ejms/WebRoot/WEB-INF/");
			
			System.out.print("서버인증서 : ");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			
	        x509Cert = Disk.readCert(br.readLine());
	        cert = x509Cert.getCert();
	        Base64 base64 = new Base64();
	        base64cert = base64.encode(cert); 
	        System.out.println(base64cert);
	 
	    } catch (GpkiApiException e) {
	        e.printStackTrace();
	    } catch (IOException ex) {
	    	ex.printStackTrace();
	    }
    }
}