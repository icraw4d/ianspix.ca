package ca.ianspix.web;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
	private static final long serialVersionUID = -4501602231081925648L;

	private static final Logger log = Logger.getLogger( AlbumServlet.class.getName() );
	
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
			log.info( "Bad album request from " + request.getRemoteAddr() + ": " + request.getPathInfo() );
			response.sendError( HttpServletResponse.SC_NOT_FOUND );
			return;
		}

		Album album = albumDatabase.getAlbum( pathHandler.getAlbumId() );
		if ( album == null || pathHandler.getPhotoIndex() > album.getImageFiles().size() )
		{
			log.warning( "Can't load album '" + pathHandler.getAlbumId() + "'" );
			response.sendError( HttpServletResponse.SC_NOT_FOUND );
			return;
		}

		// TODO inject this?  Make it static?
		AlbumHTMLRenderer html = new AlbumHTMLRenderer();

		try
		{
			html.renderAlbum( album, pathHandler.getPhotoIndex(), pathHandler.isSubdir(), response.getWriter() );
		}
		catch ( AlbumException e )
		{
			log.log(  Level.WARNING, "Couldn't render album HTML (album id '" + pathHandler.getAlbumId() + "')", e );
			response.sendError( HttpServletResponse.SC_NOT_FOUND );
			return;
		}
		
		
	}
}
