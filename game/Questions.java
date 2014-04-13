package game;

public enum Questions {
	;
	private static final String[][] questions = loadQuestions();
	private static final String[][] loadQuestions() {
		return new String[][]{
				{"Who was the 16th president of the U.S.?",
					"R:Abraham Lincoln", 
					"W:Thomas Jeffreson", 
					"W:Apple",
					"W:Banana",
					"W:Barack Obama",
					"W:George Washington"},
				{"What is 2^4?",
					"R:16",
					"W:18",
					"W:27",
					"W:8",
					"W:14",
					"W:17"},
				{"In which ocean did the Titanic sink?",
					"R:North Atlantic Ocean",
					"W:South Atlantic Ocean",
					"W:Arctic Ocean",
					"W:Indian Ocean",
					"W:Japan"},
				{"Jupiter is named after which Greek God?",
					"R:Zeus",
					"W:Apollo",
					"W:Jupiter",
					"W:Hercules"},
				{"Solve for x: 2x - 5 = 5",
					 "R:5",
					 "W:6",
					 "W:2.5",
					 "W:10",
					 "W:Banana",
					 "W:0"
				},
				{"f(x) = x^2 - 2x + 3; Find f(-1)",
					"R:6",
					"W:12",
					"W:5",
					"W:2",
					"W:0"	
				},
				{
					"Which of the following countries is in Africa?",
					"R:Botswana",
					"W:Hong Kong",
					"W:Argentina",
					"W:Peru",
					"W:Moldova",
				},
				{
					"First President on the Radio",
					"R:Calvin Coolidge",
					"W:F.D.R",
					"W:LBJ",
					"W:Herbert Hoover"
				},
				{
					"Who unified Germany?",
					"R:Otto Van Bismark",
					"W:Hitler",
					"W:Nihal Virk",
					"W:Hiskban Nitanki"
				},
			};
	}
	public final static String[][] getQuestions() {
		return questions.clone();
	}
}
