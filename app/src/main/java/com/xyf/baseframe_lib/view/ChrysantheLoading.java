package com.xyf.baseframe_lib.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.xyf.baseframe_lib.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/8/10.
 */

public class ChrysantheLoading extends Dialog {
    @Bind(R.id.tx)
    TextView tx;

    private Context mContext;
    private static int theme = R.style.dialog;

    public ChrysantheLoading(Context context) {
        this(context, 0);
    }

    public ChrysantheLoading(Context context, int themeResId) {
        super(context, theme);
        this.mContext = context;
        init();
    }

    private void init() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_chrysanthe_loading, null);
        ButterKnife.bind(this, view);
        setContentView(view);
        setCanceledOnTouchOutside(false);
    }

    public void setMsg(String message) {
        if (message == null) {
            if (tx.getVisibility() == View.VISIBLE)
                tx.setVisibility(View.GONE);
            return;
        }
        if (tx.getVisibility() == View.GONE)
            tx.setVisibility(View.VISIBLE);
        tx.setText(message);
    }
}
