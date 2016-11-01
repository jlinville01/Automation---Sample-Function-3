public void validatePerformancePulse(String id) throws Exception
{
	stmt = con.createStatement();
	String runReset = "SELECT * FROM leader_evaluation WHERE manager_token_id = " + id + " ORDER BY uid DESC LIMIT 1";
	ResultSet result = stmt.executeQuery(runReset);
	this.con.commit();
	while (result.next())
	{
		id = result.getString("uid");
	}
	Thread.sleep(1000);
	runReset = "SELECT eq.sort_order, ero.option_text FROM leader_evaluation le JOIN evaluation_response er ON er.leader_evaluation_id = le.uid JOIN evaluation_response_option ero ON er.response_option_id = ero.uid JOIN evaluation_question eq ON ero.question_id = eq.uid WHERE le.uid = " 
			+ id + " ORDER BY eq.sort_order ASC;";
	result = stmt.executeQuery(runReset);
	this.con.commit();
	Integer i = 0;

	while (result.next())
	{
		// Fetch values from DB to compare
		String[] likert = {"Strongly Agree", "Strongly Agree", "Yes", "Yes"};
		String option_text = result.getString("option_text");
		if (!(option_text.equals(likert[i])))
		{
			System.out.println("Error validating Performance Pulse");
		}
		i++;
	}
	result.close();
}
