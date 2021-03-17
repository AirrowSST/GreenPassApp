package com.example.greenpassapp.ui;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.example.greenpassapp.R;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * A full-screen dialog to show the GREEN PASS.
 */
public class ScannedFragment extends DialogFragment {

    private static final int REQUEST_CODE = 2;

    private String url;
    private boolean webViewDone = false;
    private @Nullable View root;

    public ScannedFragment(String url) {
        this.url = url;
    }

    /**
     * show admin fragment!
     */
    public static void showDialog(FragmentManager manager, String url) {
        ScannedFragment newFragment = new ScannedFragment(url);
        newFragment.show(manager, "scanned_dialog");
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            // ok
            dialog.getWindow().setWindowAnimations(
                    R.style.DialogAnimation
            );
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, R.style.Theme_GreenPassApp_Dialog_Fullscreen);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout (layout == balloon!)
        return inflater.inflate(R.layout.fragment_scanned, container, false);
    }

    @Override
    public void onViewCreated(@NotNull View root, Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);

        this.root = root;

        requireDialog().getWindow().setWindowAnimations(
                R.style.DialogAnimation
        );

        KeyboardManager.hideKeyboard(this.requireContext(), root);

        // close button
        ImageButton closeButton = root.findViewById(R.id.close_dialog);
        closeButton.setOnClickListener(view1 -> {
            dismiss();
            KeyboardManager.hideKeyboard(this.requireContext(), view1);
        });

//        if (ContextCompat.checkSelfPermission(
//                requireActivity(),
//                Manifest.permission.CAMERA
//        ) == PackageManager.PERMISSION_GRANTED) {
//
//            this.initialiseWebView(root);
//
//        } else {
//
//            // make an array of permissions:
//            requestPermissions(new String[] { Manifest.permission.INTERNET }, REQUEST_CODE);
//
//        }
        initialiseWebView(root);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if ((grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                if (!webViewDone) {
                    initialiseWebView(root);
                }
            } else {
                Snackbar.make(Objects.requireNonNull(root), "Oh no, no internet!", Snackbar.LENGTH_LONG);
            }
        }
    }

    public void initialiseWebView(View root) {
        if (webViewDone) return;

        WebView webView = root.findViewById(R.id.web_view);
        webView.loadUrl(url);

        webViewDone = true;
    }

    /**
     * The system calls this only when creating the layout in a dialog.
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // The only reason you might override this method when using onCreateView() is
        // to modify any dialog characteristics. For example, the dialog includes a
        // title by default, but your custom layout might not need it. So here you can
        // remove the dialog title, but you must call the superclass to get the Dialog.
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }
}