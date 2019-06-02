package com.example.zw.liveapp;

import android.view.Surface;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;

public class EglHelper {
    private EGL10 mEgl;
    private EGLDisplay mEglDisplay;
    private EGLContext mEglContext;
    private EGLSurface mEglSurface;
    public void initEgl(Surface surface,EGLContext eglContext){
        //1
        mEgl=(EGL10)EGLContext.getEGL();
        //2
        mEglDisplay=mEgl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
        if(mEglDisplay==EGL10.EGL_NO_DISPLAY){
            throw new RuntimeException("eglGetDisplay failed");
        }
        //3
        int[] versions=new int[2];
        if(!mEgl.eglInitialize(mEglDisplay,versions)){
            throw new RuntimeException("eglInitialize failed");
        }
        //4
        //设置窗口属性
        int[] attributes=new int[]{
                EGL10.EGL_RED_SIZE, 8,
                EGL10.EGL_GREEN_SIZE, 8,
                EGL10.EGL_BLUE_SIZE, 8,
                EGL10.EGL_ALPHA_SIZE, 8,
                EGL10.EGL_DEPTH_SIZE, 8,
                EGL10.EGL_STENCIL_SIZE, 8,
                EGL10.EGL_RENDERABLE_TYPE, 4,
                EGL10.EGL_NONE
        };
        int[] num_configs=new int[1];
        if(!mEgl.eglChooseConfig(mEglDisplay,attributes,null,1,num_configs)){
            throw new IllegalArgumentException("eglChooseConfig failed");
        }
        int numConfig=num_configs[0];
        if(numConfig<=0){
            throw new IllegalArgumentException("no configs match configSpec");
        }
        //5
        EGLConfig[] configs=new EGLConfig[numConfig];
        if(!mEgl.eglChooseConfig(mEglDisplay,attributes,configs,numConfig,num_configs)){
            throw new IllegalArgumentException("eglChooseConfig 2 failed");
        }
        //6
        if(eglContext!=null){
            mEglContext=mEgl.eglCreateContext(mEglDisplay,configs[0],eglContext,null);
        }else {
            mEglContext=mEgl.eglCreateContext(mEglDisplay,configs[0],EGL10.EGL_NO_CONTEXT,null);
        }
        //7 生成surface
        mEglSurface=mEgl.eglCreateWindowSurface(mEglDisplay,configs[0],surface,null);
        //8
        if(!mEgl.eglMakeCurrent(mEglDisplay,mEglSurface,mEglSurface,mEglContext)){
            throw new RuntimeException("eglMakeCurrent failed");
        }
    }
    //返回上下文 可以实现共享纹理等操作
    public EGLContext getEglContext(){
        return mEglContext;
    }
    public boolean swapBuffers(){
        if(mEgl!=null){
            return mEgl.eglSwapBuffers(mEglDisplay,mEglSurface);
        }else {
            throw new RuntimeException("egl is null");
        }
    }
    public void destoryEgl(){
        if (mEgl!=null){
            mEgl.eglMakeCurrent(mEglDisplay,EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE,EGL10.EGL_NO_CONTEXT);
            mEgl.eglDestroySurface(mEglDisplay,mEglSurface);
            mEglSurface=null;
            mEgl.eglDestroyContext(mEglDisplay,mEglContext);
            mEglContext=null;

            mEgl.eglTerminate(mEglDisplay);
            mEglDisplay=null;
            mEgl=null;
        }
    }

}
