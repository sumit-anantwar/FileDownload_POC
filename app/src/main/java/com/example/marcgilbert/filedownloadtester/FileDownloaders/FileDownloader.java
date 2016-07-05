package com.example.marcgilbert.filedownloadtester.FileDownloaders;

import java.io.File;
import java.net.URL;

/**
 * Created by marcgilbert on 05/07/2016.
 */
public interface FileDownloader {

    public void DownloadFile(URL url , Listener listener );


    public interface Listener{

        public void onFileDownloaded(File file);

        public void onError(String errorMessage);

    }


}
