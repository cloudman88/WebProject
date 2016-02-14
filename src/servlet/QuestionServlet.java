package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

import com.google.gson.Gson;
import model.Question;

import webApp.Constants.DBConstants;

/**
 * Servlet implementation class QuestionServlet
 */
public class QuestionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   // private static int numberingQ =0;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuestionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String action = request.getRequestURI();
		action = action.substring(action.indexOf("QuestionServlet") + "QuestionServlet".length() + 1);
		action = action.substring(0, action.length()-1);
		System.out.println("chekc111!");	
		
		Context context;
		try {
			context = new InitialContext();
			BasicDataSource ds = (BasicDataSource)context.lookup(DBConstants.DB_DATASOURCE);
			Connection conn = ds.getConnection();
			if (action.equals("Asking"))
			{
				try{
				HttpSession session = request.getSession();

				PreparedStatement pstmt = conn.prepareStatement(DBConstants.INSERT_QUESTIONS_STMT);
				String LoggedinNickname = (String) session.getAttribute("LoggedinNickname");
			
				pstmt.setString(1,request.getParameter("QuestionText"));
				pstmt.setString(2,LoggedinNickname);
				pstmt.setString(3,request.getParameter("TopicsText"));		
				pstmt.executeUpdate();
				
				///response
				response.getWriter().println(1);
				System.out.println("asking success!");	
				//commit update
				conn.commit();
				//close statements
				pstmt.close();
				//close connection
				conn.close();
				
				} catch (SQLException e) {
					getServletContext().log("Error while closing connection", e);
					response.sendError(500);//internal server error
				}
			}
			else if(action.equals("GetTwentyNewQ"))
			{
				Collection<Question> questionsResult = new ArrayList<Question>(); 
				System.out.println("inside GetTwentyNewQ!");	

					PreparedStatement stmt;
					try {
						stmt = conn.prepareStatement(DBConstants.SELECT_NEW_QUESTIONS_STMT);				
						ResultSet rs = stmt.executeQuery();
						while (rs.next()){
							questionsResult.add(new Question(rs.getString(1),rs.getString(2),rs.getString(3),
												rs.getString(4),rs.getString(5),rs.getTimestamp(6).getTime()));
						}
						rs.close();
						stmt.close();
					} catch (SQLException e) {
						getServletContext().log("Error while querying gtnq", e);
			    		response.sendError(500);//internal server error
					}
				Gson gson = new Gson();
		    	//convert from users collection to json
		    	String userJsonResult = gson.toJson(questionsResult, DBConstants.QUESTIONS_COLLECTION);

		    	response.getWriter().println(userJsonResult);
		    	response.getWriter().close();
			}
			
			else if(action.equals("NewlySubmittedQuestions"))
			{
				int startIndex=0;
				if(null==request.getParameter("count")){
					 startIndex=0;
				}
				else{
					startIndex=(Integer.parseInt(request.getParameter("count")));
				}
				startIndex=startIndex*2;
				Collection<Question> questionsResult = new ArrayList<Question>(); 
				System.out.println("inside nsq!");	
				System.out.println(startIndex);	
				PreparedStatement stmt;
				try {
					stmt = conn.prepareStatement(DBConstants.SELECT_NEWLY_SUBMITTED_QUESTIONS_STMT);				
					stmt.setInt(1,startIndex);
					ResultSet rs = stmt.executeQuery();
					while (rs.next()){
						questionsResult.add(new Question(rs.getString(1),rs.getString(2),rs.getString(3),
											rs.getString(4),rs.getString(5),rs.getTimestamp(6).getTime()));
					}
					rs.close();
					stmt.close();
				} catch (SQLException e) {
					getServletContext().log("Error while querying for NSQ", e);
		    		response.sendError(500);//internal server error
				}
				Gson gson = new Gson();
		    	//convert from users collection to json
		    	String userJsonResult = gson.toJson(questionsResult, DBConstants.QUESTIONS_COLLECTION);
		    	response.getWriter().println(userJsonResult);
		    	response.getWriter().close();
			}
			conn.close();
		} catch (NamingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		}		
}