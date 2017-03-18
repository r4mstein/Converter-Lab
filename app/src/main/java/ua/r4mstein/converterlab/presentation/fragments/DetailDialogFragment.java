package ua.r4mstein.converterlab.presentation.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Layout;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import ua.r4mstein.converterlab.R;

import static ua.r4mstein.converterlab.util.Constants.DETAIL_DIALOG_BUNDLE_KEY;

public final class DetailDialogFragment extends DialogFragment {

    public DetailDialogFragment() {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dialog_detail, container);
    }

    public static DetailDialogFragment newInstance(ArrayList<String> strings) {
        DetailDialogFragment dialogFragment = new DetailDialogFragment();

        Bundle bundle = new Bundle();
        bundle.putStringArrayList(DETAIL_DIALOG_BUNDLE_KEY, strings);

        dialogFragment.setArguments(bundle);
        return dialogFragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView imageView = (ImageView) view.findViewById(R.id.dialog_image_view);
        Button button = (Button) view.findViewById(R.id.dialog_button);

        final Bitmap bitmap = getBitmap();
        imageView.setImageBitmap(bitmap);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareBitmap(bitmap, "Currency");
            }
        });
    }

    private Bitmap getBitmap() {
        ArrayList<String> list = getArguments().getStringArrayList(DETAIL_DIALOG_BUNDLE_KEY);

        SpannableString title = new SpannableString(list.get(0));
        title.setSpan(new RelativeSizeSpan(1.3f), 0, title.length(), 0);
        title.setSpan(new StyleSpan(Typeface.BOLD), 0, title.length(), 0);

        SpannableString region = new SpannableString(list.get(1));
        SpannableString city = new SpannableString(list.get(2));

        SpannableStringBuilder orgStringBuilder = new SpannableStringBuilder();
        orgStringBuilder.append("\n").append("\t\t").append(title)
                .append("\n").append("\t\t").append(region)
                .append("\n").append("\t\t").append(city);

        SpannableStringBuilder currencyStringBuilder = new SpannableStringBuilder();
        for (int i = 3; i < list.size(); i++) {
            final String s = list.get(i);
            SpannableString currency = new SpannableString(s);
            currency.setSpan(new RelativeSizeSpan(1.3f), 0, currency.length(), 0);
            currency.setSpan(new StyleSpan(Typeface.BOLD), 0, 3, 0);
            currency.setSpan(new ForegroundColorSpan(Color.RED), 0, 3, 0);
            currencyStringBuilder.append("\n").append(currency);
        }
        currencyStringBuilder.append("\n");

        int resWidth;
        int width = Resources.getSystem().getDisplayMetrics().widthPixels;
        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        if (width < height) {
            resWidth = width;
        }
        else resWidth = height;

        return drawText(orgStringBuilder, currencyStringBuilder, resWidth);
    }

    public Bitmap drawText(SpannableStringBuilder orgText, SpannableStringBuilder currencyText, int textWidth) {

        TextPaint textPaint = new TextPaint();
        textPaint.setColor(Color.parseColor("#1b1b1b"));
        textPaint.setTextSize(30);
        textPaint.setTypeface(Typeface.MONOSPACE);

        StaticLayout orgTextLayout = new StaticLayout(orgText, textPaint, textWidth, Layout.Alignment.ALIGN_NORMAL, 1.3f, 0.0f, false);
        StaticLayout currencyTextLayout = new StaticLayout(currencyText, textPaint, textWidth, Layout.Alignment.ALIGN_CENTER, 1.7f, 0.0f, false);

        Bitmap orgBitmap = Bitmap.createBitmap(textWidth, orgTextLayout.getHeight(), Bitmap.Config.ARGB_4444);
        Canvas orgCanvas = new Canvas(orgBitmap);
        orgCanvas.drawColor(Color.WHITE);
        orgTextLayout.draw(orgCanvas);

        Bitmap currencyBitmap = Bitmap.createBitmap(textWidth, currencyTextLayout.getHeight(), Bitmap.Config.ARGB_4444);
        Canvas currencyCanvas = new Canvas(currencyBitmap);
        currencyCanvas.drawColor(Color.WHITE);
        currencyTextLayout.draw(currencyCanvas);

        Bitmap resBitmap = Bitmap.createBitmap(textWidth, orgTextLayout.getHeight() + currencyTextLayout.getHeight(), Bitmap.Config.ARGB_4444);
        Canvas resCanvas = new Canvas(resBitmap);
        resCanvas.drawColor(Color.WHITE);
        resCanvas.drawBitmap(orgBitmap, 0f, 0f, null);
        resCanvas.drawBitmap(currencyBitmap, 0f, orgBitmap.getHeight(), null);

        return resBitmap;
    }

    private void shareBitmap (Bitmap bitmap, String fileName) {
        try {
            File file = new File(getContext().getCacheDir(), fileName + ".png");
            FileOutputStream fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
            file.setReadable(true, false);
            final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
            intent.setType("image/png");
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
