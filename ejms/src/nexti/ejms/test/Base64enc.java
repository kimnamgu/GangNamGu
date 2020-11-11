/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: Base64 ���ڵ�
 * ����:
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
