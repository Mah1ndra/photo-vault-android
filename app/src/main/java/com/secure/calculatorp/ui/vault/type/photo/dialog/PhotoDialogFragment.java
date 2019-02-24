package com.secure.calculatorp.ui.vault.type.photo.dialog;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.secure.calculatorp.R;
import com.secure.calculatorp.data.DataManager;
import com.secure.calculatorp.di.component.ActivityComponent;
import com.secure.calculatorp.ui.base.BaseDialogFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;



public class PhotoDialogFragment extends BaseDialogFragment implements PhotoDialogView {


    public static final String IMAGE_PATH = "image_path";
    private static final String LISTENER = "restore_click_listener";

    @BindView(R.id.iv_image_full)
    ImageView imageView;

    @BindView(R.id.btn_restore)
    ImageButton btnRestore;

    @BindView(R.id.btn_back)
    ImageButton btnBack;

    @Inject
    PhotoDialogPresenter<PhotoDialogView> presenter;


    private Uri imageFilePath;
    private OnRestoreClickListener mListener;


    public static PhotoDialogFragment newInstance(Uri uri, OnRestoreClickListener onRestoreClickListener) {
        PhotoDialogFragment photoDialogFragment = new PhotoDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(IMAGE_PATH, uri);
        bundle.putParcelable(LISTENER, onRestoreClickListener);
        photoDialogFragment.setArguments(bundle);
        return photoDialogFragment;
    }

    public PhotoDialogFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_FullScreenDialog);
    }

    @Override
    protected void setUp(View view) {

    }

    @Override
    public void onStart() {

        getDialog().getWindow().setWindowAnimations(R.style.AppTheme_FullScreenDialog);
        super.onStart();
        presenter.onViewCreated();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.photo_dialog_fragment, container, false);

        ButterKnife.bind(view);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            presenter.onAttach(this);
        }


        btnRestore.setOnClickListener(view1 -> {
            presenter.onRestoreClick();
        });

        btnBack.setOnClickListener((view2) -> {
            getDialog().dismiss();
        });
        return view;
    }

    @Override
    public void switchToFullScreen() {
        Dialog d = getDialog();
        if (d != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            d.getWindow().setLayout(width, height);
        }
    }

    @Override
    public void initArguments() {
        if (getArguments() != null) {
            imageFilePath = getArguments().getParcelable(IMAGE_PATH);
            mListener = getArguments().getParcelable(LISTENER);
            if (imageFilePath != null) {
                Glide.with(getContext())
                        .load(imageFilePath.getPath())
                        .asBitmap()
                        .centerCrop()
                        .error(R.drawable.ic_photo_library_black_24dp)
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                                imageView.setImageBitmap(resource);
                            }

                            @Override
                            public void onLoadFailed(Exception e, Drawable errorDrawable) {

                            }
                        });
            }
        }
    }

    @Override
    public void transmitRestoreClick() {
        if (mListener != null) {
            mListener.onRestoreClicked(getDialog());
        }
    }

    public interface OnRestoreClickListener extends Parcelable {
        void onRestoreClicked(Dialog dialog);
    }
}
