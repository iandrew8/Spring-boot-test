package com.springboot.systems.system_wide.services.dao.impl;

import com.springboot.systems.system_wide.models.enums.RecordStatus;
import com.springboot.systems.system_wide.services.dao.BaseDao;
import com.googlecode.genericdao.dao.jpa.GenericDAOImpl;
import com.googlecode.genericdao.search.ISearch;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.jpa.JPASearchProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public abstract class BaseDAOImpl<T> extends GenericDAOImpl<T, String> implements BaseDao<T> {

	protected EntityManager entityManager;

	@PersistenceContext
	public void setEntityManager(final EntityManager entityManager) {
		super.setEntityManager(entityManager);
		this.entityManager = entityManager;
	}

	@Autowired
	public void setSearchProcessor(final JPASearchProcessor searchProcessor) {
		super.setSearchProcessor(searchProcessor);
	}

	public List<T> searchByPropertyEqual(final String property, final Object value) {
		final Search search = new Search();
		search.addFilterEqual(property, value);
		return this.search((ISearch) search);
	}

	public T searchUniqueByPropertyEqual(final String property, final Object value) {
		final Search search = new Search();
		search.addFilterEqual(property, value);
		return this.searchUnique((ISearch) search);
	}

	public List<T> searchByPropertyEqual(final String property, final Object value, final RecordStatus recordStatus) {
		final Search search = new Search();
		search.addFilterEqual(property, value);
		search.addFilterEqual("recordStatus", recordStatus);
		return this.search((ISearch) search);
	}

	public T searchUniqueByPropertyEqual(final String property, final Object value, final RecordStatus recordStatus) {
		final Search search = new Search();
		search.addFilterEqual(property, value);
		search.addFilterEqual("recordStatus", recordStatus);
		return this.searchUnique((ISearch) search);
	}

	public List<T> searchByRecordStatus(final RecordStatus recordStatus) {
		final Search search = new Search();
		search.addFilterEqual("recordStatus", recordStatus);
		return this.search((ISearch) search);
	}

	public T save(final T entity) {
		if (entity == null) {
			return null;
		}
		return super.save(entity);
	}

	public T merge(final T entity) {
		if (entity == null) {
			return null;
		}
		return super.merge(entity);
	}
	
	public void delete(final T entity) {
		if (entity != null) {
			super.merge(entity);
		}
	}
	
	public void deletePermanently(T entity) {
		if (entity != null) {
			super.remove(entity);
		}
	}
	
}
