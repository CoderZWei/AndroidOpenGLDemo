package com.example.zw.liveapp;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

public class MyGLSurfaceView extends GLSurfaceView {
    private MyGLRender myGLRender;
    public MyGLSurfaceView(Context context) {
        super(context,null);
    }

    public MyGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        myGLRender=new MyGLRender();
        setRenderer(myGLRender);
    }

}
