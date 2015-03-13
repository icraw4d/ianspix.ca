package ca.ianspix.data;

import java.util.List;


public class Album
{
	private String id;
	private String title;
	
	private String password;
	private String passwordHint;
	
	private List<String> imageFiles;
	private String rawExtension;
	
	public String getId()
	{
		return id;
	}

	public void setId( String id )
	{
		this.id = id;
	}

	public String getTitle()
	{
		return title;
	}
	
	public void setTitle( String title )
	{
		this.title = title;
	}
	
	public List<String> getImageFiles()
	{
		return imageFiles;
	}
	
	public void setImageFiles( List<String> imageFiles )
	{
		this.imageFiles = imageFiles;
	}

	public String getRawExtension()
	{
		return rawExtension;
	}

	public void setRawExtension( String rawExtension )
	{
		this.rawExtension = rawExtension;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword( String password )
	{
		this.password = password;
	}

	public String getPasswordHint()
	{
		return passwordHint;
	}

	public void setPasswordHint( String passwordHint )
	{
		this.passwordHint = passwordHint;
	}
}
