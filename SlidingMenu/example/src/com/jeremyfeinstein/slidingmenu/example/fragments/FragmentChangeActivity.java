package com.jeremyfeinstein.slidingmenu.example.fragments;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.jeremyfeinstein.slidingmenu.example.BaseActivity;
import com.jeremyfeinstein.slidingmenu.example.LeftAndRightListFragment;
import com.jeremyfeinstein.slidingmenu.example.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class FragmentChangeActivity extends SlidingFragmentActivity {

	private Fragment mContent;

	public FragmentChangeActivity() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(R.string.changing_fragments);

		// set the Above View
		if (savedInstanceState != null)
			mContent = getSupportFragmentManager().getFragment(
					savedInstanceState, "mContent");
		if (mContent == null)
			mContent = new ColorFragment(R.color.red);

		// set the Above View
		setContentView(R.layout.content_frame);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, mContent).commit();

		// set the Behind View
		setBehindContentView(R.layout.menu_frame);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.menu_frame, new ColorMenuFragment()).commit();
		setSlidingActionBarEnabled(true);
		// customize the SlidingMenu
		SlidingMenu sm = getSlidingMenu();
		sm.setMode(SlidingMenu.LEFT);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

		// sm.setShadowWidthRes(R.dimen.shadow_width);
		// sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		sm.setBehindScrollScale(0.0f);
		sm.setFadeEnabled(true);
		sm.setFadeDegree(0.8f);
		
		
//		sm.setSecondaryMenu(R.layout.menu_frame_two);
//		sm.setSecondaryShadowDrawable(R.drawable.shadowright);
//		getSupportFragmentManager().beginTransaction()
//				.replace(R.id.menu_frame_two, new LeftAndRightListFragment())
//				.commit();

		sm.setBackgroundImage(R.drawable.img_frame_background);
		sm.setBehindCanvasTransformer(new SlidingMenu.CanvasTransformer() {
			@Override
			public void transformCanvas(Canvas canvas, float percentOpen) {
				float scale = (float) (percentOpen * 0.25 + 0.75);
				canvas.scale(scale, scale, -canvas.getWidth() / 2,
						canvas.getHeight() / 2);
			}
		});

		sm.setAboveCanvasTransformer(new SlidingMenu.CanvasTransformer() {
			@Override
			public void transformCanvas(Canvas canvas, float percentOpen) {
				float scale = (float) (1 - percentOpen * 0.25);
				canvas.scale(scale, scale, 0, canvas.getHeight() / 2);
			}
		});
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		getSupportFragmentManager().putFragment(outState, "mContent", mContent);
	}

	public void switchContent(Fragment fragment) {
		mContent = fragment;
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, fragment).commit();
		getSlidingMenu().showContent();
	}

}
