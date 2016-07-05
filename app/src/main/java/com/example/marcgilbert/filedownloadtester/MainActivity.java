package com.example.marcgilbert.filedownloadtester;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.marcgilbert.filedownloadtester.FileDownloaders.FileDownloadVolley;
import com.example.marcgilbert.filedownloadtester.FileDownloaders.FileDownloader;
import com.example.marcgilbert.filedownloadtester.FileDownloaders.FileDownloaderHttpAsync;
import com.example.marcgilbert.filedownloadtester.FileDownloaders.FileDownloaderHttpUrlConn;

import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {


    EditText editTextUrl;
    Button buttonVolley;
    Button buttonHttpASync;
    Button buttonHttpUrlConn;
    ProgressBar progressBar;

    EditText editTextResults;
    ThreadFileDownloadTest threadFileDownloadTest;
    URL url;
    Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        handler = new Handler();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        editTextResults = (EditText) findViewById(R.id.editTextResults);
        editTextUrl = (EditText) findViewById(R.id.editTextUrl);
        try {
            url = new URL( editTextUrl.getText().toString() );
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        buttonVolley = (Button) findViewById(R.id.buttonVolley);
        buttonVolley.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(  url!=null &&
                    ( threadFileDownloadTest==null || threadFileDownloadTest.getState()== Thread.State.TERMINATED )
                ){

                    editTextResults.setText("");
                    startProgressBar();

                    FileDownloadVolley fileDownloader = new FileDownloadVolley();
                    fileDownloader.setContext(getApplicationContext());

                    threadFileDownloadTest = new ThreadFileDownloadTest(10, fileDownloader, url, new ThreadFileDownloadTest.Listener() {
                        @Override
                        public void onOneTestCompleted(final String message) {

                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    editTextResults.append(message+"\n");
                                }
                            });
                        }

                        @Override
                        public void onAllTestsCompleted() {
                            stopProgressBar();
                        }
                    });

                    threadFileDownloadTest.start();

                }
            }
        });


        buttonHttpASync = (Button) findViewById(R.id.buttonHttpASync);
        buttonHttpASync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(  url!=null &&
                        ( threadFileDownloadTest==null || threadFileDownloadTest.getState()== Thread.State.TERMINATED )
                        ){

                    editTextResults.setText("");
                    startProgressBar();

                    FileDownloaderHttpAsync fileDownloader = new FileDownloaderHttpAsync();
                    fileDownloader.setContext(getApplicationContext());

                    threadFileDownloadTest = new ThreadFileDownloadTest(10, fileDownloader, url, new ThreadFileDownloadTest.Listener() {
                        @Override
                        public void onOneTestCompleted(final String message) {


                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    editTextResults.append(message+"\n");
                                }
                            });
                        }

                        @Override
                        public void onAllTestsCompleted() {

                            stopProgressBar();

                        }
                    });

                    threadFileDownloadTest.start();


                }


            }
        });


        buttonHttpUrlConn = (Button) findViewById(R.id.buttonHttpUrlConn);
        buttonHttpUrlConn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(  url!=null &&
                        ( threadFileDownloadTest==null || threadFileDownloadTest.getState()== Thread.State.TERMINATED )
                        ){

                    editTextResults.setText("");
                    startProgressBar();

                    FileDownloaderHttpUrlConn fileDownloader = new FileDownloaderHttpUrlConn();
                    fileDownloader.setContext(getApplicationContext());

                    threadFileDownloadTest = new ThreadFileDownloadTest(10, fileDownloader, url, new ThreadFileDownloadTest.Listener() {
                        @Override
                        public void onOneTestCompleted(final String message) {

                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    editTextResults.append(message+"\n");
                                }
                            });
                        }

                        @Override
                        public void onAllTestsCompleted() {

                            stopProgressBar();
                        }
                    });

                    threadFileDownloadTest.start();

                }
            }
        });



    }


    private void startProgressBar(){

        handler.post(new Runnable() {
            @Override
            public void run() {

                if( progressBar!=null ){

                    progressBar.setVisibility(View.VISIBLE);
                }

            }
        });

    }

    private void stopProgressBar(){

        handler.post(new Runnable() {
            @Override
            public void run() {

                if( progressBar!=null ){
                    progressBar.setVisibility(View.INVISIBLE);
                }

            }
        });


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }








}
