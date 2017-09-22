package test;

import pyf.java.db.annotation.Column;
import pyf.java.db.annotation.Column.GenType;
import pyf.java.db.annotation.Ignore;
import pyf.java.db.annotation.Table;

@Table("user")
public class User {
	@Column(isId = true, nullable = false, unique = true, genId = GenType.AUTO_INC)
	private int id;

	@Column(nullable = false, unique = true)
	private String userName;

	@Column(nullable = false)
	private String password;

	private int age;

	@Column(size = "200")
	private String profile;

	@Column(nullable = false, unique = true)
	private String mobile;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", password=" + password + ", age=" + age + ", profile="
				+ profile + ", mobile=" + mobile + "]";
	}
	
	

}
