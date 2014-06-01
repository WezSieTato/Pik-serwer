package com.pik.moviecollection.model.datamanagement;

import com.pik.moviecollection.model.entity.Category;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import java.util.ArrayList;
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
	List<Category> categories = new ArrayList<>();
	Query query = entityManager.createQuery("select c from Category c");
	List<?> categoriesList = query.getResultList();

	for (Object category: categoriesList)
	{
	    categories.add((Category)category);
	}

	return categories;
    }

    @Override
    public Category getCategoryByName(String name)
    {
	Query query = entityManager.createQuery("select c.name from Category c where c.name = :name");
	query.setParameter("name", name);
	Category category = null;
	try
	{
	    category = (Category) query.getSingleResult();
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
	return category.getCategoryID();
    }

    @Override
    public boolean deleteCategory(String categoryID)
    {
	Category category = entityManager.find(Category.class, categoryID);
	entityManager.remove(category);
	return true;
    }
}
