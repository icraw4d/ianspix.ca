package ca.ianspix.web;

import java.io.IOException;

import javax.servlet.ServletException;
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
	// TODO don't add the hash until the photo changes
	// TODO load JSON album config from S3 (securely)
	// TODO Figure out of path info is app config
	// TODO Support different extension for RAW files
	// TODO log stuff
	// TODO traffic monitoring/analytics
	// TODO make album image file size smaller
	// TODO Make some albums
	// TODO password
	// TODO AWS IAM users
	// TODO cache
	// TODO better template
	// TODO Root page
	// TODO Hide mobile address bar (looked into it - iOS 8 behaviour isn't friendly with single screen apps)
	// TODO figure out how to make www.ianspix.ca forward to ianspix.ca, not the other way around
	
	private static final long serialVersionUID = 1L;
	
	@Inject private AlbumDatabase albumDatabase;

	@Override
	protected void doGet( HttpServletRequest request,
			HttpServletResponse response ) throws ServletException, IOException
	{
		response.setContentType("text/html;charset=utf-8");
		
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
			// TODO log this
			response.sendError( HttpServletResponse.SC_NOT_FOUND );
			return;
		}

		// TODO cache HTML

		Album album = albumDatabase.getAlbum( pathHandler.getAlbumId() );
		if ( album == null )
		{
			// TODO log this
			response.sendError( HttpServletResponse.SC_NOT_FOUND );
			return;
		}

		// TODO inject this, or something.
		AlbumHTMLRenderer html = new AlbumHTMLRenderer();

		try
		{
			html.renderAlbum( album, pathHandler.getPhotoIndex(), pathHandler.isSubdir(), response.getWriter() );
		}
		catch ( AlbumException e )
		{
			// TODO log this
			response.sendError( HttpServletResponse.SC_NOT_FOUND );
			return;
		}
		
		
	}
}
