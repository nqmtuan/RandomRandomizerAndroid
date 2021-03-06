/**
 * 
 */
package com.nqmtuan.android.randomrandomizer.SquareLayout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * @author nqmtuan
 * This class is a base class that generates a layout where the height = (width * multiplier + offset)
 */
public class AspectLayout extends RelativeLayout {
	protected float multiplier = 1.0f;
	protected float offset = 0.0f;
	
	public AspectLayout(Context context) {
		super(context);
	}	

	public AspectLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	protected void onMeasure (int widthMeasureSpec, int heightMeasureSpec) {
		int width = MeasureSpec.getSize(widthMeasureSpec);
		int height = MeasureSpec.getSize(heightMeasureSpec);
		if (height > width * multiplier + offset) {
			height = (int) (width * multiplier + offset);
		}
		else if (height < width * multiplier + offset) {
			width = (int)((height - offset)/ multiplier);			
		}		
		super.onMeasure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
	}
	
}
