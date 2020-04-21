//Author: VividerAphid
package com.mime.MysterySurvivalWorldGameV2;

import java.awt.Canvas;
import javax.swing.JFrame;
import com.mime.MysterySurvivalWorldGameV2.graphics.Render;

public class Display extends Canvas implements Runnable {
    
    private static final long serialVersionUID = 1L;
    
    
    public static int WIDTH = 800;
    public static int HEIGHT = 600;
    public static final String TITLE = "MysterySurvivalWorldGame Pre-Alpha 0.01";
    
    private Thread thread;
    private boolean running = false;
    private Render render;
    
    public Display(){
        render = new Render(WIDTH, HEIGHT);
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
        while(running){
            tick();
            render();
        }
    }
    
    private void tick(){
        
    }
    
    private void render(){
        
    }
    
    public static void main(String[] args){
        Display game = new Display();
        JFrame frame = new JFrame();
        frame.add(game);
        frame.pack();
        frame.setTitle(TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setResizable(false);
        frame.setVisible(true);
        
        System.out.println("Running...");
        
        game.start();
    }
    
}