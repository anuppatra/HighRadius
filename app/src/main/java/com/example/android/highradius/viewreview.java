package com.example.android.highradius;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class viewreview extends AppCompatActivity {

    ActionBarDrawerToggle mDrawerToggle;
    DrawerLayout mDrawer;
    NavigationView navigationView;
    Toolbar mtoolbar;
    ListView list;
    TextView txt;
    Context c=this;

    Handler mHandler;
    int size;
    int z=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewreview);
        mtoolbar=findViewById(R.id.nav_action);
        ActionBar actionbar=getSupportActionBar();
        actionbar.setCustomView(mtoolbar);
        navigationView=(NavigationView)findViewById(R.id.navigation);
        View header=navigationView.getHeaderView(0);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle= new ActionBarDrawerToggle(this,mDrawer,R.string.Open,R.string.Close);
        mDrawer.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /*mDrawerToggle = new ActionBarDrawerToggle(this,mDrawer,R.string.app_name,R.string.app_name){
            @Override
            public void onDrawerClosed(View drawerView) {
            }

            @Override
            public void onDrawerOpened(View drawerView) {
            }
        };*/
        navigationView=(NavigationView)findViewById(R.id.navigation);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.requests :
                        Intent in=new Intent(viewreview.this,viewreview.class);
                        startActivity(in);
                        break;
                }
                mDrawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });



    }

    class ExecuteTask extends AsyncTask<String,Integer,String>
    {

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL("http://argetim2k17.com/Highradius/profile.php");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                String str;
                String l = "";

                while ((str = bufferedReader.readLine()) != null) {
                    l += str;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return l;
            }
            catch (Exception e)
            {

            }

            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            // txt.setText(s);

            String [] id;
            String [] dep_rev;
            String [] empid;
            String [] depart;
            String [] name;
            String [] r1;
            String [] r2;
            String [] r3;
            String [] r4;
            String [] r5;
            String [] comment;
            try {
                JSONArray array=new JSONArray(s);
                size=array.length();

                id = new String[size];
                //n=new String[size];
                dep_rev=new String[size];
                empid=new String[size];
                depart=new String[size];
                name=new String[size];
                r1=new String[size];
                r2=new String[size];
                r3=new String[size];
                r4=new String[size];
                r5=new String[size];
                comment=new String[size];
                int i=0;
                int j=0;
                for(i=0;j<array.length();j++)
                {
                    JSONObject obj=array.getJSONObject(j);
                        id[i]=obj.getString("id");
                        // n[i]=obj.getString("Name");
                        dep_rev[i] = obj.getString("Department_review");
                        empid[i] = obj.getString("emp_id");
                        depart[i] = obj.getString("department");
                        name[i] = obj.getString("name");
                         r1[i]= obj.getString("review1");
                    r2[i]= obj.getString("review2");
                    r3[i]= obj.getString("review3");
                    r4[i]= obj.getString("review4");
                    r5[i]= obj.getString("review5");
                    comment[i]= obj.getString("comment");
                    i++;


                }
                customfaculty cs=new customfaculty(viewreview.this,dep_rev,name,depart,r1,r2,r3,r4,r5,comment);
                list.setAdapter(cs);


            } catch (JSONException e1) {
                e1.printStackTrace();
            }


        }
    }

}
