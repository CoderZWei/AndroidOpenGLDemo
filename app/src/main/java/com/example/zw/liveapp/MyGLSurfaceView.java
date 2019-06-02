package com.example.zw.liveapp;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

public class MyGLSurfaceView extends MyEGLSurfaceView{
    //private MyGLRender myGLRender;
    public MyGLSurfaceView(Context context) {
        this(context,null);
    }

    public MyGLSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setRender(new com.example.zw.liveapp.MyGLRender());
        setRenderMode(MyEGLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    public MyGLSurfaceView(Context context, AttributeSet attrs) {
        this(context,attrs,0);

    }

}
