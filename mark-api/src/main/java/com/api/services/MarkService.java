package com.api.services;

public interface MarkService<T> {

	T save(T entity);

	T update(Integer id, T entity);

	T get(Integer id);

	
}
