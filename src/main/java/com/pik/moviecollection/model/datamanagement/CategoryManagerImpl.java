package com.pik.moviecollection.model.datamanagement;

import com.pik.moviecollection.model.entity.Category;

import javax.persistence.EntityManager;
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
