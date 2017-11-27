package jp.aknot.materialdesigndemo.presentation.util;

import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

public class TextViewLinkify {

    public interface OnLinkClickListener {
        void onLinkClick(int linkId, @NonNull String linkText);
    }

    public static void linkify(@NonNull TextView textView, @NonNull SparseArray<String> linkTexts, @NonNull OnLinkClickListener onLinkClickListener) {
        CharSequence text = textView.getText();

        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(text);

        int size = linkTexts.size();
        for (int i = 0; i < size; i++) {
            final int linkId = linkTexts.keyAt(i);
            final String linkText = linkTexts.valueAt(i);
            int start = TextUtils.indexOf(text, linkText);
            int end = start + linkText.length();
            builder.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View view) {
                    onLinkClickListener.onLinkClick(linkId, linkText);
                }
            }, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        textView.setText(builder);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
