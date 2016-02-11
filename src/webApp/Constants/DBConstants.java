package webApp.Constants;

import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import java.util.Collection;
import model.Question;

public interface DBConstants {
	public final String USERS = "users";
	public final Type QUESTIONS_COLLECTION = new TypeToken<Collection<Question>>() {}.getType();
	//derby constants
	public final String DB_NAME = "WebProjectDB"; /// UsersDB
	public final String DB_DATASOURCE = "java:comp/env/jdbc/WebProjectDatasource"; /// UsersDatasource
	public final String PROTOCOL = "jdbc:derby:"; 
	//sql statements
	///USERS table
	public final String CREATE_USERS_TABLE = "CREATE TABLE USERS("   ///need to change table name to USER
			+ "Username varchar(10) PRIMARY KEY,"
			+ "Password varchar(8) NOT NULL,"
			+ "Nickname varchar(20) UNIQUE NOT NULL,"
			+ "Description varchar(50),"
			+ "Photo varchar(300),"
			+ "Rating real DEFAULT 0"
			+ ")";
	public final String INSERT_USER_STMT = "INSERT INTO USERS VALUES(?,?,?,?,?,DEFAULT)"; ///need to change name to USER
	///QUESTIONS table
	public final String CREATE_QUESTIONS_TABLE= "CREATE TABLE QUESTIONS("
			+ "Id Integer GENERATED ALWAYS AS IDENTITY PRIMARY KEY,"
			+ "QuestionText varchar(300) NOT NULL,"
			+ "Nickname varchar(20),"
			+ "Topics varchar(50),"
			+ "Rating real DEFAULT 0,"
			+ "Timestamp TIMESTAMP DEFAULT TIMESTAMP (CURRENT_DATE,CURRENT_TIME)," //// change date format
			+ "FOREIGN KEY (Nickname) REFERENCES USERS(Nickname))";
	public final String INSERT_QUESTIONS_STMT = "INSERT INTO QUESTIONS (QuestionText,Nickname,Topics,Rating,Timestamp)"
			+ " VALUES(?,?,?,DEFAULT,DEFAULT)";
	public final String SELECT_NEW_QUESTIONS_STMT = "SELECT * FROM QUESTIONS ";
	///ANSWERS table
	public final String CREATE_ANSWERS_TABLE= "CREATE TABLE ANSWERS("
				+ "Id Integer GENERATED ALWAYS AS IDENTITY PRIMARY KEY,"
				+ "AnswerText varchar(300) NOT NULL,"
				+ "Nickname varchar(20),"
				+ "QuestionRelated Integer,"
				+ "Rating real DEFAULT 0,"
				+ "Timestamp TIMESTAMP DEFAULT TIMESTAMP (CURRENT_DATE,CURRENT_TIME),"
				+ "FOREIGN KEY (Nickname) REFERENCES USERS(Nickname),"
				+ "FOREIGN KEY (QuestionRelated) REFERENCES QUESTIONS(Id))";
	public final String INSERT_ANSWERS_STMT = "INSERT INTO ANSWERS (AnswerText,Nickname,QuestionRelated,Rating,Timestamp)"
				+ " VALUES(?,?,?,DEFAULT,DEFAULT)";
	public final String SELECT_NEWLY_SUBMITTED_QUESTIONS_STMT = "SELECT * FROM QUESTIONS WHERE "  
			   + "NOT ID IS NULL AND NOT EXISTS("
			   + "SELECT * FROM ANSWERS "
			   + "WHERE QUESTIONS.ID = QuestionRelated) ORDER BY QUESTIONS.TIMESTAMP DESC "
			   + "OFFSET ? ROWS FETCH NEXT 20 ROWS ONLY";
//	public final String SELECT_USER_BY_NAME_STMT = "SELECT * FROM USER "+ "WHERE Username=?";
}