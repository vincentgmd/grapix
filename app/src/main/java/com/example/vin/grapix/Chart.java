package com.example.vin.grapix;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vin on 4/7/2015.
 */
public class Chart {
    List<Integer> valueList=new ArrayList<>();
    List<Integer> colorList=new ArrayList<>();
    Paint paint=new Paint();

    Chart( ){
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
        paint.setColor(colorList.get(0));
    }
    void draw(Canvas canvas) {

    }
    void draw(Canvas canvas,List<Integer> ivalueList){
            valueList=ivalueList;
            draw( canvas);
    }
    void draw(Canvas canvas,List<Integer> ivalueList,List<Integer> icolorList){
        valueList=ivalueList;
        colorList=icolorList;
        paint.setColor(colorList.get(0));
        draw( canvas);
    }


}
