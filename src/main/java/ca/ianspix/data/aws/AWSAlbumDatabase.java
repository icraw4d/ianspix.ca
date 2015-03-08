package ca.ianspix.data.aws;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import ca.ianspix.data.Album;
import ca.ianspix.data.AlbumDatabase;

public class AWSAlbumDatabase implements AlbumDatabase
{

	@Override
	public Album getAlbum( String key )
	{
		
		if ( !"abc123".equals( key ) )
			return null;
		
		
		JSONObject albumJSON = new JSONObject( "{\n\t\"id\": \"abc123\",\n\t\"title\": \"Family Ball\",\n\t\n\t\"imageFiles\": [\n\t\t\"familybball-1.jpg\",\n\t\t\"familybball-2\",\n\t\t\"familybball-3\",\n\t\t\"familybball-4\",\n\t\t\"familybball-5\",\n\t\t\"familybball-6\",\n\t\t\"familybball-7\",\n\t\t\"familybball-8\",\n\t\t\"familybball-9\",\n\t\t\"familybball-10\",\n\t\t\"familybball-11\",\n\t\t\"familybball-12\",\n\t\t\"familybball-13\"\n\t],\n\n\t\"rawExtension\": \"jpg\"\n}\n" );
		Album album = new Album();
		
		album.setId( albumJSON.getString( "id" ) );
		album.setTitle( albumJSON.getString( "title" ) );
		
		JSONArray imageFilesJSON = albumJSON.getJSONArray( "imageFiles" );

		List<String> imageFiles = new LinkedList<String>();
		for ( int i = 0; i < imageFilesJSON.length(); ++i )
			imageFiles.add( imageFilesJSON.getString( i ) );
		album.setImageFiles( imageFiles );
		album.setRawExtension( albumJSON.getString( "rawExtension" ) );

		/*
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
		*/
		
		return album;
	}

}
