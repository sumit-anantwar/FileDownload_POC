package com.example.marcgilbert.filedownloadtester.FileDownloaders;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

/**
 * Created by marcgilbert on 05/07/2016.
 */
public class FileDownloadVolley implements FileDownloader{

    Context context;




    @Override
    public void DownloadFile(final URL url , final Listener listener) {

        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url.toString(),

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        File file = new File( url.getFile() );

                        if( file.exists() ){
                            file.delete();
                        }

                        try {
                            file.createNewFile();
                            FileWriter fileWriter = new FileWriter(file);
                            fileWriter.write(response);
                            fileWriter.close();
                            listener.onFileDownloaded(file);

                        } catch (IOException e) {
                            e.printStackTrace();
                            listener.onError(e.getMessage());
                        }


                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        listener.onError( error.getMessage() );

                    }
                });
        queue.add(stringRequest);

    }


    public void setContext(Context context) {
        this.context = context;
    }
}
