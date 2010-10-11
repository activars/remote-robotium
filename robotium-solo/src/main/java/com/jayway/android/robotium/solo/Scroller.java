package com.jayway.android.robotium.solo;

import android.app.Instrumentation;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.widget.ListView;
import android.widget.ScrollView;

/**
* This class contains scroll methods. Examples are scrollDown(), scrollUpList(),
* scrollToSide().
* 
* @author Renas Reda, renas.reda@jayway.com
* 
*/

class Scroller {
	
    public enum Direction {UP, DOWN}
    public enum Side {LEFT, RIGHT}
	private final Instrumentation inst;
	private final ActivityUtils activityUtils;
	private final ViewFetcher viewFetcher;
   	private int scrollAmount = 0;

    /**
     * Constructs this object.
     *
     * @param inst the {@code Instrumentation} instance.
     * @param activityUtils the {@code ActivityUtils} instance.
     * @param viewFetcher the {@code ViewFetcher} instance.
     */
	
    public Scroller(Instrumentation inst, ActivityUtils activityUtils, ViewFetcher viewFetcher) {
        this.inst = inst;
        this.activityUtils = activityUtils;
        this.viewFetcher = viewFetcher;
    }

	
	 /**
	 * Simulate touching a specific location and dragging to a new location.
	 *
	 * This method was copied from {@code TouchUtils.java} in the Android Open Source Project, and modified here.
	 *
	 * @param fromX X coordinate of the initial touch, in screen coordinates
	 * @param toX Xcoordinate of the drag destination, in screen coordinates
	 * @param fromY X coordinate of the initial touch, in screen coordinates
	 * @param toY Y coordinate of the drag destination, in screen coordinates
	 * @param stepCount How many move steps to include in the drag
	 *
	 */
	
	public void drag(float fromX, float toX, float fromY, float toY,
					  int stepCount) {
		long downTime = SystemClock.uptimeMillis();
		long eventTime = SystemClock.uptimeMillis();
		float y = fromY;
		float x = fromX;
		float yStep = (toY - fromY) / stepCount;
		float xStep = (toX - fromX) / stepCount;
		MotionEvent event = MotionEvent.obtain(downTime, eventTime,MotionEvent.ACTION_DOWN, fromX, fromY, 0);
		try {
			inst.sendPointerSync(event);
		} catch (SecurityException ignored) {}
		for (int i = 0; i < stepCount; ++i) {
			y += yStep;
			x += xStep;
			eventTime = SystemClock.uptimeMillis();
			event = MotionEvent.obtain(downTime, eventTime,MotionEvent.ACTION_MOVE, x, y, 0);
			try {
				inst.sendPointerSync(event);
			} catch (SecurityException ignored) {}
			inst.waitForIdleSync();
		}
		eventTime = SystemClock.uptimeMillis();
		event = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_UP,toX, toY, 0);
		try {
			inst.sendPointerSync(event);
		} catch (SecurityException ignored) {}
	}


	/**
	 * Scrolls up and down.
	 * 
	 * @param direction the direction in which to scroll
	 * @return {@code true} if more scrolling can be done
	 * 
	 */
	
	public boolean scroll(Direction direction) {
		int yStart;
		int yEnd;
		if (direction == Direction.DOWN) {
			yStart = (activityUtils.getCurrentActivity().getWindowManager()
					.getDefaultDisplay().getHeight() - 20);
			yEnd = ((activityUtils.getCurrentActivity(false).getWindowManager()
					.getDefaultDisplay().getHeight() / 2));
		} 
		else {
			yStart = ((activityUtils.getCurrentActivity().getWindowManager()
					.getDefaultDisplay().getHeight() / 2));
			yEnd = (activityUtils.getCurrentActivity(false).getWindowManager()
					.getDefaultDisplay().getHeight() - 20);
		}
		int x = activityUtils.getCurrentActivity(false).getWindowManager()
				.getDefaultDisplay().getWidth() / 2;

		if (viewFetcher.getCurrentViews(ListView.class).size() > 0) {
			return scrollList(0, direction);
		} 
		else {
			if (viewFetcher.getCurrentViews(ScrollView.class).size() > 0) {
				ScrollView scroll = viewFetcher.getCurrentViews(ScrollView.class).get(0);
				scrollAmount = scroll.getScrollY();
				drag(x, x, yStart, yEnd, 40);
				if (scrollAmount == scroll.getScrollY()) {
					scrollAmount = 0;
					return false;
				}
				else
					return true;
			}
			else
				return false;
		}

	}

	/**
	 * Scrolls a list.
	 * 
	 * @param listIndex the list to be scrolled
	 * @param direction the direction to be scrolled
	 * @return {@code true} if more scrolling can be done
	 * 
	 */
	
	public boolean scrollList(int listIndex, Direction direction) {
		ListView listView = viewFetcher.getCurrentViews(ListView.class).get(listIndex);
		int[] xy = new int[2];
		listView.getLocationOnScreen(xy);

		while (xy[1] + 20 > activityUtils.getCurrentActivity()
				.getWindowManager().getDefaultDisplay().getHeight()) {
			int yStart = (activityUtils.getCurrentActivity(false).getWindowManager()
					.getDefaultDisplay().getHeight() - 20);
			int yEnd = ((activityUtils.getCurrentActivity(false).getWindowManager()
					.getDefaultDisplay().getHeight() / 2));
			int x = activityUtils.getCurrentActivity(false).getWindowManager()
					.getDefaultDisplay().getWidth() / 2;
			drag(x, x, yStart, yEnd, 40);
			listView.getLocationOnScreen(xy);
		}
		int yStart;
		int yEnd;
		if (direction == Direction.DOWN) {
			yStart = ((xy[1] + listView.getHeight()) - 20);
			yEnd = (xy[1] + 20);
		} else {
			
			yStart = ((xy[1]) + 20);
			yEnd = (xy[1] + listView.getHeight());
		}
		int x = activityUtils.getCurrentActivity(false).getWindowManager()
				.getDefaultDisplay().getWidth() / 2;
		
		if (listView.getLastVisiblePosition() < listView.getCount()-1) {
			drag(x, x, yStart, yEnd, 40);
			return true;

		} else {
			return false;
		}

	}
	
	
	/**
	 * Scrolls horizontally.
	 *
	 * @param side the side to which to scroll; {@link Side#RIGHT} or {@link Side#LEFT}
	 *
	 */
	
	public void scrollToSide(Side side) {
		int screenHeight = activityUtils.getCurrentActivity().getWindowManager().getDefaultDisplay()
		.getHeight();
		int screenWidth = activityUtils.getCurrentActivity(false).getWindowManager().getDefaultDisplay()
		.getWidth();
		float x = screenWidth / 2.0f;
		float y = screenHeight / 2.0f;
		if (side == Side.LEFT)
			drag(0, x, y, y, 40);
		else if (side == Side.RIGHT)
			drag(x, 0, y, y, 40);
	}
	

}
