package ca.ianspix.api;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Singleton;

@Singleton
public class APIServlet extends HttpServlet
{
	// Second servlet for future use as a REST API. Currently used for various
	// tests.

	private static final long serialVersionUID = -2048773479000560799L;

	private static final Logger log = Logger.getLogger( APIServlet.class.getName() );

	@Override
	protected void doGet( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException
	{
		log.info( "API servlet called by " + req.getRemoteAddr() );
	}

}
