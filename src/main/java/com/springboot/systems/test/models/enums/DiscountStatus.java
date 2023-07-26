package com.springboot.systems.test.models.enums;

import org.apache.commons.lang.StringUtils;

public enum DiscountStatus {
	APPLIED("Applied"),

	NOT_APPLIED("Not Applied");

	private String status;

	DiscountStatus(String status) {
		this.status = status;
	}

	public static final DiscountStatus getEnumObject(String value) {
		if (StringUtils.isBlank(value))
			return null;
		for (DiscountStatus object : DiscountStatus.values()) {
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
