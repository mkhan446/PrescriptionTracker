package com.example.brand.goodrx;

/**
 * Created by mkhan446 on 4/24/2016.
 */
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GoodRxActivity extends AppCompatActivity {

    private TextView medName;
    private TextView price;
    private TextView quantity;
    private TextView dosage;
    private TextView savingTitle;
    private TextView savingDescription;
    private Button search;
    //    private final String apiKey = "d049602abc";
//    private final String SECRET_KEY = "VPpa9Ur+WxSJvMBRd0IcVw==";
    private final String url =  "https://api.goodrx.com/low-price?name=lipitor&api_key=d049602abc&sig=J1dFDmj5Ufz184RnI2fYjXweotJLv0nCAoS04JNKwCw=";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_rx);
        medName = (TextView)findViewById(R.id.displayText);
        price = (TextView)findViewById(R.id.priceText);
        quantity = (TextView)findViewById(R.id.quantityText);
        dosage = (TextView)findViewById(R.id.dosageText);
        savingTitle = (TextView)findViewById(R.id.savingsHeader);
        savingDescription = (TextView)findViewById(R.id.savingsDescription);
        search = (Button)findViewById(R.id.button);
        search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                //try{
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
//                    signature = URLEncoder.encode(signature, "-8");
//                    String signedQueryString = String.format("?%s&sig=%s=", queryString, signature);
//                    String URL = url+signedQueryString;
//                    System.out.println(URL);
//                    String query = "?name="+drugName.getText().toString();
                // Instantiate the RequestQueue
                RequestQueue queue = Volley.newRequestQueue(getBaseContext());
                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the first 500 characters of the response string.
                                parseJSON(response);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        CharSequence text = "Unable to retrieve data!";
                        Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
                // Add the request to the RequestQueue.
                queue.add(stringRequest);

//                }
//                catch(){
//                }
            }
        });

    }

    protected void parseJSON(String json){
        try {
            JSONObject reader = new JSONObject(json);
            JSONObject data = reader.getJSONObject("data");
            medName.setText(data.getString("display"));
            price.setText("$"+data.getJSONArray("price").get(0).toString());
            quantity.setText(data.getString("quantity"));
            dosage.setText(data.getString("dosage"));
            JSONArray savingsArray = data.getJSONArray("savings_tips");
            JSONObject savingsObject = savingsArray.getJSONObject(0);
            savingTitle.setText(savingsObject.getString("title"));
            savingDescription.setText(savingsObject.getString("description"));
        }
        catch(JSONException e){
            e.printStackTrace();
        }

    }

//    protected String buildQueryString(String medName){
//       // String query = "name=" + medName + "&api_key=" +apiKey;
//        return null;
//    }
//
//    protected byte[] signQueryString(String query) throws SignatureException, java.security.NoSuchAlgorithmException, java.security.InvalidKeyException{
////        //SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes(StandardCharsets.US_ASCII), "HmacSHA256");
////        Mac mac = Mac.getInstance("HmacSHA256");
////        mac.init(signingKey);
////        return mac.doFinal(query.getBytes(StandardCharsets.US_ASCII));
//            return null;
//    }
//
//    protected String encodeString(byte[] hMacBytes) throws UnsupportedEncodingException {
//        String signature = Base64.encodeToString(hMacBytes, Base64.DEFAULT);
//        signature = signature.replace("+", "_");
//        signature = signature.replace("/", "_");
//        signature = URLEncoder.encode(signature, StandardCharsets.US_ASCII.name());
//        return signature;
//    }
//
//    protected String createURL(String query, String signature){
//        return url+"?"+query+"&sig="+signature;
//    }



}
