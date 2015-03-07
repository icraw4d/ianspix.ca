package ca.ianspix.web;

import junit.framework.TestCase;

public class PathHandlerTest extends TestCase
{
	public void testNoPhoto() throws AlbumException
	{
		PathHandler pathHandler = new PathHandler( "/abc123" );
		assertEquals( "abc123", pathHandler.getAlbumId() );
		assertFalse( pathHandler.isSubdir() );
		assertFalse( pathHandler.isTrailingSlash() );

		pathHandler = new PathHandler( "/abc123/" );
		assertEquals( "abc123", pathHandler.getAlbumId() );
		assertFalse( pathHandler.isSubdir() );
		assertTrue( pathHandler.isTrailingSlash() );
	}
	
	public void testPhoto() throws AlbumException
	{
		PathHandler pathHandler = new PathHandler( "/abc123/42" );
		assertEquals( "abc123", pathHandler.getAlbumId() );
		assertEquals( 42, pathHandler.getPhotoIndex() );		
		assertTrue( pathHandler.isSubdir() );
		assertFalse( pathHandler.isTrailingSlash() );

		pathHandler = new PathHandler( "/abc123/42/" );
		assertEquals( "abc123", pathHandler.getAlbumId() );
		assertEquals( 42, pathHandler.getPhotoIndex() );		
		assertTrue( pathHandler.isSubdir() );
		assertTrue( pathHandler.isTrailingSlash() );
}
	
	public void testBadPaths()
	{
		String[] badpaths = {
				"abc123",
				"/abc123//",
				"/abc1234",
				"/abc12",
				"",
				"/",
				"//",
				"/abc123/a",
				"/abc123/-1",
				"/abc123/0",
				"/abc123//42",
				"/abc123/42/a"
		};
		
		for ( String badpath : badpaths )
		{
			boolean exceptionThrown = false;
			try
			{
				new PathHandler( badpath );
			}
			catch ( AlbumException e )
			{
				exceptionThrown = true;
			}
			assertTrue( exceptionThrown );
		}
		
	}
	
	
}
