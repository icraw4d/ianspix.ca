package ca.ianspix.web;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ca.ianspix.data.Album;
import ca.ianspix.data.AlbumDatabase;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class AlbumServlet extends HttpServlet
{
	private static final long serialVersionUID = -4501602231081925648L;

	private static final String PARAM_PASSWORD = "password";
	private static final int SECONDS_IN_YEAR = 60*60*24*365;

	private static final Logger log = Logger.getLogger( AlbumServlet.class.getName() );

	@Inject
	private AlbumDatabase albumDatabase;

	@Override
	protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
	{
		String password = null;

		if ( request.getCookies() != null )
		{
			for ( Cookie cookie : request.getCookies() )
			{
				if ( cookie.getName().equals( PARAM_PASSWORD ) )
				{
					password = cookie.getValue();
					break;
				}
			}
		}

		handleRequest( request, response, password );
	}

	@Override
	protected void doPost( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException
	{
		handleRequest( req, resp, req.getParameter( PARAM_PASSWORD ) );
	}

	private void handleRequest( HttpServletRequest request, HttpServletResponse response, String providedPassword ) throws ServletException, IOException
	{
		response.setContentType( "text/html;charset=utf-8" );

		PathHandler pathHandler = null;
		try
		{
			pathHandler = new PathHandler( request.getPathInfo() );
			if ( !pathHandler.isTrailingSlash() )
			{
				response.sendRedirect( request.getRequestURL() + "/" );
				return;
			}
		}
		catch ( AlbumException e )
		{
			log.info( "Bad album request from " + request.getRemoteAddr() + ": " + request.getPathInfo() );
			response.sendError( HttpServletResponse.SC_NOT_FOUND );
			return;
		}
		
		setCookie( request, response, pathHandler, providedPassword );

		Album album = albumDatabase.getAlbum( pathHandler.getAlbumId() );
		if ( album == null || pathHandler.getPhotoIndex() > album.getImageFiles().size() )
		{
			log.warning( "Can't load album '" + pathHandler.getAlbumId() + "'" );
			response.sendError( HttpServletResponse.SC_NOT_FOUND );
			return;
		}

		if ( album.getPassword() != null )
		{
			if ( providedPassword == null )
			{
				// TODO inject this? Make it static?
				PasswordHTMLWriter html = new PasswordHTMLWriter();
				try
				{
					html.printHTML( album, pathHandler.getPhotoIndex(), pathHandler.isSubdir(), response.getWriter() );
				}
				catch ( AlbumException e )
				{
					log.log( Level.WARNING, "Couldn't render album HTML (album id '" + pathHandler.getAlbumId() + "')", e );
					response.sendError( HttpServletResponse.SC_NOT_FOUND );
				}
				return;
			}
			else if ( !album.getPassword().equals( providedPassword ) )
			{
				// TODO inject this? Make it static?
				PasswordHTMLWriter html = new PasswordHTMLWriter();
				try
				{
					html.printHTML( album, pathHandler.getPhotoIndex(), pathHandler.isSubdir(), response.getWriter() );
				}
				catch ( AlbumException e )
				{
					log.log( Level.WARNING, "Couldn't render album HTML (album id '" + pathHandler.getAlbumId() + "')", e );
					response.sendError( HttpServletResponse.SC_NOT_FOUND );
				}
				return;
			}
		}

		// TODO inject this? Make it static?
		AlbumHTMLWriter html = new AlbumHTMLWriter();

		try
		{
			html.printHTML( album, pathHandler.getPhotoIndex(), pathHandler.isSubdir(), response.getWriter() );
		}
		catch ( AlbumException e )
		{
			log.log( Level.WARNING, "Couldn't render album HTML (album id '" + pathHandler.getAlbumId() + "')", e );
			response.sendError( HttpServletResponse.SC_NOT_FOUND );
			return;
		}

	}

	private void setCookie( HttpServletRequest req, HttpServletResponse resp, PathHandler pathHandler, String providedPassword )
	{
		if ( providedPassword != null )
		{
			Cookie cookie = new Cookie( PARAM_PASSWORD, providedPassword );
			cookie.setMaxAge( SECONDS_IN_YEAR );
			cookie.setPath( req.getServletPath() + "/" + pathHandler.getAlbumId() );
			resp.addCookie( cookie );
		}
	}
}
