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

		Album album = albumDatabase.getAlbum( pathHandler.getAlbumId() );
		if ( album == null || pathHandler.getPhotoIndex() > album.getImageFiles().size() )
		{
			// TODO log this
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
			// TODO log this
			response.sendError( HttpServletResponse.SC_NOT_FOUND );
			return;
		}
		
		
	}
}
