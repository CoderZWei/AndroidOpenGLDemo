package com.example.zw.AndroidOpenGLDemo;

import android.content.Context;
import android.util.AttributeSet;

public class MyGLSurfaceView extends MyEGLSurfaceView{
    //private MyGLRender myGLRender;
    public MyGLSurfaceView(Context context) {
        this(context,null);
    }

    public MyGLSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setRender(new com.example.zw.AndroidOpenGLDemo.MyGLRender());
        setRenderMode(MyEGLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    public MyGLSurfaceView(Context context, AttributeSet attrs) {
        this(context,attrs,0);

    }

}
