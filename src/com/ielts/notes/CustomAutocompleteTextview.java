package com.ielts.notes;

import java.util.HashMap;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;

/** Customizing AutoCompleteTextView to return Place Description   
 *  corresponding to the selected item
 */
public class CustomAutocompleteTextview extends AutoCompleteTextView{

	public CustomAutocompleteTextview(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	/** Returns the place description corresponding to the selected item */
	@Override
	protected CharSequence convertSelectionToString(Object selectedItem) {
		/** Each item in the autocompetetextview suggestion list is a hashmap object */
		HashMap<String, String> hm = (HashMap<String, String>) selectedItem;
		return hm.get("description");
	}

}
