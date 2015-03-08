package ca.ianspix.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Singleton;

@Singleton
public class APIServlet extends HttpServlet
{
	// Second servlet for future use as a REST API.  Currently used for various tests.
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException
	{
	}

}
