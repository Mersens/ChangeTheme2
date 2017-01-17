package com.mersens.changetheme;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Mersens on 2017/1/10 11:10
 * Email:626168564@qq.com
 */

public class BaseActivity extends AppCompatActivity implements ISkinChangeListener{
    private final Object[] mConstructorArgs = new Object[2];
    private static final String[] sClassPrefixList = {
            "android.widget.",
            "android.view.",
            "android.webkit."

    };
    private static final Class<?>[] sConstructorSignature = new Class[]{
            Context.class, AttributeSet.class};
    private static final Map<String, Constructor<? extends View>> sConstructorMap
            = new ArrayMap<>();
    private Method mMethod = null;
    static final Class[] sCreatViewSingnature = new Class[]{View.class, String.class,
            Context.class, AttributeSet.class};
    private final Object[] mCreatViewArgs = new Object[4];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        SkinManager.getInstance().registerListener(this);
        LayoutInflater mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LayoutInflaterCompat.setFactory(mInflater, new LayoutInflaterFactory() {
            @Override
            public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
                AppCompatDelegate delegate = getDelegate();
                //重写appcompat factory的初始化工作
                View view = null;
                List<SkinAttr> skinAttrs=null;
                try {
                    if (mMethod == null) {
                        mMethod = delegate.getClass().getMethod("createView", sCreatViewSingnature);
                    }
                    mCreatViewArgs[0] = parent;
                    mCreatViewArgs[1] = name;
                    mCreatViewArgs[2] = context;
                    mCreatViewArgs[3] = attrs;
                    view = (View) mMethod.invoke(delegate,mCreatViewArgs);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                skinAttrs=SkinAttrSupport.getSkinAttrs(attrs,context);
                if(skinAttrs.isEmpty()){
                  return null ;
                }
                if (view == null) {
                    //构建View
                    view = createViewFromTag(context, name, attrs);
                }
                if (view != null) {
                    injectSkin(view,skinAttrs);
                }


                return view;
            }


        });
        super.onCreate(savedInstanceState);

    }

    private void injectSkin(View view, List<SkinAttr> skinAttrs) {
         List<SkinView> skinViews = SkinManager.getInstance().getSkinViews(this);
        if(skinViews==null){
            skinViews=new ArrayList<SkinView>();
            SkinManager.getInstance().addSkinView(this,skinViews);
        }
        skinViews.add(new SkinView(view,skinAttrs));
        //当前是否需要换肤 如果需要换肤
       if(SkinManager.getInstance().isNeedChangeSkin()){
            SkinManager.getInstance().skinChange(this);
        }


    }

    private View createViewFromTag(Context context, String name, AttributeSet attrs) {
        if (name.equals("view")) {
            name = attrs.getAttributeValue(null, "class");
        }

        try {
            mConstructorArgs[0] = context;
            mConstructorArgs[1] = attrs;

            if (-1 == name.indexOf('.')) {
                for (int i = 0; i < sClassPrefixList.length; i++) {
                    final View view = createView(context, name, sClassPrefixList[i]);
                    if (view != null) {
                        return view;
                    }
                }
                return null;
            } else {
                return createView(context, name, null);
            }
        } catch (Exception e) {
            // We do not want to catch these, lets return null and let the actual LayoutInflater
            // try
            return null;
        } finally {
            // Don't retain references on context.
            mConstructorArgs[0] = null;
            mConstructorArgs[1] = null;
        }
    }

    private View createView(Context context, String name, String prefix)
            throws ClassNotFoundException, InflateException {
        Constructor<? extends View> constructor = sConstructorMap.get(name);

        try {
            if (constructor == null) {
                // Class not found in the cache, see if it's real, and try to add it
                Class<? extends View> clazz = context.getClassLoader().loadClass(
                        prefix != null ? (prefix + name) : name).asSubclass(View.class);

                constructor = clazz.getConstructor(sConstructorSignature);
                sConstructorMap.put(name, constructor);
            }
            constructor.setAccessible(true);
            return constructor.newInstance(mConstructorArgs);
        } catch (Exception e) {
            // We do not want to catch these, lets return null and let the actual LayoutInflater
            // try
            return null;
        }
    }

    private void initTheme() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SkinManager.getInstance().unReigisterListener(this);
    }

    @Override
    public void onChange() {

    }
}
