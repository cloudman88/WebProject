package servlet;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

import sun.print.resources.serviceui_es;
import webApp.Constants.DBConstants;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getRequestURI();
		action = action.substring(action.indexOf("LoginServlet") + "LoginServlet".length() + 1);
		action = action.substring(0, action.length()-1);
		
	if (action.equals("Register"))
	{
		try{
		Context context = new InitialContext();
		BasicDataSource ds = (BasicDataSource)context.lookup(DBConstants.DB_DATASOURCE);
		Connection conn = ds.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(DBConstants.INSERT_USER_STMT);
		boolean isNameAvailable = isAvailable(request.getParameter("Username"),"Username",conn);
		boolean isNicknameAvailable = isAvailable(request.getParameter("Nickname"),"NickName",conn);
		if (isNameAvailable && isNicknameAvailable ) 
		{
			pstmt.setString(1,request.getParameter("Username"));
			pstmt.setString(2,request.getParameter("Password"));
			pstmt.setString(3,request.getParameter("Nickname"));
			pstmt.setString(4,request.getParameter("Description"));
			pstmt.setString(5,request.getParameter("Photo"));
	
			pstmt.executeUpdate();
			response.getWriter().println("register success");
			System.out.println("reg suc!");	
		}
		else if (!isNameAvailable && isNicknameAvailable)
		{
			System.out.println("The Username:"+request.getParameter("Username")+ "isnt available, please choose a different Username");
			response.getWriter().println("The Username: '"+request.getParameter("Username")+ "' isnt available, please choose a different Username");
		}
		else if (isNameAvailable && !isNicknameAvailable)
		{
			System.out.println("The Nickname:"+request.getParameter("Nickname")+ "isnt available, please choose a different Nickname");
			response.getWriter().println("The Nickname: '"+request.getParameter("Nickname")+ "' isnt available, please choose a different Nickname");
		}
		else
		{
			System.out.println("both nickname and username arent available");
			response.getWriter().println("The Nickname: '"
										+ request.getParameter("Nickname")
										+ "' and Username: '"
										+ request.getParameter("Username") 
										+ "' arent available, please choose a different Nickname and Username");
		}
    	response.getWriter().close();
		//commit update
		conn.commit();
		//close statements
		pstmt.close();
		//close connection
		conn.close();
		} catch (SQLException | NamingException e) {
			getServletContext().log("Error while closing connection", e);
			response.sendError(500);//internal server error
		}	
	}
	else if (action.equals("Login")) 
	{ 
		try {
			Context context = new InitialContext();
			BasicDataSource ds = (BasicDataSource)context.lookup(DBConstants.DB_DATASOURCE);
			Connection conn = ds.getConnection();
			//String res = isNameExist(request.getParameter("Name"),"Name",conn);
			String res="SELECT * FROM USERS WHERE Username = '"+request.getParameter("Username")+ "' AND Password = '"+request.getParameter("Password")+"'";
			final PreparedStatement ps = conn.prepareStatement(res);	
			final ResultSet resultSet = ps.executeQuery();
			if (resultSet.next()) {		
				 System.out.println("Login Success!");
				 HttpSession session = request.getSession();
				 session.setAttribute("LoggedinUsername", resultSet.getString("Username"));
				 session.setAttribute("LoggedinNickname", resultSet.getString("Nickname"));
				 response.getWriter().println(7);
			}
			else
			{
				System.out.println("wrong Username and/or password");
				response.getWriter().println("Wrong Username and/or Password");
			}
		 	response.getWriter().close();
			//commit update
			conn.commit();
			//close statements
			ps.close();
			//close connection
			conn.close();
			}		
		catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	//	response.sendRedirect("index.html");
	////the user will be shown the main application’s UI
	//response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	
	public Boolean isAvailable(String name, String columnname,Connection conn) throws NamingException, SQLException
	{
		boolean returnValue=true;
		final String queryCheck = "SELECT count(*) FROM USERS WHERE " +columnname+ " = "+"'"+name+"'";
		final PreparedStatement ps = conn.prepareStatement(queryCheck);	
		final ResultSet resultSet = ps.executeQuery();
		if(resultSet.next()) {
			if (resultSet.getInt(1) >0)
				returnValue =  false;	// if false, field already exist
		}
		ps.close();
		return returnValue;
	}
}