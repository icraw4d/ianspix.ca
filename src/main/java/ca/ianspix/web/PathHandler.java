package ca.ianspix.web;

public class PathHandler
{
	private String albumId;

	private boolean trailingSlash; // No trailing slash will get redirected.
	
	private boolean subdir; // Specifies whether the <photo index> format was used
	
	private int photoIndex;
	
	/**
	 * Accepts:
	 * - /<album id>
	 * - /<album id>/
	 * - /<album id>/<photo index>
	 * - /<album id>/<photo index>/
	 * 
	 * <album id> = exactly 6 characters (0-9, a-z)
	 * <photo index> = integer > 0
	 *  
	 * External logic will redirect URLs that don't feature a trailing slash.
	 *  
	 * @param pathInfo A path in one of the accepted forms
	 * @throws AlbumException Thrown if the path is not in the accepted form
	 */
	public PathHandler( String pathInfo ) throws AlbumException
	{
		// Basic check.
		if ( pathInfo == null || pathInfo.isEmpty() )
			throw new AlbumException( "Invalid album path: " + pathInfo );

		// Make sure it starts with /
		int offset = 0;
		int slashIndex = pathInfo.indexOf( '/', offset );
		if ( slashIndex != 0 )
			throw new AlbumException( "Invalid album path: " + pathInfo );
		
		offset = slashIndex + 1;
		slashIndex = pathInfo.indexOf( '/', offset );
		if ( slashIndex == -1 )
		{
			// Simple case: /<album id>
			if ( !setAlbumId( pathInfo.substring( offset ) ) )
				throw new AlbumException( "Invalid album path: " + pathInfo );
			photoIndex = 1;
			trailingSlash = false;
			subdir = false;
		}
		else
		{
			if ( !setAlbumId( pathInfo.substring( offset, slashIndex ) ) )
				throw new AlbumException( "Invalid album path: " + pathInfo );
		
			offset = slashIndex + 1;
			if ( offset == pathInfo.length() )
			{
				// /<album id>/
				photoIndex = 1;
				trailingSlash = true;
				subdir = false;
			}
			else
			{
				slashIndex = pathInfo.indexOf( '/', offset );
				if ( slashIndex == -1 )
				{
					// /<album id>/<photo index>
					if ( !setPhotoIndex( pathInfo.substring( offset ) ) )
						throw new AlbumException( "Invalid album path: " + pathInfo );
					trailingSlash = false;
					subdir = true;
				}
				else
				{
					// Hopefully /<album id>/<photo index>/ .  Make sure we're at the end of the string and we don't have /<album id>// .
					if ( slashIndex == offset || slashIndex != pathInfo.length() - 1 || !setPhotoIndex( pathInfo.substring( offset, slashIndex ) ) )
						throw new AlbumException( "Invalid album path: " + pathInfo );
					trailingSlash = true;
					subdir = true;
				}
			}
		}
	}
	
	private boolean setAlbumId( String pathPart )
	{
		if ( pathPart == null || pathPart.length() != 6 )
			return false;
		
		for ( int i = 0; i < 6; ++i )
		{
			char c = pathPart.charAt( i );
			if ( !( ('a' <= c && c <= 'z') || ('0' <= c && c <= '9') ) )
				return true;
		}

		albumId = pathPart;
		return true;
	}
	
	private boolean setPhotoIndex( String pathPart ) throws AlbumException
	{
		if ( pathPart == null || pathPart.length() == 0 )
			return false;

		int n = -1;
		try { n = Integer.parseInt( pathPart ); } catch ( NumberFormatException e ) { }

		// If invalid or out of range...
		if ( n <= 0 )
			return false;
		
		photoIndex = n;
			
		return true;
	}

	public String getAlbumId()
	{
		return albumId;
	}

	public int getPhotoIndex()
	{
		return photoIndex;
	}
	
	public boolean isSubdir()
	{
		return subdir;
	}

	public boolean isTrailingSlash()
	{
		return trailingSlash;
	}

}
