package com.example.marcgilbert.filedownloadtester.FileDownloaders;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

import cz.msebera.android.httpclient.Header;

/**
 * Created by marcgilbert on 05/07/2016.
 */
public class FileDownloaderHttpAsync implements FileDownloader{

    Context context;


    @Override
    public void DownloadFile(final URL url, final Listener listener) {


        SyncHttpClient client = new SyncHttpClient();
        client.setTimeout(20000);
        client.get(context, url.toString() , new AsyncHttpResponseHandler()
        {
            @Override
            public void onProgress(long bytesWritten, long totalSize)
            {
                //super.onProgress(bytesWritten, totalSize);

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody)
            {

                try {

                    String fileName = url.getFile();
                    String[] parts = fileName.split("/");
                    fileName = parts[parts.length-1];

                    File file = new File( context.getCacheDir() + File.separator +  fileName);
                    if( file.exists() ){
                        file.delete();
                    }
                    file.createNewFile();



                    //FileOutputStream outputStream = context.openFileOutput( fileName  , Context.MODE_PRIVATE);
                    FileOutputStream outputStream = new FileOutputStream(file);
                    outputStream.write(responseBody);
                    outputStream.close();


                    listener.onFileDownloaded( file );


                } catch (IOException e) {
                    e.printStackTrace();
                    listener.onError(e.getMessage());
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error)
            {

                listener.onError(error.getMessage());

            }

            @Override
            public void onRetry(int retryNo) {
                super.onRetry(retryNo);

   //             Log.i(LOG_TAG, "Retry count : " + retryNo);
            }
        });




    }


    public void setContext(Context context) {
        this.context = context;
    }
}
