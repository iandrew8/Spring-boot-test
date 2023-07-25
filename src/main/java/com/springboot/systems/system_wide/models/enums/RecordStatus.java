package com.springboot.systems.system_wide.models.enums;

import org.apache.commons.lang.StringUtils;

public enum RecordStatus {
	/**
	 * 
	 */
	ACTIVE("Active"),
	
	DELETED("Deleted"),
	
	PERMANENTLY_DELETED("Permanently Deleted");
	
	private String status;

	RecordStatus(String status) {
		this.status = status;
	}

	public static final RecordStatus getEnumObject(String value) {
		if (StringUtils.isBlank(value))
			return null;
		for (RecordStatus object : RecordStatus.values()) {
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
