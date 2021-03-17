package com.example.greenpassapp.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.example.greenpassapp.R;
import com.example.greenpassapp.model.Account;

import org.jetbrains.annotations.NotNull;

/**
 * A full-screen dialog to show the GREEN PASS.
 */
public class PassDialog extends DialogFragment {

    public PassDialog() {}

    /**
     * use this method to show the pass (must be called from a fragment?)
     */
    public static void showDialog(FragmentManager manager) {
        PassDialog newFragment = new PassDialog();
        newFragment.show(manager, "dialog");

//        // show the fragment fullscreen
//        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
//        // transition animation
//        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//        // show
//        transaction
//                .add(android.R.id.content, newFragment)
//                .addToBackStack(null)
//                .commit();
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            // no need, already in style
//            int width = ViewGroup.LayoutParams.MATCH_PARENT;
//            int height = ViewGroup.LayoutParams.MATCH_PARENT;
//            dialog.getWindow().setLayout(width, height);

            // doesn't work?
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

    /**
     * The system calls this to get the DialogFragment's layout, regardless of whether it's being displayed as a dialog or an embedded fragment.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout (layout == balloon?)
        return inflater.inflate(R.layout.pass_dialog_layout, container, false);
    }

    @Override
    public void onViewCreated(@NotNull View root, Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);

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

        TextView text = root.findViewById(R.id.greenPassDate);

        text.setText(Account.getUserPassDate());

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