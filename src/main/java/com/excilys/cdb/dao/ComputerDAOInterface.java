package com.excilys.cdb.dao;

import java.util.List;
import java.util.Optional;
import com.excilys.cdb.model.Computer;

public interface ComputerDAOInterface<T> {

	/**
	 * Méthode de création
	 * 
	 * @param obj
	 */
	public abstract void create(T obj);

	/**
	 * Méthode pour effacer par id
	 * 
	 * @param id
	 */
	public abstract void delete(long id);
	
	/**
	 * Méthode pour effacer par company_id
	 * 
	 * @param id
	 */
	public abstract void deleteByCompany(long id);

	/**
	 * Méthode de mise à jour
	 * 
	 * @param obj
	 */
	public abstract void update(T obj);

	/**
	 * Méthode de recherche des informations par id
	 * 
	 * @param id
	 * @return T
	 */
	public abstract Optional<Computer> find(long id);

	/**
	 * Méthode de recherche des informations
	 * 
	 * @param name
	 * @return ArrayList<T>
	 */
	public abstract List<T> findAll(String name, int page, int size);

	/**
	 * Méthode pour compter le nombre total de données
	 * 
	 * @param name
	 * @return int
	 */
	public abstract long count(String name);


}
