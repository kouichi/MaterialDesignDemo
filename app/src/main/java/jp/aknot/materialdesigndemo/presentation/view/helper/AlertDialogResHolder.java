package jp.aknot.materialdesigndemo.presentation.view.helper;

import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;

/**
 * <ul>
 * <li>Side-by-side buttons</li>
 * <li>Stacked full-width buttons</li>
 * </ul>
 *
 * <h3>Side-by-side buttons</h3>
 * <pre>
 * +-----------------------------------------------------+
 * | Title                                               |
 * +-----------------------------------------------------|
 * | Message                                             |
 * |                                                     |
 * |                                                     |
 * +-----------------------------------------------------+
 * | [NeutralButton]    [PositiveButton][NegativeButton] |
 * +-----------------------------------------------------+
 * </pre>
 *
 * <h3>Stacked full-width buttons</h3>
 * <pre>
 * +-------------------------+
 * | Title                   |
 * +-------------------------|
 * | Message                 |
 * |                         |
 * |                         |
 * +-------------------------+
 * |        [PositiveButton] |
 * |        [NegativeButton] |
 * |         [NeutralButton] |
 * +-------------------------+
 * </pre>
 */
public final class AlertDialogResHolder extends DialogResHolder {

    @StringRes
    public final int msgResId;

    @StringRes
    public final int positiveBtnTextResId;

    @StringRes
    public final int negativeBtnTextResId;

    @StringRes
    public final int neutralBtnTextResId;

    public AlertDialogResHolder(@StringRes int titleResId, @StringRes int msgResId,
            @StringRes int positiveBtnTextResId, @StringRes int negativeBtnTextResId, @StringRes int neutralBtnTextResId) {
        this(UNKNOWN_RES_ID, titleResId, msgResId, positiveBtnTextResId, negativeBtnTextResId, neutralBtnTextResId);
    }

    public AlertDialogResHolder(@StyleRes int themeResId, @StringRes int titleResId, @StringRes int msgResId,
            @StringRes int positiveBtnTextResId, @StringRes int negativeBtnTextResId, @StringRes int neutralBtnTextResId) {
        super(themeResId, titleResId);
        this.msgResId = msgResId;
        this.positiveBtnTextResId = positiveBtnTextResId;
        this.negativeBtnTextResId = negativeBtnTextResId;
        this.neutralBtnTextResId = neutralBtnTextResId;
    }
}
