package com.ruby.x.json2.Views;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import com.ruby.x.json2.BaseActivity;
import com.ruby.x.json2.Controllers.MainController;
import com.ruby.x.json2.Controllers.TaskListAsyncController;
import com.ruby.x.json2.Libraries.AdapterListViewTask1;
import com.ruby.x.json2.Models.DataConstant;
import com.ruby.x.json2.Models.DataTask;
import com.ruby.x.json2.R;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private MainController controller;
    private Toolbar toolbar;
    private TextView btnInquiry, txtUserName, txtUserRole;
    private int pageCount = 0, hasData = 0, isFirst = 1;
    private AdapterListViewTask1 adapter;
    private ListView listView;
    private ArrayList<DataTask> taskList;
    private SwipeRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller = new MainController(this);
        initUI();
        initAttribute();
        initValue();
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click once more to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initUI() {
        this.setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        listView = (ListView) findViewById(R.id.list);
        listView.setOnScrollListener(onScrollListener());
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.content_main);
    }

    private void initValue() {
        pageCount = 0;
        taskList = new ArrayList<DataTask>();
        adapter = new AdapterListViewTask1(this, controller, R.layout.listview_tasklist, taskList);
        listView.setAdapter(adapter);
        setDataList(pageCount);
    }

    private void initAttribute() {
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initValue();
                swipeRefresh.setRefreshing(false);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
/*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add) {
            DataTask data = new DataTask();
            controller.toActivity(FormActivity.class, data);
        }
        return super.onOptionsItemSelected(item);
    }
*/
    private void setDataList(Integer page) {
        new TaskListAsyncController(this, page).execute();
    }

    private AbsListView.OnScrollListener onScrollListener() {
        return new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                int threshold = 1;
                int count = listView.getCount();
                if (scrollState == SCROLL_STATE_IDLE) {
                    if (listView.getLastVisiblePosition() >= count - threshold && pageCount < DataConstant.MAX_PAGE) {
                        setDataList(pageCount);
                    }
                }
            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                                 int totalItemCount) {
            }
        };
    }

    public void parseJsonResponse(String result) {
        hasData = 0;
        try {
            JSONObject json = new JSONObject(result);
            JSONArray jArray = new JSONArray(json.getString("message"));
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject jObject = jArray.getJSONObject(i);
                DataTask data = new DataTask();
                data.setId(jObject.getString("id"));
                data.setTitle(jObject.getString("title"));
                data.setApellido(jObject.getString("apellido"));
                data.setEstado(jObject.getString("estado"));
                data.setMunicipio(jObject.getString("municipio"));
                data.setDescription(jObject.getString("description"));
                data.setLat(jObject.getString("lat"));
                data.setLng(jObject.getString("lng"));
                data.setFileDocumentation(jObject.getString("file_documentation"));
                data.setFileDocumentation1(jObject.getString("file_documentation1"));
                data.setCreatedDate(jObject.getString("created_date"));
                taskList.add(data);
                hasData = 1;
            }

            if(hasData > 0) {
                pageCount++;
            }

            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
