package com.jeremyfeinstein.slidingmenu.example;

import android.R.integer;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;

import com.jeremyfeinstein.slidingmenu.example.fragments.ColorFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnClosedListener;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnOpenedListener;

public class LeftAndRightActivity extends BaseActivity {
	private static final String TAG = LeftAndRightActivity.class
			.getSimpleName();
	int screenWidth;
	int screenHeight;

	public LeftAndRightActivity() {
		super(R.string.left_and_right);
	}

	private void name() {
		// 获取屏幕宽高（方法1）
		// screenWidth = getWindowManager().getDefaultDisplay().getWidth(); //
		// 屏幕宽（像素，如：480px）
		// screenHeight = getWindowManager().getDefaultDisplay().getHeight(); //
		// 屏幕高（像素，如：800p）
		// Log.e(TAG + " getDefaultDisplay", "screenWidth=" + screenWidth
		// + "; screenHeight=" + screenHeight);
		// 获取屏幕密度（方法2）
		DisplayMetrics dm = new DisplayMetrics();
		dm = getResources().getDisplayMetrics();
		float density = dm.density; // 屏幕密度（像素比例：0.75/1.0/1.5/2.0）
		int densityDPI = dm.densityDpi; // 屏幕密度（每寸像素：120/160/240/320）
		float xdpi = dm.xdpi;
		float ydpi = dm.ydpi;
		Log.e(TAG + " DisplayMetrics", "xdpi=" + xdpi + "; ydpi=" + ydpi);
		Log.e(TAG + " DisplayMetrics", "density=" + density + "; densityDPI="
				+ densityDPI);
		screenWidth = dm.widthPixels; // 屏幕宽（像素，如：480px）
		screenHeight = dm.heightPixels; // 屏幕高（像素，如：800px）
		Log.e(TAG + " DisplayMetrics(111)", "screenWidth=" + screenWidth
				+ "; screenHeight=" + screenHeight);
		// 获取屏幕密度（方法3）
		dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		density = dm.density; // 屏幕密度（像素比例：0.75/1.0/1.5/2.0）
		densityDPI = dm.densityDpi; // 屏幕密度（每寸像素：120/160/240/320）
		xdpi = dm.xdpi;
		ydpi = dm.ydpi;
		Log.e(TAG + " DisplayMetrics", "xdpi=" + xdpi + "; ydpi=" + ydpi);
		Log.e(TAG + " DisplayMetrics", "density=" + density + "; densityDPI="
				+ densityDPI);
		int screenWidthDip = dm.widthPixels; // 屏幕宽（dip，如：320dip）
		int screenHeightDip = dm.heightPixels; // 屏幕宽（dip，如：533dip）
		Log.e(TAG + " DisplayMetrics(222)", "screenWidthDip=" + screenWidthDip
				+ "; screenHeightDip=" + screenHeightDip);
		screenWidth = (int) (dm.widthPixels * density + 0.5f); // 屏幕宽（px，如：480px）
		screenHeight = (int) (dm.heightPixels * density + 0.5f); // 屏幕高（px，如：800px）
		Log.e(TAG + " DisplayMetrics(222)", "screenWidth=" + screenWidth
				+ "; screenHeight=" + screenHeight);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSlidingMenu().setMode(SlidingMenu.LEFT_RIGHT);
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

		setContentView(R.layout.content_frame);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, new SampleListFragment()).commit();

		getSlidingMenu().setSecondaryMenu(R.layout.menu_frame_two);
		getSlidingMenu().setSecondaryShadowDrawable(R.drawable.shadowright);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.menu_frame_two, new SampleListFragment())
				.commit();
		SlidingMenu sm = getSlidingMenu();
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setShadowDrawable(R.drawable.shadow);
//		sm.setShadowWidthRes(R.dimen.shadow_width);
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

}
