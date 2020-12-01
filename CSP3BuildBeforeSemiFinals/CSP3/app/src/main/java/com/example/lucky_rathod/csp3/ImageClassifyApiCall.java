package com.example.lucky_rathod.csp3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Created by Lucky_Rathod on 16-02-2018.
 */

public class ImageClassifyApiCall {

    private static final String serverURL =
            "http://192.168.43.242:8000/classify_image/classify/api/";

    private URL connectURL;
    private String imagePath;
    private FileInputStream fileInputStream;

    public ImageClassifyApiCall(String imgPath, String cacheDir) throws IOException {


        Log.d("path",imgPath);
        long fileSize = new File(imgPath).length();
        Log.d("File size =", String.valueOf(fileSize));
        int qualityPercentage = (int) (10240000 / fileSize);
        if(qualityPercentage > 100) qualityPercentage = 100;

        File tmpFile = File.createTempFile("tf_", ".jpeg", new File(cacheDir));
        FileOutputStream fos = new FileOutputStream(tmpFile);
        BitmapFactory.decodeFile(imgPath).compress(Bitmap.CompressFormat.JPEG, qualityPercentage, fos);

        this.fileInputStream = new FileInputStream(tmpFile);
        this.imagePath = imgPath;
        connectURL = new URL(serverURL);
    }

    public List<Pair<String, Float>> uploadAndClassify() throws IOException, JSONException {
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";

        HttpURLConnection conn = (HttpURLConnection)connectURL.openConnection();
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);

        DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
        dos.writeBytes("Content-Disposition: form-data; name=\"image\";filename=\"" + imagePath +"\"" + lineEnd);
        dos.writeBytes(lineEnd);

        int maxBufferSize = 1024;
        int bytesAvailable = fileInputStream.available();
        int bufferSize = Math.min(bytesAvailable, maxBufferSize);
        byte[] buffer = new byte[bufferSize];

        int bytesRead = fileInputStream.read(buffer, 0, bufferSize);
        while (bytesRead > 0) {
            dos.write(buffer, 0, bufferSize);
            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable,maxBufferSize);
            bytesRead = fileInputStream.read(buffer, 0,bufferSize);
        }

        fileInputStream.close();

        dos.writeBytes(lineEnd + twoHyphens + boundary + twoHyphens + lineEnd);
        dos.flush();
        dos.close();

        if(conn.getResponseCode() == 200){
            Log.d("Erroorrrrrrrrrrr","error arriveed");
            return parseResponse(conn.getInputStream());
        }
        else {
            return null;
        }
    }

    private List<Pair<String, Float>> parseResponse(InputStream is) throws IOException, JSONException {
        List<Pair<String, Float>> result = new ArrayList<>();

        StringBuilder jsonSB =new StringBuilder();
        for(int ch; ( ch = is.read() ) != -1;){ jsonSB.append( (char)ch); }

        String jsonString = jsonSB.toString();

        JSONObject responseObject = new JSONObject(jsonString);
        boolean success = responseObject.getBoolean("success");

        if(success){
            JSONObject confidenceScores = responseObject.getJSONObject("confidence");
            Iterator<String> keys = confidenceScores.keys();
            while(keys.hasNext()) {
                String key = keys.next();
                if(TextUtils.equals(key, "success")) continue;
                String strValue = confidenceScores.getString(key);
                Float value = Float.parseFloat(strValue);
                result.add(new Pair<>(key, value));
            }
            Collections.sort(result, new Comparator<Pair<String, Float>>() {
                @Override
                public int compare(Pair<String, Float> o1, Pair<String, Float> o2) {
                    if(o1.second > o2.second) return -1;
                    else return 1;
                }
            });
        }
        else return null;

        return result;

    }

}
