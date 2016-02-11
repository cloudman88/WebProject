package servlet;

import java.io.IOException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class AuthenticationServlet
 */
public class AuthenticationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AuthenticationServlet() {
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
		action = action.substring(action.indexOf("AuthenticationServlet") + "AuthenticationServlet".length() + 1);
		action=action.substring(0, action.length()-1);
		if(action.equals("Logout") ){
			try {
				Context context = new InitialContext();
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			HttpSession session = request.getSession();
			session.invalidate();
			response.getWriter().println(1);
			
		}
		else if(action.equals("Authenticate") )
		{
			HttpSession userSession = request.getSession();
			String usernameFromSession =(String) userSession.getAttribute("LoggedinUsername");
			
			if (usernameFromSession !=null ) 
			{
				response.getWriter().println("user authorized to enter");
				System.out.println("user validated");	
			}
			else
			{
				response.getWriter().println(0);
				System.out.println("user isnt valid to enter, redirect to login page");		
		}
		
	//	doGet(request, response);
	}		
	}
}