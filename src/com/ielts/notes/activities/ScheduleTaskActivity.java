package com.ielts.notes.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.ielts.notes.R;

public class ScheduleTaskActivity extends Activity implements OnClickListener{
	
	RelativeLayout setTime_rl, repeat_rl, setLocation_rl, remainder_rl;
	private String selected_time;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_task);
        InitializeUI();
        setListeners();
        

	}
	
	private void InitializeUI() {
		// TODO Auto-generated method stub
		setTime_rl = (RelativeLayout) findViewById(R.id.setTime_relativeLayout);
		repeat_rl = (RelativeLayout) findViewById(R.id.repeat_relativeLayout);
		setLocation_rl = (RelativeLayout) findViewById(R.id.setLocation_relativeLayout);
		remainder_rl = (RelativeLayout) findViewById(R.id.remainder_relativeLayout);
	}
	
	private void setListeners() {
		// TODO Auto-generated method stub
		setTime_rl.setOnClickListener(this);
		repeat_rl.setOnClickListener(this);
		setLocation_rl.setOnClickListener(this);
		remainder_rl.setOnClickListener(this);
	}

	protected void DateTimePickerDialog() {
		// TODO Auto-generated method stub
		LayoutInflater inflator = LayoutInflater.from(ScheduleTaskActivity.this);
		View commentView = inflator.inflate(R.layout.date_time_layout, null);
		
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ScheduleTaskActivity.this);

		alertDialogBuilder.setTitle("Set time");
		alertDialogBuilder.setCancelable(false);
		//alert.setMessage("Message");
		
		// set prompts.xml to alertdialog builder
		alertDialogBuilder.setView(commentView);

		// Set an EditText view to get user input 
		final DatePicker date_picker = (DatePicker) commentView.findViewById(R.id.datePicker1);
		final TimePicker time_picker = (TimePicker) commentView.findViewById(R.id.timePicker1);
	

		alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		
			public void onClick(DialogInterface dialog, int whichButton) {
				
			  int day_of_month = date_picker.getDayOfMonth();
			  int month_of_year = date_picker.getMonth();
			  int year = date_picker.getYear();
			  int hour_day = time_picker.getCurrentHour();
			  int min_hour = time_picker.getCurrentMinute();
			  
			  String updatedTime = updateTime(hour_day, min_hour);
			  
			  selected_time = day_of_month+"/"+month_of_year+"/"+year+" "+updatedTime;
			  Toast.makeText(getApplicationContext(), selected_time, Toast.LENGTH_LONG).show();
			  }
			});

			alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			  public void onClick(DialogInterface dialog, int whichButton) {
			    // Canceled.
				  dialog.cancel();
			  }
			});
			
			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();

			alertDialog.show();
	}
	
	// Used to convert 24hr format to 12hr format with AM/PM values
	protected String updateTime(int hours, int mins) {
		// TODO Auto-generated method stub
		
		 String timeSet = "";
	        if (hours > 12) {
	            hours -= 12;
	            timeSet = "PM";
	        } else if (hours == 0) {
	            hours += 12;
	            timeSet = "AM";
	        } else if (hours == 12)
	            timeSet = "PM";
	        else
	            timeSet = "AM";
	 
	         
	        String minutes = "";
	        if (mins < 10)
	            minutes = "0" + mins;
	        else
	            minutes = String.valueOf(mins);
	 
	        // Append in a StringBuilder
	         String aTime = new StringBuilder().append(hours).append(':')
	                .append(minutes).append(" ").append(timeSet).toString();
	 
	    
		return aTime;
	}

	

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.to_do_notes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		
			case R.id.setTime_relativeLayout:
				
				DateTimePickerDialog();
				
			break;
			
			case R.id.repeat_relativeLayout:
				
				Intent repeatRemainderIntent = new Intent(ScheduleTaskActivity.this, RepeatRemainderFragmentActivity.class);
				startActivity(repeatRemainderIntent);
				
			break;
			
			case R.id.setLocation_relativeLayout:
				
				Intent setLocationIntent = new Intent(ScheduleTaskActivity.this, SetLocationActivity.class);
				startActivity(setLocationIntent);
				
				
			break;
			
			case R.id.remainder_relativeLayout:
				
				
			break;
			
		}
	}

}
