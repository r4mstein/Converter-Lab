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
import android.os.Environment;
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
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import ua.r4mstein.converterlab.R;
import ua.r4mstein.converterlab.util.logger.LogManager;
import ua.r4mstein.converterlab.util.logger.Logger;

import static ua.r4mstein.converterlab.util.Constants.DETAIL_DIALOG_BUNDLE_KEY;

public final class DetailDialogFragment extends DialogFragment {

    private static final String TAG = "DetailDialogFragment";

    private Logger mLogger;

    public DetailDialogFragment() {
        super();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(final Bundle _savedInstanceState) {
        Dialog dialog = super.onCreateDialog(_savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater _inflater, @Nullable final ViewGroup _container,
                             @Nullable final Bundle _savedInstanceState) {
        return _inflater.inflate(R.layout.fragment_dialog_detail, _container);
    }

    public static DetailDialogFragment newInstance(final ArrayList<String> _strings) {
        DetailDialogFragment dialogFragment = new DetailDialogFragment();

        Bundle bundle = new Bundle();
        bundle.putStringArrayList(DETAIL_DIALOG_BUNDLE_KEY, _strings);

        dialogFragment.setArguments(bundle);
        return dialogFragment;
    }

    @Override
    public void onViewCreated(final View _view, @Nullable final Bundle _savedInstanceState) {
        super.onViewCreated(_view, _savedInstanceState);
        mLogger = LogManager.getLogger();

        ImageView imageView = (ImageView) _view.findViewById(R.id.dialog_image_view);
        Button button = (Button) _view.findViewById(R.id.dialog_button);

        final Bitmap bitmap = getBitmap();
        imageView.setImageBitmap(bitmap);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    shareBitmap(bitmap, "Currency");
                } catch (Exception e) {
                    e.printStackTrace();
                    mLogger.d(TAG, "Exception: " + e.getMessage());
                    Toast.makeText(getActivity(), "We got the error. Try again.", Toast.LENGTH_SHORT).show();
                }
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
        } else resWidth = height;

        return drawText(orgStringBuilder, currencyStringBuilder, resWidth);
    }

    private Bitmap drawText(final SpannableStringBuilder _orgText,
                            final SpannableStringBuilder _currencyText, final int _textWidth) {

        TextPaint textPaint = new TextPaint();
        textPaint.setColor(Color.parseColor("#1b1b1b"));
        textPaint.setTextSize(30);
        textPaint.setTypeface(Typeface.MONOSPACE);

        StaticLayout orgTextLayout = new StaticLayout(_orgText, textPaint, _textWidth, Layout.Alignment.ALIGN_NORMAL, 1.3f, 0.0f, false);
        StaticLayout currencyTextLayout = new StaticLayout(_currencyText, textPaint, _textWidth, Layout.Alignment.ALIGN_CENTER, 1.7f, 0.0f, false);

        Bitmap orgBitmap = Bitmap.createBitmap(_textWidth, orgTextLayout.getHeight(), Bitmap.Config.ARGB_4444);
        Canvas orgCanvas = new Canvas(orgBitmap);
        orgCanvas.drawColor(Color.WHITE);
        orgTextLayout.draw(orgCanvas);

        Bitmap currencyBitmap = Bitmap.createBitmap(_textWidth, currencyTextLayout.getHeight(), Bitmap.Config.ARGB_4444);
        Canvas currencyCanvas = new Canvas(currencyBitmap);
        currencyCanvas.drawColor(Color.WHITE);
        currencyTextLayout.draw(currencyCanvas);

        Bitmap resBitmap = Bitmap.createBitmap(_textWidth, orgTextLayout.getHeight() + currencyTextLayout.getHeight(), Bitmap.Config.ARGB_4444);
        Canvas resCanvas = new Canvas(resBitmap);
        resCanvas.drawColor(Color.WHITE);
        resCanvas.drawBitmap(orgBitmap, 0f, 0f, null);
        resCanvas.drawBitmap(currencyBitmap, 0f, orgBitmap.getHeight(), null);

        return resBitmap;
    }

    private void shareBitmap(final Bitmap _bitmap, final String _fileName) throws Exception {
        File file = getShareFile(_bitmap, _fileName);

        final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        intent.setType("image/png");
        startActivity(Intent.createChooser(intent, "SHARE CURRENCY"));
    }

    private File getShareFile(final Bitmap _bitmap, final String _fileName) throws Exception {
        FileOutputStream fOut = null;
        File file = null;

        try {
            File path = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES), "TestDir");
            if (!path.mkdirs()) {

            }

            file = new File(path, _fileName + System.currentTimeMillis() + ".png");
            fOut = new FileOutputStream(file);
            _bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
        } finally {
            if (fOut != null)
                fOut.close();
        }

        return file;
    }
}
