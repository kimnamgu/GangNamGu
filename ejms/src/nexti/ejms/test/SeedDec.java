/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: Seed ���ڵ�
 * ����:
 */
package nexti.ejms.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import nexti.ejms.agent.AgentUtil;

public class SeedDec {
	public static void main(String[] args) throws IOException
    {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter String to decode: ");
    	System.out.println(AgentUtil.decryptSeed(bf.readLine()));
    }
}
