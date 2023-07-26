package com.springboot.systems.test.services.dao;

import com.springboot.systems.test.models.enums.RecordStatus;
import com.googlecode.genericdao.dao.jpa.GenericDAO;

import java.util.List;

public interface BaseDao<T> extends GenericDAO<T, String> {
	List<T> searchByPropertyEqual(final String p0, final Object p1);

	T searchUniqueByPropertyEqual(final String p0, final Object p1);

	List<T> searchByPropertyEqual(final String p0, final Object p1, final RecordStatus p2);

	List<T> searchByRecordStatus(final RecordStatus p0);

	T searchUniqueByPropertyEqual(final String p0, final Object p1, final RecordStatus p2);

	void delete(final T p0);
	
	void deletePermanently(final T p0);
}