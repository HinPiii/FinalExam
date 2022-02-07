package com.vku.lmhiep.finalexam.Dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.vku.lmhiep.finalexam.R;

public class BottomSheetDialog extends BottomSheetDialogFragment {

    public BottomSheetDialog() {


    }

    public static BottomSheetDialog newInstance() {
        BottomSheetDialog fragment = new BottomSheetDialog();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setupDialog(Dialog dialog, int style) {
        setStyle(BottomSheetDialog.STYLE_NORMAL, R.style.DialogStyle);
        View contentView = View.inflate(getContext(), R.layout.comment_dialog, null);
        dialog.setContentView(contentView);
    }
}
