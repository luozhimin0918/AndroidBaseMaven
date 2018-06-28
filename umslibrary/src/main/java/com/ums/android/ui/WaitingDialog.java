package com.ums.android.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import com.ums.android.R;

public class WaitingDialog extends ProgressDialog {

    private View    rootView;
    private Context mContext;

    public WaitingDialog(Context context) {
        super(context, R.style.dialog);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView = LayoutInflater.from(mContext).inflate(R.layout.ums_common_progress_dialog, null);
        setContentView(rootView);
    }

    public void setDialogWindowStyle() {
        Window       window = this.getWindow();
        LayoutParams lp     = window.getAttributes();
        lp.flags = LayoutParams.FLAG_DIM_BEHIND;
        lp.alpha = 1.0f;
        lp.dimAmount = 0.5f;
        window.setAttributes(lp);
        //window.setLayout(68, 68);
    }
}
