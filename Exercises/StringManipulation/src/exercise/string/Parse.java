package exercise.string;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parse {

	public static void main(String[] args) {
		Parse sp = new Parse();
		
		// 1. �������� ���е� ���ڿ��� split�� ������
		sp.splitBySpace();
		
		// 2. ���ڿ��� ��/�ڷ� ������ �ִ� ���ڿ��� split�� ������
		sp.splitBySpaceWithMargins();
		
		// 3. Space�� Tab�� ���� ���ڿ��� ���Խ����� ������
		sp.splitByMixedSpaceAndTabs();
		
		// 4. ���ڿ��� Ư����ġ ������ ���ڿ��� �߶󳻱�
		sp.substring();
		
		// 5. ���ڿ��� Ư����ġ���� ���� ������ŭ ���ڿ��� �߶󳻱�
		sp.substringWithLength();
		
		// 6. ���Խ��� �̿��� ���ڿ� �Ľ�
		sp.parseUsingRegEx();
	}

	/** 1. �������� ���е� ���ڿ��� split�� ������ */
	public void splitBySpace() {
		System.out.println("==<< splitBySpace >>==");
		
		String input1 = "01 TEST S1234 abcd";
		String[] split1 = input1.split(" ");
		
		System.out.println("size : "+split1.length+" / origin : ["+input1+"]");		
		for(String s1 : split1) System.out.print("["+s1+"] ");
		
		System.out.println("\n-----------------");
	}
	
	/** 2. ���ڿ��� ��/�ڷ� ������ �ִ� ���ڿ��� split�� ������ */
	public void splitBySpaceWithMargins() {
		System.out.println("==<< splitBySpaceWithMargins >>==");
		
		String input2 = "   02 TEST S1234\t5678   abcd   ";
		String[] split2 = input2.split(" ");
		
		System.out.println("size : "+split2.length+" / origin : ["+input2+"]");
		for(String s2 : split2) System.out.print("["+s2+"] ");
		
		System.out.println("\n-----------------");
	}
	
	/** 3. Space�� Tab�� ���� ���ڿ��� ���Խ����� ������ */
	public void splitByMixedSpaceAndTabs() {
		System.out.println("==<< splitByMixedSpaceAndTabs >>==");
		
		String input3 = "03\tsample    with tabs\t\tAnd  spaces";
		String[] split3 = input3.split("(\\t+|\\s+)");
		
		System.out.println("size : "+split3.length+" / origin : ["+input3+"]");
		for(String s3 : split3) System.out.print("["+s3+"] ");
		
		System.out.println("\n-----------------");
	}
	
	/** 4. ���ڿ��� Ư����ġ ������ ���ڿ��� �߶󳻱� */
	public void substring() {
		System.out.println("==<< substring >>==");
		
		String input = "hambugger";
		
		System.out.println("origin : " + input);
		System.out.println("substring(0) : " + input.substring(0));
		System.out.println("substring(1) : " + input.substring(1));
		System.out.println("substring(length-1) : " + input.substring(input.length()-3));
				
		System.out.println("-----------------");
	}
	
	/** 5. ���ڿ��� Ư����ġ���� ���� ������ŭ ���ڿ��� �߶󳻱� */
	public void substringWithLength() {
		System.out.println("==<< substringWithLength >>==");
		
		String input = "hambugger";
		
		System.out.println("origin : " + input);
		System.out.println("substring(0, 2) : " + input.substring(0,2));
		System.out.println("substring(2, 4) : " + input.substring(2,4));
		System.out.println("substring(1, indexOf('g')) : " + input.substring(1,input.indexOf("g")));
		System.out.println("substring(1, lastIndexOf('g')) : " + input.substring(1,input.lastIndexOf("g")));
		
		System.out.println("-----------------");
	}
	
	/** 6. ���Խ��� �̿��� ���ڿ� �Ľ� */
	public void parseUsingRegEx() {
		System.out.println("==<< parseUsingRegEx >>==");
		
		String[] input = new String[] {
				"2018.01.17 13:05:52 RECV some data to recieve RES_OK",
				"2018.01.17 13:06:12 SEND some data to send data is long long long very long and contains $peci@L charactors RES_OK",
				"20180318 060552 RECV error to process RES_ERR",
				"2018.03.18 06:05:52 RECV error to process RES_ERR",
		};
		
		Pattern p = Pattern.compile("^(\\d{4}\\.\\d{2}\\.\\d{2})\\s(\\d{2}:\\d{2}:\\d{2})\\s(\\w{4}).*(RES_(OK|ERR))$");
				
		for(String s : input) {
			Matcher m = p.matcher(s);
			
			System.out.println("origin : " + s);
			if(m.matches()) {				
				System.out.println("DATE: "+ m.group(1) + " / TYPE : "+ m.group(3) + " / RESULT : "+ m.group(4)+"\n");
			} else {
				System.out.println("Cannot parse data\n");
			}
		}
		
		System.out.println("-----------------");
	}
}
