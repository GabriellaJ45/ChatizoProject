package com.example.gabriella.chatizoproject.keypattern;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.gabriella.chatizoproject.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.send_button)
    public void onBtnNormalClick() {
        startActivity(new Intent(this, NormalActivity.class));
    }
    

}


