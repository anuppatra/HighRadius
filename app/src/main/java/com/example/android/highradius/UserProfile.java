package com.example.android.highradius;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

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

public class UserProfile extends Activity implements AdapterView.OnItemSelectedListener {
    private EditText editTextUsername, editTextPassword, editTextRepeatPassword;
    private TextView emailID;
    public static String STR_EXTRA_ACTION_REGISTER = "register";
    String[] spinner={"Development", "HR Team", "Pantry", "Accounts", "Cleaning", "Manager"};




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        editTextUsername = (EditText) findViewById(R.id.et_username);
        editTextPassword = (EditText) findViewById(R.id.et_password);
        editTextRepeatPassword = (EditText) findViewById(R.id.et_repeatpassword);
        emailID = (TextView) findViewById(R.id.emailID);
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


}
