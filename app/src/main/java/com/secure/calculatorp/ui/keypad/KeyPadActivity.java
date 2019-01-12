package com.secure.calculatorp.ui.keypad;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.secure.calculatorp.R;
import com.secure.calculatorp.ui.base.BaseActivity;
import com.secure.calculatorp.ui.custom.KeyPad;
import com.secure.calculatorp.ui.task.DecryptionTask;
import com.secure.calculatorp.ui.vault.VaultActivity;
import com.secure.calculatorp.util.AppConstants;
import com.secure.calculatorp.util.DialogUtil;
import com.secure.calculatorp.util.StringUtil;


import javax.crypto.SecretKey;
import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class KeyPadActivity extends BaseActivity implements KeyPadView {


    @Inject
    KeyPadPresenter<KeyPadView> presenter;

    @BindView(R.id.layout_keypad)
    KeyPad keyPad;

    @BindView(R.id.et_key)
    TextView etKey;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keypad);
        setUnBinder(ButterKnife.bind(this));
        getActivityComponent().inject(this);
        presenter.onAttach(this);

        if (keyPad != null)
            keyPad.setKeyUpdateListener(key -> presenter.handleKeyInput(key));
    }

    @Override
    protected void setUp() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onVisibleScreen();
    }


    @Override
    public void showPinDialog() {
        DialogUtil.showAlert(getString(R.string.pin_alert_title),
                getString(R.string.pin_alert_message)
                , KeyPadActivity.this);
    }

    @Override
    public void showPinSuccessDialog() {
        DialogUtil.showAlert(getString(R.string.pin_success_title),
                getString(R.string.pin_success_message)
                , KeyPadActivity.this);
    }

    @Override
    public void showPinErrorToast() {
        Toast.makeText(this, R.string.pin_error_message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setResultView(String result) {
        if (result != null && etKey != null) {
            etKey.setText(result);
        }
    }

    @Override
    public void destroyActivity() {
        finish();
    }

    @Override
    public void moveToVaultActivity() {
        Intent intent = new Intent(KeyPadActivity.this, VaultActivity.class);
        startActivity(intent);
    }

    @Override
    public void showProgress() {
        keyPad.setVisibility(View.GONE);
        etKey.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {

    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.onHiddenScreen();
    }

    @Override
    public boolean isPermissionGranted(String permission) {
        return hasPermission(permission);
    }

    @Override
    public void requestPermission(String[] permissions, int requestCode) {
        requestPermissionsSafely(permissions, requestCode);
    }
}
