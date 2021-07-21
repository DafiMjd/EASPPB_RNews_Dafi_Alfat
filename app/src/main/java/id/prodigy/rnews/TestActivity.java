package id.prodigy.rnews;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class TestActivity extends AppCompatActivity {
    private ImageView backgroundImage;
    private TextView title, author, publisher, publishedAt, content;
    private String mTitle, mAuthor, mDate, mContent, mUrl, mImg, mPublisher;
    private FloatingActionButton favoriteButton;
    private WebView webView;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        setComponentsID();
        Intent intent = getIntent();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mUrl = intent.getStringExtra("url");
        mImg = intent.getStringExtra("img");
        mTitle = intent.getStringExtra("title");
        mDate = intent.getStringExtra("date");
//        mSource = intent.getStringExtra("source");
        mPublisher = intent.getStringExtra("publisher");
        mAuthor = intent.getStringExtra("author");
        mAuthor = (mAuthor == "" || mAuthor == null) ? " - " : mAuthor;
        mContent = intent.getStringExtra("content");

        Glide.with(getApplication()).load(mImg).into(backgroundImage);
        title.setText(Utils.getTitle(mTitle));
        author.setText(mAuthor);
        publisher.setText(mPublisher);
        publishedAt.setText(Utils.DateFormat(mDate));
        content.setText(mContent);
    }

    private void setComponentsID() {
        backgroundImage = findViewById(R.id.iv_main_image);
        title = findViewById(R.id.tv_main_title);
        author = findViewById(R.id.tv_main_author);
        publisher = findViewById(R.id.tv_main_publisher);
        publishedAt = findViewById(R.id.tv_main_publish_date);
        content = findViewById(R.id.tv_main_content);
        favoriteButton = findViewById(R.id.fab_favorite_button);
    }


    public void onClickLinkToNews(View view) {
        showDialog(mUrl);
    }

    private void showDialog(String mUrl) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        webView = new WebView(this);
        webView.loadUrl(mUrl);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        alert.setView(webView);
        alert.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.show();
    }


}
