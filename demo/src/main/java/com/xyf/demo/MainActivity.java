package com.xyf.demo;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.text_layout)
    TextInputLayout text_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ThreadDemo demo = new ThreadDemo();
        demo.teset();
    }

    @OnTextChanged(value = R.id.edit_tx, callback = OnTextChanged.Callback.TEXT_CHANGED)
    void onTextChanged(CharSequence s, int start, int before, int count) {
        if (TextUtils.isEmpty(s)) {
            text_layout.setError(null);
        } else if (s.length() > 10) {
            text_layout.setError("密码长度超过十位");
        } else {
            text_layout.setError(null);
        }
    }
}
