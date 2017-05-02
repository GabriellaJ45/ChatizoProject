package com.example.gabriella.chatizoproject.keypattern;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.gabriella.chatizoproject.R;
import com.example.lock9view.Lock9View;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NormalActivity extends AppCompatActivity {

    @BindView(R.id.lock_9_view)
    protected Lock9View lock9View;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal);
        ButterKnife.bind(this);

        lock9View.setCallBack(new Lock9View.CallBack() {

            @Override
            public void onFinish(String password) {
                Toast.makeText(NormalActivity.this, password, Toast.LENGTH_SHORT).show();
            }

        });
    }

}
