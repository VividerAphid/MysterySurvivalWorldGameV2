//Author: VividerAphid
package com.mime.MysterySurvivalWorldGameV2;

import java.awt.Canvas;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.BufferStrategy;
import java.awt.Graphics;
import javax.swing.JFrame;
import com.mime.MysterySurvivalWorldGameV2.graphics.Render;
import com.mime.MysterySurvivalWorldGameV2.graphics.Screen;
import java.awt.Dimension;

public class Display extends Canvas implements Runnable {
    
    private static final long serialVersionUID = 1L;
    
    
    public static int WIDTH = 800;
    public static int HEIGHT = 600;
    public static final String TITLE = "MysterySurvivalWorldGame Pre-Alpha 0.01";
    
    private Thread thread;
    private boolean running = false;
    private Screen screen;
    private Game game;
    private BufferedImage img;
    private int[] pixels;
    
    public Display(){ 
        Dimension size = new Dimension(WIDTH, HEIGHT);
        screen = new Screen(WIDTH, HEIGHT);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        game = new Game();
        img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt)img.getRaster().getDataBuffer()).getData();
    }
    
    private void start(){
        if (running) 
            return;
        running = true;
        thread = new Thread(this);
        thread.start();
        
        //System.out.println("Start method complete");
    }
    
    private void stop(){
        if(!running)
            return;
        running = false;
        try{
        thread.join();
        } catch (Exception e){
            e.printStackTrace();
            System.exit(0);
        }
        
        //System.out.println("Stop method complete");
    }
    
    public void run(){
        int frames = 0;
        double unprocessedSeconds = 0;
        long previousTime = System.nanoTime();
        double secondsPerTick = 1 / 60.0;
        int tickCount = 0;
        boolean ticked = false;
        
        while(running){
            long currentTime = System.nanoTime();
            long passedTime = currentTime - previousTime;
            previousTime = currentTime;
            unprocessedSeconds += passedTime / 1000000000.0;
            
            while(unprocessedSeconds > secondsPerTick){
                tick();
                unprocessedSeconds -= secondsPerTick;
                ticked = true;
                tickCount++;
                if(tickCount % 60 == 0){
                    System.out.println(frames + " fps");
                    previousTime += 1000;
                    frames = 0;
                }
            }
            if(ticked){
                render();
                frames++;
            }
            render();
            frames++;
        }
    }
    
    private void tick(){
        game.tick();
    }
    
    private void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs==null){
            createBufferStrategy(3);
            return;
        }
        
        screen.render(game);
        
        for(int i = 0; i<WIDTH*HEIGHT; i++){
            pixels[i] = screen.pixels[i];
        }
        
        Graphics g = bs.getDrawGraphics();
        g.drawImage(img, 0,0, WIDTH + 50, HEIGHT + 50, null);
        g.dispose();
        bs.show();
    }
    
    public static void main(String[] args){
        Display game = new Display();
        JFrame frame = new JFrame();
        frame.add(game);
        frame.pack();
        frame.setTitle(TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setSize(WIDTH, HEIGHT);
        frame.setResizable(false);
        frame.setVisible(true);
        
        System.out.println("Running...");
        
        game.start();
    }
    
}