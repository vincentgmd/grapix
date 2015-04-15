package com.example.vin.grapix;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vin on 3/26/2015.
 */
public class PieChart extends Chart{
    int count;
    double sum;
    double mark;
    double refer;

    PieChart( ){
        super();
    }
    void draw(Canvas canvas){
        count=0;
        sum=0;
        while(count<valueList.size()){
            sum += valueList.get(count);
            count++;
        }
        count=0;
        mark=0;
        while(count<valueList.size()){
            paint.setColor(colorList.get(count%colorList.size()));
            refer=canvas.getWidth()>canvas.getHeight()?canvas.getHeight():canvas.getWidth();
            RectF f =new RectF(0,0,(float)refer,(float)refer);
            canvas.drawArc(f,(float) mark, (float)((valueList.get(count) * 360) / sum),true,paint);
            mark+= ((valueList.get(count) * 360) / sum);


            count++;
        }

    }



}
