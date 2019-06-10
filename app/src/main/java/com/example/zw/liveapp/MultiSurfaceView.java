package com.example.zw.liveapp;

import android.content.Context;
import android.util.AttributeSet;

public class MultiSurfaceView extends MyEGLSurfaceView{
    private MultiRender mMultiRender;
    public MultiSurfaceView(Context context) {
        this(context,null);
    }
    public MultiSurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }
    public MultiSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs,defStyleAttr);
        mMultiRender=new MultiRender(context);
        setRender(mMultiRender);
    }
    public void setTextureId(int textureId,int index){
        if((mMultiRender!=null)){
            mMultiRender.setTextureId(textureId,index);
        }
    }

}
