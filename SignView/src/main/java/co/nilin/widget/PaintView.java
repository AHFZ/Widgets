package co.nilin.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class PaintView extends View {

    private final Paint paint = new Paint();

    private Canvas c;
    private Bitmap b;
    boolean flag = false;

    private Path touchPath;


    public PaintView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4);
        paint.setStrokeJoin(Paint.Join.ROUND);

        touchPath = new Path();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchPath.moveTo(x, y);
                c.drawCircle(x, y, 1, paint);
                break;
            case MotionEvent.ACTION_MOVE:
                touchPath.lineTo(x, y);
                break;
            case MotionEvent.ACTION_UP:
                touchPath.lineTo(x, y);
                c.drawPath(touchPath, paint);
                c.drawCircle(x, y, 1, paint);
                touchPath = new Path();
                break;

            default:
                return false;
        }
        invalidate();
        flag = true;
        return true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        b = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        c = new Canvas(b);
        c.drawColor(Color.WHITE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(b, 0, 0, paint);
        canvas.drawPath(touchPath, paint);
    }

    public Bitmap getPaint() {
        return b.copy(Bitmap.Config.ARGB_8888, true);
    }

    public boolean hasDrawn() {
        return flag;
    }

    public void clear() {
        b = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        c = new Canvas(b);
        c.drawColor(Color.WHITE);
        touchPath = new Path();
        invalidate();
        flag = false;
    }
}
