package com.example.zw.liveapp;

import android.content.Context;
import android.util.AttributeSet;

public class MyTextureView extends MyEGLSurfaceView{
    public MyTextureView(Context context) {
        this(context,null);
    }

    public MyTextureView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyTextureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setRender(new MyTextureRender(context));
    }
}
