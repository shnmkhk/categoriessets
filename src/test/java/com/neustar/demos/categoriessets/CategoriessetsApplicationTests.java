package com.neustar.demos.categoriessets;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:application.properties")
public class CategoriessetsApplicationTests {

	private static final String TEST_USER_002 = "Test User 002";
	private static final String TESTEMAIL_TEST_COM = "testemail@test.com";
	@Autowired
	private UserRepository userRepository;

	@Test
	public void saveUserTest() {

		WebsiteUser user = new WebsiteUser();
		user.setEmail(TESTEMAIL_TEST_COM);
		user.setName(TEST_USER_002);
	    userRepository.save(user);
	    
	    
	    // Read the same object
	    List<WebsiteUser> retrievdUsers = userRepository.findByName(TEST_USER_002);
	    if (retrievdUsers == null || retrievdUsers.size() <= 0) {
	    	Assert.fail("Unable to retrieve user object with given test criteria");
	    }
    	WebsiteUser retrievedUser = retrievdUsers.get(0);
    	Assert.assertEquals(TESTEMAIL_TEST_COM, retrievedUser.getEmail());
    	Assert.assertEquals(TEST_USER_002, retrievedUser.getName());
	}

}
