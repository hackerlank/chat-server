package com.randioo.randioo_server_base.entity;

public class DefaultRole implements RoleInterface {

	private String account;

	@Override
	public String getAccount() {
		return account;
	}

	@Override
	public void setAccount(String account) {
		this.account = account;
	}

}
