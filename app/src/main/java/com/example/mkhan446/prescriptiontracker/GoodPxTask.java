package com.example.mkhan446.prescriptiontracker;

import android.os.AsyncTask;
import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.SignatureException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by mkhan446 on 4/23/2016.
 */
public class GoodPxTask extends AsyncTask<String, int, String>{
    private final String apiKey = "d049602abc";
    private final String secretKey = "WxSJvMBRd0IcVw";
    private final String url =  "https://api.goodrx.com/compare-price";

    protected String doInBackground(String medName) throws UnsupportedEncodingException, SignatureException, java.security.NoSuchAlgorithmException, java.security.InvalidKeyException{
        String query = buildQueryString(medName);
        String signature = encodeString(signQueryString(query));
        String signedRequest = createURL(query, signature);
    }

    protected void onPostExecute(Long result) {
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


}
