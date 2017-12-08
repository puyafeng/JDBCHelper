package test;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import pyf.java.db.JDBCHelper;

public class Test {
	public static void main(String[] args) {
		List<User> list = JDBCHelper.query(User.class, "select * from user");
		System.out.println(list);
		
		
		System.out.println(JDBCHelper.query(Map.class, "select count(1) as n from user"));
		
		JDBCHelper.other("update user set user.userName = ? where id = ?", "1",2);
		
	}
}
