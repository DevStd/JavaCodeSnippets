package exercise.string;

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
		System.out.println("substring(2,4) : " + input.substring(2,4));
		System.out.println("substring(1, indexOf('g')) : " + input.substring(1,input.indexOf("g")));
		System.out.println("substring(1, lastIndexOf('g')) : " + input.substring(1,input.lastIndexOf("g")));
		
		System.out.println("-----------------");
	}
}
