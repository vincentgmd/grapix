package com.example.vin.grapix;

import android.graphics.Canvas;
import android.graphics.Rect;


public class LineChart extends Chart{
    int count;
    float sum;
    double mark;
    int space;
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
        mark=0;
        width=canvas.getWidth()/valueList.size();
        while(count<valueList.size()){
            paint.setColor(colorList.get(count%colorList.size()));
            //refer=canvas.getWidth()>canvas.getHeight()?canvas.getHeight():canvas.getWidth();

            Rect r =new Rect ((int)mark,(int)(canvas.getHeight()-(((canvas.getHeight())*valueList.get(count))/big)),(int)(mark+width),((canvas.getClipBounds()).bottom));
            //Rect r =new Rect(0,0,100,100);
            canvas.drawRect(r,paint);
            mark+= (width);


            count++;
        }

    }

}

