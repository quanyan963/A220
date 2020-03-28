package com.txtled.gpa220.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.txtled.gpa220.R;

import static com.txtled.gpa220.utils.Constants.ONE;
import static com.txtled.gpa220.utils.Constants.TWO;

/**
 * Created by Mr.Quan on 2020/3/11.
 */
@SuppressLint("AppCompatCustomView")
public class CustomTextView extends TextView {
    public CustomTextView(Context context) {
        super(context);
        init(context,null,0);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs,0);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs,defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        if (isInEditMode()) return;
        String fontName = "HelveticaNeueLTPro-Roman.otf";
        if (attrs != null) {
            TypedArray typedArray = context.getTheme().
                    obtainStyledAttributes(attrs, R.styleable.Roman, defStyleAttr, 0);
            int fontType = typedArray.getInt(R.styleable.Roman_text_type,0);
            switch (fontType) {
                case ONE:
                    fontName = "HelveticaNeueLTPro-Roman.otf";
                    break;
                case TWO:
                    fontName = "HelveticaNeueLTPro-Roman-ExtraBold.otf";
                    break;
            }
        }
        super.setTypeface(Typeface.createFromAsset(getContext().getAssets(),
                "fonts/" + fontName), defStyleAttr);
    }


}
