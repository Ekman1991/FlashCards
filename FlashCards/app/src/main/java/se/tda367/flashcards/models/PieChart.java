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
 * Created by ZlatanH on 2016-05-15.
 */
public class PieChart extends View {

    private Paint piePaint;
    private Paint shadowPaint;

    float totalItems;
    float degreesUsed;

    private RectF shadowDiameter;
    private RectF pieDiameter;

    private List<StatisticsItem> slices = new ArrayList<StatisticsItem>();

    //AttributeSet takes variables from XML code set in the layout xml files
    public PieChart(Context context, AttributeSet attributes) {
        super(context, attributes);
        TypedArray array = context.getTheme().obtainStyledAttributes(
                attributes,
                R.styleable.PieChart,
                0, 0);
        try {
        } finally {
            array.recycle();
        }
        init();
    }

    private void init() {
        piePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        piePaint.setStyle(Paint.Style.FILL);
        degreesUsed = 0;
        totalItems = 0;

        shadowPaint = new Paint();
        shadowPaint.setColor(0xFFD9D9D9);
    }

    //calculates the size of the view
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        float xpad = (float)(getPaddingLeft() + getPaddingRight());
        float ypad = (float)(getPaddingTop() + getPaddingBottom());

        float ww = (float)w - xpad;
        float hh = (float)h - ypad;

        float diameter = Math.min(ww, hh);
        pieDiameter = new RectF(
                0.0f,
                0.0f,
                diameter,
                diameter);

        shadowDiameter = new RectF(
                pieDiameter.left+10,
                pieDiameter.top+10,
                pieDiameter.right+10,
                pieDiameter.bottom+10);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawOval(shadowDiameter,shadowPaint);

        //Calculates and draws the slices of the PieChart, with the starting angle
        //corresponding to 12 o' clock.
        for (int i = 0; i < slices.size(); i++) {
            StatisticsItem currentItem = slices.get(i);
            changePiePaint(currentItem);
            float amount = currentItem.getStatistic();
            canvas.drawArc(pieDiameter,
                    270 + degreesUsed,
                    (amount/totalItems)*360,
                    true, piePaint);
            degreesUsed += (amount/totalItems)*360;
        }
    }

    protected void onMeasure(int w, int h) {
        setMeasuredDimension(w, h);
    }

    public void addItem(StatisticsItem newItem) {
        slices.add(newItem);
        totalItems++;
    }

    public void changePiePaint(StatisticsItem item) {
        piePaint.setColor(item.getColor());
        piePaint.setShader(item.getShader());
    }
}
