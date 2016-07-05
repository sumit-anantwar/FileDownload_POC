package com.example.marcgilbert.filedownloadtester.FileDownloaders;

import android.content.Context;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by marcgilbert on 05/07/2016.
 */
public class FileDownloaderHttpUrlConn implements FileDownloader{

    Context context;


    @Override
    public void DownloadFile(URL url, Listener listener) {

        HttpURLConnection conn = null;
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;

        try {

            String fileName = url.getFile();
            String[] parts = fileName.split("/");
            fileName = parts[parts.length-1];

            File file = new File( context.getCacheDir() + File.separator +  fileName);
            if( file.exists() ){
                file.delete();
            }
            file.createNewFile();

            conn = (HttpURLConnection) url.openConnection();
            inputStream = conn.getInputStream();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);


            fileOutputStream = new FileOutputStream(file);
            final byte data[] = new byte[1024];
            int count;
            while ((count = bufferedInputStream.read(data, 0, 1024)) != -1){
                fileOutputStream.write(data, 0, count);
            }

            listener.onFileDownloaded(file);


        } catch (IOException e) {
            e.printStackTrace();
            listener.onError(e.getMessage());

        }finally {

            if (inputStream != null) try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (fileOutputStream != null) try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if( conn!=null ){
                conn.disconnect();
            }
        }

    }


    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
