package com.ielts.notes;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

public class SetLocationActivity extends FragmentActivity implements OnClickListener, OnTouchListener{
	
	private FrameLayout map_frame;
	private EditText enter_location_edt;
	private Button find_loc_btn;
	private GoogleMap googleMap;
	
	static final LatLng AMEERPET = new LatLng(17.436793, 78.443906);
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_location);
        InitializeUI();
        setListeners();
        

	}

	private void setListeners() {
		// TODO Auto-generated method stub
		map_frame.setOnTouchListener(this);
		enter_location_edt.setOnClickListener(this);
		find_loc_btn.setOnClickListener(this);
	}

	private void InitializeUI() {
		// TODO Auto-generated method stub
		map_frame = (FrameLayout) findViewById(R.id.map_frame_layout);
		map_frame.setVisibility(View.INVISIBLE);
		enter_location_edt = (EditText) findViewById(R.id.enter_location_editText);
		find_loc_btn = (Button) findViewById(R.id.find_location_button);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		
		case R.id.find_location_button:
			
			map_frame.setVisibility(View.VISIBLE);	
			hideSoftKeyboard();
			displayLocation();
			
		break;
		
		}
	}

	private void displayLocation() {
		// TODO Auto-generated method stub
		
		// Getting Google Play availability status
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

        if(status!=ConnectionResult.SUCCESS){ // Google Play Services are not available

            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
            dialog.show();

        }else { // Google Play Services are available
        	
        	googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map_fragment)).getMap();
		    
		    Marker kiel = googleMap.addMarker(new MarkerOptions().position(AMEERPET).title("Ameerpet").snippet("Hyderabad, India."));

		    // Move the camera instantly to hamburg with a zoom of 15.
		    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(AMEERPET, 15));

		    // Zoom in, animating the camera.
		    googleMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
        }
	}
	
	/**
	 * Hides the soft keyboard
	 */
	public void hideSoftKeyboard() {
	    if(getCurrentFocus()!=null) {
	        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
	        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
	    }
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

}
