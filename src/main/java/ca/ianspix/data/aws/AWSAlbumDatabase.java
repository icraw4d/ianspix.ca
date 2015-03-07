package ca.ianspix.data.aws;

import java.util.LinkedList;
import java.util.List;

import ca.ianspix.data.Album;
import ca.ianspix.data.AlbumDatabase;

public class AWSAlbumDatabase implements AlbumDatabase
{

	@Override
	public Album getAlbum( String key )
	{
		if ( !"abc123".equals( key ) )
			return null;
		
		Album album = new Album();
		album.setTitle( "Family Ball" );
		album.setPassword( null );
		album.setPasswordHint( null );
		
		List<String> imageFiles = new LinkedList<String>();
		imageFiles.add( "familybball-1.jpg" );
		imageFiles.add( "familybball-2.jpg" );
		imageFiles.add( "familybball-3.jpg" );
		imageFiles.add( "familybball-4.jpg" );
		imageFiles.add( "familybball-5.jpg" );
		imageFiles.add( "familybball-6.jpg" );
		imageFiles.add( "familybball-7.jpg" );
		imageFiles.add( "familybball-8.jpg" );
		imageFiles.add( "familybball-9.jpg" );
		imageFiles.add( "familybball-10.jpg" );
		imageFiles.add( "familybball-11.jpg" );
		imageFiles.add( "familybball-12.jpg" );
		imageFiles.add( "familybball-13.jpg" );
		album.setImageFiles( imageFiles );
		
		album.setImagePath( "http://static.ianspix.ca/" + key + "/image" );
		album.setFullPath( "http://static.ianspix.ca/" + key + "/full" );
		album.setRawPath( "http://static.ianspix.ca/" + key + "/raw" );
		
		return album;
	}

}
