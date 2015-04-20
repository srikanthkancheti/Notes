package com.ielts.notes.tabswipe;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ielts.notes.R;

public class DayFragment extends Fragment implements OnClickListener{

	private RelativeLayout startsOn_rl, repeatEvery_rl, ends_rl;
	private TextView startsOn_tv, repearEvery_tv, ends_tv;
	private View rootView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.fragment_day, container, false);
		
		InitializeUI();
		setListeners();
		
		return rootView;
	}

	private void setListeners() {
		// TODO Auto-generated method stub
		startsOn_rl.setOnClickListener(this);
		repeatEvery_rl.setOnClickListener(this);
		ends_rl.setOnClickListener(this);
	}

	private void InitializeUI() {
		// TODO Auto-generated method stub
		startsOn_rl = (RelativeLayout) rootView.findViewById(R.id.day_startOn_relativeLayout);
		repeatEvery_rl = (RelativeLayout) rootView.findViewById(R.id.day_repeat_relativeLayout);
		ends_rl = (RelativeLayout) rootView.findViewById(R.id.day_ends_relativeLayout);
		startsOn_tv = (TextView) rootView.findViewById(R.id.day_startsOn_textView);
		repearEvery_tv = (TextView) rootView.findViewById(R.id.day_repeatEvery_textView);
		ends_tv = (TextView) rootView.findViewById(R.id.day_ends_textView);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
			
			case R.id.day_startOn_relativeLayout:
				
				
			break;
			
			case R.id.day_repeat_relativeLayout:
	
				
			break;
			
			case R.id.day_ends_relativeLayout:
				
				
			break;
		
				
		}
	}
}
