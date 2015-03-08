package ca.ianspix.web;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import ca.ianspix.data.Album;
import junit.framework.TestCase;

public class AlbumHTMLRendererTest extends TestCase
{
	public void testIt() throws AlbumException
	{
		int imageNum = 2;
		Album album = new Album();
		
		album.setId( "abc123" );
		album.setTitle( "IansPix" );
		
		List<String> imageFiles = new LinkedList<String>();
		imageFiles.add( "img1" );
		imageFiles.add( "img2" );
		imageFiles.add( "img3" );
		album.setImageFiles( imageFiles );
		album.setRawExtension( "dng" );

		AlbumHTMLRenderer render = new AlbumHTMLRenderer();
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PrintWriter pw = new PrintWriter( out );
		
		render.renderAlbum( album, imageNum, true, pw );
		pw.close(); // Forces flush
		
		String result = out.toString();
		
		// Better than nothing.
		assertTrue( result.contains( album.getId() ) );
		assertTrue( result.contains( album.getTitle() ) );
		assertTrue( result.contains( album.getRawExtension()) );
		for ( String file : imageFiles )
			assertTrue( result.contains( file ) );
	}	
	
}
