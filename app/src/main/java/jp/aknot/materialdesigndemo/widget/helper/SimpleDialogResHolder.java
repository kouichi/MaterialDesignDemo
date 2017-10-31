package jp.aknot.materialdesigndemo.widget.helper;

import android.support.annotation.ArrayRes;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;

/**
 * <pre>
 * +------------------+
 * | Title            |
 * +------------------|
 * | Item[1]          |
 * | Item[2]          |
 * |  ...             |
 * +------------------+
 * </pre>
 */
public final class SimpleDialogResHolder extends DialogResHolder {

    @ArrayRes
    public final int itemsResId;

    public SimpleDialogResHolder(@StringRes int titleResId, @ArrayRes int itemsResId) {
        this(UNKNOWN_RES_ID, titleResId, itemsResId);
    }

    public SimpleDialogResHolder(@StyleRes int themeResId, @StringRes int titleResId, @ArrayRes int itemsResId) {
        super(themeResId, titleResId);
        this.itemsResId = itemsResId;
    }
}
