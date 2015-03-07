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
		
		album.setTitle( "IansPix" );
		
		album.setImagePath( "http://static.ianspix.ca/abc123/image" );
		album.setRawPath( "http://static.ianspix.ca/abc123/raw" );
		album.setFullPath( "http://static.ianspix.ca/abc123/full" );
		
		List<String> imageFiles = new LinkedList<String>();
		imageFiles.add( "img1.jpg" );
		imageFiles.add( "img2.jpg" );
		imageFiles.add( "img3.jpg" );
		album.setImageFiles( imageFiles );

		// TODO test password
		album.setPassword( null );
		album.setPasswordHint( null );
		

		AlbumHTMLRenderer render = new AlbumHTMLRenderer();
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PrintWriter pw = new PrintWriter( out );
		
		render.renderAlbum( album, imageNum, true, pw );
		pw.close(); // Forces flush
		
		String result = out.toString();
		
		// Better than nothing.
		assertTrue( result.contains( album.getTitle() ) );
		assertTrue( result.contains( album.getImagePath() + "/" + album.getImageFiles().get(  imageNum - 1 ) ) );
		assertTrue( result.contains( album.getFullPath() + "/" + album.getImageFiles().get(  imageNum - 1 ) ) );
		assertTrue( result.contains( album.getRawPath() + "/" + album.getImageFiles().get(  imageNum - 1 ) ) );
		
		for ( String file : imageFiles )
		{
			assertTrue( result.contains( file ) );
		}
	}	
	
}
