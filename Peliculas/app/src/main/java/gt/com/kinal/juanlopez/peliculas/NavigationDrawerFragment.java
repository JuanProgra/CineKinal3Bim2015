package gt.com.kinal.juanlopez.peliculas;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;

import gt.com.kinal.juanlopez.peliculas.Adapter.NavigationDrawerAdapter;
import gt.com.kinal.juanlopez.peliculas.BaseDatos.BDD_sqlite;
import gt.com.kinal.juanlopez.peliculas.Helpers.RecyclerItemClickListener;
import gt.com.kinal.juanlopez.peliculas.beans.Usuario;


public class NavigationDrawerFragment extends Fragment {

    public SQLiteDatabase db;
    public BDD_sqlite sqlite;

    Context cx;

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FragmentDrawerListener mDrawerListener;
    private View containerView;

    private int ICONS[] = {R.drawable.ic_home, R.drawable.ic_events, R.drawable.ic_mail, R.drawable.ic_travel };
    private String TITLES[] = {"Peliculas", "Favoritos","Ajustes", "Perfil"};
    private String NAME = "Yo";
    private String EMAIL = "yo@kinal.org.gt";
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


        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mDrawerListener.onDrawerItemSelected(view, position);
                mDrawerLayout.closeDrawer(containerView);
            }
        }));

        return v;
    }
    public void datos(){
        mRecyclerView.setAdapter(mAdapter);
    }


    public void setUp(DrawerLayout drawerLaout, Toolbar toolbar, int fragmentId) {
        this.mDrawerLayout = drawerLaout;
        this.mToolbar = toolbar;
        this.containerView = getActivity().findViewById(fragmentId);

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

    public void setDrawerListener(FragmentDrawerListener drawerListener){
        this.mDrawerListener =  drawerListener;
    }

    public interface FragmentDrawerListener {
        public void onDrawerItemSelected(View view, int position);
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
}



