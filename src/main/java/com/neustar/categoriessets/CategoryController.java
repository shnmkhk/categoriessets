package com.neustar.categoriessets;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Arrays;
import java.util.Properties;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.neustar.categoriessets.util.AppConstants;

@RestController
public class CategoryController {

	private static Properties props = new Properties();
	
	private static void refreshConfigProps() {
		try {
			props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	static {
		refreshConfigProps();
	}
	
	@RequestMapping(value = "/clist", produces = "application/json")
	public String[] list() {
		final String initCategories = props.getProperty(AppConstants.KEY_INITIAL_CATEGORIES).trim();
		final String extCategories =  props.getProperty(AppConstants.KEY_EXTENDED_CATEGORIES).trim();
		return (initCategories + "," + extCategories).split("[ ]*,[ ]*");
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/clist/add")
	public String[] add(@RequestBody final String newCategory) {
		final String initCategories = props.getProperty(AppConstants.KEY_INITIAL_CATEGORIES).trim();
		String extCategories =  props.getProperty(AppConstants.KEY_EXTENDED_CATEGORIES).trim();
		if (extCategories.length() > 0) extCategories += ",";
		extCategories += newCategory;
		props.setProperty(AppConstants.KEY_EXTENDED_CATEGORIES, extCategories);
		this.resyncProps();
		return (initCategories + "," + extCategories).split("[ ]*,[ ]*");
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/clist/remove")
	public String[] remove(@RequestBody final String deletingCategory) {
		final String initCategories = props.getProperty(AppConstants.KEY_INITIAL_CATEGORIES).trim();
		String extCategories =  props.getProperty(AppConstants.KEY_EXTENDED_CATEGORIES).trim();
		extCategories = Arrays.stream(extCategories.split("[ ]*,[ ]*")).filter((arrEl)->!arrEl.equals(deletingCategory)).collect(Collectors.joining(","));
		props.setProperty(AppConstants.KEY_EXTENDED_CATEGORIES, extCategories);
		this.resyncProps();
		return (initCategories + "," + extCategories).split("[ ]*,[ ]*");
	}
	
	
	public void resyncProps() {
		URL url = Thread.currentThread().getContextClassLoader().getResource("application.properties");
		String filePath = url.toExternalForm().replaceAll("file:/", "").replaceAll("jar:/", "");
		System.out.println("filepath: "+filePath);
		try {
			props.store(new PrintWriter(new FileWriter(filePath, false)), null);
			refreshConfigProps();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}