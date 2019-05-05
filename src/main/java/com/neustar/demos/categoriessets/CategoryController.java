package com.neustar.demos.categoriessets;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController {

	@Autowired
	private CategoryConfig categoryConfig;

    @RequestMapping("/listcategories")
    public List<Category> list() {
    	final List<Category> categories = new ArrayList<Category>();
        for (final String category : this.categoryConfig.getCategories()) {
        	categories.add(new Category(category));
        }
        return categories;
    }
}