package com.example.vin.grapix;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceView;
import android.view.View;
import android.widget.LinearLayout;




public class crafico extends ActionBarActivity {

    Bitmap bg = Bitmap.createBitmap(480, 800, Bitmap.Config.ARGB_8888);
    Canvas canvas= new Canvas(bg);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crafico);

}
    public void clearCanvas(View v) {
       // canvas.clearCanvas();

    }
    private void doDraw(Canvas canvas) {
        // Draw the background image. Operations on the Canvas accumulate
        // so this is like clearing the screen.
        canvas.drawBitmap(bg, 0, 0, null);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_crafico, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
