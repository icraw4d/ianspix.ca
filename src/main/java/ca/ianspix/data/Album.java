package ca.ianspix.data;

import java.util.List;


public class Album
{
	private String title;
	private String password;
	private String passwordHint;
	private List<String> imageFiles;
	private String imagePath;
	private String fullPath;
	private String rawPath;
	
	public String getTitle()
	{
		return title;
	}
	
	public void setTitle( String title )
	{
		this.title = title;
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
	
	public List<String> getImageFiles()
	{
		return imageFiles;
	}
	
	public void setImageFiles( List<String> imageFiles )
	{
		this.imageFiles = imageFiles;
	}

	public String getImagePath()
	{
		return imagePath;
	}

	public void setImagePath( String imagePath )
	{
		this.imagePath = imagePath;
	}

	public String getFullPath()
	{
		return fullPath;
	}

	public void setFullPath( String fullPath )
	{
		this.fullPath = fullPath;
	}

	public String getRawPath()
	{
		return rawPath;
	}

	public void setRawPath( String rawPath )
	{
		this.rawPath = rawPath;
	}
}
