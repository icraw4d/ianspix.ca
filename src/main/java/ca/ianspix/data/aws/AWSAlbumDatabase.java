package ca.ianspix.data.aws;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import ca.ianspix.Util;
import ca.ianspix.data.Album;
import ca.ianspix.data.AlbumDatabaseImpl;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

public class AWSAlbumDatabase extends AlbumDatabaseImpl
{
	private static final Logger log = Logger.getLogger( AWSAlbumDatabase.class.getName() );

	@Override
	public Album getAlbum( String key )
	{
		AmazonS3 s3Client = new AmazonS3Client();
		InputStream in = null;

		try
		{
			// TODO don't hardcode this
			in = s3Client.getObject( "static.ianspix.ca", "album/" + key + ".json" ).getObjectContent();
			return parseJSON( key, Util.readStream( in ) );
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
	}

}
