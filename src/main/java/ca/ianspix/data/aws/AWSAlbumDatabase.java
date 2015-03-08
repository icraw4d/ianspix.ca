package ca.ianspix.data.aws;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import ca.ianspix.Util;
import ca.ianspix.data.Album;
import ca.ianspix.data.AlbumDatabase;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

public class AWSAlbumDatabase implements AlbumDatabase
{
	private static final Logger log = Logger.getLogger( AWSAlbumDatabase.class.getName() );

	private static final String JSONKEY_ID = "id";
	private static final String JSONKEY_TITLE = "title";
	private static final String JSONKEY_IMAGEFILES = "imageFiles";
	private static final String JSONKEY_RAWEXTENSION = "rawExtension";

	@Override
	public Album getAlbum( String key )
	{
		AmazonS3 s3Client = new AmazonS3Client();
		InputStream in = null;
		JSONObject albumJSON = null;

		try
		{
			// TODO don't hardcode this
			in = s3Client.getObject( "static.ianspix.ca", "album/" + key + ".json" ).getObjectContent();
			albumJSON = new JSONObject( Util.readStream( in ) );
		}
		catch ( AmazonClientException awsException )
		{
			log.log( Level.WARNING, "Can't get album '" + key + "' from S3", awsException );
			return null;
		}
		catch ( IOException ioException )
		{
			log.log( Level.WARNING, "Can't get album '" + key + "' from S3", ioException );
			return null;
		}
		finally
		{
			try
			{
				if ( in != null )
					in.close();
			}
			catch ( IOException e )
			{
				log.log( Level.WARNING, "Exception closing input stream (album key '" + key + "')", e );
			}
			in = null;
		}

		Album album = new Album();

		if ( albumJSON.getString( JSONKEY_ID ) == null || !albumJSON.getString( JSONKEY_ID ).equals( key ) || 
				albumJSON.getString( JSONKEY_TITLE ) == null || 
				albumJSON.getJSONArray( JSONKEY_IMAGEFILES ) == null || albumJSON.getJSONArray( JSONKEY_IMAGEFILES ).length() == 0 || 
				albumJSON.getString( JSONKEY_RAWEXTENSION ) == null )
		{
			log.warning( "Album '" + key + "' is improperly formatted" );
			return null;
		}

		album.setId( albumJSON.getString( JSONKEY_ID ) );
		album.setTitle( albumJSON.getString( JSONKEY_TITLE ) );

		JSONArray imageFilesJSON = albumJSON.getJSONArray( JSONKEY_IMAGEFILES );

		List<String> imageFiles = new LinkedList<String>();
		for ( int i = 0; i < imageFilesJSON.length(); ++i )
			imageFiles.add( imageFilesJSON.getString( i ) );
		album.setImageFiles( imageFiles );
		album.setRawExtension( albumJSON.getString( JSONKEY_RAWEXTENSION ) );

		return album;
	}

}
