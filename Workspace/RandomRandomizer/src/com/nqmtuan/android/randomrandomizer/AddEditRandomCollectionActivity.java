package com.nqmtuan.android.randomrandomizer;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

public class AddEditRandomCollectionActivity extends Activity {
	public ScrollView scrollView;
	public EditText nameEditText;
	public EditText nSelectionEditText;
	public CheckBox shouldAllowRepeatCheckBox;
	public Button addImageButton;
	public RelativeLayout imageCollectionContainer;
	public EditText listElementNameEditText;
	
	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_edit_random_collection);
		linkUI ();
	}
	
	public void linkUI () {
		scrollView = (ScrollView) this.findViewById(R.id.create_random_collection_scroll_view);
		nameEditText = (EditText) this.findViewById(R.id.create_random_collection_name_edit_text);
		nSelectionEditText = (EditText) this.findViewById(R.id.create_random_collection_nselection_edit_text);
		shouldAllowRepeatCheckBox = (CheckBox) this.findViewById(R.id.create_random_collection_should_allow_repeat_check_box);
		addImageButton = (Button) this.findViewById(R.id.create_random_collection_image_add_button);
		imageCollectionContainer = (RelativeLayout) this.findViewById(R.id.create_random_collection_image_collection_container);
		listElementNameEditText = (EditText) this.findViewById(R.id.create_random_collection_list_element_name_edit_text);		
	}
	
	
}
