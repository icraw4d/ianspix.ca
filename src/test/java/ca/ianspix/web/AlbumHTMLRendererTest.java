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
		/*
		template = template.replaceAll( "@@TITLE", album.getTitle() );
		
		template = template.replaceAll( "@@ALBUMPATH", fromSubdir ? ".." : "." ); 
		template = template.replaceAll( "@@IMAGEPATH", album.getImagePath() );
		template = template.replaceAll( "@@FULLPATH", album.getFullPath() );
		template = template.replaceAll( "@@RAWPATH", album.getRawPath() );
		
		template = template.replaceAll( "@@FILELIST",  fileList.toString() );
		template = template.replaceAll( "@@IMAGEFILE", "" + album.getImageFiles().get( imageNum ) );
		
		template = template.replaceAll( "@@IMAGECOUNT", "" + imageCount );
		template = template.replaceAll( "@@IMAGENUM", "" + imageNum ); // TODO make sure imageNum is between 1 and album size
		template = template.replaceAll( "@@PREVIMAGENUM", "" + (imageNum > 1 ? imageNum - 1 : imageCount ) ); 
		template = template.replaceAll( "@@NEXTIMAGENUM", "" + (imageNum < imageCount ? imageNum + 1 : 1 ) ); 
		 */
		
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
