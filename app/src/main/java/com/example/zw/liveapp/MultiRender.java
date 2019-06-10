package com.example.zw.liveapp;

import android.content.Context;
import android.opengl.GLES20;

import com.example.zw.liveapp.utils.ShaderUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class MultiRender implements MyEGLSurfaceView.MyGLRender
{
    private Context mContext;
    private float[] vertexData={
            -1f, -1f,
            1f, -1f,
            -1f, 1f,
            1f, 1f
    };
    private FloatBuffer vertexBuffer;
    //fbo有自己的坐标系，需要单独设置
    private float[] fragmentData={
            //fbo坐标系
//            0f, 0f,
//            1f, 0f,
//            0f, 1f,
//            1f, 1f
            //android纹理坐标系
            0f, 1f,
            1f, 1f,
            0f, 0f,
            1f, 0f
//            0f, 0.5f,
//            0.5f, 0.5f,
//            0f, 0f,
//            0.5f, 0f
    };
    private FloatBuffer fragmentBuffer;
    private int program;
    private int vPosition;
    private int fPosition;
    private int textureId;
    private int sampler;
    //顶点缓冲
    private int vboId;
    private int index;
    public void setTextureId(int textureId,int index){
        this.textureId=textureId;
        this.index=index;
    }

    public MultiRender(Context context) {
        this.mContext = context;
        vertexBuffer=ByteBuffer.allocateDirect(vertexData.length*4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(vertexData);
        vertexBuffer.position(0);
        fragmentBuffer=ByteBuffer.allocateDirect(fragmentData.length*4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(fragmentData);
        fragmentBuffer.position(0);
    }

    @Override
    public void onSurfaceCreated() {
        String vertexSource = ShaderUtil.getRawResource(mContext, R.raw.vertex_shader);
        String fragmentSource=ShaderUtil.getRawResource(mContext,R.raw.fragment_shader);
        switch (index){
            case 0:
                fragmentSource=ShaderUtil.getRawResource(mContext,R.raw.fragment_shader0);
                break;
            case 1:
                fragmentSource=ShaderUtil.getRawResource(mContext,R.raw.fragment_shader1);
                break;
            case 2:
                fragmentSource=ShaderUtil.getRawResource(mContext,R.raw.fragment_shader2);
                break;
        }
        program=ShaderUtil.createProgram(vertexSource,fragmentSource);
        vPosition = GLES20.glGetAttribLocation(program, "v_Position");
        fPosition = GLES20.glGetAttribLocation(program, "f_Position");
        sampler = GLES20.glGetUniformLocation(program, "sTexture");

        int [] vbos = new int[1];
        GLES20.glGenBuffers(1, vbos, 0);
        vboId = vbos[0];
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vboId);
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, vertexData.length * 4 + fragmentData.length * 4, null, GLES20. GL_STATIC_DRAW);
        GLES20.glBufferSubData(GLES20.GL_ARRAY_BUFFER, 0, vertexData.length * 4, vertexBuffer);
        GLES20.glBufferSubData(GLES20.GL_ARRAY_BUFFER, vertexData.length * 4, fragmentData.length * 4, fragmentBuffer);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
    }

    @Override
    public void onSurfaceChanged(int width, int height) {
        GLES20.glViewport(0,0,width,height);
    }

    @Override
    public void onDrawFrame() {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glClearColor(1f,0f, 0f, 1f);

        GLES20.glUseProgram(program);

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);


        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vboId);

        GLES20.glEnableVertexAttribArray(vPosition);
        GLES20.glVertexAttribPointer(vPosition, 2, GLES20.GL_FLOAT, false, 8,
                0);

        GLES20.glEnableVertexAttribArray(fPosition);
        GLES20.glVertexAttribPointer(fPosition, 2, GLES20.GL_FLOAT, false, 8,
                vertexData.length * 4);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
    }
}
