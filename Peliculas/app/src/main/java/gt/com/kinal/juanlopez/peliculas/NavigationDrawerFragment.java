package gt.com.kinal.juanlopez.peliculas;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import gt.com.kinal.juanlopez.peliculas.Adapter.NavigationDrawerAdapter;
import gt.com.kinal.juanlopez.peliculas.BaseDatos.BDD_sqlite;
import gt.com.kinal.juanlopez.peliculas.beans.Usuario;


public class NavigationDrawerFragment extends Fragment {

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public SQLiteDatabase db;
    public BDD_sqlite sqlite;

    Context cx;

    private int ICONS[] = {R.drawable.ic_home, R.drawable.ic_events, R.drawable.ic_mail, R.drawable.ic_shop, R.drawable.ic_travel };
    private String TITLES[] = {"Peliculas", "Favoritos","Mail", "Shop", "Travel"};
    private String NAME = "";
    private String EMAIL = "";
    private int PROFILE = R.mipmap.ic_profile;

    public NavigationDrawerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);

        cx = getActivity().getApplicationContext();

        Login();

        mRecyclerView = (RecyclerView) v.findViewById(R.id.RecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new NavigationDrawerAdapter(TITLES, ICONS, NAME, EMAIL, PROFILE);

        mRecyclerView.setAdapter(mAdapter);
        return v;
    }

    public void Login() {
        sqlite = new BDD_sqlite(cx);
        db = sqlite.getReadableDatabase();

        String Sql = "SELECT NOMBRE,CORREO FROM USUARIOS WHERE ESTADO='1'";

        Cursor cc = db.rawQuery(Sql, null);
        Usuario obj;
        int valida = 0;
        if (cc.moveToFirst()) {
            do {
                valida++;
                NAME = cc.getString(0);
                EMAIL = cc.getString(1);
            } while (cc.moveToNext());
        }
        db.close();
    }

    public void setUp(DrawerLayout drawerLaout, Toolbar toolbar) {
        this.mDrawerLayout = drawerLaout;
        this.mToolbar = toolbar;

        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLaout, toolbar, R.string.drawer_open, R.string.drawer_close){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                mToolbar.setAlpha(1 - slideOffset / 2);
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }
}
