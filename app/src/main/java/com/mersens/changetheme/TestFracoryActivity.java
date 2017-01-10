package com.mersens.changetheme;

import android.content.Context;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

public class TestFracoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LayoutInflater mInflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LayoutInflaterCompat.setFactory(mInflater, new LayoutInflaterFactory() {
            @Override
            public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
                Log.e("TAG",name);
                for(int i=0,n=attrs.getAttributeCount();i<n;i++){
                    String attrName=attrs.getAttributeName(i);
                    String attrVal=attrs.getAttributeValue(i);
                    Log.e("TAG",attrName+"===="+attrVal);

                }
                if(name.equals("TextView")){

                }

                return null;
            }
        });


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_fracory);
    }
}
