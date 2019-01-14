package com.secure.calculatorp.ui.vault.type.photo;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.secure.calculatorp.R;
import com.secure.calculatorp.di.component.ActivityComponent;
import com.secure.calculatorp.ui.base.BaseFragment;
import com.secure.calculatorp.ui.vault.type.photo.dialog.PhotoDialogFragment;


import java.util.HashSet;

import javax.crypto.SecretKey;
import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PhotoFragment extends BaseFragment implements PhotoView, PhotoAdapter.OnListFragmentInteractionListener {


    public static final String TAG_PHOTO_DIALOG_FRAGMENT = "tag_photo_dialog_fragment";
    @Inject
    PhotoAdapter photoAdapter;

    @BindView(R.id.list_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.swipe_photo_layout)
    SwipeRefreshLayout swipeLayout;

    @Inject
    PhotoPresenter<PhotoView> presenter;


    public PhotoFragment() {
    }


    public static PhotoFragment newInstance() {
        PhotoFragment fragment = new PhotoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo_list, container, false);


        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            presenter.onAttach(this);
        }

        Context context = getBaseActivity();
        photoAdapter.setmListener(this);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        recyclerView.setAdapter(photoAdapter);
        swipeLayout.setOnRefreshListener(() -> presenter.onRefresh());
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onViewCreated();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }

    @Override
    protected void setUp(View view) {

    }

    @Override
    public boolean isPermissionGranted(String permission) {
        return getBaseActivity().hasPermission(permission);
    }

    @Override
    public void requestPermission(String[] permissions, int requestCode) {

    }

    @Override
    public void updateList(HashSet<Uri> imageList) {
        photoAdapter.addItems(imageList);
        swipeLayout.setRefreshing(false);
    }

    @Override
    public void showFullScreenImageView(Uri uri) {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
        PhotoDialogFragment photoDialogFragment = PhotoDialogFragment.newInstance(uri,
                new PhotoDialogFragment.OnRestoreClickListener() {
                    @Override
                    public void onRestoreClicked(Dialog dialog) {
                        presenter.onRestoreClicked(uri);
                        dialog.dismiss();
                        presenter.onUpdate();
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }

                    @Override
                    public void writeToParcel(Parcel parcel, int i) {

                    }
                });
        photoDialogFragment.show(ft, TAG_PHOTO_DIALOG_FRAGMENT);
    }

    @Override
    public void onListFragmentInteraction(Uri uri) {
        presenter.onImageClick(uri);
    }

}
