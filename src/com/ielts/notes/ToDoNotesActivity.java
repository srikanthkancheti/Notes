package com.ielts.notes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;


public class ToDoNotesActivity extends Activity {
	
	GridView notes_grid_view;
	RelativeLayout grid_home_new_note_rl;
	ImageView new_note_iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_notes);
        InitializeUI();
        
        grid_home_new_note_rl.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent scheduleTaskIntent = new Intent(ToDoNotesActivity.this, ScheduleTaskActivity.class);
				startActivity(scheduleTaskIntent);
			}
		});
    }


    private void InitializeUI() {
		// TODO Auto-generated method stub
    	notes_grid_view = (GridView) findViewById(R.id.all_notes_gridView);
    	grid_home_new_note_rl = (RelativeLayout) findViewById(R.id.grid_view_new_note);
    	new_note_iv = (ImageView) findViewById(R.id.add_new_note_imageview);
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
}
