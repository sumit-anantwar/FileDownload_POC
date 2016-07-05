package com.example.marcgilbert.filedownloadtester;

import com.example.marcgilbert.filedownloadtester.FileDownloaders.FileDownloader;

import java.io.File;
import java.net.URL;

/**
 * Created by marcgilbert on 05/07/2016.
 */
public class ThreadFileDownloadTest extends Thread{


    Integer nbTries = 10;
    FileDownloader fileDownloader;
    URL url;
    Listener listener;


    public ThreadFileDownloadTest(Integer nbTries, FileDownloader fileDownloader, URL url, Listener listener) {
        this.nbTries = nbTries;
        this.fileDownloader = fileDownloader;
        this.url = url;
        this.listener = listener;
    }

    @Override
    public void run() {

        for(int t=0 ; t<nbTries; t++ ){

            final long start = System.currentTimeMillis();

            final File[] fileDownloaded = {null};
            new Thread(){

                @Override
                public void run() {

                    fileDownloader.DownloadFile( url , new FileDownloader.Listener() {

                        @Override
                        public void onFileDownloaded(File file) {

                            fileDownloaded[0] = file;
                            synchronized (fileDownloaded){
                                fileDownloaded.notify();
                            }
                        }

                        @Override
                        public void onError(String errorMessage) {

                            synchronized (fileDownloaded){
                                fileDownloaded.notify();
                            }

                        }
                    });

                }
            }.start();


            synchronized (fileDownloaded){

                try {
                    fileDownloaded.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

            long stop  = System.currentTimeMillis();
            long seconds = (stop-start)/1000;


            if( fileDownloaded[0]!=null ){
                listener.onOneTestCompleted(seconds+" sec -- success, "+fileDownloaded[0].length()/1024+" kb "  );
            }
            else{
                listener.onOneTestCompleted(seconds+" sec -- failed ");
            }

        }

        listener.onAllTestsCompleted();

    }


    public interface Listener{

        public void onOneTestCompleted(String message);

        public void onAllTestsCompleted();

    }




}












