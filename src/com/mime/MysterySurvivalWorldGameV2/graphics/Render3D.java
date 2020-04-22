//Author: VividerAphid
package com.mime.MysterySurvivalWorldGameV2.graphics;

import com.mime.MysterySurvivalWorldGameV2.Game;

public class Render3D extends Render{
    
    public Render3D(int height, int width){
        super(height, width);
    }
    
    
    public void floor(Game game){
        
        double floorPosition = 8;
        double ceilingPosition = 8;
        double rotation = 0; //game.time / 100.0;
        double cosine = Math.cos(rotation);
        double sine = Math.sin(rotation);
        double forward = 0; //game.time / 10.0;
        double right = 0; //game.time / 10.0;
        
        for (int y=0; y< height; y++){
            double ceiling = (y - height / 2.0) / height;
            
            double z = floorPosition / ceiling;
            
            if(ceiling < 0){
                z = ceilingPosition / -ceiling;
            }
           
                        
            for(int x=0; x< width; x++){
                double depth = (x - width / 2.0) / height;
                depth *= z;
                double xx = depth * cosine + z * sine + right;
                double yy = z * cosine - depth * sine + forward;
                int xPix = (int) xx;
                int yPix = (int) yy;
                pixels[x+y*width] = (xPix & 15) * 16  | (yPix & 15) * 16  << 8;
            }
        }
    }
}