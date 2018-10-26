package com.flym.fhzk.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;
import com.flym.fhzk.R;

import java.io.File;

/**
 * Created by Morphine on 2017/8/12.
 */

public class GlideUtil {

    public static void loadImage(Context context, String imgUrl, ImageView imageView) {
        if (!TextUtils.isEmpty(imgUrl)) {
            Glide.with(context).load(ApiConstants.getImageUrl(imgUrl)).error(R.drawable.ic_simple).into(imageView);
        } else {
            Glide.with(context).load(R.drawable.ic_simple).into(imageView);
        }
    }

    public static void loadImage(Context context, String imgUrl, ImageView imageView, @DrawableRes int errorImgId) {
        if (!TextUtils.isEmpty(imgUrl)) {
            Glide.with(context).load(ApiConstants.getImageUrl(imgUrl)).error(errorImgId).into(imageView);
        } else {
            Glide.with(context).load(errorImgId).into(imageView);
        }
    }

    public static void loadImage(Context context, @DrawableRes int drawableRes, ImageView imageView) {
        Glide.with(context).load(drawableRes).into(imageView);
    }

    public static void loadImage(Context context, Bitmap bitmap, ImageView imageView) {
        Glide.with(context).load(bitmap).into(imageView);
    }

    public static void loadImage(Context context, File file, ImageView imageView) {
        Glide.with(context)
                .load(file)
                .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                .error(R.drawable.ic_simple)
                .into(imageView);
    }

    public static void loadImage(Context context, File file, ImageView imageView, @DrawableRes int errorDrawableRes) {
        Glide.with(context)
                .load(file)
                .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                .error(errorDrawableRes)
                .into(imageView);
    }

}
