package main;

import java.util.*;
import java.sql.*;
import db.DBconnect;

class PayHobongCodeList{
	int cnt;
	String grade;
	int ho;
	int ho_won;
	
	
	void printPayHobongCode() {
		System.out.printf("%3d   %2s  %4d    %4d        %6d\n",
				cnt, grade, ho, ho_won, ho_won*12);
	}
}
public class Pay_HobongCode_list {
    public static void main(String[] args) {
        DBconnect d = new DBconnect();

        Connection conn = d.getConnection();
        PreparedStatement pstmt = null;
        String sql;

        Scanner input = new Scanner(System.in);
        try {
            while (true) {
                System.out.println("호봉코드를 출력합니다. 전체:1 종료:9 직급(2자리)");
                int in_order = input.nextInt();

                if (in_order == 9) {
                    System.out.println("프로그램을 종료합니다.");
                    break; // 9를 입력하면 루프를 종료하고 프로그램을 종료합니다.
                }

                sql = "select grade, ho, ho_won "
                        + "from hr_hobong_code";
                if (in_order == 1) {
                    sql = sql + " order by grade";
                } else if (in_order >= 0 && in_order <= 99) {
                    sql = sql + " where grade=" + in_order;
                }

                pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery();

                PayHobongList p = new PayHobongList();
                int i_cnt = 0;
                System.out.println("=======================기준정보현황=======================");
                System.out.println("번호  직급   호봉    금액(월봉)          연봉          ");
                System.out.println("=======================================================");

                while (rs.next()) {
                    p.grade = rs.getString("grade");
                    p.ho = rs.getInt("ho");
                    p.ho_won = rs.getInt("ho_won");
                    
                    p.cnt = ++i_cnt;
                    p.printPayHobongCode();
                }
                System.out.println("=======================================================");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}