package id.varianresep.bantu;


import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

public class KotakIcon extends android.support.v7.widget.AppCompatImageView{
    public KotakIcon(Context context) {
        super(context);
    }

    public KotakIcon(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public KotakIcon(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int height = getMeasuredHeight();
        setMeasuredDimension(height, height);
    }
}
