package quiz38;

import java.util.Scanner;

public class Health {
	Scanner sc = new Scanner(System.in);
	String gender;
	double tall,weight;
	
	Health() {
		System.out.println("***** 비만도 Check *****");
	}
	
	Health(int a) {
		System.out.println("***** 비만도 Check *****");
	}
	
	void input(int a) {
		System.out.println("M / F");
		gender=sc.nextLine();
		System.out.println("키");
		tall=sc.nextInt();
		System.out.println("몸무게");
		weight=sc.nextInt();
	}
	
	void output1(){
		System.out.println("성별 - "+gender);
		System.out.println();
		System.out.println("신장 - "+tall);
		System.out.println();
		System.out.println("체중 - "+95.45);
	}
	
	
}
