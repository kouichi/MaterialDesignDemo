package jp.aknot.materialdesigndemo.widget.helper;

import android.support.annotation.ArrayRes;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;

/**
 * <pre>
 * +------------------+
 * | Title            |
 * +------------------|
 * | ( ) Item[1]      |
 * | ( ) Item[2]      |
 * | ( )  ...         |
 * +------------------+
 * |     [Cancel][OK] |
 * +------------------+
 * </pre>
 */
public final class ConfirmationDialogResHolder extends DialogResHolder {

    @ArrayRes
    public final int itemsResId;

    public ConfirmationDialogResHolder(@StringRes int titleResId, @ArrayRes int itemsResId) {
        this(UNKNOWN_RES_ID, titleResId, itemsResId);
    }

    public ConfirmationDialogResHolder(@StyleRes int themeResId, @StringRes int titleResId, @ArrayRes int itemsResId) {
        super(themeResId, titleResId);
        this.itemsResId = itemsResId;
    }
}
