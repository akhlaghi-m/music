package roid.berlin.android.onlinemusicplayer.activitys;

import androidx.appcompat.app.AppCompatActivity;
import roid.berlin.android.onlinemusicplayer.R;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText name,email,password,c_password;
    private Button btn_register;
    private ProgressBar loading;
    private static String URL_REGISTER = "http://berlinroid.ir/register.php";
    String str_name,str_email,str_password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        loading =findViewById(R.id.loading);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        c_password = findViewById(R.id.c_password);
        btn_register = findViewById(R.id.btn_register);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Register();

            }
        });
    }

    private void Register(){

        if (name.getText().toString().equals("")){

            Toast.makeText(this,"Enter Name",Toast.LENGTH_SHORT).show();
        }
        else if (email.getText().toString().equals("")){

            Toast.makeText(this,"Enter Email",Toast.LENGTH_SHORT).show();
        }
        else if (password.getText().toString().equals("")){

            Toast.makeText(this,"Enter Password",Toast.LENGTH_SHORT).show();
        }

        else {

            str_name = name.getText().toString().trim();
            str_email = email.getText().toString().trim();
            str_password = password.getText().toString().trim();

            StringRequest request = new StringRequest(Request.Method.POST, URL_REGISTER, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(RegisterActivity.this,response,Toast.LENGTH_SHORT).show();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(RegisterActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();

                }
            }

            )
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                   Map<String,String> params = new HashMap<String, String>();

                   params.put("name",str_name);
                   params.put("email",str_email);
                   params.put("password",str_password);

                   return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);

            requestQueue.add(request);

        }
    }
}