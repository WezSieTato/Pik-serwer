package com.pik.moviecollection.model.datamanagement;

import com.pik.moviecollection.model.entity.Category;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Robert on 2014-05-31.
 */
public class CategoryManagerImpl implements CategoryManager
{
    private final EntityManager entityManager;

    public CategoryManagerImpl(EntityManager entityManager)
    {
	this.entityManager = entityManager;
    }

    @Override
    public List<Category> getCategories()
    {
	String queryString = "select c from Category c";
	TypedQuery<Category> query = entityManager.createQuery(queryString, Category.class);
	return query.getResultList();
    }

    @Override
    public Category getCategoryByName(String name)
    {
	String queryString = "select c from Category c where c.name = :name";
	TypedQuery<Category> query = entityManager.createQuery(queryString, Category.class);
	query.setParameter("name", name);
	Category category = null;
	try
	{
	    category = query.getSingleResult();
	}
	catch (NoResultException | NonUniqueResultException ignored)
	{
	}
	return category;
    }

    @Override
    public String addCategory(Category category)
    {
	entityManager.persist(category);
	return category.getName();
    }

    @Override
    public boolean deleteCategory(String categoryID)
    {
	Category category = entityManager.find(Category.class, categoryID);
	entityManager.remove(category);
	return true;
    }
}
