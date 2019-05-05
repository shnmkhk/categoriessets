## How to use the Categories Sets API 

- Run the following commands

        mvnw clean compile
        mvnw spring-boot:run

-   Make sure you open 4 Postman tabs open

       First-tab   >> Lists the available categories
       [GET]    http://localhost:8080/clist/
       
       Second-tab  >> Adds a new category to the listing
       [POST]   http://localhost:8080/clist/add
       with new category name sent in the body Eg. FOOD
       
       Third-tab   >> Removes the existing category from the listing
       [DELETE] http://localhost:8080/clist/remove Eg. FOOD
       
       Fourth-tab  >> Verify the set to skip invalid categories and return the occurrances count (considering the latest added/ deleted categories in the above 2nd and 3rd API calls
       [POST] 	http://localhost:8080/sets
       
       Eg Input:
       
       [
    		{"PERSON":"Bob Jones"},
    		{"PLACE":"Washington"},
    		{"PERSON":"Mary"},
    		{"COMPUTER":"Mac"},
    		{"LAPTOP":"HP"},
    		{"PERSON":"Bob Jones"},
    		{"OTHER":"Tree"},
    		{"ANIMAL":"Dog"},
    		{"PLACE":"Texas"},
    		{"FOOD":"Steak"},
    		{"ANIMAL":"Cat"},
    		{"LAPTOP":"Dell"}
    	]
    	
    	Eg Output:
    	
    	{
		    "PERSON": 3,
		    "PLACE": 2,
		    "COMPUTER": 1,
		    "OTHER": 1,
		    "ANIMAL": 2
		}
   