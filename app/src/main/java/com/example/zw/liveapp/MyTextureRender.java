package com.example.zw.liveapp;

import android.animation.FloatArrayEvaluator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import com.example.zw.liveapp.utils.ShaderUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class MyTextureRender implements MyEGLSurfaceView.MyGLRender{
    private Context mContext;
    private float[] vertexData={
            -1f, -1f,
            1f, -1f,
            -1f, 1f,
            1f, 1f
    };
    private FloatBuffer vertexBuffer;
    private float[] fragmentData={
                0f,1f,
                1f,1f,
                0f,0f,
                1f,0f
//            0f, 0.5f,
//            0.5f, 0.5f,
//            0f, 0f,
//            0.5f, 0f
    };
    private FloatBuffer fragmentBuffer;
    private int program;
    private int vPosition;
    private int fPosition;
    private int textureid;
    private int sampler;

    //顶点缓冲
    private int vboId;

    public MyTextureRender(Context context) {
        this.mContext=context;
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
        String vertexSource=ShaderUtil.getRawResource(mContext,R.raw.vertex_shader);
        String fragmentSource=ShaderUtil.getRawResource(mContext,R.raw.fragment_shader);
        program=ShaderUtil.createProgram(vertexSource,fragmentSource);

        vPosition=GLES20.glGetAttribLocation(program,"v_Position");
        fPosition=GLES20.glGetAttribLocation(program,"f_Position");
        sampler=GLES20.glGetUniformLocation(program,"sTexture");

        //
        int [] vbos=new int[1];
        //创建vbo
        GLES20.glGenBuffers(1,vbos,0);
        vboId=vbos[0];
        //绑定vbo
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER,vboId);
        //分配vbo需要的缓存大小
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER,vertexData.length*4+fragmentData.length*4,null,GLES20.GL_STATIC_DRAW);
        //为vbo设置顶点数据的值
        GLES20.glBufferSubData(GLES20.GL_ARRAY_BUFFER,0,vertexData.length*4,vertexBuffer);
        GLES20.glBufferSubData(GLES20.GL_ARRAY_BUFFER,vertexData.length*4,fragmentData.length*4,fragmentBuffer);
        //解绑vbo
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER,0);


        //
        int[] textureIds=new int[1];
        GLES20.glGenTextures(1,textureIds,0);
        textureid=textureIds[0];

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureid);

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glUniform1i(sampler, 0);

        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT);

        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.img);
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);

        bitmap.recycle();
        bitmap = null;
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
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

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureid);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER,vboId);
        GLES20.glEnableVertexAttribArray(vPosition);
        //从偏移量为0的vbo中取数据
        GLES20.glVertexAttribPointer(vPosition, 2, GLES20.GL_FLOAT, false, 8,
                0);

        GLES20.glEnableVertexAttribArray(fPosition);
        //从偏移量为 vertexData.length*4 的vbo中取数据
        GLES20.glVertexAttribPointer(fPosition, 2, GLES20.GL_FLOAT, false, 8,
                vertexData.length*4);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER,0);
    }
}
