package se.tda367.flashcards;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import se.tda367.flashcards.Singleton;
import se.tda367.flashcards.models.Deck;

/**
 * Created by ZlatanH on 2016-05-15.
 */
public class PieChart extends View {

    private Paint piePaint;

    private Deck currentDeck;
    float degreesUsed;
    private RectF shadowBounds;
    private RectF pieDiameter;
    //TODO Add the XML AttributeSet values
    //TODO Add text and shading to chart
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
    }

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
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        currentDeck = Singleton.getInstance().getFlashCards().getCurrentDeck();
        float total = currentDeck.getSize();
        //TODO Change implementation completely, make a new class or an inner class to store information for drawing
        for (int i = 0; i < 3; i++) {
            piePaint.setColor(0x77334455*(i+1));
            int amount = currentDeck.getAmountOfCardsWithDifficulty(i);
            canvas.drawArc(pieDiameter,
                    270 + degreesUsed,
                    (amount/total)*360,
                    true, piePaint);
            degreesUsed += (amount/total)*360;
        }
    }

    @Override
    protected void onMeasure(int w, int h) {
        setMeasuredDimension(w, h);
    }
}
