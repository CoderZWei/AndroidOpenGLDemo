package com.example.zw.liveapp;

import android.content.Context;
import android.util.AttributeSet;

public class MyTextureView extends MyEGLSurfaceView{
    private MyTextureRender mTextureRender;
    public MyTextureView(Context context) {
        this(context,null);
    }

    public MyTextureView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyTextureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTextureRender=new MyTextureRender(context);
        setRender(mTextureRender);
    }
    public MyTextureRender getTextureRender(){
        return mTextureRender;
    }

}
