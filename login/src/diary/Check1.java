package diary;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Check1 {
	
	private String SID;
	

	// SQL진입문--------------------------------
	private Connection conn() throws Exception {
		Class.forName("oracle.jdbc.driver.OracleDriver"); // 데이터베이스 드라이버를 로딩한
		return DriverManager.getConnection("jdbc:oracle:thin:@192.168.0.46:1521:xe", "scott", "tiger");
	}

	// 명령어 닫기--------------------------------
	private void connClose(ResultSet rs, PreparedStatement stmt, Connection con, Scanner sc) {
//		try {if (rs != null)rs.close();} catch (Exception e) {e.printStackTrace();}
//		try {if (stmt != null)stmt.close();} catch (Exception e) {e.printStackTrace();}
//		try {if (con != null)con.close();} catch (Exception e) {e.printStackTrace();}
//		try {if (sc != null)sc.close();} catch (Exception e) {e.printStackTrace();}
	}

	
	// 첫 로그인--------------------------------
	
	int login(String id, String pw){

			ResultSet rs = null;
			PreparedStatement stmt = null;
			Connection con = null;

			try {

				con = conn();
//				System.out.println("DB접속 성공");
				stmt = con.prepareStatement("Select * from Diary_login where id=?");

				stmt.setString(1, id);
				
				rs = stmt.executeQuery(); // sql문(select)실행하기;
				String dbPw="";
				if (rs.next()) { // 한행이 있다면(즉, 아이디가 있다면)
					dbPw = rs.getString("pw");					
					if (pw.equals(dbPw)) {
						System.out.println("로그인 성공");
						SID=id;
						return 1;
					} else {
						System.out.println("PW 오류");
						return 2;
					}
				} else {
					System.out.println("ID 또는 PW 오류");
					return 2;
				}
			} catch (Exception e) {
				System.out.println("예외발생");
				e.printStackTrace();
			}
			return 2;
		}
	
	// 목록 뽑기--------------------------------
	
	List<Diarylist> list(){
		
		Scanner sc = new Scanner(System.in);
		PreparedStatement stmt = null;
		Connection con = null;
		ResultSet rs = null;
		List<Diarylist>list=new ArrayList<>();
		
		try {
			
			con = conn();
//			System.out.println("DB접속 성공");

			stmt = con.prepareStatement("Select * from Diary where id=? order by dateno");
			stmt.setString(1, SID);
			rs = stmt.executeQuery();

			while (rs.next()) {
				Diarylist l = new Diarylist();
				l.diaryno=rs.getString(1);
				l.id=rs.getString(2);
				l.dateno=rs.getString(3);
				l.title=rs.getString(4);
				l.contents=rs.getString(5);
				list.add(l);
			}

		} catch (Exception e) {
			System.out.println("예외발생");
			e.printStackTrace();
		}
		return list;
	}
	
	// 고유번호 뽑기--------------------------------
	int a;
	
	int noR() {
		ResultSet rs = null;
		PreparedStatement stmt = null;
		Connection con = null;
		try {

			con = conn();
//			System.out.println("DB접속 성공");
			stmt = con.prepareStatement("Select diaryno from Diary order by diaryno");
			rs = stmt.executeQuery();
			int i = 0;
			while(rs.next()) {
			a = rs.getInt("diaryno");
			i++;
			if (a!=i)
				break;
			}
			System.out.println("고유번호 부여 : "+i);
			return i;
		} catch (Exception e) {
			System.out.println("예외발생");
			e.printStackTrace();
		}
		return 0;
	}
	
	// 신규작성하기--------------------------------	
	
	void newDiary(int DNo, String date, String title, String contents) {
		ResultSet rs = null;
		PreparedStatement stmt = null;
		Connection con = null;
		try {
			con = conn();
//			System.out.println("DB접속 성공");
			stmt = con.prepareStatement("insert into Diary (diaryno,id,dateno,title,contents) values (?,?,?,?,?)");
			stmt.setInt(1, DNo);
			stmt.setString(2, SID);
			stmt.setString(3, date);
			stmt.setString(4, title);
			stmt.setString(5, contents);
			rs = stmt.executeQuery();
		} catch (Exception e) {
			System.out.println("예외발생");
			e.printStackTrace();
		}
		
		
	}
	
	// 신규작성하기--------------------------------	
	
	// All
	void updeteall(String date, int DNo,String title, String contents) {
		ResultSet rs = null;
		PreparedStatement stmt = null;
		Connection con = null;
		try {
			con = conn();
//			System.out.println("DB접속 성공");
			stmt = con.prepareStatement("update diary set dateno = ?,title = ?,contents = ? where diaryno=?");
			stmt.setString(1, date);
			stmt.setString(2, title);
			stmt.setString(3, contents);
			stmt.setInt(4, DNo);
			stmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("예외발생");
			e.printStackTrace();
		}
	}
	
	// 제목만
	void updetetitle(String date, int DNo,String title) {
		ResultSet rs = null;
		PreparedStatement stmt = null;
		Connection con = null;
		try {
			con = conn();
//			System.out.println("DB접속 성공");
			stmt = con.prepareStatement("update diary set dateno = ?,title = ? where diaryno=?");
			stmt.setString(1, date);
			stmt.setString(2, title);
			stmt.setInt(3, DNo);
			stmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("예외발생");
			e.printStackTrace();
		}
	}
	
	// 내용만
	void updetecontents(String date, int DNo,String contents) {
		ResultSet rs = null;
		PreparedStatement stmt = null;
		Connection con = null;
		try {
			con = conn();
//			System.out.println("DB접속 성공");
			stmt = con.prepareStatement("update diary set dateno = ?,contents = ? where diaryno=?");
			stmt.setString(1, date);
			stmt.setString(2, contents);
			stmt.setInt(3, DNo);
			stmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("예외발생");
			e.printStackTrace();
		}
	}
	
	// 계정삭제--------------------------------	
	
	void delete(int DNo) {
		ResultSet rs = null;
		PreparedStatement stmt = null;
		Connection con = null;
		try {
			con = conn();
//			System.out.println("DB접속 성공");
			stmt = con.prepareStatement("Delete from Diary where diaryno=?");
			stmt.setInt(1, DNo);
			stmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("예외발생");
			e.printStackTrace();
		}
	}
		
	}
