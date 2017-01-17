package com.mersens.changetheme;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.afollestad.materialdialogs.color.ColorChooserDialog;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.Thing;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, ColorChooserDialog.ColorCallback {
    private ListView mListView;
    private View headerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTheme();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        headerView=navigationView.getHeaderView(0);


        init();

    }


    private void initTheme() {
        int index = SharePreferenceUtil.getInstance(this).getIndex();
        switch (index) {
            case 0:
                setTheme(R.style.AppTheme);
                break;
            case 1:
                setTheme(R.style.ThemeRed);
                break;
            case 2:
                setTheme(R.style.ThemePurple);
                break;
            case 3:
                setTheme(R.style.ThemeDeepPurple);
                break;
            case 4:
                setTheme(R.style.ThemeBlue);
                break;
            case 5:
                setTheme(R.style.ThemeLightBlue);
                break;
            case 6:
                setTheme(R.style.ThemeTeal);
                break;
            case 7:
                setTheme(R.style.ThemeGreen);
                break;
            case 8:
                setTheme(R.style.ThemeLime);
                break;
            case 9:
                setTheme(R.style.ThemeBrown);
                break;
            case 10:
                setTheme(R.style.ThemeYellow);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    String skin_plugin_path = Environment.getExternalStorageDirectory() + File.separator + "app-skin.apk";
    String skin_pkg = "com.mersens.skin_plugin";
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if(id==R.id.nav_app_change_skin){
            //应用内换肤
            SkinManager.getInstance().changeSkin("purple");

        }


        if (id == R.id.nav_manage) {
            new ColorChooserDialog.Builder(MainActivity.this, R.string.theme)
                    .customColors(R.array.arraycolors, null)
                    .doneButton(R.string.done)
                    .cancelButton(R.string.cancel)
                    .allowUserColorInput(false)
                    .allowUserColorInput(false)
                    .show();

        } else if (id == R.id.nav_change_skin) {
            //更换皮肤


           // loadPlugin(skin_plugin_path,skin_pkg);
            SkinManager.getInstance().changeSkin(skin_plugin_path,skin_pkg, new ISkinChangeCallBack() {
                @Override
                public void onStart() {

                }

                @Override
                public void onError(Exception e) {

                }

                @Override
                public void onComplate() {

                }
            });

        } else if (id == R.id.nav_reset_skin) {
            //重置皮肤

        }else if(id==R.id.nav_factory){
            startActivity(new Intent(MainActivity.this,TestFracoryActivity.class));
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    private void init() {
        final LayoutInflater inflater = LayoutInflater.from(this);
        mListView = (ListView) findViewById(R.id.listView);
        final List<String> mList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mList.add("Item " + (i + 1));
        }
        mListView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return mList.size();
            }

            @Override
            public Object getItem(int i) {
                return mList.get(i);
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                ViewHolder holder=null;
                if(view==null){
                    view=inflater.inflate(R.layout.item,viewGroup,false);
                    holder=new ViewHolder();
                    holder.tv=(TextView) view.findViewById(R.id.tv);
                    view.setTag(holder);

                }else{
                   holder=(ViewHolder)view.getTag();
                }
                holder.tv.setText(mList.get(i));
                return view;
            }
        });
    }


    static class ViewHolder{
        TextView tv;
    }


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }


    @Override
    public void onColorSelection(@NonNull ColorChooserDialog dialog, @ColorInt int selectedColor) {
        if (selectedColor == 0) {
            return;
        } else if (selectedColor == getResources().getColor(R.color.red)) {
            setTheme(R.style.ThemeRed);
            SharePreferenceUtil.getInstance(MainActivity.this).setIndex(1);
        } else if (selectedColor == getResources().getColor(R.color.purple)) {
            setTheme(R.style.ThemePurple);
            SharePreferenceUtil.getInstance(MainActivity.this).setIndex(2);

        } else if (selectedColor == getResources().getColor(R.color.deep_purple)) {

            setTheme(R.style.ThemeDeepPurple);
            SharePreferenceUtil.getInstance(MainActivity.this).setIndex(3);
        } else if (selectedColor == getResources().getColor(R.color.blue)) {
            setTheme(R.style.ThemeBlue);
            SharePreferenceUtil.getInstance(MainActivity.this).setIndex(4);

        } else if (selectedColor == getResources().getColor(R.color.light_blue)) {
            setTheme(R.style.ThemeLightBlue);
            SharePreferenceUtil.getInstance(MainActivity.this).setIndex(5);

        } else if (selectedColor == getResources().getColor(R.color.teal)) {
            setTheme(R.style.ThemeTeal);
            SharePreferenceUtil.getInstance(MainActivity.this).setIndex(6);

        } else if (selectedColor == getResources().getColor(R.color.green)) {

            setTheme(R.style.ThemeGreen);
            SharePreferenceUtil.getInstance(MainActivity.this).setIndex(7);
        } else if (selectedColor == getResources().getColor(R.color.lime)) {

            setTheme(R.style.ThemeLime);
            SharePreferenceUtil.getInstance(MainActivity.this).setIndex(8);
        } else if (selectedColor == getResources().getColor(R.color.brown)) {

            setTheme(R.style.ThemeBrown);
            SharePreferenceUtil.getInstance(MainActivity.this).setIndex(9);
        } else if (selectedColor == getResources().getColor(R.color.yellow)) {

            setTheme(R.style.ThemeYellow);
            SharePreferenceUtil.getInstance(MainActivity.this).setIndex(10);
        }
        MainActivity.this.recreate();

    }


}
