package com.example.vin.grapix;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * Created by vin on 4/6/2015.
 */
public class BlockChart extends Chart{
    int count;
    double sum;
    double mark;
    double big;
    double width;


    BlockChart( ){
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
            //Rect r =new Rect(L,T,R,B);
            canvas.drawRect(r,paint);
            mark+= (width);


            count++;
        }

    }

}
