package com.jayway.android.robotium.solo;

import java.util.ArrayList;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

public interface ISolo {

	public final static int LANDSCAPE = 0;
	public final static int PORTRAIT = 1;
	public final static int RIGHT = 2;
	public final static int LEFT = 3;
	public final static int UP = 4;
	public final static int DOWN = 5;
	public final static int ENTER = 6;
	public final static int MENU = 7;
	public final static int DELETE = 8;

	/**
	 * Returns an ArrayList of all the views located in the current activity.
	 *
	 * @return ArrayList with the views found in the current activity
	 *
	 */

	public abstract ArrayList<View> getViews();

	/**
	 * Returns the absolute top view in an activity.
	 *
	 * @param view the view whose top parent is requested
	 * @return the top parent view
	 *
	 */

	public abstract View getTopParent(View view);

	/**
	 * Clears the value of an edit text.
	 * 
	 * @param index the index of the edit text that should be cleared
	 */

	public abstract void clearEditText(int index);

	/**
	 * Waits for a text to be shown. Default timeout is 20 seconds. 
	 * 
	 * @param text the text that needs to be shown
	 * @return true if text is found and false if it is not found before the timeout
	 * 
	 */

	public abstract boolean waitForText(String text);

	/**
	 * Waits for a text to be shown. 
	 * 
	 * @param text the text that needs to be shown
	 * @param matches the number of matches of text that must be shown. 0 means any number of matches
	 * @param timeout the the amount of time in milliseconds to wait 
	 * @return true if text is found and false if it is not found before the timeout
	 * 
	 */

	public abstract boolean waitForText(String text, int matches, long timeout);

	/**
	 * Waits for a text to be shown. 
	 * 
	 * @param text the text that needs to be shown
	 * @param matches the number of matches of text that must be shown. 0 means any number of matches
	 * @param timeout the the amount of time in milliseconds to wait
	 * @param scroll true if scrolling should be performed
	 * @return true if text is found and false if it is not found before the timeout
	 * 
	 */

	public abstract boolean waitForText(String text, int matches, long timeout,
			boolean scroll);

	/**
	 * Searches for a text string in the edit texts located in the current
	 * activity. Will automatically scroll when needed. 
	 *
	 * @param search the search string to be searched
	 * @return true if an edit text with the given text is found or false if it is not found
	 *
	 */

	public abstract boolean searchEditText(String search);

	/**
	 * Searches for a button with the given search string and returns true if at least one button 
	 * is found with the expected text. Will automatically scroll when needed. 
	 *
	 * @param search the string to be searched. Regular expressions are supported
	 * @return true if a button with the given text is found and false if it is not found
	 *
	 */

	public abstract boolean searchButton(String search);

	/**
	 * Searches for a toggle button with the given search string and returns true if at least one toggle button 
	 * is found with the expected text. Will automatically scroll when needed. 
	 *
	 * @param search the string to be searched. Regular expressions are supported
	 * @return true if a toggle button with the given text is found and false if it is not found
	 *
	 */

	public abstract boolean searchToggleButton(String search);

	/**
	 * Searches for a button with the given search string and returns true if the 
	 * searched button is found a given number of times. Will automatically scroll when needed. 
	 * 
	 * @param search the string to be searched. Regular expressions are supported
	 * @param matches the number of matches expected to be found. 0 matches means that one or more 
	 * matches are expected to be found
	 * @return true if a button with the given text is found a given number of times and false 
	 * if it is not found
	 *  
	 */

	public abstract boolean searchButton(String search, int matches);

	/**
	 * Searches for a toggle button with the given search string and returns true if the 
	 * searched toggle button is found a given number of times. Will automatically scroll when needed. 
	 * 
	 * @param search the string to be searched. Regular expressions are supported
	 * @param matches the number of matches expected to be found. 0 matches means that one or more 
	 * matches are expected to be found
	 * @return true if a toggle button with the given text is found a given number of times and false 
	 * if it is not found
	 *  
	 */

	public abstract boolean searchToggleButton(String search, int matches);

	/**
	 * Searches for a text string and returns true if at least one item 
	 * is found with the expected text. Will automatically scroll when needed. 
	 *
	 * @param search the string to be searched. Regular expressions are supported
	 * @return true if the search string is found and false if it is not found
	 *
	 */

	public abstract boolean searchText(String search);

	/**
	 * Searches for a text string and returns true if the searched text is found a given
	 * number of times. Will automatically scroll when needed. 
	 * 
	 * @param search the string to be searched. Regular expressions are supported
	 * @param matches the number of matches expected to be found. 0 matches means that one or more 
	 * matches are expected to be found
	 * @return true if search string is found a given number of times and false if the search string
	 * is not found
	 *  
	 */

	public abstract boolean searchText(String search, int matches);

	/**
	 * Searches for a text string and returns true if the searched text is found a given
	 * number of times.
	 * 
	 * @param search the string to be searched. Regular expressions are supported
	 * @param matches the number of matches expected to be found. 0 matches means that one or more 
	 * matches are expected to be found
	 * @param scroll true if scrolling should be performed
	 * @return true if search string is found a given number of times and false if the search string
	 * is not found
	 *  
	 */

	public abstract boolean searchText(String search, int matches,
			boolean scroll);

	/**
	 * Sets the Orientation (Landscape/Portrait) for the current activity.
	 * 
	 * @param orientation the orientation to be set. 0 for landscape and 1 for portrait 
	 */

	public abstract void setActivityOrientation(int orientation);

	/**
	 * Returns an ArrayList of all the opened/active activities.
	 * 
	 * @return ArrayList of all the opened activities
	 */

	public abstract ArrayList<Activity> getAllOpenedActivities();

	/**
	 * Returns the current activity.
	 *
	 * @return current activity
	 *
	 */

	public abstract Activity getCurrentActivity();

	/**
	 * Asserts that an expected activity is currently active.
	 * 
	 * @param message the message that should be displayed if the assert fails
	 * @param name the name of the activity that is expected to be active e.g. "MyActivity"
	 * 
	 */

	public abstract void assertCurrentActivity(String message, String name);

	/**
	 * Asserts that an expected activity is currently active.
	 * 
	 * @param message the message that should be displayed if the assert fails
	 * @param expectedClass the class object that is expected to be active e.g. MyActivity.class
	 * 
	 */

	@SuppressWarnings("unchecked")
	public abstract void assertCurrentActivity(String message,
			Class expectedClass);

	/**
	 * Asserts that an expected activity is currently active with the possibility to 
	 * verify that the expected activity is a new instance of the activity.
	 * 
	 * @param message the message that should be displayed if the assert fails
	 * @param name the name of the activity that is expected to be active e.g. "MyActivity"
	 * @param isNewInstance true if the expected activity is a new instance of the activity 
	 * 
	 */

	public abstract void assertCurrentActivity(String message, String name,
			boolean isNewInstance);

	/**
	 * Asserts that an expected activity is currently active with the possibility to 
	 * verify that the expected activity is a new instance of the activity.
	 * 
	 * @param message the message that should be displayed if the assert fails
	 * @param expectedClass the class object that is expected to be active e.g. MyActivity.class
	 * @param isNewInstance true if the expected activity is a new instance of the activity
	 * 
	 */

	@SuppressWarnings("unchecked")
	public abstract void assertCurrentActivity(String message,
			Class expectedClass, boolean isNewInstance);

	/**
	 * Asserts that the available memory in the system is not low.
	 * 
	 */

	public abstract void assertLowMemory();

	/**
	 * Waits for a Dialog to close.
	 * 
	 * @param timeout the amount of time in milliseconds to wait
	 * @return true if the dialog is closed before the timeout and false if it is not closed.
	 * 
	 */

	public abstract boolean waitForDialogToClose(long timeout);

	/**
	 * Simulates pressing the hard key back.
	 * 
	 */

	public abstract void goBack();

	/**
	 * Clicks on a specific coordinate on the screen.
	 *
	 * @param x the x coordinate
	 * @param y the y coordinate
	 *
	 */

	public abstract void clickOnScreen(float x, float y);

	/**
	 * Long clicks a specific coordinate on the screen
	 *
	 * @param x the x coordinate
	 * @param y the y coordinate
	 *
	 */

	public abstract void clickLongOnScreen(float x, float y);

	/**
	 * Clicks on a button with a given text. Will automatically scroll when needed. 
	 *
	 * @param name the name of the button presented to the user. Regular expressions are supported
	 *
	 */

	public abstract void clickOnButton(String name);

	/**
	 * Clicks on an image button with a certain index.
	 *
	 * @param index the index of the image button to be clicked
	 *
	 */

	public abstract void clickOnImageButton(int index);

	/**
	 * Clicks on a toggle button with a given text.
	 * 
	 * @param name the name of the toggle button presented to the user. Regular expressions are supported
	 * 
	 */

	public abstract void clickOnToggleButton(String name);

	/**
	 * Clicks on a menu item with a given text.
	 * @param text the menu text that should be clicked on. Regular expressions are supported 
	 */

	public abstract void clickOnMenuItem(String text);

	/**
	 * Presses a MenuItem with a certain index. Index 0 is the first item in the 
	 * first row, index 3 is the first item in the second row and 
	 * index 5 is the first item in the third row.
	 * 
	 * @param index the index of the menu item to be pressed
	 * 
	 */

	public abstract void pressMenuItem(int index);

	/**
	 * Presses on a spinner (drop-down menu) item.
	 * 
	 * @param spinnerIndex the index of the spinner menu to be used
	 * @param itemIndex the index of the spinner item to be pressed relative to the current selected item. 
	 * A Negative number moves up on the spinner, positive down
	 * 
	 */

	public abstract void pressSpinnerItem(int spinnerIndex, int itemIndex);

	/**
	 * Clicks on a specific view.
	 *
	 * @param view the view that should be clicked
	 *
	 */

	public abstract void clickOnView(View view);

	/**
	 * Long clicks on a specific view.
	 *
	 * @param view the view that should be long clicked
	 *
	 */

	public abstract void clickLongOnView(View view);

	/**
	 * Clicks on a specific view displaying a certain
	 * text. Will automatically scroll when needed. 
	 *
	 * @param text the text that should be clicked on. Regular expressions are supported
	 *
	 */

	public abstract void clickOnText(String text);

	/**
	 * This method is used to click on a specific text view displaying a certain
	 * text.
	 *
	 * @param text the text that should be clicked on. Regular expressions are supported
	 * @param match the match that should be clicked on 
	 *
	 */

	public abstract void clickOnText(String text, int match);

	/**
	 * Long clicks on a specific text view. ClickOnText() can then be
	 * used to click on the context menu items that appear after the long click.
	 *
	 * @param text the text that should be clicked on. Regular expressions are supported
	 *
	 */

	public abstract void clickLongOnText(String text);

	/**
	 * Long clicks on a specific text view. ClickOnText() can then be
	 * used to click on the context menu items that appear after the long click.
	 *
	 * @param text the text that should be clicked on. Regular expressions are supported
	 * @param match the match that should be clicked on 
	 *
	 */

	public abstract void clickLongOnText(String text, int match);

	/**
	 * Long clicks on a specific text view and then selects
	 * an item from the context menu that appears. Will automatically scroll when needed. 
	 *
	 * @param text the text to be clicked on. Regular expressions are supported
	 * @param index the index of the menu item to be pressed
	 *
	 */

	public abstract void clickLongOnTextAndPress(String text, int index);

	/**
	 * Clicks on a button with a certain index.
	 *
	 * @param index the index number of the button
	 * @return true if button with specified index is found
	 *
	 */

	public abstract boolean clickOnButton(int index);

	/**
	 * Clicks on a radio button with a certain index.
	 *
	 * @param index the index of the radio button to be clicked
	 *
	 */

	public abstract void clickOnRadioButton(int index);

	/**
	 * Clicks on a check box with a certain index.
	 *
	 * @param index the index of the check box to be clicked
	 *
	 */

	public abstract void clickOnCheckBox(int index);

	/**
	 * Clicks on an edit text with a certain index.
	 *
	 * @param index the index of the edit text to be clicked
	 *
	 */

	public abstract void clickOnEditText(int index);

	/**
	 * Clicks on a certain list line and return the text views that
	 * the list line is showing. Will use the first list it finds.
	 * 
	 * @param line the line that should be clicked
	 * @return an array list of the text views located in the list line
	 */

	public abstract ArrayList<TextView> clickInList(int line);

	/**
	 * Clicks on a certain list line on a specified list and 
	 * return the text views that the list line is showing. 
	 * 
	 * @param line the line that should be clicked
	 * @param listIndex the index of the list. E.g. Index 1 if two lists are available
	 * @return an array list of the text views located in the list line
	 */

	public abstract ArrayList<TextView> clickInList(int line, int listIndex);

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

	public abstract void drag(float fromX, float toX, float fromY, float toY,
			int stepCount);

	/**
	 * Scrolls down the screen.
	 *
	 * @return true if more scrolling can be done and false if it is at the end of 
	 * the screen 
	 *
	 */

	public abstract boolean scrollDown();

	/**
	 * Scrolls up the screen.
	 *
	 * @return true if more scrolling can be done and false if it is at the top of 
	 * the screen 
	 *
	 */

	public abstract boolean scrollUp();

	/**
	 * Scrolls down a list with a given listIndex.
	 * 
	 * @param listIndex the list to be scrolled. 0 if only one list is available 
	 * @return true if more scrolling can be done
	 * 
	 */

	public abstract boolean scrollDownList(int listIndex);

	/**
	 * Scrolls up a list with a given listIndex.
	 * 
	 * @param listIndex the ListView to be scrolled. 0 if only one list is available
	 * @return true if more scrolling can be done
	 * 
	 */

	public abstract boolean scrollUpList(int listIndex);

	/**
	 * Scrolls horizontally.
	 *
	 * @param side the side in which to scroll
	 *
	 */

	public abstract void scrollToSide(int side);

	/**
	 * Enters text into an EditText or a NoteField with a certain index.
	 *
	 * @param index the index of the text field. Index 0 if only one available.
	 * @param text the text string that is to be entered into the text field
	 *
	 */

	public abstract void enterText(int index, String text);

	/**
	 * Clicks on an image with a certain index.
	 *
	 * @param index the index of the image to be clicked
	 *
	 */

	public abstract void clickOnImage(int index);

	/**
	 * Returns an ArrayList of the images contained in the current
	 * activity.
	 *
	 * @return ArrayList of the images contained in the current activity
	 *
	 */

	public abstract ArrayList<ImageView> getCurrentImageViews();

	/**
	 * Returns an EditText with a certain index.
	 *
	 * @return the EditText with a specified index
	 *
	 */

	public abstract EditText getEditText(int index);

	/**
	 * Returns a button with a certain index.
	 *
	 * @param index the index of the button
	 * @return the button with the specific index
	 *
	 */

	public abstract Button getButton(int index);

	/**
	 * Returns the number of buttons located in the current
	 * activity.
	 *
	 * @return the number of buttons in the current activity
	 * @deprecated legacy method that is not needed anymore
	 *
	 */

	public abstract int getCurrenButtonsCount();

	/**
	 * Returns an ArrayList of all the edit texts located in the
	 * current activity.
	 *
	 * @return an ArrayList of the edit texts located in the current activity
	 *
	 */

	public abstract ArrayList<EditText> getCurrentEditTexts();

	/**
	 * Returns an ArrayList of all the list views located in the current activity.
	 * 
	 * 
	 * @return an ArrayList of the list views located in the current activity
	 * 
	 */

	public abstract ArrayList<ListView> getCurrentListViews();

	/**
	 * Returns an ArrayList of all the scroll views located in the current activity.
	 *
	 *
	 * @return an ArrayList of the scroll views located in the current activity
	 *
	 */

	public abstract ArrayList<ScrollView> getCurrentScrollViews();

	/**
	 * Returns an ArrayList of spinners (drop-down menus) located in the current
	 * activity.
	 *
	 * @return an ArrayList of the spinners located in the current activity or view
	 *
	 */

	public abstract ArrayList<Spinner> getCurrentSpinners();

	/**
	 * Returns an ArrayList of the text views located in the current
	 * activity.
	 *
	 * @param parent the parent View in which the text views should be returned. Null if
	 * all text views from the current activity should be returned
	 * 
	 * @return an ArrayList of the text views located in the current activity or view
	 *
	 */

	public abstract ArrayList<TextView> getCurrentTextViews(View parent);

	/**
	 * Returns an ArrayList of the grid views located in the current
	 * activity.
	 *
	 * @return an ArrayList of the grid views located in the current activity
	 *
	 */

	public abstract ArrayList<GridView> getCurrentGridViews();

	/**
	 * Returns an ArrayList with the buttons located in the current
	 * activity.
	 *
	 * @return and ArrayList of the buttons located in the current activity
	 * 
	 */

	public abstract ArrayList<Button> getCurrentButtons();

	/**
	 * Returns an ArrayList with the toggle buttons located in the current
	 * activity.
	 *
	 * @return and ArrayList of the toggle buttons located in the current activity
	 *
	 */

	public abstract ArrayList<ToggleButton> getCurrentToggleButtons();

	/**
	 * Returns an ArrayList of the radio buttons contained in the current
	 * activity.
	 *
	 * @return ArrayList of the radio buttons contained in the current activity
	 *
	 */

	public abstract ArrayList<RadioButton> getCurrentRadioButtons();

	/**
	 * Returns an ArrayList of the check boxes contained in the current
	 * activity.
	 *
	 * @return ArrayList of the check boxes contained in the current activity
	 *
	 */

	public abstract ArrayList<CheckBox> getCurrentCheckBoxes();

	/**
	 * Returns an ArrayList of the image buttons contained in the current
	 * activity.
	 *
	 * @return ArrayList of the image buttons contained in the current activity
	 *
	 */

	public abstract ArrayList<ImageButton> getCurrentImageButtons();

	/**
	 * Checks if a radio button with a given index is checked.
	 * 
	 * @param index of the radio button to check
	 * @return true if radio button is checked and false if it is not checked
	 */

	public abstract boolean isRadioButtonChecked(int index);

	/**
	 * Checks if a check box with a given index is checked.
	 * 
	 * @param index of the check box to check
	 * @return true if check box is checked and false if it is not checked
	 */

	public abstract boolean isCheckBoxChecked(int index);

	/**
	 * Tells Robotium to send a key: Right, Left, Up, Down, Enter, Menu or Delete.
	 * 
	 * @param key the key to be sent. Use Solo.RIGHT/LEFT/UP/DOWN/ENTER/MENU/DELETE
	 * 
	 */

	public abstract void sendKey(int key);

	/**
	 * Returns to the given Activity.
	 * @param name the name of the Activity to be returned to e.g. "MyActivity"
	 */

	public abstract void goBackToActivity(String name);

	/**
	 * Waits for the given Activity.
	 * @param name the name of the Activity to wait for e.g. "MyActivity"
	 * @param timeout the amount of time in milliseconds to wait
	 * @return true if Activity appears before the timeout and false if it does not
	 */

	public abstract boolean waitForActivity(String name, int timeout);

	/**
	 * Robotium will sleep for a specified time.
	 * 
	 * @param time the time in milliseconds that Robotium should sleep 
	 * 
	 */

	public abstract void sleep(int time);

	/**
	 *
	 * All activites that have been active are finished.
	 *
	 */
	public abstract void finalize() throws Throwable;

}