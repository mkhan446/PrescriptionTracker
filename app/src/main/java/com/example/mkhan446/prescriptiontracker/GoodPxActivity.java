package com.example.mkhan446.prescriptiontracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class GoodPxActivity extends AppCompatActivity {

    private EditText medName;
    private Button search;
    private TextView resultView;
//    private final String apiKey = "d049602abc";
//    private final String SECRET_KEY = "VPpa9Ur+WxSJvMBRd0IcVw==";
    private final String url =  "https://api.goodrx.com/low-price?name=lipitor&api_key=d049602abc&sig=J1dFDmj5Ufz184RnI2fYjXweotJLv0nCAoS04JNKwCw=";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_px);
        medName = (EditText)findViewById(R.id.medName);
        search = (Button)findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
//                try{
//                    String query = buildQueryString(medName.getText().toString());
//                    String signature = encodeString(signQueryString(query));
//                    String URL = createURL(query, signature);
//                    System.out.println(URL);

//                    String queryString = "name=lipitor&api_key=d049602abc";
//                    byte[] code = SECRET_KEY.getBytes(StandardCharsets.US_ASCII);
//                    Mac hmSha256 = Mac.getInstance("HmacSHA256");
//                    hmSha256.init(new SecretKeySpec(code, "HmacSHA256"));
//                    byte[] hashMe = queryString.getBytes();
//                    byte[] hmBytes = hmSha256.doFinal(hashMe);
//                    String signature = Base64.encodeToString(hmBytes, Base64.DEFAULT);
//                    signature = signature.replace("+", "_").replace("/", "_");
//                    signature = URLEncoder.encode(signature, "UTF-8");
//                    String signedQueryString = String.format("?%s&sig=%s=", queryString, signature);
//                    String URL = url+signedQueryString;
//                    System.out.println(URL);

                    // Instantiate the RequestQueue
                    RequestQueue queue = Volley.newRequestQueue(getBaseContext());
                    // Request a string response from the provided URL.
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    // Display the first 500 characters of the response string.
                                    resultView.setText(response);
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            resultView.setText("That didn't work!");
                        }
                    });
                    // Add the request to the RequestQueue.
                    queue.add(stringRequest);

//                }
//                catch(){
//                }
            }
        });

        resultView = (TextView)findViewById(R.id.resultView);
    }

    protected String buildQueryString(String medName){
       // String query = "name=" + medName + "&api_key=" +apiKey;
        return null;
    }

    protected byte[] signQueryString(String query) throws SignatureException, java.security.NoSuchAlgorithmException, java.security.InvalidKeyException{
//        //SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes(StandardCharsets.US_ASCII), "HmacSHA256");
//        Mac mac = Mac.getInstance("HmacSHA256");
//        mac.init(signingKey);
//        return mac.doFinal(query.getBytes(StandardCharsets.US_ASCII));
            return null;
    }

    protected String encodeString(byte[] hMacBytes) throws UnsupportedEncodingException {
        String signature = Base64.encodeToString(hMacBytes, Base64.DEFAULT);
        signature = signature.replace("+", "_");
        signature = signature.replace("/", "_");
        signature = URLEncoder.encode(signature, StandardCharsets.US_ASCII.name());
        return signature;
    }

    protected String createURL(String query, String signature){
        return url+"?"+query+"&sig="+signature;
    }



}
