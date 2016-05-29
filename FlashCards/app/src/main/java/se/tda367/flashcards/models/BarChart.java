package se.tda367.flashcards.models;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import se.tda367.flashcards.R;

/**
 * Created by ZlatanH on 2016-05-22.
 */
public class BarChart extends View {
    private Paint barPaint;

    private int[] locationOnScreen;

    private float xpad;
    private float ypad;
    private float widthOfBar;
    private float maxHeight;

    private ArrayList<StatisticsItem> bars = new ArrayList<StatisticsItem>();

    public BarChart(Context context, AttributeSet attributes) {
        super(context, attributes);
        init();
    }

    private void init() {
        barPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        barPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        xpad = (float) (getPaddingLeft() + getPaddingRight());
        ypad = (float) (getPaddingTop() + getPaddingBottom());

        float ww = (float) w - xpad;
        maxHeight = (float) h - ypad;

        widthOfBar = ww/30;
   }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float barPositionX = xpad;
        float barPositionY = ypad;

        //Change how distance between bars is calculated
        for (int i = 0; i < bars.size(); i++) {
            StatisticsItem currentItem = bars.get(i);
            float height = maxHeight-currentItem.getStatistic();
            canvas.drawRect(barPositionX, barPositionY,barPositionX+widthOfBar,height,barPaint);
            barPositionX += widthOfBar + 5;
            barPositionY += widthOfBar + 5;
        }
    }

    public void addItem(StatisticsItem newItem) {
        bars.add(newItem);
    }
}
