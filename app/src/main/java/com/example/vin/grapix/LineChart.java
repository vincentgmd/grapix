package com.example.vin.grapix;

import android.graphics.Canvas;
import android.graphics.Rect;


public class LineChart extends Chart{
    int count;
    float sum;
    double mark;
    int pheight;
    int big;
    double width;


    LineChart( ){
        super();
    }
    void draw(Canvas canvas){
        count=0;
        sum=0;
        big= valueList.get(0);
        while(count<valueList.size()){
            if(valueList.get(count)>big){
                big= valueList.get(count);
            }
            sum += valueList.get(count);
            count++;
        }
        count=0;

        width=canvas.getWidth()/(valueList.size()-1);
        mark = -width;
        pheight= valueList.get(0);
        while(count<valueList.size()){
            //paint.setColor(colorList.get(count%colorList.size()));
            //refer=canvas.getWidth()>canvas.getHeight()?canvas.getHeight():canvas.getWidth();

           // Rect r =new Rect ((int)mark,(int)(canvas.getHeight()-(((canvas.getHeight())*valueList.get(count))/big)),(int)(mark+width),((canvas.getClipBounds()).bottom));
            //Rect r =new Rect(0,0,100,100);
            canvas.drawLine((float)mark,(float)(canvas.getHeight()-(((canvas.getHeight())*pheight)/big)),(float)(mark+width),(canvas.getHeight()-(((canvas.getHeight())*valueList.get(count))/big)),paint);
            mark+= (width);
            pheight= valueList.get(count);


            count++;
        }

    }

}

