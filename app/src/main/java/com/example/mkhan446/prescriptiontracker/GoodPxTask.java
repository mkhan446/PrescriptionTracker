package com.example.mkhan446.prescriptiontracker;

import android.os.AsyncTask;
import android.util.Base64;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by mkhan446 on 4/23/2016.
 */
public class GoodPxTask extends AsyncTask<String, Void, String>{

    private final String apiKey = "d049602abc";
    private final String secretKey = "WxSJvMBRd0IcVw";
    private final String url =  "https://api.goodrx.com/compare-price";

    protected String doInBackground(String... medNames){
        try{
            String query = buildQueryString(medNames[0]);
            String signature = encodeString(signQueryString(query));
            String URL = createURL(query, signature);




            return URL;
        }
        catch(UnsupportedEncodingException|SignatureException|NoSuchAlgorithmException |InvalidKeyException e){
            return null;
        }

    }

    protected String buildQueryString(String medName){
        String query = "name=" + medName + "&api_key=" +apiKey;
        return query;
    }

    protected byte[] signQueryString(String query) throws SignatureException, java.security.NoSuchAlgorithmException, java.security.InvalidKeyException{
        SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes(StandardCharsets.US_ASCII), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(signingKey);
        return mac.doFinal(query.getBytes(StandardCharsets.US_ASCII));

    }

    protected String encodeString(byte[] hMacBytes) throws UnsupportedEncodingException {
        String signature = Base64.encodeToString(hMacBytes, Base64.DEFAULT);
        signature = signature.replace("+", "_");
        signature = signature.replace("/", "_");
        signature = URLEncoder.encode(signature, "UTF-8");
        return signature;
    }

    protected String createURL(String query, String signature){
        return url+"?"+query+"&sig="+signature;
    }

    protected void onPostExecute(Long result) {
    }




}
