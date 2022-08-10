package login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Check {

	// sql 사용명령어
	private Connection conn() throws Exception {
		Class.forName("oracle.jdbc.driver.OracleDriver"); // 데이터베이스 드라이버를 로딩한
		return DriverManager.getConnection("jdbc:oracle:thin:@192.168.0.46:1521:xe", "scott", "tiger");
	}

	// 명령어 닫기
	private void connClose(ResultSet rs, PreparedStatement stmt, Connection con, Scanner sc) {
		try {
			if (rs != null)
				rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if (stmt != null)
				stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if (con != null)
				con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if (sc != null)
				sc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 로그인
	CheckType login(String id, String pw) {

		int flag = 0;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		Connection con = null;

		try {

			con = conn();
//			System.out.println("DB접속 성공");
			stmt = con.prepareStatement("Select * from TBL_USER where id=?");

			stmt.setString(1, id);

			// 삽입, 삭제, 갱신시에는 .executeUpdate() 리턴이 정수(정수가 의미하는 몇개가 처리되었는지)

			rs = stmt.executeQuery(); // sql문(select)실행하기;
//			rs.next();
//			String dbPw = rs.getString("pw");
			
			if (rs.next()) { // 한행이 있다면(즉, 아이디가 있다면)
				String dbPw = rs.getString("pw");
				if (pw.equals(dbPw)) {
					return CheckType.SUCCESS;
				} else 
					return CheckType.PW_ERROR;
			} else {
				return CheckType.ID_ERROR;
			}
			
		} catch (Exception e) {
			System.out.println("예외발생");
			e.printStackTrace();
		} finally {
			connClose(rs, stmt, con, null);
		}
		return CheckType.DB_ERROR;
	}

	// 회원가입
	void signUp(String id, String pw, String name) {

		Scanner sc = new Scanner(System.in);
		PreparedStatement stmt = null;
		Connection con = null;
		ResultSet rs = null;

		try {

			con = conn();
//			System.out.println("DB접속 성공");

			stmt = con.prepareStatement("Select * from TBL_USER where id=?");
			stmt.setString(1, id);
			rs = stmt.executeQuery();

			while (rs.next()) {
				System.out.println("아이디가 중복됩니다.");
				System.out.println("다시 입력해주세요.");
				id = sc.nextLine();
			}

			connClose(null, stmt, null, null);

			stmt = con.prepareStatement("INSERT INTO TBL_USER (ID, PW, NAME) VALUES (?, ?, ?)");
			stmt.setString(1, id);
			stmt.setString(2, pw);
			stmt.setString(3, name);

			stmt.executeUpdate(); // sql명령문 실행하기;

		} catch (Exception e) {
			System.out.println("예외발생");
			e.printStackTrace();
		} finally {
			connClose(null, stmt, con, sc);
		}

	}

	// 계정삭제
	void Delete(String id, String pw) {
		Scanner sc = new Scanner(System.in);
		PreparedStatement stmt = null;
		Connection con = null;
		ResultSet rs = null;

		try {

			con = conn();
			// System.out.println("DB접속 성공");
			stmt = con.prepareStatement("Select * from TBL_USER where id=?");

			stmt.setString(1, id);

			// 삽입, 삭제, 갱신시에는 .executeUpdate() 리턴이 정수(정수가 의미하는 몇개가 처리되었는지)

			rs = stmt.executeQuery(); // sql문(select)실행하기;

			if (rs.next()) {
				System.out.println("회원탈퇴를 원하시면 1번을 입력해주세요.");
				int num = sc.nextInt();
				if (num == 1) {
					if (pw.equals(rs.getString("PW"))) {
						connClose(null, stmt, null, null);
						stmt = con.prepareStatement("Delete from TBL_USER where id=? and pw=?");
						stmt.setString(1, id);
						stmt.setString(2, pw);
						stmt.executeUpdate();
						System.out.println("회원탈퇴를 완료했습니다.");
					} else
						System.out.println("비밀번호가 다릅니다.");
				} else
					System.out.println("회원탈퇴를 취소합니다.");
			} else {
				System.out.println("발견된 회원이 없습니다.");
			}

		} catch (Exception e) {
			System.out.println("예외발생");
			e.printStackTrace();
		} finally {
			connClose(rs, stmt, con, sc);
		}
	}

	// id찾기
	void fID(String name) {

		ResultSet rs = null;
		PreparedStatement stmt = null;
		Connection con = null;
		Scanner sc = new Scanner(System.in);

		try {
			con = conn();
			stmt = con.prepareStatement("Select * from TBL_USER where name=?");
			stmt.setString(1, name);
			rs = stmt.executeQuery();

			if (rs.next()) {
				System.out.println(name + "님의 아이디는 " + rs.getString("ID") + "입니다.");
			} else {
				while (!rs.next()) {
					System.out.println("이름이 존재하지 않습니다.");
					System.out.println("이름을 다시 입력해주세요.");
					name = sc.nextLine();

					connClose(rs, stmt, null, null);
					stmt = con.prepareStatement("Select * from TBL_USER where name=?");
					stmt.setString(1, name);
					rs = stmt.executeQuery();
				}
				System.out.println(name + "님의 아이디는 " + rs.getString("ID") + "입니다.");
			}
		} catch (Exception e) {
			System.out.println("예외발생");
			e.printStackTrace();
		} finally {
			connClose(rs, stmt, con, null);
		}

	}

	// pw찾기
	void fPW(String name, String id) {

		ResultSet rs = null;
		PreparedStatement stmt = null;
		Connection con = null;
		Scanner sc = new Scanner(System.in);

		try {
			con = conn();
			stmt = con.prepareStatement("Select * from TBL_USER where name=? and id=?");
			stmt.setString(1, name);
			stmt.setString(2, id);
			rs = stmt.executeQuery();

			// 행이 있으면 pw출력
			if (rs.next()) {
				System.out.println(name + "님의 비밀번호는 " + rs.getString("PW") + "입니다.");
			} else { // 없으면 이름만으로 검색
				for (;;) {
					connClose(rs, stmt, null, null);
					stmt = con.prepareStatement("Select * from TBL_USER where name=?");
					stmt.setString(1, name);
					rs = stmt.executeQuery();

					// 이름만으로 검색이 존재하면 id수정
					if (rs.next()) {
						System.out.println("존재하지 않는 ID입니다.");
						System.out.println("ID를 다시 입력해주세요.");
						id = sc.nextLine();
						connClose(rs, stmt, null, null);
						stmt = con.prepareStatement("Select * from TBL_USER where name=? and id=?");
						stmt.setString(1, name);
						stmt.setString(2, id);
						rs = stmt.executeQuery();

						// id 이름 같이 검색해서 될때까지 반복
						for (; !(rs.next());) {
							System.out.println("존재하지 않는 ID입니다.");
							System.out.println("ID를 다시 입력해주세요.");
							id = sc.nextLine();
							connClose(rs, stmt, null, null);
							stmt = con.prepareStatement("Select * from TBL_USER where name=? and id=?");
							stmt.setString(1, name);
							stmt.setString(2, id);
							rs = stmt.executeQuery();
						}

						// 이름만으로 검색이 안되면 id수정
					} else {
						connClose(rs, stmt, null, null);
						stmt = con.prepareStatement("Select * from TBL_USER where id=?");
						stmt.setString(1, id);
						rs = stmt.executeQuery();
						if (rs.next()) {
							System.out.println("존재하지 않는 이름입니다.");
							System.out.println("이름을 다시 입력해주세요.");
							name = sc.nextLine();
							connClose(rs, stmt, null, null);
							stmt = con.prepareStatement("Select * from TBL_USER where name=? and id=?");
							stmt.setString(1, name);
							stmt.setString(2, id);
							rs = stmt.executeQuery();

						}
						for (; !(rs.next());) {
							System.out.println("존재하지 않는 이름입니다.");
							System.out.println("이름을 다시 입력해주세요.");
							name = sc.nextLine();
							connClose(rs, stmt, null, null);
							stmt = con.prepareStatement("Select * from TBL_USER where name=?");
							stmt.setString(1, name);
							rs = stmt.executeQuery();
						}

					}
					System.out.println(name + "님의 비밀번호는 " + rs.getString("PW") + "입니다.");
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("예외발생");
			e.printStackTrace();
		} finally {
			connClose(rs, stmt, con, null);
		}
		
		

	}
	
	List<User> list() {
		ResultSet rs = null;
		PreparedStatement stmt = null;
		Connection con = null;
		Scanner sc = new Scanner(System.in);
		List<User> list = new ArrayList<>();

		try {
			con = conn();
			stmt = con.prepareStatement("Select * from TBL_USER");
			rs = stmt.executeQuery();
			
			while(rs.next() ) {
				User u = new User();
				u.id=rs.getString(1);
				u.pw=rs.getString(2);
				list.add(u);
			}
			
		} catch (Exception e) {
			System.out.println("예외발생");
			e.printStackTrace();
		} finally {
			connClose(rs, stmt, con, sc);
		}
		return list;
	}
}
