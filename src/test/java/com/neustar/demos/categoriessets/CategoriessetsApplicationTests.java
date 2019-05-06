package com.neustar.demos.categoriessets;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.neustar.categoriessets.CategoriessetsApplication;
import com.neustar.categoriessets.CategoryController;
import com.neustar.categoriessets.SetsController;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=CategoriessetsApplication.class)
@TestPropertySource("classpath:application.properties")
public class CategoriessetsApplicationTests {

	@Autowired
	private CategoryController categoryCtrl;
	
	@Autowired
	private SetsController setsCtrl;
	
	@Test
	public void addNewCategoryTest() {
		String[] list = this.categoryCtrl.list();
		this.categoryCtrl.add("FOOD");
		String[] revisedList = this.categoryCtrl.list();
		Assert.assertEquals(revisedList.length, list.length + 1);
		this.categoryCtrl.remove("FOOD");
		revisedList = this.categoryCtrl.list();
		Assert.assertEquals(revisedList.length, list.length);
	}
	
	@Test
	public void verifySets() {
		final String requestBody = "[\r\n" + 
				"{\"PERSON\":\"Bob Jones\"},\r\n" + 
				"{\"PLACE\":\"Washington\"},\r\n" + 
				"{\"PERSON\":\"Mary\"},\r\n" + 
				"{\"COMPUTER\":\"Mac\"},\r\n" + 
				"{\"PERSON\":\"Bob Jones\"},\r\n" + 
				"{\"OTHER\":\"Tree\"},\r\n" + 
				"{\"ANIMAL\":\"Dog\"},\r\n" + 
				"{\"PLACE\":\"Texas\"},\r\n" + 
				"{\"FOOD\":\"Steak\"},\r\n" + 
				"{\"ANIMAL\":\"Cat\"}\r\n" + 
				"]";
		Map<String, Integer> resultObject = this.setsCtrl.verify(requestBody);
		Assert.assertEquals(3, (int) resultObject.get("PERSON"));
		Assert.assertNull(resultObject.get("FOOD"));
		this.categoryCtrl.add("FOOD");
		resultObject = this.setsCtrl.verify(requestBody);
		Assert.assertEquals(1, (int) resultObject.get("FOOD"));
		this.categoryCtrl.remove("FOOD");
		resultObject = this.setsCtrl.verify(requestBody);
		Assert.assertNull(resultObject.get("FOOD"));
	}

}
