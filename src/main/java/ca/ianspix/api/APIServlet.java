package ca.ianspix.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.AmazonS3Client;
//import com.amazonaws.services.s3.model.Bucket;
import com.google.inject.Singleton;

@Singleton
public class APIServlet extends HttpServlet
{
	// Second servlet for future use as a REST API
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException
	{
//		AmazonS3 s3Client = new AmazonS3Client();
//		for ( Bucket bucket : s3Client.listBuckets() )
//		{
//			resp.getWriter().println( bucket.getName() );
//		}
	}

}
