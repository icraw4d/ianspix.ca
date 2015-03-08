package ca.ianspix;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Util
{
	public static String readStream( InputStream in ) throws IOException
	{
		BufferedReader reader = new BufferedReader( new InputStreamReader( in ) );
		StringBuilder sb = new StringBuilder();
		String line;
		while ( ( line = reader.readLine() ) != null )
		{
			sb.append( line + "\n" );
		}
		return sb.toString();
	}
}
