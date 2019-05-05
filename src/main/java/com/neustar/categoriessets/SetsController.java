package com.neustar.categoriessets;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SetsController {
	
	@Autowired
	private CategoryController categoryController;
	
	@PostMapping("/sets")
	public Map<String, Integer> verifySetsData(@RequestBody final String requestBody) {
		final JSONParser jp = new JSONParser(requestBody);
		final List<String> categoryNames = Arrays.asList(this.categoryController.list());
		final Map<String, Integer> categoryCountMap = new LinkedHashMap<String, Integer>();
		try {
			List<Object> jsonArr = (List<Object>) jp.parse();
			for (int ix = 0; ix < jsonArr.size(); ix++) {
				Map<String, String> obj = (Map<String, String>) jsonArr.get(ix);
				for (final Map.Entry<String, String> entry: obj.entrySet()) {
					if (categoryCountMap.containsKey(entry.getKey()) || 
							categoryNames.contains(entry.getKey())) {
						Integer currentValue = categoryCountMap.getOrDefault(entry.getKey(), 0);
						categoryCountMap.put(entry.getKey(), ++currentValue);
					}
				}
			}
			
			System.out.println(categoryCountMap.toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return categoryCountMap;
	}
		
}
