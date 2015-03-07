package ca.ianspix;

import ca.ianspix.api.APIServlet;
import ca.ianspix.data.AlbumDatabase;
import ca.ianspix.data.aws.AWSAlbumDatabase;
import ca.ianspix.web.AlbumServlet;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;

public class GuiceServletConfig extends GuiceServletContextListener
{
	@Override
	protected Injector getInjector()
	{
		return Guice.createInjector( new AbstractModule() {
			@Override
			protected void configure()
			{
				bind( AlbumDatabase.class ).to(  AWSAlbumDatabase.class );
			}
		},
		new ServletModule() {
			@Override
			protected void configureServlets() {
				serve( "/api/*" ).with( APIServlet.class );
				serve( "/*" ).with( AlbumServlet.class );
			}
		});
	}

}
