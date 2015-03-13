package ca.ianspix.data;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ca.ianspix.data.Album;
import ca.ianspix.data.AlbumDatabase;

public abstract class AlbumDatabaseImpl implements AlbumDatabase
{
	private static final Logger log = Logger.getLogger( AlbumDatabaseImpl.class.getName() );

	private static final String JSONKEY_ID = "id";
	private static final String JSONKEY_TITLE = "title";
	private static final String JSONKEY_PASSWORD = "password";
	private static final String JSONKEY_PASSWORDHINT = "passwordHint";
	private static final String JSONKEY_IMAGEFILES = "imageFiles";
	private static final String JSONKEY_RAWEXTENSION = "rawExtension";

	protected Album parseJSON( String key, String json )
	{
		JSONObject albumJSON = null;

		try
		{
			albumJSON = new JSONObject( json );
		}
		catch ( JSONException jsonException )
		{
			log.log( Level.WARNING, "Invalid album JSON", jsonException );
			return null;
		}

		Album album = new Album();

		try
		{
			album.setId( albumJSON.getString( JSONKEY_ID ) );
			album.setTitle( albumJSON.getString( JSONKEY_TITLE ) );
	
			JSONArray imageFilesJSON = albumJSON.getJSONArray( JSONKEY_IMAGEFILES );
	
			List<String> imageFiles = new LinkedList<String>();
			for ( int i = 0; i < imageFilesJSON.length(); ++i )
				imageFiles.add( imageFilesJSON.getString( i ) );
			album.setImageFiles( imageFiles );
			album.setRawExtension( albumJSON.getString( JSONKEY_RAWEXTENSION ) );
			
			if ( !album.getId().equals( key ) || album.getImageFiles().isEmpty() )
			{
				log.warning( "Album '" + key + "' is improperly formatted" );
				return null;
			}
		}
		catch ( JSONException e )
		{
			log.warning( "Album '" + key + "' is improperly formatted" );
			return null;
		}
		
		// Optional parameters, eat the exceptions
		try
		{
			album.setPassword( albumJSON.getString( JSONKEY_PASSWORD ) );
			album.setPasswordHint( albumJSON.getString( JSONKEY_PASSWORDHINT ) );
		}
		catch ( JSONException e )
		{
			// Eat it.
		}

		return album;
	}

}
