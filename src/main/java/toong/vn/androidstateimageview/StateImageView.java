package toong.vn.androidstateimageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

public class StateImageView extends AppCompatImageView {

    public StateImageView(Context context) {
        this(context, null);
    }

    public StateImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StateImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public void init(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.StateImageView);
        if (!isDuplicateParentStateEnabled()) {
            setClickable(true);
        }
        try {
            Drawable pressedDrawable = getPressedDrawable(ta);
            Drawable selectedDrawable = getSelectedDrawable(ta);
            Drawable disableDrawable = getDisableDrawable(ta);
            StateListDrawable stateList = new StateListDrawable();
            stateList.addState(new int[] { android.R.attr.state_pressed }, pressedDrawable);
            stateList.addState(new int[] { android.R.attr.state_selected }, selectedDrawable);
            stateList.addState(new int[] { -android.R.attr.state_enabled }, disableDrawable);
            stateList.addState(new int[] {},
                    getEnabledDrawable()); // must add it at last of all state
            setImageDrawable(stateList);
        } finally {
            ta.recycle();
        }
    }

    private Drawable getPressedDrawable(TypedArray ta) {
        int drawableId = ta.getResourceId(R.styleable.StateImageView_siv_srcPressed, 0);
        if (drawableId == 0) {
            Drawable drawable = getDrawable().getConstantState().newDrawable();
            drawable.setAlpha(
                    (int) (AppUtils.getFloatResource(getContext(), R.integer.alpha_default_pressed)
                            * 255));
            return drawable;
        }
        return AppCompatResources.getDrawable(getContext(), drawableId);
    }

    private Drawable getSelectedDrawable(TypedArray ta) {
        int drawableId = ta.getResourceId(R.styleable.StateImageView_siv_srcSelected, 0);
        if (drawableId == 0) {
            Drawable drawable = getDrawable().getConstantState().newDrawable();
            drawable.setAlpha(
                    (int) (AppUtils.getFloatResource(getContext(), R.integer.alpha_default_selected)
                            * 255));
            return drawable;
        }
        return AppCompatResources.getDrawable(getContext(), drawableId);
    }

    private Drawable getDisableDrawable(TypedArray ta) {
        int drawableId = ta.getResourceId(R.styleable.StateImageView_siv_srcDisable, 0);
        if (drawableId == 0) {
            Drawable drawable = getDrawable().getConstantState().newDrawable();
            drawable.setAlpha(
                    (int) (AppUtils.getFloatResource(getContext(), R.integer.alpha_default_disabled)
                            * 255));
            return drawable;
        }
        return AppCompatResources.getDrawable(getContext(), drawableId);
    }

    private Drawable getEnabledDrawable() {
        Drawable drawable = getDrawable().getConstantState().newDrawable();
        drawable.setAlpha(
                (int) (AppUtils.getFloatResource(getContext(), R.integer.alpha_default_enabled)
                        * 255));
        return drawable;
    }
}