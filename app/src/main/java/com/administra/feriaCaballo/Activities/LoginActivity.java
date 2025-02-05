package com.administra.feriaCaballo.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.administra.feriaCaballo.MainActivity;
import com.administra.feriaCaballo.Model.MySingleton;
import com.administra.feriaCaballo.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {
    EditText emailET, passwordET;
    Button loginBTN;
    private FirebaseAuth mAuth;
    LinearLayout splash;
    RelativeLayout loading;
    private static final String TAG = "DocSnippets";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        loading = findViewById(R.id.loadingPanel);
        splash = findViewById(R.id.splash);
        emailET = findViewById(R.id.email_login);
        passwordET = findViewById(R.id.password_login);
        loginBTN = findViewById(R.id.login_btn);

        //reviso si hay user
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){

            loginHttp(mAuth.getCurrentUser().getEmail(),"Kforum2020");

        }else {
            splash.setVisibility(View.GONE);
        }
        loginBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loading.setVisibility(View.VISIBLE);
                if(emailET.getText().toString().isEmpty()&&passwordET.getText().toString().isEmpty()){
                    Toast.makeText(LoginActivity.this, "Enter your email and password",
                            Toast.LENGTH_SHORT).show();
                }else {
                    loginFB(emailET.getText().toString(),passwordET.getText().toString());
                }
            }
        });

    }

    public void loginFB(final String email, final String password){
        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            loginHttp(email, password);
                        } else {
                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                        }


                    }
                });
        // [END sign_in_with_email]

    }
    public void loginHttp(String email,String password){
        HashMap<String, Object> map = new HashMap<>();// Mapeo previo
        map.put("email", email);
        map.put("password", password);
        JSONObject jsonObject = new JSONObject(map);
        String url = "https://www.kforum2020.com/backend/apps/login_user.php";

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, jsonObject,
                new Response.Listener<JSONObject>() {

                    int estado=0;
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            estado= response.getInt("estado");
                            int tipo= response.getInt("tipo");
                            String nombre= response.getString("nombre")+" "+response.getString("apellido");
                            String id= response.getString("id");
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("nombre", nombre);
                            intent.putExtra("id", id);
                            startActivity(intent);
                            getToken(tipo);

                            //progressDialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                /*progressDialog.dismiss();
                toastPersonalizado.crearToast("Error de conexión, revisa tu conexión a internet e intenta nuevamente", null);*/
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("User-agent", "My useragent");
                return headers;
            }


        };

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjReq);

    }

    public void getToken(final int tipo){
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
//                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        String email=mAuth.getCurrentUser().getEmail();
                        String id=mAuth.getCurrentUser().getUid();

                        DocumentReference washingtonRef = db.collection("users").document(id);

// Set the "isCapital" field of the city 'DC'
                        Map<String, Object> docData = new HashMap<>();
                        docData.put("email", email);
                        docData.put("tipo", tipo);
                        docData.put("token", token);
                        washingtonRef
                                .set(docData)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "DocumentSnapshot successfully updated!");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w(TAG, "Error updating document", e);
                                    }
                                });

                        // Log and toast
//                        String msg = getString("", token);
//                        Log.d(TAG, msg);

                    }
                });
    }
}