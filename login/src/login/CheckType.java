package login;

public enum CheckType {
	//0:db error,1:아이디가 다름,2:비번이 다름,3: 로그인성공
	
	DB_ERROR(0),ID_ERROR(1),PW_ERROR(2),SUCCESS(3); 
	
	private int num;
	
	CheckType(int num) {
		this.num=num;
	}
	
	
	public int value() {
		return num;
	}
	
}
