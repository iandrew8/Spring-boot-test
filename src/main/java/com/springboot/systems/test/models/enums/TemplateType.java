package com.springboot.systems.test.models.enums;

import org.apache.commons.lang.StringUtils;

public enum TemplateType {
	USER_ACCOUNT_CREATION("User Account Creation"),
	FORGOT_PASSWORD("Forgot Password"),
	OUTSTANDING_BILL_REMINDER("Outstanding Bill Reminder"),
	MONTHLY_BILL_DELIVERY("Monthly Bill Delivery");

	private String status;

	TemplateType(String status) {
		this.status = status;
	}

	public static final TemplateType getEnumObject(String value) {
		if (StringUtils.isBlank(value))
			return null;
		for (TemplateType object : TemplateType.values()) {
			if (object.getName().equals(value))
				return object;
		}
		return null;
	}

	public String getName() {
		return this.status;
	}

	@Override
	public String toString() {
		return this.status;
	}
}
