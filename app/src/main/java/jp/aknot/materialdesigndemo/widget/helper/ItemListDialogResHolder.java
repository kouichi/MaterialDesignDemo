package jp.aknot.materialdesigndemo.widget.helper;

import android.support.annotation.ArrayRes;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;

/**
 * <pre>
 * +------------------+
 * | Title            |
 * +------------------|
 * | @ Item[1]        |
 * | @ Item[2]        |
 * | @  ...           |
 * +------------------+
 * </pre>
 */
public final class ItemListDialogResHolder extends DialogResHolder {

    @ArrayRes
    public final int itemDrawablesResId;

    @ArrayRes
    public final int itemsResId;

    public ItemListDialogResHolder(@StringRes int titleResId, @ArrayRes int itemDrawablesResId, @ArrayRes int itemsResId) {
        this(UNKNOWN_RES_ID, titleResId, itemDrawablesResId, itemsResId);
    }

    public ItemListDialogResHolder(@StyleRes int themeResId, @StringRes int titleResId, @ArrayRes int itemDrawablesResId, @ArrayRes int itemsResId) {
        super(themeResId, titleResId);
        this.itemDrawablesResId = itemDrawablesResId;
        this.itemsResId = itemsResId;
    }
}
