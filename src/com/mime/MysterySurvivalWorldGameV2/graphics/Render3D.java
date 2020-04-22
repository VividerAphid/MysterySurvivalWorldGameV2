//Author: VividerAphid
package com.mime.MysterySurvivalWorldGameV2.graphics;

public class Render3D extends Render{
    
    public Render3D(int height, int width){
        super(height, width);
    }
    
    public void floor(){
        for (int y=0; y< height; y++){
            double yDepth = y - height / 2;
            double z = 100.0 / yDepth;
            
            for(int x=0; x< width; x++){
                double xDepth = x - width / 2;
                xDepth *= z;
                int xx = (int) (xDepth) & 5;
                pixels[x+y*width] = xx * 128;
            }
        }
    }
}