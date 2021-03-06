package com.pik.moviecollection.model.datamanagement;

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
public class CategoryManagerTest
{
    private EntityManager entityManager;
    private CategoryManager categoryManager;
    private final String CATEGORY_NAME = "unique category name";

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
	Category category  = addTestCategoryToDatabase();
	String categoryID = category.getName();

	List<Category> categories = categoryManager.getCategories();
	assertTrue(categories.size() > 0);

	category = entityManager.find(Category.class, categoryID);
	entityManager.remove(category);
    }

    @Test
    public void getCategoryByName()
    {
	Category category  = addTestCategoryToDatabase();
	String categoryName = category.getName();

	Category categoryFromManager = categoryManager.getCategoryByName(categoryName);
	assertEquals(category.getName(), categoryFromManager.getName());

	entityManager.remove(category);
    }

    private Category addTestCategoryToDatabase()
    {
	Category category = new Category();
	category.setName(CATEGORY_NAME);

	entityManager.persist(category);
	return category;
    }

    @Test
    public void canDeleteCategory()
    {
	Category category  = addTestCategoryToDatabase();
	String categoryID = category.getName();

	categoryManager.deleteCategory(categoryID);
	category = entityManager.find(Category.class, categoryID);

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
	category.setName(CATEGORY_NAME);

	return category;
    }

}
