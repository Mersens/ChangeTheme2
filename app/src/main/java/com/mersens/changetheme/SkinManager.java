package com.mersens.changetheme;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.AsyncTask;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mersens on 2017/1/10 16:41
 * Email:626168564@qq.com
 */

public class SkinManager {
    private Map<ISkinChangeListener, List<SkinView>> skinMaps = new HashMap<ISkinChangeListener, List<SkinView>>();
    private ResourceManager mResourceManager;
    private List<ISkinChangeListener> mListener = new ArrayList<ISkinChangeListener>();
    private Context mContext;
    private static SkinManager sInstance;
    private PrefUtils mPrefUtils;
    private String mCurPath;
    private String mCurPkg;
    private String suffix;
    private boolean isFromPlugin=false;



    private SkinManager() {

    }

    public static SkinManager getInstance() {
        if (sInstance == null) {
            synchronized (SkinManager.class) {
                if (sInstance == null) {
                    sInstance = new SkinManager();
                }
            }
        }
        return sInstance;
    }

    public void init(Context context) {
        mContext = context.getApplicationContext();
        mPrefUtils=new PrefUtils(mContext);
        String path=mPrefUtils.getPluginPath();
        String pkg=mPrefUtils.getPluginPkg();
        suffix=mPrefUtils.getSuffix();
        File file=new File(path);
        if(file.exists()){
            loadPlugin(path,pkg);
        }
        mCurPath=path;
        mCurPkg=pkg;
    }

    public ResourceManager getmResourceManager() {
        if(!isFromPlugin){
            return new ResourceManager(mContext.getResources(),mContext.getPackageName(),suffix);
        }
        return mResourceManager;
    }

    private void loadPlugin(String skin_plugin_path, String skin_pkg) {
        if(skin_pkg.equals(mCurPkg) && skin_plugin_path.equals(mCurPath)){
            return;
        }
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager, skin_plugin_path);
            Resources resource = mContext.getResources();
            Resources mResource = new Resources(assetManager, resource.getDisplayMetrics(), resource.getConfiguration());
            mResourceManager = new ResourceManager(mResource, skin_pkg,null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<SkinView> getSkinViews(ISkinChangeListener listener) {
        return skinMaps.get(listener);
    }

    public void addSkinView(ISkinChangeListener listener, List<SkinView> skinViews) {
        skinMaps.put(listener, skinViews);
    }

    public void registerListener(ISkinChangeListener listener) {
        mListener.add(listener);

    }

    public void unReigisterListener(ISkinChangeListener listener) {
        mListener.remove(listener);
        skinMaps.remove(listener);
    }


    public void changeSkin(String suffix){
        isFromPlugin=false;
        clearPluginInfo();
        this.suffix=suffix;
        mPrefUtils.saveSuffix(suffix);
        notifyChangeListener();

    }

    private void clearPluginInfo() {
        mCurPath=null;
        mCurPkg=null;
        suffix=null;
        mPrefUtils.clear();
    }

    public void changeSkin(final String skin_plugin_path, final String skin_pkg, ISkinChangeCallBack iSkinChangeCallBack) {
        isFromPlugin=true;
        clearPluginInfo();
        if(iSkinChangeCallBack==null){
            iSkinChangeCallBack=ISkinChangeCallBack.DEFAULT_CALLBACK;
        }
        final  ISkinChangeCallBack finalCallBack=iSkinChangeCallBack;
        finalCallBack.onStart();
        new AsyncTask<Void,Void,Integer>(){

            @Override
            protected Integer doInBackground(Void... params) {
                try{
                    loadPlugin(skin_plugin_path,skin_pkg);
                }catch (Exception e){
                    e.printStackTrace();
                    finalCallBack.onError(e);
                    return -1;
                }

                return 0;
            }

            @Override
            protected void onPostExecute(Integer aVoid) {
                super.onPostExecute(aVoid);
                if(aVoid==-1){
                    finalCallBack.onError(new Exception());
                    return;
                }
                notifyChangeListener();
                finalCallBack.onComplate();
                updatePluginInfo(skin_plugin_path,skin_pkg);
            }
        }.execute();

    }

    private void updatePluginInfo(String skin_plugin_path,String skin_pkg) {
        mPrefUtils.savePluginPath(skin_plugin_path);
        mPrefUtils.savePluginPkg(skin_pkg);


    }

    private void notifyChangeListener() {
        for(ISkinChangeListener listener:mListener){
            skinChange(listener);
            listener.onChange();

        }
    }

    public void skinChange(ISkinChangeListener listener) {
        List<SkinView> skinViews=skinMaps.get(listener);
        for(SkinView sv:skinViews){
            sv.apply();

        }
    }

    public boolean isNeedChangeSkin() {
        return usePlugin() || useSuffix()  ;
    }
    private boolean usePlugin(){
        return mCurPath!=null && !mCurPath.trim().equals("");
    }

    private boolean useSuffix(){
        return suffix!=null && !suffix.trim().equals("");
    }

}
