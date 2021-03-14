package com.example.greenpassapp.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.greenpassapp.R;
import com.example.greenpassapp.model.RealController;

import org.jetbrains.annotations.NotNull;

/**
 * A full-screen dialog to show the GREEN PASS.
 */
class PassDialog extends DialogFragment {

    /**
     * use this method to show the pass (must be called from a fragment?)
     */
    public static void showDialog(FragmentActivity activity) {
        PassDialog newFragment = new PassDialog();
        // show the fragment fullscreen
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        // transition animation
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        // show
        transaction
                .add(android.R.id.content, newFragment)
                .addToBackStack(null)
                .commit();
    }

    /**
     * The system calls this to get the DialogFragment's layout, regardless of whether it's being displayed as a dialog or an embedded fragment.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout to use as dialog or embedded fragment
        return inflater.inflate(R.layout.pass_dialog_layout, container, false);
    }

    @Override
    public void onViewCreated(@NotNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RealController.hideKeyboard(requireContext(), view);
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