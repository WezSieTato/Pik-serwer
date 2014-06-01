package com.pik.moviecollection.model.datamanagement;

import com.pik.moviecollection.model.entity.Category;

import java.util.List;

/**
 * Created by Robert on 2014-05-31.
 */
public interface CategoryManager
{
    List<Category> getCategories();
    Category getCategoryByName(String name);
    String addCategory(Category category);
    boolean deleteCategory(String categoryName);
}
