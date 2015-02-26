package com.jeremyfeinstein.slidingmenu.example;

import android.R.integer;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.jeremyfeinstein.slidingmenu.example.fragments.ColorFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnClosedListener;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnOpenedListener;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class LeftAndRightActivity extends SlidingFragmentActivity {
	private static final String TAG = LeftAndRightActivity.class
			.getSimpleName();

	private Fragment mContent;

	public LeftAndRightActivity() {
	
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(R.string.left_and_right);
		// set the Above View
		if (savedInstanceState != null)
			mContent = getSupportFragmentManager().getFragment(
					savedInstanceState, "mContent");
		if (mContent == null)
			mContent = new ColorFragment(R.color.red);
		 
		// set the Behind View
		setBehindContentView(R.layout.menu_frame);
		 this.getSupportFragmentManager()
				.beginTransaction()
	  
		.replace(R.id.menu_frame, new LeftAndRightListFragment())
		.commit();

		// customize the SlidingMenu
		SlidingMenu sm = getSlidingMenu();
		sm.setMode(SlidingMenu.LEFT_RIGHT);
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

		setContentView(R.layout.content_frame);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, mContent).commit();

		sm.setSecondaryMenu(R.layout.menu_frame_two);
		sm.setSecondaryShadowDrawable(R.drawable.shadowright);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.menu_frame_two, new LeftAndRightListFragment())
				.commit();

		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setShadowDrawable(R.drawable.shadow);
		// sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowWidth(15);
		setSlidingActionBarEnabled(true);
		sm.setBehindScrollScale(0.0f);
		sm.setFadeEnabled(true);
		sm.setFadeDegree(0.8f);

		// sm.setBackgroundImage(R.drawable.img_frame_background);
		sm.setBehindCanvasTransformer(new SlidingMenu.CanvasTransformer() {
			@Override
			public void transformCanvas(Canvas canvas, float percentOpen) {
				float scale = (float) (percentOpen * 0.2 + 0.8);

				canvas.scale(scale, scale, -canvas.getWidth() / 2,
						canvas.getHeight() / 2);
				//
				//
				//

			}
		});

		sm.setAboveCanvasTransformer(new SlidingMenu.CanvasTransformer() {
			@Override
			public void transformCanvas(Canvas canvas, float percentOpen) {
				float scale = (float) (1 - percentOpen * 0.2);

				canvas.scale(scale, scale, canvas.getWidth() / 2,
						canvas.getHeight() / 2);

			}
		});
	}

	public void switchContent(Fragment fragment) {
		mContent = fragment;
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, fragment).commit();
		getSlidingMenu().showContent();
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			toggle();
			return true;
		case R.id.github:
			Util.goToGitHub(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
