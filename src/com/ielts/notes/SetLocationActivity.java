package com.ielts.notes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.json.JSONObject;

import android.app.Dialog;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class SetLocationActivity extends FragmentActivity implements OnClickListener, OnTouchListener, OnItemClickListener{

	private FrameLayout map_frame;
	private AutoCompleteTextView enter_location_auto;
	private Button show_on_map_btn, set_location_btn;
	private GoogleMap googleMap;
	private ImageView current_location_iv;
	private LatLng Current_Location;
	
	// GPSTracker class
    GPSTracker gps;

	
	PlacesTask placesTask;
	ParserTask parserTask;
	
	private static LatLng Current_location_latlng;
	
	private static final String LOG_TAG = "Google Places Autocomplete";
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_location);
        
        InitializeUI();
        setListeners();
        
        //final GooglePlacesAutocompleteAdapter mAdapter = null;
        
        enter_location_auto.setThreshold(1);		
		
        enter_location_auto.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {				
				placesTask = new PlacesTask();				
				placesTask.execute(s.toString());
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub				
			}
		});	

	}

	private void setListeners() {
		// TODO Auto-generated method stub
		map_frame.setOnTouchListener(this);
		enter_location_auto.setOnClickListener(this);
		//show_on_map_btn.setOnClickListener(this);
		set_location_btn.setOnClickListener(this);
		enter_location_auto.setOnItemClickListener(this);
		current_location_iv.setOnClickListener(this);
	}

	private void InitializeUI() {
		// TODO Auto-generated method stub
		map_frame = (FrameLayout) findViewById(R.id.map_frame_layout);
		map_frame.setVisibility(View.INVISIBLE);
		enter_location_auto = (AutoCompleteTextView) findViewById(R.id.enter_location_autoCompleteTextView);
		//show_on_map_btn = (Button) findViewById(R.id.show_on_map_button);
		set_location_btn = (Button) findViewById(R.id.set_location_button);
		current_location_iv = (ImageView) findViewById(R.id.current_location_imageView);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		
		case R.id.enter_location_autoCompleteTextView:
			
			//enter_location_auto.setAdapter(new GooglePlacesAutocompleteAdapter(this, R.layout.autocomplete_list_item));
			
		break;
		
//		case R.id.show_on_map_button:
//			
//			map_frame.setVisibility(View.VISIBLE);	
//			hideSoftKeyboard();
//			
//		break;
		
		case R.id.set_location_button:
			
			
		break;
		
		case R.id.current_location_imageView:
			
			// create class object
            gps = new GPSTracker(SetLocationActivity.this);

            // check if GPS enabled     
            if(gps.canGetLocation()){
            	map_frame.setVisibility(View.VISIBLE);	 
                double latitude = gps.getLatitude();
                double longitude = gps.getLongitude();
                // \n is for new line
                //Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show(); 
                Current_Location= new LatLng(latitude, longitude);
                displayLocation(Current_Location);
                String current_location_address = getCompleteAddressString(latitude, longitude);
                enter_location_auto.setText(current_location_address);
                
            }else{
                // can't get location
                // GPS or Network is not enabled
                // Ask user to enable GPS/network in settings
                gps.showSettingsAlert();
            }

		break;
		
		}
	}

	

	private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
		// TODO Auto-generated method stub
		String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                Log.w("My Current loction address", "" + strReturnedAddress.toString());
            } else {
                Log.w("My Current loction address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("My Current loction address", "Canont get Address!");
        }
        return strAdd;

	}

	private void displayLocation(LatLng currentLocationLatlng) {
		// TODO Auto-generated method stub
		
		// Getting Google Play availability status
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

        if(status!=ConnectionResult.SUCCESS){ // Google Play Services are not available

            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
            dialog.show();

        }else { // Google Play Services are available
        	
        	googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map_fragment)).getMap();
		    
		    Marker kiel = googleMap.addMarker(new MarkerOptions().position(currentLocationLatlng));

		 // define point to center on	         
		    CameraUpdate panToOrigin = CameraUpdateFactory.newLatLng(currentLocationLatlng); 
		    googleMap.moveCamera(panToOrigin); 
		    // Zoom in, animating the camera.
		    googleMap.animateCamera(CameraUpdateFactory.zoomTo(11));
        }
	}
	
	// Fetches all places from GooglePlaces AutoComplete Web Service
	private class PlacesTask extends AsyncTask<String, Void, String>{

		@Override
		protected String doInBackground(String... place) {
			// For storing data from web service
			String data = "";
			
			/** Obtain browser key from https://code.google.com/apis/console
			 * no need to enter any specefic example.com
			 */
			String key = "key=AIzaSyBOv2iz7tFzof2MxjXXgrOZ6Y5hMpdnrpE";
			
			String input="";
			
			try {
				input = "input=" + URLEncoder.encode(place[0], "utf-8");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}		
			
			
			// place type to be searched
			String types = "types=geocode";
			
			// Sensor enabled
			String sensor = "sensor=false";			
			
			// Building the parameters to the web service
			String parameters = input+"&"+types+"&"+sensor+"&"+key;
			
			// Output format
			String output = "json";
			
			// Building the url to the web service
			String url = "https://maps.googleapis.com/maps/api/place/autocomplete/"+output+"?"+parameters;
	
			try{
				// Fetching the data from web service in background
				data = downloadUrl(url);
			}catch(Exception e){
                Log.d("Background Task",e.toString());
			}
			return data;		
		}
		
	
		@Override
		protected void onPostExecute(String result) {			
			super.onPostExecute(result);
			
			// Creating ParserTask
			parserTask = new ParserTask();
			
			// Starting Parsing the JSON string returned by Web Service
			parserTask.execute(result);
		}		
	}
	
	/** A class to parse the Google Places in JSON format */
    class ParserTask extends AsyncTask<String, Integer, List<HashMap<String,String>>>{

    	JSONObject jObject;
    	
		@Override
		protected List<HashMap<String, String>> doInBackground(String... jsonData) {			
			
			List<HashMap<String, String>> places = null;
			
            PlaceJSONParser placeJsonParser = new PlaceJSONParser();
            
            try{
            	jObject = new JSONObject(jsonData[0]);
            	
            	// Getting the parsed data as a List construct
            	places = placeJsonParser.parse(jObject);

            }catch(Exception e){
            	Log.d("Exception",e.toString());
            }
            return places;
		}
		
		@Override
		protected void onPostExecute(List<HashMap<String, String>> result) {			
			
				String[] from = new String[] { "description"};
				int[] to = new int[] { android.R.id.text1 };
				
				// Creating a SimpleAdapter for the AutoCompleteTextView			
				SimpleAdapter adapter = new SimpleAdapter(SetLocationActivity.this, result, android.R.layout.simple_list_item_1, from, to);				
				
				// Setting the adapter
				enter_location_auto.setAdapter(adapter);
		}
    }
    
    /** A method to download json data from url */
    private String downloadUrl(String strUrl) throws IOException{
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
                URL url = new URL(strUrl);                

                // Creating an http connection to communicate with url 
                urlConnection = (HttpURLConnection) url.openConnection();

                // Connecting to url 
                urlConnection.connect();

                // Reading data from url 
                iStream = urlConnection.getInputStream();

                BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

                StringBuffer sb  = new StringBuffer();

                String line = "";
                while( ( line = br.readLine())  != null){
                        sb.append(line);
                }
                
                data = sb.toString();

                br.close();

        }catch(Exception e){
                Log.d("Exception while downloading url", e.toString());
        }finally{
                iStream.close();
                urlConnection.disconnect();
        }
        return data;
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

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int position,
			long id) {
		// TODO Auto-generated method stub
//		String str = (String) adapterView.getItemAtPosition(position);
//		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}

}
