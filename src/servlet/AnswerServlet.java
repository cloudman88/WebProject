package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

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
 * Servlet implementation class AnswerServlet
 */
public class AnswerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AnswerServlet() {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getRequestURI();
		action = action.substring(action.indexOf("AnswerServlet") + "AnswerServlet".length() + 1);
		action = action.substring(0, action.length()-1);
		Context context;
		try {
			context = new InitialContext();
			BasicDataSource ds = (BasicDataSource)context.lookup(DBConstants.DB_DATASOURCE);
			Connection conn = ds.getConnection();
			if (action.equals("Answering"))
			{
				try{
				PreparedStatement pstmt = conn.prepareStatement(DBConstants.INSERT_ANSWERS_STMT);			
				pstmt.setString(1,request.getParameter("AnswerText"));
				pstmt.setString(2,request.getParameter("Nickname"));
				pstmt.setString(3,request.getParameter("Qid"));		
				pstmt.executeUpdate();
				
				///response
				response.getWriter().println(1);
				System.out.println("answering success!");	
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
		} catch (NamingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	}
