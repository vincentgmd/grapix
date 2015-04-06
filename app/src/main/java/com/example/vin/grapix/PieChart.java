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
public class PieChart{
    //Canvas canvas;
    List<Integer> valueList=new ArrayList<>();
    List<Integer> colorList=new ArrayList<>();
    Paint paint=new Paint();
    int count;
    float sum;
    double mark;
    int refer;
    PieChart( ){
        //canvas = icanvas;
        colorList.add(Color.RED);
        colorList.add(Color.BLUE);
        colorList.add(Color.YELLOW);
        colorList.add(Color.DKGRAY);
        colorList.add(Color.CYAN);
        colorList.add(Color.BLACK);
        colorList.add(Color.GREEN);
        colorList.add(Color.LTGRAY);
        colorList.add(Color.MAGENTA);
        colorList.add(Color.GRAY);
        colorList.add(Color.WHITE);



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
            RectF f =new RectF(0,0,refer,refer);
            canvas.drawArc(f,(float) mark, ((valueList.get(count) * 360) / sum),true,paint);
            mark+= ((valueList.get(count) * 360) / sum);


            count++;
        }

    }
    void draw(Canvas canvas,List<Integer> ivalueList){
        valueList=ivalueList;
        draw( canvas);


    }
    void draw(Canvas canvas,List<Integer> ivalueList,List<Integer> icolorList){
        valueList=ivalueList;
        colorList=icolorList;
        draw( canvas);


    }


}
