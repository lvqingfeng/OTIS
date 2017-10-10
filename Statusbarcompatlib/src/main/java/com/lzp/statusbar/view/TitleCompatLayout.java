package com.lzp.statusbar.view;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.util.AttributeSet;
import android.view.View;

public class TitleCompatLayout extends AppBarLayout {


    public TitleCompatLayout(Context context) {
        super(context);
        init(context);
    }

    public TitleCompatLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    private void init(Context context) {

        View statusBar = new View(context);
        statusBar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 0));
        addView(statusBar);

    }
}
