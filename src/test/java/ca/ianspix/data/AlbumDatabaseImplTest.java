package ca.ianspix.data;

import junit.framework.TestCase;

public class AlbumDatabaseImplTest extends TestCase
{
	public void testIt()
	{
		AlbumDatabaseImpl albumDatabase = new AlbumDatabaseImpl() {

			@Override
			public Album getAlbum( String key )
			{
				return parseJSON( "abc123",
							"{\n" +
							"\t\"id\": \"abc123\",\n" +
							"\t\"title\": \"Test Album\",\n" +
							"\n" +
							"\t\"password\": \"test password\",\n" +
							"\t\"passwordHint\": \"test hint\",\n" +
							"\t\n" +
							"\t\"imageFiles\": [\n" +
							"\t\t\"image-1\",\n" +
							"\t\t\"image-2\",\n" +
							"\t\t\"image-3\",\n" +
							"\t],\n" +
							"\n" +
							"\t\"rawExtension\": \"jpg\"\n" +
							"}\n" );
			}

		};
		
		Album album = albumDatabase.getAlbum( "abc123" );
		assertEquals( "abc123", album.getId() );
		assertEquals( "Test Album", album.getTitle() );
		assertEquals( "test password", album.getPassword() );
		assertEquals( "test hint", album.getPasswordHint() );
		assertEquals( 3, album.getImageFiles().size() );
		assertEquals( "image-1", album.getImageFiles().get( 0 ) );
		assertEquals( "image-2", album.getImageFiles().get( 1 ) );
		assertEquals( "image-3", album.getImageFiles().get( 2 ) );
		assertEquals( "jpg", album.getRawExtension() );
	}
}
