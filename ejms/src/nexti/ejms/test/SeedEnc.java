/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: Seed 인코드
 * 설명:
 */
package nexti.ejms.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import nexti.ejms.agent.AgentUtil;

public class SeedEnc {
	public static void main(String[] args) throws IOException
    {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter String to encode: ");
    	System.out.println(AgentUtil.encryptSeed(bf.readLine()));
    }
}
