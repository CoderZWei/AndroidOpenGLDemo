package com.example.zw.AndroidOpenGLDemo.utils;

import android.content.Context;
import android.opengl.GLES20;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ShaderUtil {
    public static String getRawResource(Context context,int rawId){
        InputStream inputStream=context.getResources().openRawResource(rawId);
        BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));
        StringBuffer sb=new StringBuffer();
        String line;
        try {
            while ((line=reader.readLine())!=null){
                sb.append(line).append("\n");
            }
            reader.close();
        }catch (IOException e){
            e.printStackTrace();
        }
       return sb.toString();
    }
    public static int loadShader(int shaderType,String source){
        int shader=GLES20.glCreateShader(shaderType);
        if(shader!=0){
            GLES20.glShaderSource(shader,source);
            GLES20.glCompileShader(shader);
            int compile[]=new int[1];
            GLES20.glGetShaderiv(shader,GLES20.GL_COMPILE_STATUS,compile,0);
            if(compile[0]!=GLES20.GL_TRUE){
                GLES20.glDeleteShader(shader);
                shader=0;
            }
            return shader;
        }else {
            return 0;
        }
    }
    public static int createProgram(String vertexSource,String fragmentSource){
        int vertexShader=loadShader(GLES20.GL_VERTEX_SHADER,vertexSource);
        int fragmentShader=loadShader(GLES20.GL_FRAGMENT_SHADER,fragmentSource);
        if(vertexShader!=0 && fragmentShader!=0){
            int program=GLES20.glCreateProgram();
            GLES20.glAttachShader(program,vertexShader);
            GLES20.glAttachShader(program,fragmentShader);
            GLES20.glLinkProgram(program);
            return program;
        }
        return 0;
    }
}

