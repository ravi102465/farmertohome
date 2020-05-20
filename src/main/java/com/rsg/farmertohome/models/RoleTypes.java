package com.rsg.farmertohome.models;

import lombok.Data;

@Data
public class RoleTypes {
	
	public RoleTypes(long roleId) {
		this.roleId = roleId;
	}

	private long roleId;
	
	private String roleType;
	
	private String roleDescription;

	public RoleTypes() {
	}
}
