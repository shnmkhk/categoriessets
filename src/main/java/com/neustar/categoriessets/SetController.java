package com.neustar.categoriessets;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SetController {

	@Autowired
	private CategoryController categoryController;

	@RequestMapping(method = RequestMethod.POST, value = "/sets")
	public Map<String, Integer> verify(@RequestBody final String requestBody) {
		final JSONParser jp = new JSONParser(requestBody);
		final List<String> categoryNames = Arrays.asList(this.categoryController.list());
		final Map<String, Integer> categoryCountMap = new LinkedHashMap<String, Integer>();
		final Map<String, String> categorySubCategoryMatchMap = new HashMap<String, String>();
		try {
			List<Object> jsonArr = (List<Object>) jp.parse();
			for (int ix = 0; ix < jsonArr.size(); ix++) {
				Map<String, String> obj = (Map<String, String>) jsonArr.get(ix);
				for (final Map.Entry<String, String> entry : obj.entrySet()) {
					String subCategory = categorySubCategoryMatchMap.get(entry.getKey());
					if (!StringUtils.isEmpty(subCategory) && subCategory.equals(entry.getValue()))
						continue;
					categorySubCategoryMatchMap.put(entry.getKey(), entry.getValue());
					if (categoryCountMap.containsKey(entry.getKey()) || categoryNames.contains(entry.getKey())) {
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
