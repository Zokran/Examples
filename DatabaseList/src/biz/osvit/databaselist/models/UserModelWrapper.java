package biz.osvit.databaselist.models;

import java.util.ArrayList;

/**
 * Title: Database List <br />
 * Copyright: Copyright @ 2014 <br />
 * 
 * @author Zoran Veres
 * @version 1.0.0
 */

public class UserModelWrapper {

	private ArrayList<UserModel> users = new ArrayList<UserModel>();

	public ArrayList<UserModel> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<UserModel> users) {
		this.users = users;
	}

	public void addUser(UserModel user) {
		users.add(user);
	}

	public void clearUsers() {
		users.clear();
	}
}
