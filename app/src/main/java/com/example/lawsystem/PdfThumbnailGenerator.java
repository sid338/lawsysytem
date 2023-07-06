package com.example.lawsystem;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import okhttp3.OkHttpClient;
import java.util.concurrent.TimeUnit;

public class PdfThumbnailGenerator {

    public static void generateThumbnail(Context context, String pdfUrl, ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL); // Caching the thumbnail

        Glide.with(context)
                .setDefaultRequestOptions(requestOptions)
                .asBitmap()
                .load(pdfUrl)
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@NonNull GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        // Handle failure
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        // Thumbnail loaded successfully, use the Bitmap
                        imageView.setImageBitmap(resource);
                        return true;
                    }
                })
                .submit(); // Start the image request
    }
}
