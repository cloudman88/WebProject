package model;

public class User {

private String Username, Password, Nickname,Description,Photo ; //customer "schema"

	public User(String username, String password, String nickname,String description, String photo) {
		Username = username;
		Password = password;
		Nickname = nickname;
		Description = description;
		Photo = photo;
	}

	public String getUsername() {
		return Username;
	}

	public String getPassword() {
		return Password;
	}

	public String getNickname() {
		return Nickname;
	}
	
	public String getDesc() {
		return Description;
	}
	
	public String getPhoto() {
		return Photo;
	}
}
