package ua.r4mstein.converterlab.presentation.fragments;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import ua.r4mstein.converterlab.R;

public final class ProgressDialogFragment extends DialogFragment {


    public ProgressDialogFragment() {
        super();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(final Bundle _savedInstanceState) {
        Dialog dialog = super.onCreateDialog(_savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public View onCreateView(final LayoutInflater _inflater, final ViewGroup _container,
                             final Bundle _savedInstanceState) {
        return _inflater.inflate(R.layout.fragment_progress_dialog, _container);
    }

    @Override
    public void onViewCreated(final View _view, @Nullable final Bundle _savedInstanceState) {
        super.onViewCreated(_view, _savedInstanceState);
    }
}
