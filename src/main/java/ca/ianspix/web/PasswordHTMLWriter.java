package ca.ianspix.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import ca.ianspix.Util;
import ca.ianspix.data.Album;

public class PasswordHTMLWriter
{
	private static final Logger log = Logger.getLogger( PasswordHTMLWriter.class.getName() );

	private static final String TEMPLATE_RESOURCE = "ca/ianspix/web/password_template.txt";
	private static final String PARAM_PASSWORDHINT = "@@PASSWORDHINT";

	public void printHTML( Album album, int imageNum, boolean fromSubdir, PrintWriter writer ) throws AlbumException
	{
		int imageCount = album.getImageFiles().size();

		StringBuilder fileList = new StringBuilder();
		for ( int i = 0; i < imageCount; ++i )
		{
			fileList.append( "\t\"" + album.getImageFiles().get( i ) + "\"" + ( i < imageCount - 1 ? ",\n" : "\n" ) );
		}

		String template = loadTemplate();
		writer.print( template.replaceAll( PARAM_PASSWORDHINT, album.getPasswordHint() ) );
	}

	private String loadTemplate() throws AlbumException
	{
		InputStream in = PasswordHTMLWriter.class.getClassLoader().getResourceAsStream( TEMPLATE_RESOURCE );

		if ( in == null )
			throw new AlbumException( "Couldn't load album template" );

		String template = null;
		try
		{
			template = Util.readStream( in );
		}
		catch ( IOException e )
		{
			throw new AlbumException( "Couldn't load album template" );
		}
		finally
		{
			try
			{
				in.close();
			}
			catch ( IOException e )
			{
				log.log( Level.WARNING, "Exception closing input stream", e );
			}
			in = null;
		}

		return template;
	}

}
