package com.txtled.gpa220.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.button.MaterialButton;
import com.txtled.gpa220.R;

import static com.txtled.gpa220.utils.Constants.ONE;
import static com.txtled.gpa220.utils.Constants.TWO;

/**
 * Created by Mr.Quan on 2020/3/11.
 */
public class CustomButton extends MaterialButton {
    public CustomButton(@NonNull Context context) {
        this(context,null,0);
    }

    public CustomButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs,defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
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
                    fontName = "HelveticaNeueLTPro-Roman.otf";
                    break;
            }
        }
        super.setTypeface(Typeface.createFromAsset(getContext().getAssets(),
                "fonts/" + fontName), defStyleAttr);
    }


}
