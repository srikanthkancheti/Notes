package com.ielts.notes.adapter;

import com.ielts.notes.tabswipe.WeekFragment;
import com.ielts.notes.tabswipe.MonthFragment;
import com.ielts.notes.tabswipe.DayFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			// Day fragment activity
			return new DayFragment();
		case 1:
			// Week fragment activity
			return new WeekFragment();
		case 2:
			// Month fragment activity
			return new MonthFragment();
		}

		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 3;
	}

}
