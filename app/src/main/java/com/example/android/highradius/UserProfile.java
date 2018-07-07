package com.example.android.highradius;

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
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class UserProfile extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private TextView editTextUsername, description, age,dept;
    private TextView emailID;
    public static String STR_EXTRA_ACTION_REGISTER = "register";
    String[] spinner={"Development", "HR Team", "Pantry", "Accounts", "Cleaning", "Manager"};
    ActionBarDrawerToggle mDrawerToggle;
    DrawerLayout mDrawer;
    NavigationView navigationView;
    Toolbar mtoolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        editTextUsername = (TextView) findViewById(R.id.user_profile_name);
        description = (TextView) findViewById(R.id.job_desc);
        age=findViewById(R.id.age);
        dept=findViewById(R.id.dept);




        emailID = (TextView) findViewById(R.id.mailId);
        /*Spinner spin=(Spinner)findViewById(R.id.action_bar_spinner);
        spin.setOnItemSelectedListener(UserProfile.this);
        ArrayAdapter aa=new ArrayAdapter(this, android.R.layout.simple_spinner_item, spinner);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);*/
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        String email=user.getEmail();
        emailID.setText(email);
        ExecuteTask1 e=new ExecuteTask1();
        e.execute(email);
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
                        Intent in=new Intent(UserProfile.this,viewreview.class);
                        startActivity(in);
                    break;

                }
                mDrawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

    }
    class ExecuteTask1 extends AsyncTask<String,Integer,String> {

        @Override
        protected String doInBackground(String... strings) {
            try {

                URL url = new URL("http://argetim2k17.com/Highradius/fetchdetails.php");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post = "";
                post = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(strings[0], "UTF-8") ;

                bufferedWriter.write(post);
                bufferedWriter.flush();
                bufferedWriter.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                String j;
                StringBuffer sb = new StringBuffer();
                while ((j = bufferedReader.readLine()) != null) {
                    sb.append(j);
                }
                return sb.toString();
            } catch (Exception e) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);

            try {
                JSONArray array = new JSONArray(s);
                if (array != null)
                {
                    int size = array.length();

                    String n[] = new String[size];
                    String me []= new String[size];
                    String b[] = new String[size];
                    String c[] = new String[size];
                    String m[] = new String[size];
                    String empid[] = new String[size];

                    int i = 0;
                    int j = 0;
                    for (i = 0; j < array.length(); j++) {
                        JSONObject obj = array.getJSONObject(j);



                        obj = array.getJSONObject(j);
                        obj.getString("name");
                        m[j] = obj.getString("age");
                        c[j] = obj.getString("department");
                        me[j] = obj.getString("position");
                        b[j] = obj.getString("email");
                        empid[j] = obj.getString("emp_id");

                        editTextUsername.setText(obj.getString("name"));
                        description.setText(obj.getString("position"));
                        emailID.setText(obj.getString("email"));
                        age.setText("Age : "+obj.getString("age"));
                        dept.setText(obj.getString("department"));

                    }
                    }

            } catch(JSONException e1){
                e1.printStackTrace();
            }



                }


        }
    @Override
    public void onItemSelected(AdapterView arg1, View arg2, int postition, long id)
    {

    }

    @Override
    public void onNothingSelected(AdapterView arg)
    {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }


}

