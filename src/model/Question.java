package model;

public class Question {

	private String Id, QuestionText, Nickname,Topics,Rating ; //customer "schema"
	private long Timestamp;
	public Question(String id, String questiontext, String nickname,String topics, String rating, long timestamp) {
		Id = id;
		QuestionText = questiontext;
		Nickname = nickname;
		Topics = topics;
		Timestamp = timestamp;
		Rating = rating;
	}

	public String getId() {
		return Id;
	}

	public String getQuestionText() {
		return QuestionText;
	}

	public String getNickname() {
		return Nickname;
	}
	
	public String getTopics() {
		return Topics;
	}
	
	public String getRating() {
		return Rating;
	}

}
