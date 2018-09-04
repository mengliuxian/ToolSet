package com.flym.yh.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.widget.ImageView;
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
//import com.bumptech.glide.request.RequestOptions;
//import com.bumptech.glide.signature.ObjectKey;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.flym.yh.R;


import java.io.File;

/**
 * Created by Morphine on 2017/8/12.
 */

public class GlideUtil {


    //
    public static void loadImage(Context context, String imgUrl, ImageView imageView) {
        if (!TextUtils.isEmpty(imgUrl)) {

            Glide.with(context).load(imgUrl).apply(new RequestOptions().error(R.drawable.ic_logo)).into(imageView);
        } else {
            Glide.with(context).load(R.drawable.ic_logo).into(imageView);
        }
    }

    //
    public static void loadImage(Context context, String imgUrl, ImageView imageView, @DrawableRes int errorImgId) {
        if (!TextUtils.isEmpty(imgUrl)) {
            Glide.with(context).load(imgUrl).apply(new RequestOptions().error(errorImgId)).into(imageView);
        } else {
            Glide.with(context).load(errorImgId).into(imageView);
        }
    }

    //
    public static void loadImage(Context context, @DrawableRes int drawableRes, ImageView imageView) {
        Glide.with(context).load(drawableRes).into(imageView);
    }

    //
    public static void loadImage(Context context, Bitmap bitmap, ImageView imageView) {
        Glide.with(context).load(bitmap).into(imageView);
    }

    //
    public static void loadImage(Context context, File file, ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions()
                .signature(new ObjectKey(System.currentTimeMillis()));
        Glide.with(context)
                .load(file)
                .apply(requestOptions)
                .into(imageView);
    }

    //
    public static void loadImage(Context context, File file, ImageView imageView, @DrawableRes int errorDrawableRes) {
        RequestOptions requestOptions = new RequestOptions()
                .signature(new ObjectKey(System.currentTimeMillis()))
                .error(errorDrawableRes);
        Glide.with(context)
                .load(file)
                .apply(requestOptions)
                .into(imageView);
//
    }

}
