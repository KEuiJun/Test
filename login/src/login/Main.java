package login;

import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String id, pw, name;
		
		System.out.println("프로그램 시작");

		// 항목선택
		System.out.println("행동을 정해주세요.");
		System.out.println("1 : 로그인");
		System.out.println("2 : 회원가입");
		System.out.println("3 : 회원탈퇴");
		System.out.println("4 : 계정찾기");
		System.out.println("5 : 계정목록");
		int num = sc.nextInt();
		sc.nextLine(); // 숫자 입력후 엔터값 없애기..

		// 잘못선택 방지함수
		while (num < 1 || num > 5) {
			System.out.println("다음 항목내에서 선택해주세요.");
			System.out.println("1 : 로그인");
			System.out.println("2 : 회원가입");
			System.out.println("3 : 회원탈퇴");
			System.out.println("4 : 계정찾기");
			System.out.println("5 : 계정목록");
			num = sc.nextInt();
		}

		// 로그인
		if (num == 1) {
			System.out.println("ID를 입력해주세요.");
			id = sc.nextLine();
			System.out.println("비밀번호를 입력해주세요.");
			pw = sc.nextLine();

			// 객체(Check)의 메소드(login(string id, string pw):int (0: db 접속오류 1: 아이디가 틀리다.
			// 2: 패스워드가 틀리다. 3: 로그인성공)를 통해서 로그인 여부 확인
			
			Check check = new Check();
			CheckType flag=check.login(id, pw);
			
//			int flag = check.login(id, pw);
//			System.out.println(flag); // 클래스 숫자확인
			
			switch (flag) {
			case DB_ERROR : {
				System.out.println("db 접속오류");
				break;
			}
			case ID_ERROR : {
				System.out.println("아이디가 틀렸습니다.");
				break;
			}
			case PW_ERROR : {
				System.out.println("패스워드가 틀렸습니다.");
				break;
			}
			case SUCCESS : {
				System.out.println("로그인 성공");
			}
			}

		}

		// 회원가입
		if (num == 2) {
			Check check = new Check();

			System.out.println("회원가입");
			System.out.println("ID, 비밀번호, 이름을 입력해주세요.");
			System.out.print("ID를 입력해주세요.");
			id = sc.nextLine();
			System.out.print("비밀번호를 입력해주세요.");
			pw = sc.nextLine();
			System.out.print("이름를 입력해주세요.");
			name = sc.nextLine();
			
			check.signUp(id, pw, name);

			System.out.println("회원가입완료");
		}

		// 회원탈퇴
		if (num == 3) {
			Check check = new Check();

			System.out.println("회원탈퇴");
			System.out.println("ID를 입력해주세요.");
			id = sc.nextLine();
			System.out.println("비밀번호를 입력해주세요.");
			pw = sc.nextLine();

			check.Delete(id, pw);
		}

		// 계정찾기
		if (num == 4) {
			Check check = new Check();

			System.out.println("찾으실 항목을 정해주세요.");
			System.out.println("1 : ID");
			System.out.println("2 : PW");
			int IDORPW = sc.nextInt();

			while (IDORPW < 1 || IDORPW > 2) {
				System.out.println("다음 항목내에서 선택해주세요.");
				System.out.println("1 : ID");
				System.out.println("2 : PW");
				IDORPW = sc.nextInt();
			}

			sc.nextLine(); // 자동 엔터 방지
			
			// id 찾기
			if (IDORPW == 1) {
				System.out.println("이름을 입력해주세요.");
				name = sc.nextLine();
				check.fID(name);

				//추가로 PW를 찾을것인지
				System.out.println("비밀번호도 찾으시겠습니까?");
				System.out.println("1 : 네");
				System.out.println("2 : 아니요");
				int i = sc.nextInt();

				while (IDORPW < 1 || IDORPW > 2) {
					System.out.println("다음 항목내에서 선택해주세요.");
					System.out.println("1 : 네");
					System.out.println("2 : 아니요");
					i = sc.nextInt();
				}
						
				if(i==1)
					IDORPW=2;
				sc.nextLine();
			}
			
			// pw 찾기
			if(IDORPW==2) {
				System.out.println("이름과 ID를 입력해주세요.");
				System.out.print("이름 : ");
				name = sc.nextLine();
				System.out.print("id : ");
				id = sc.nextLine();
				
				check.fPW(name, id);
			}
		} // 계정찾기
		if(num==5) { // ID확인
			Check check = new Check();
			List<User> list=check.list();
			for (User temp : list) {
				System.out.println("ID : "+temp.id+" / PW : "+temp.pw);
			}
			
		} // 
			
	} // 메인
}
