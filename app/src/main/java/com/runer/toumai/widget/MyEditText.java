package com.runer.toumai.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.EditText;

public class MyEditText extends android.support.v7.widget.AppCompatEditText {
	private OnImputCompleteListener mOnImputCompleteListener;

	public MyEditText(Context context) {
		super(context);
	}

	public MyEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onFocusChanged(boolean focused, int direction,
			Rect previouslyFocusedRect) {
		super.onFocusChanged(focused, direction, previouslyFocusedRect);

		if (!focused) {
			if (mOnImputCompleteListener!=null) {
				mOnImputCompleteListener.onImputComplete();
			}
		}
	}

	public void setOnImputCompleteListener(OnImputCompleteListener l) {
		this.mOnImputCompleteListener = l;
	}

	public interface OnImputCompleteListener {
		void onImputComplete();
	}
}
