package ca.ianspix.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import ca.ianspix.Util;
import ca.ianspix.data.Album;

public class AlbumHTMLRenderer
{

	private static final String TEMPLATE_RESOURCE = "ca/ianspix/web/album_template.txt";
	private static final String PARAM_ALBUMID = "@@ALBUMID";
	private static final String PARAM_TITLE = "@@TITLE";
	private static final String PARAM_ALBUMPATH = "@@ALBUMPATH";
	private static final String PARAM_FILELIST = "@@FILELIST";
	private static final String PARAM_IMAGEFILE = "@@IMAGEFILE";
	private static final String PARAM_RAWEXTENSION = "@@RAWEXTENSION";
	private static final String PARAM_IMAGECOUNT = "@@IMAGECOUNT";
	private static final String PARAM_IMAGENUM = "@@IMAGENUM";
	private static final String PARAM_PREVIMAGENUM = "@@PREVIMAGENUM";
	private static final String PARAM_NEXTIMAGENUM = "@@NEXTIMAGENUM";

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
				.replaceAll( PARAM_ALBUMID, album.getId() )
				.replaceAll( PARAM_TITLE, album.getTitle() )

				.replaceAll( PARAM_ALBUMPATH, fromSubdir ? ".." : "." )
				
				.replaceAll( PARAM_FILELIST, fileList.toString() )
				.replaceAll( PARAM_IMAGEFILE, "" + album.getImageFiles().get( imageNum - 1 ) )
				.replaceAll( PARAM_RAWEXTENSION, "" + album.getRawExtension() )

				.replaceAll( PARAM_IMAGECOUNT, "" + imageCount )
				.replaceAll( PARAM_IMAGENUM, "" + imageNum )
				.replaceAll( PARAM_PREVIMAGENUM, "" + ( imageNum > 1 ? imageNum - 1 : imageCount ) )
				.replaceAll( PARAM_NEXTIMAGENUM, "" + ( imageNum < imageCount ? imageNum + 1 : 1 ) ) );
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
			template = Util.readStream( in );
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

}
