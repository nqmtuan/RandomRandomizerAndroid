package com.nqmtuan.android.randomrandomizer;

import android.content.Context;
import android.widget.RelativeLayout;

public class RandomCollectionTile extends RelativeLayout {
	private RandomElementCollection collection;
	private Context context;

	public RandomCollectionTile(Context _context) {
		super(_context);
		this.context = _context;
	}
	
	public RandomCollectionTile (Context _context, RandomElementCollection _collection) {
		this (_context);
		this.collection = _collection;
	}
	
	public void linkUI () {
		
	}
}
