package ca.ianspix.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import ca.ianspix.data.Album;

public class AlbumHTMLRenderer
{

	private static final String TEMPLATE_RESOURCE = "ca/ianspix/web/album_template.txt";

	public void renderAlbum( Album album, int imageNum, boolean fromSubdir, PrintWriter writer ) throws AlbumException
	{
		int imageCount = album.getImageFiles().size();

		StringBuilder fileList = new StringBuilder();
		for ( int i = 0; i < imageCount; ++i )
		{
			fileList.append( "\t\"" + album.getImageFiles().get( i ) + "\"" + ( i < imageCount - 1 ? ",\n" : "\n" ) );
		}

		String template = loadTemplate();
		writer.print( template
				.replaceAll( "@@ALBUMID", album.getId() )
				.replaceAll( "@@TITLE", album.getTitle() )

				.replaceAll( "@@ALBUMPATH", fromSubdir ? ".." : "." )
				
				.replaceAll( "@@FILELIST", fileList.toString() )
				.replaceAll( "@@IMAGEFILE", "" + album.getImageFiles().get( imageNum - 1 ) )
				.replaceAll( "@@RAWEXTENSION", "" + album.getRawExtension() )

				.replaceAll( "@@IMAGECOUNT", "" + imageCount )
				.replaceAll( "@@IMAGENUM", "" + imageNum )
				.replaceAll( "@@PREVIMAGENUM", "" + ( imageNum > 1 ? imageNum - 1 : imageCount ) )
				.replaceAll( "@@NEXTIMAGENUM", "" + ( imageNum < imageCount ? imageNum + 1 : 1 ) ) );
	}

	private String loadTemplate() throws AlbumException
	{
		InputStream in = AlbumHTMLRenderer.class.getClassLoader().getResourceAsStream( TEMPLATE_RESOURCE );

		if ( in == null )
			// TODO log me
			throw new AlbumException( "Couldn't load album template" );

		String template = null;
		try
		{
			template = readStream( in );
		}
		catch ( IOException e )
		{
			// TODO log me
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
				// TODO log me (WARNING)
			}
			in = null;
		}

		return template;
	}

	private static String readStream( InputStream in ) throws IOException
	{
		BufferedReader reader = new BufferedReader( new InputStreamReader( in ) );
		StringBuilder sb = new StringBuilder();
		String line;
		while ( ( line = reader.readLine() ) != null )
		{
			sb.append( line + "\n" );
		}
		return sb.toString();
	}
}
