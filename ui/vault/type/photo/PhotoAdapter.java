package com.lionroarsrk.scapp.ui.vault.type.photo;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.SystemClock;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
//import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.lionroarsrk.scapp.R;
import com.lionroarsrk.scapp.crypto.operation.AppCryptoOperation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.crypto.SecretKey;
import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {


    private final List<Uri> mImages;
    private OnListFragmentInteractionListener mListener;


    @Inject
    public PhotoAdapter(ArrayList<Uri> mImages) {
        this.mImages = mImages;
    }

    public static final int VIEW_TYPE_EMPTY = 0;


    public static final int VIEW_TYPE_NORMAL = 1;


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.onBind(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                return new ViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_photo_item, parent, false));
            default:
                return new ViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_photo_item, parent, false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (this.mImages != null && mImages.size() > 0) {
            return VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_EMPTY;
        }
    }

    @Override
    public int getItemCount() {
        if (mImages != null && mImages.size() > 0) {
            return mImages.size();
        } else {
            return 0;
        }
    }

    public void addItems(HashSet<Uri> mImages) {
        this.mImages.clear();
        this.mImages.addAll(mImages);
        notifyDataSetChanged();
    }

    public void setmListener(OnListFragmentInteractionListener mListener) {
        this.mListener = mListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.list_image_view)
        ImageView coverImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void onBind(int position) {

            final Uri image = mImages.get(position);

            if (image != null) {

//                Glide.with(itemView.getContext())
//                        .load(image.getPath())
//                        .asBitmap()
//                        .centerCrop()
//                        .diskCacheStrategy(DiskCacheStrategy.NONE)
//                        .skipMemoryCache(true)
//                        .error(R.drawable.ic_photo_library_black_24dp)
//                        .into(new SimpleTarget<Bitmap>(150, 150) {
//                            @Override
//                            public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
//                                coverImageView.setImageBitmap(resource);
//                            }
//
//                            @Override
//                            public void onLoadFailed(Exception e, Drawable errorDrawable) {
//
//                            }
//                        });
                Glide.with(itemView.getContext())
                        .asBitmap()
                        .load(image.getPath())
                        .centerCrop()
                        .error(R.drawable.ic_photo_library_black_24dp)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .error(R.drawable.ic_photo_library_black_24dp)
                        .into(new CustomTarget<Bitmap>(150, 150) {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                coverImageView.setImageBitmap(resource);
                            }
                            @Override
                            public void onLoadCleared(@Nullable Drawable placeholder) { }
                        });


                itemView.setOnClickListener(v -> {
                    mListener.onListFragmentInteraction(image);
                });
            }

        }
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Uri imageUri);
    }

}
