package diary;

import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Main {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		Check1 ch = new Check1();
		LocalDate now = LocalDate.now();
		DateTimeFormatter mmdd = DateTimeFormatter.ofPattern("MMdd");
		String date = now.format(mmdd);
		String id, pw;
		int sel, DNo;
		
		// 로그인--------------------------------
		
		System.out.println("아이디를 입력해주세요.");
		id = sc.nextLine();
		System.out.println("비밀번호를 입력해주세요.");
		pw = sc.nextLine();
		
		int flag = ch.login(id, pw);
		for(;flag!=1;) {
			System.out.println("다시 아이디를 입력해주세요.");
			id = sc.nextLine();
			System.out.println("비밀번호를 입력해주세요.");
			pw = sc.nextLine();
			
			flag = ch.login(id, pw);
		}
		
		// 로그인 후 목록 뽑기--------------------------------
		
		List<Diarylist> dl=ch.list();
		for (Diarylist temp : dl) {
			System.out.println("Diaryno : " + temp.diaryno + " / Dateno : " + temp.dateno + "  Title : " + temp.title);
		}
			
		// 행동선택--------------------------------
		
		System.out.println("행동을 정해주세요.");
		System.out.println("1 : 신규일기작성");
		System.out.println("2 : 일기수정");
		System.out.println("3 : 일기삭제");
		sel = sc.nextInt();
		for(;sel<1 || sel>3;) {
			System.out.println("범위를 벗어났습니다. ");
			System.out.println("다시 입력해주세요.");
			System.out.println("1 : 신규일기작성");
			System.out.println("2 : 일기수정");
			System.out.println("3 : 일기삭제");
			sel = sc.nextInt();
			
		}
		sc.nextLine(); // 엔터오류방지
		// 신규 일기작성--------------------------------
		
		if(sel==1) {
			DNo=ch.noR();
			if (DNo==0)
				System.out.println("고유번호 수식오류");
			System.out.println("제목을 입력해주세요.");
			String title = sc.nextLine();
			System.out.println("내용을 입력해주세요.");
			String contents = sc.nextLine();
			ch.newDiary(DNo, date, title, contents);
			System.out.println("저장을 완료했습니다.");
		}
		
		// 일기수정--------------------------------
		
		if(sel==2) {
			
			System.out.println("수정할 일기의 번호를 입력해주세요.");
			int diarynno = sc.nextInt();
			
			System.out.println("어디를 수정하시겠습니까?");
			System.out.println("1. 제목, 내용");
			System.out.println("2. 제목");
			System.out.println("3. 내용");
			int num = sc.nextInt();
			sc.nextLine(); // 엔터오류방지
			
			if(num==1) {
				System.out.println("변경될 제목을 입력해주세요.");
				String title=sc.nextLine();
				System.out.println("변경될 내용을 입력해주세요.");
				String contents=sc.nextLine();
				System.out.println();
				ch.updeteall(date,diarynno, title, contents);
				System.out.println("변경완료했습니다.");
			}
			
			if(num==2) {
				System.out.println("변경될 제목을 입력해주세요.");
				String title=sc.nextLine();
				System.out.println();
				ch.updetetitle(date,diarynno,title);
				System.out.println("변경완료했습니다.");
			}
			
			if(num==3) {
				System.out.println("변경될 내용을 입력해주세요.");
				String contents=sc.nextLine();
				System.out.println();
				ch.updetecontents(date,diarynno,contents);
				System.out.println("변경완료했습니다.");
			}
		}
		
		// 일기삭제--------------------------------
		
		if(sel==3) {
			
			System.out.println("삭제할 일기의 번호를 입력해주세요.");
			int diarynno = sc.nextInt();
			
			System.out.println("정말로 삭제하시겠습니까?");
			System.out.println("1 : 삭제");
			System.out.println("2 : 취소");
			int num = sc.nextInt();
			
			
			for(;num<1 || num>2;) {
				System.out.println("잘못입력하셨습니다.");
				System.out.println("다시 입력해주세요.");
				System.out.println("1 : 삭제");
				System.out.println("2 : 취소");
				num = sc.nextInt();
			}
			
			
			if (num==1) {
				ch.delete(diarynno);
				System.out.println("삭제를 완료했습니다.");
			}
			
			if (num==2) {
				System.out.println("삭제를 취소했습니다.");
			}
		}

	}

}
