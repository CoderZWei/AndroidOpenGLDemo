package com.example.zw.AndroidOpenGLDemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class TextureActivity extends AppCompatActivity {
    private MyTextureView mTextureView;
    private LinearLayout mLinearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_texture);
        mTextureView=(MyTextureView)findViewById(R.id.textureView_above);
        mLinearLayout=(LinearLayout)findViewById(R.id.layout_content);
        mTextureView.getTextureRender().setOnRenderCreateListener(new MyTextureRender.OnRenderCreateListener() {
            @Override
            public void onCreate(final int textId) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(mLinearLayout.getChildCount()>0){
                            mLinearLayout.removeAllViews();
                        }
                        for(int i=0;i<3;i++){
                            MultiSurfaceView multiSurfaceView=new MultiSurfaceView(TextureActivity.this);
                            multiSurfaceView.setTextureId(textId,i);
                            multiSurfaceView.setSurfaceAndEglContext(null,mTextureView.getEglContext());
                            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                            params.width=200;
                            params.height=300;
                            multiSurfaceView.setLayoutParams(params);
                            mLinearLayout.addView(multiSurfaceView);
                        }
                    }
                });
            }
        });
    }
}
