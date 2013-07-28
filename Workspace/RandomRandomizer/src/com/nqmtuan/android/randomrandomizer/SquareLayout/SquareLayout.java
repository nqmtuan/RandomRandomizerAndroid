package com.nqmtuan.android.randomrandomizer.SquareLayout;

import android.content.Context;
import android.util.AttributeSet;

public class SquareLayout extends AspectLayout {
	protected float multiplier = 1.0f;
	protected float offset = 0.0f;
	
	public SquareLayout(Context context) {
		super(context);
	}
	
	public SquareLayout (Context context, AttributeSet attr) {
		super (context, attr);
	}
}
