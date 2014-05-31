package com.pik.moviecollection.server;

import com.pik.moviecollection.model.datamanagement.CategoryManager;
import com.pik.moviecollection.model.datamanagement.CategoryManagerImpl;
import com.pik.moviecollection.model.datamanagement.EntityConnection;
import com.pik.moviecollection.model.entity.Category;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.List;

import static junit.framework.Assert.*;

/**
 * Created by Robert on 2014-05-31.
 */
public class CategoryDBTest
{
    private EntityManager entityManager;
    private CategoryManager categoryManager;

    @Before
    public void setup()
    {
	entityManager = EntityConnection.getConnection();
	categoryManager = new CategoryManagerImpl(entityManager);
    }

    @After
    public void clean()
    {
	EntityConnection.closeConnection();
    }

    @Test
    public void getCategoriesTest()
    {
	String categoryID = addTestCategoryToDatabase();

	List<Category> categories = categoryManager.getCategories();
	assertTrue(categories.size() > 0);

	Category category = entityManager.find(Category.class, categoryID);
	entityManager.remove(category);
    }

    private String addTestCategoryToDatabase()
    {
	Category category = new Category();
	category.setName("akcja");

	entityManager.persist(category);
	return category.getCategoryID();
    }

    @Test
    public void canDeleteCategory()
    {
	String categoryName = addTestCategoryToDatabase();

	categoryManager.deleteCategory(categoryName);
	Category category = entityManager.find(Category.class, categoryName);

	assertNull(category);
    }

    @Test
    public void canInsertCategory()
    {
	Category category = prepareDataToInsert();
	String categoryID = categoryManager.addCategory(category);

	category = entityManager.find(Category.class, categoryID);
	assertNotNull(category);

	entityManager.remove(category);
    }

    private Category prepareDataToInsert()
    {
	Category category = new Category();
	category.setName("akcja");

	return category;
    }

}
