package app;
import img.BufferedImg;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import math.MathUtill;
import collision.Scene;

public class App extends Canvas implements Runnable {

	public static final int WIDTH = 1024, HEIGHT = WIDTH*9/16;
	public static final float FPS = 120.0f;
	public static final float DT =  1/FPS ;
	public static BufferedImage img;
	
	private boolean running = false;
	private Thread thread;
	private Input input;
	private Scene scene;
    private Frame frame;
	
	public App( Scene scene ) 
	{
		img = new BufferedImg("/img/ball4.png").getImg();
		this.scene = scene;
		setIgnoreRepaint( true );
		frame = new Frame( WIDTH, HEIGHT, "Basket", this );
		input = new Input( scene, this );
		
		Objects.load( scene );
		
		start();
	}
	
	public synchronized void start()
	{
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop(){
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		running = false;
	}
	
	@Override		
	public void run()
	{	
	      long initialTime = System.nanoTime();
	      float accumulator = 0;
	      
	      while ( running ) 
	      {  
	         long currentTime = System.nanoTime();
	         accumulator += (currentTime - initialTime) / 1000000000.0;
	         initialTime = currentTime;
	   
	         accumulator = MathUtill.clamp( 0.0f, 0.1f, accumulator );
	         
	         while( accumulator > DT )
	         {
	        	 scene.step();
	        	 accumulator -= DT;
	         }
	         render();
	      }
		stop();
	}

	private void render()
	{
        BufferStrategy bufferStrategy = this.getBufferStrategy();
        if(bufferStrategy == null)
        {
        	this.createBufferStrategy( 3 );
        	return;
        }
        
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        
        g.setColor( Color.lightGray );
        g.fillRect( 0, 0, WIDTH, HEIGHT );
        scene.render(g);
        
		input.renderMouseActions( g );
        
        g.dispose();
        bufferStrategy.show();
        Toolkit.getDefaultToolkit().sync();
	}
	
	public static void main( String[] args ) {
		
		new App( new Scene( DT, 50 ) );
	}
	
}

