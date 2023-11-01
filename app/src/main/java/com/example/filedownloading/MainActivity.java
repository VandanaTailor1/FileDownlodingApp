package com.example.filedownloading;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
        private static final int RESULT_CODE_PROGRESS = 0;
        private static final int RESULT_CODE_COMPLETE = 1;
        private ProgressBar progressBar;
        private TextView progressText;
        Button startButton;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            startButton =findViewById(R.id.startDownloadButton);

            progressBar = findViewById(R.id.progressBar);
            progressText = findViewById(R.id.progressText);

            final ResultReceiver receiver = new ResultReceiver(new Handler()) {
                @Override
                protected void onReceiveResult(int resultCode, Bundle resultData) {
                    if (resultCode == RESULT_CODE_PROGRESS) {
                        int progress = resultData.getInt("progress");
                        updateProgress(progress);
                    } else if (resultCode == RESULT_CODE_COMPLETE) {
                        boolean success = resultData.getBoolean("success");
                    }
                }
            };
             startButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startDownloadService("http://example.com/yourfile.zip", receiver);
                }
            });
        }

        private void startDownloadService(String downloadUrl, ResultReceiver receiver) {
            Intent downloadIntent = new Intent(this, DownloadService.class);
            downloadIntent.putExtra("download_url", downloadUrl);
            downloadIntent.putExtra("receiver", receiver);
            startService(downloadIntent);
        }
        private void updateProgress(int progress) {
            progressBar.setProgress(progress);
            progressText.setText("Progress: " + progress + "%");
        }
    }


}