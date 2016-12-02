/**
 * As a Manager account complete a four-question quiz.
 *
 * @param id	the managers id
 */
public void validateResponse(String id) throws Exception
{
	try
	{
		stmt = con.createStatement();
		
		// Get the token for a Manager account using their ID
		String searchForManager = "SELECT * FROM leader_evaluation WHERE manager_token_id = " + id + " ORDER BY uid DESC LIMIT 1";
		ResultSet result = stmt.executeQuery(searchForManager);
		this.con.commit();
		while (result.next())
		{
			id = result.getString("uid");
		}
		
		// Get the responses results for a Manager who completed a four-question test
		runReset = "SELECT eq.sort_order, ero.option_text FROM leader_evaluation le JOIN evaluation_response er ON er.leader_evaluation_id = le.uid JOIN evaluation_response_option ero ON er.response_option_id = ero.uid JOIN evaluation_question eq ON ero.question_id = eq.uid WHERE le.uid = " 
				+ id + " ORDER BY eq.sort_order ASC;";
		result = stmt.executeQuery(runReset);
		this.con.commit();
		
		// Compare the values from likert array to values saved in database
		Integer i = 0;
		while (result.next())
		{
			// Fetch values from DB to compare
			String[] likert = {"1", "2", "3", "4"};
			String option_text = result.getString("option_text");
					
			// Assert values
			assertThat(option_text, is(likert[i]));
			
			i++;
		}
		result.close();
	}
	catch (Exception exc)
	{
		exc.printStackTrace();
		result.close();
	}
}
