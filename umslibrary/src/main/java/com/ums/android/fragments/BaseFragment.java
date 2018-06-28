package com.ums.android.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.ums.android.R;
import com.ums.android.helper.BitmapHelper;
import com.ums.android.ui.WaitingDialog;
import org.xutils.common.Callback;

/**
 */
public class BaseFragment extends Fragment {
    private String title;
    private WaitingDialog mDlg;
    private static final String TAG = "BaseFragment";
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ums_fragment_base, null, false);
        TextView textView = (TextView) view.findViewById(R.id.text);
        textView.setText(getTitle());
        return view;
    }

    protected void startWaitDialog() {
        // 加载等待提示
        if (mDlg == null ) {
            mDlg = new WaitingDialog(getActivity());
            mDlg.show();
            mDlg.setDialogWindowStyle();
        }
    }

    protected void removeWaitDialog() {
        // 图片加载结束，移除等待提示框
        if (mDlg != null) {
            mDlg.dismiss();
            mDlg = null;
        }
    }


    /**
     * 异步加载网络图片
     * @param iv
     * @param imgUrl
     */
    protected void asyncLoadImageView(ImageView iv, String imgUrl){
        BitmapHelper.displayImageFromUrl(iv, imgUrl, new Callback.CommonCallback<Drawable>() {
            @Override
            public void onSuccess(Drawable result) {

            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(Callback.CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

}
