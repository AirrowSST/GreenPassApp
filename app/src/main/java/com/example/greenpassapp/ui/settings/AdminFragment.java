package com.example.greenpassapp.ui.settings;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.example.greenpassapp.R;
import com.example.greenpassapp.model.NRICModel;
import com.example.greenpassapp.model.PasswordCreator;
import com.example.greenpassapp.ui.KeyboardManager;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * A full-screen dialog to show the GREEN PASS.
 */
public class AdminFragment extends DialogFragment {

    public AdminFragment() {}

    /**
     * show admin fragment!
     */
    public static void showDialog(FragmentManager manager) {
        AdminFragment newFragment = new AdminFragment();
        newFragment.show(manager, "dialog");
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            // doesn't work!?
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
        return inflater.inflate(R.layout.fragment_admin, container, false);
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

        // stuff in the check fragment
        Button getPasswordButton = root.findViewById(R.id.get_password_button);
        TextInputLayout layout = root.findViewById(R.id.layout_admin_username);
        TextInputEditText input = root.findViewById(R.id.input_admin_username);
//        TextView resultText = root.findViewById(R.id.check_result);

        // on username text changed (the same as check)
        TextWatcher usernameTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // no
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = s.toString();
                // if the text is a valid NRIC
                if (NRICModel.checkIC(text)) {
                    // show a tick ✔
                    layout.setHelperText("✔");
                    // and enable the check button
                    getPasswordButton.setEnabled(true);
                    // and update the password
                    TextView textView = root.findViewById(R.id.password_result);
                    textView.setText(getString(R.string.text_password_result, PasswordCreator.create(text)));
                } else {
                    // disable the button due to invalid input
                    getPasswordButton.setEnabled(false);
                    // do stuff based on the length of the input
                    int len = text.length();
                    if (len != 9) {
                        // invalid ic length
                        if (len > 9) {
                            // too many characters
                            if (len > 20) {
                                layout.setError(getString(R.string.error_spam));
                            } else {
                                layout.setError(getString(R.string.error_long_nric));
                            }
                        } else {
                            // too few characters
                            layout.setHelperText("...");
                        }
                    } else {
                        // correct number of characters, but still invalid
                        layout.setError(getString(R.string.error_invalid_nric));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // nothing to see here
            }
        };
        input.addTextChangedListener(usernameTextWatcher);

        getPasswordButton.setOnClickListener(view -> {

            // hide the keyboard
            KeyboardManager.hideKeyboard(requireContext(), view);

            String ic = Objects.requireNonNull(input.getText()).toString();
            KeyboardManager.copyToClipboard(requireContext(), "Password", PasswordCreator.create(ic));

            Snackbar.make(view, "Password copied to clipboard!", Snackbar.LENGTH_LONG)
                .show();

            // no need to update textview here

        });

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