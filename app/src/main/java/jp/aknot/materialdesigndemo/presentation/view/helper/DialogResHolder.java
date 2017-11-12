package jp.aknot.materialdesigndemo.presentation.view.helper;

import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;

public abstract class DialogResHolder {

    public static final int UNKNOWN_RES_ID = 0;

    @StyleRes
    public final int themeResId;

    @StringRes
    public final int titleResId;

    public DialogResHolder(@StyleRes int themeResId, @StringRes int titleResId) {
        this.themeResId = themeResId;
        this.titleResId = titleResId;
    }
}
