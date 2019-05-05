package com.neustar.demos.categoriessets;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix="base")
@Component
public class CategoryConfig {

	private List<String> categories = new ArrayList<String>();

	public List<String> getCategories() {
		return categories;
	}
}