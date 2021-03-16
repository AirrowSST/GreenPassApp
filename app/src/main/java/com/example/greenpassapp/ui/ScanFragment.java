package com.example.greenpassapp.ui;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.greenpassapp.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.Result;

import java.util.Objects;

public class ScanFragment extends Fragment {


    private CodeScanner mCodeScanner;
    private @Nullable View root;
    private boolean isScannerSet = false;
    private final int REQUEST_CODE = 1;

    /**
     * show this fragment
     */
    public static void startScan(FragmentManager fragmentManager) {
        ScanFragment newScanFragment = new ScanFragment();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction
                .add(android.R.id.content, newScanFragment)
                .addToBackStack(null)
                .commit(); // die
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("scan fragment opened");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_scan, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);

        this.root = root;

        Activity activity = this.requireActivity();
        CodeScannerView scannerView = root.findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(activity, scannerView);

        if (ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED) {

            this.initialiseScanner(root);

        } else {

            // make an array of permissions:
            requestPermissions(new String[] { Manifest.permission.CAMERA }, REQUEST_CODE);

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    public void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if ((grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                if (!isScannerSet) {
                    initialiseScanner(root);
                }
            } else {
                // alert user using a snackbar
                // (Typo: In word 'snackbar')
                Snackbar.make(Objects.requireNonNull(root), "Oh no, camera denied!", Snackbar.LENGTH_LONG);
            }
        }
    }

    private void initialiseScanner(View root) {
        if (isScannerSet) return;

        Activity activity = this.requireActivity();

        // when QR code decoded
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        done(activity, result.getText());
//                        Toast.makeText(activity, result.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        // click thing
        root.findViewById(R.id.scanner_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });

        // already set
        isScannerSet = true;
    }

    // This method is run when a QR code is decoded.
    private void done(Activity activity, String text) {
        // change this to something more useful than a TOAST
        Toast.makeText(activity, text, Toast.LENGTH_SHORT).show();
    }
}