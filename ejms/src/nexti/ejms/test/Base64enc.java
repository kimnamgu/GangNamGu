/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: Base64 인코드
 * 설명:
 */
package nexti.ejms.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import nexti.ejms.util.Base64;

public class Base64enc {
	public static void main(String[] args) throws IOException
    {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter String to encode: ");
        byte[] arrByte = bf.readLine().getBytes();
        System.out.println(Base64.encodeBytes(arrByte, Base64.DONT_BREAK_LINES));
        System.out.println(Base64.encodeBytes(arrByte, Base64.GZIP | Base64.DONT_BREAK_LINES) + "(GZIP)");
    }
}
