package insoft.info;

import java.util.Vector;

public class UserInfo implements Cloneable {

	String name = "";
	int userId = -1;
	int configId = -1;
	String phone = "";
	String email = "";
	boolean isNotifyMinor = false;
	boolean isNotifyMajor = false;
	boolean isNotifyCritical = false;
	boolean isNotifyResolve = false;
	boolean isGroup = false;
	Vector<Integer> vMembers = new Vector<Integer>();

	public UserInfo(int configId, String name, int userId, boolean isNotifyMinor, boolean isNotifyMajor,
			boolean isNotifyCritical, boolean isNotifyResolve) {
		this.configId = configId;
		this.name = name;
		this.userId = userId;
		this.isNotifyMinor = isNotifyMinor;
		this.isNotifyMajor = isNotifyMajor;
		this.isNotifyCritical = isNotifyCritical;
		this.isNotifyResolve = isNotifyResolve;
	}

	public void setMembers(Vector<Integer> vMembers) {
		this.vMembers = vMembers;
	}

	public Vector<Integer> getMembers() {
		return vMembers;
	}

	public void setGroup(boolean isGroup) {
		this.isGroup = isGroup;
	}

	public boolean isGroup() {
		return isGroup;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UserInfo clone() throws CloneNotSupportedException {
		return (UserInfo) super.clone();
	}

	public boolean isNotifyMinor() {
		return isNotifyMinor;
	}

	public boolean isNotifyMajor() {
		return isNotifyMajor;
	}

	public boolean isNotifyCritical() {
		return isNotifyCritical;
	}

	public boolean isNotifyResolve() {
		return isNotifyResolve;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String msg = "[cid:" + configId + "][" + name + "][id:" + userId + "][phone:" + phone + "][email:" + email + "][min:"
				+ isNotifyMinor + "][maj:" + isNotifyMajor + "][cri:" + isNotifyCritical + "][rsv:" + isNotifyResolve + "]";

		if (vMembers.size() > 0)
			msg += vMembers;

		return msg;

	}

	public String getInfo() {

		return "[" + name + "][id:" + userId + "][phone:" + phone + "][email:" + email + "][min:" + isNotifyMinor + "][maj:"
				+ isNotifyMajor + "][cri:" + isNotifyCritical + "][rsv:" + isNotifyResolve + "]";

	}
}
