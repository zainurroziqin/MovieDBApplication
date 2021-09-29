package com.example.jsonmoviedb;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.HashMap;
public class JsonApi extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    private ListView lv;

    //url to get Connection JSON
    private static String url = "https://api.androidhive.info/contacts/";
    ArrayList<HashMap<String, String>> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_api);

        contactList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.user_list);
        new GetContacts().execute();
    }

    private class GetContacts extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            //showing progres dialog
            pDialog = new ProgressDialog(JsonApi.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0){
            HttpHandler sh = new HttpHandler();
            //making a request url and getting response
            String jsonstr = sh.makeServiceCall(url);
            Log.e(TAG, "Response from url: "+ jsonstr );
            if (jsonstr != null){
                try {
                    JSONObject jsonObj = new JSONObject(jsonstr);
                    //getting json array node
                    JSONArray contacts = jsonObj.getJSONArray("contacts");
                    //looping through All contacts
                    for (int i = 0; i<contacts.length(); i++){
                        JSONObject c = contacts.getJSONObject(i);
                        String id = c.getString("id");
                        String name = c.getString("name");
                        String email = c.getString("email");
                        String addres = c.getString("addres");
                        String gender = c.getString("gender");

                        //phone node is json object
                        JSONObject phone = c.getJSONObject("phone");
                        String mobile = c.getString("mobile");
                        String home = c.getString("home");
                        String office = c.getString("office");

                        HashMap<String, String> contact = new HashMap<>();

                        contact.put("id", id);
                        contact.put("name", name);
                        contact.put("email", email);
                        contact.put("mobile", mobile);

                        contactList.add(contact);
                    }
                }catch (final JSONException e) {
                    Log.e(TAG, "json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "json parsing error" + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }else {
                Log.e(TAG, "Couldn't get json from server. " );
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check logcat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result){
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();

            ListAdapter adapter = new SimpleAdapter(
                    JsonApi.this, contactList, R.layout.list_item, new String[]{"name", "email",
                    "mobile"}, new int[]{R.id.name, R.id.email, R.id.mobile});
            lv.setAdapter(adapter);
        }
    }
}