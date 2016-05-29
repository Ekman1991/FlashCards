package se.tda367.flashcards.models;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

import se.tda367.flashcards.R;

/**
 * Created by ZlatanH on 2016-05-22.
 */
public class BarChart extends View {
    private ArrayList<StatisticsItem> data = new ArrayList<StatisticsItem>();
    private Paint barPaint;
    private RectF area;

    private int totalItems;

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
        float xpad = (float) (getPaddingLeft() + getPaddingRight());
        float ypad = (float) (getPaddingTop() + getPaddingBottom());

        float ww = (float) w - xpad;
        float hh = (float) h - ypad;

        float diameter = Math.min(ww, hh);
        area = new RectF(
                0.0f,
                0.0f,
                diameter,
                diameter);
    }


    public void addItem(StatisticsItem newItem) {
        data.add(newItem);
        totalItems++;
    }
}
