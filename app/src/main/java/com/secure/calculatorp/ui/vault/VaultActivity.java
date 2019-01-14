package com.secure.calculatorp.ui.vault;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;

import com.secure.calculatorp.R;
import com.secure.calculatorp.ui.base.BaseActivity;
import com.secure.calculatorp.ui.vault.type.photo.PhotoFragment;
import com.secure.calculatorp.util.AppConstants;
import com.secure.calculatorp.util.DialogUtil;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VaultActivity extends BaseActivity implements VaultView {

    public static final String FRAGMENT_PHOTOS = "fragment_photos";
    @Inject
    VaultPresenter<VaultView> presenter;

    @Inject
    FragmentManager fragmentManager;

    @BindView(R.id.navigation_vault)
    BottomNavigationView mBottomNavigationView;

    @BindView(R.id.fab)
    FloatingActionButton actionButton;

    private boolean onPauseForFileSelection = false;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_photos:
                    presenter.onNavItemSelected(R.id.navigation_photos);
                    return true;
                case R.id.navigation_videos:
                    presenter.onNavItemSelected(R.id.navigation_videos);
                    return true;
                case R.id.navigation_file:
                    presenter.onNavItemSelected(R.id.navigation_file);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vault);

        getActivityComponent()
                .inject(this);
        setUnBinder(ButterKnife.bind(this));
        presenter.onAttach(this);
        setUp();
    }

    @Override
    protected void setUp() {
        setTitle("Photos");
        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void switchFragment(AppConstants.Fragment fragment) {
        if (fragmentManager != null) {
            if (fragment == AppConstants.Fragment.PHOTO) {
                fragmentManager.beginTransaction()
                        .replace(R.id.layout_vault_fragment_container, PhotoFragment.newInstance(), FRAGMENT_PHOTOS)
                        .commit();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onScreenVisible();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.onScreenHidden();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @OnClick(R.id.fab)
    void onNewImage() {
        presenter.onAddClick();
    }

    @Override
    public boolean isPermissionGranted(String permission) {
        return hasPermission(permission);
    }

    @Override
    public void requestPermission(String[] permissions, int requestCode) {
        requestPermissionsSafely(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestCode);
    }

    @Override
    public void openFileBrowser() {
        presenter.onPausedForSelection();
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setType("image/*");
        startActivityForResult(intent, AppConstants.REQUEST_CODE_FILE_SELECTION);
    }

    @Override
    public void encryptionErrorDialog() {
        DialogUtil.showAlert(getString(R.string.title_encryption_error),
                getString(R.string.message_encryption_error),
                VaultActivity.this);
    }

    @Override
    public void destroyActivity() {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
        if (requestCode == AppConstants.REQUEST_CODE_FILE_SELECTION && resultCode == Activity.RESULT_OK) {
            if (resultData != null) {
                presenter.onImageSelected(resultData);
            }
        }
    }

    @Override
    public void showLoading() {
        super.showLoading();
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
    }
}
