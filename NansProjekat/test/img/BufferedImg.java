package img;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BufferedImg {
	
	private BufferedImage img;
		
	public BufferedImg( String path )
	{
		load( path );
	}

	
	public void load(String path )
	{
		try {
			img = ImageIO.read(getClass().getResourceAsStream( path ));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public BufferedImage getImg()
	{
		return img;
	}
}
