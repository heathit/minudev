package com.minu.jjaljup.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.minu.jjaljup.R;

public class QuickProgressDialog extends Dialog {

        public QuickProgressDialog(Context context) {
            super(context);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.layout_loading);

            ImageView iv = (ImageView)findViewById(R.id.img_loding);
            //iv.setImageResource(R.drawable.img);
            Glide.with(context)
                    .load(R.drawable.gif_loading)
                    .asGif()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(iv);
        }
}
