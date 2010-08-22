package com.jayway.android.robotium.remotesolo;

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

import com.jayway.android.robotium.solo.ISolo;

public class RemoteSolo implements ISolo {

	public void assertCurrentActivity(String message, String name) {
		// TODO Auto-generated method stub
	}

	public void assertCurrentActivity(String message, Class expectedClass) {
		// TODO Auto-generated method stub
		
	}

	public void assertCurrentActivity(String message, String name,
			boolean isNewInstance) {
		// TODO Auto-generated method stub
		
	}

	public void assertCurrentActivity(String message, Class expectedClass,
			boolean isNewInstance) {
		// TODO Auto-generated method stub
		
	}

	public void assertLowMemory() {
		// TODO Auto-generated method stub
		
	}

	public void clearEditText(int index) {
		// TODO Auto-generated method stub
		
	}

	public ArrayList<TextView> clickInList(int line) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<TextView> clickInList(int line, int listIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	public void clickLongOnScreen(float x, float y) {
		// TODO Auto-generated method stub
		
	}

	public void clickLongOnText(String text) {
		// TODO Auto-generated method stub
		
	}

	public void clickLongOnText(String text, int match) {
		// TODO Auto-generated method stub
		
	}

	public void clickLongOnTextAndPress(String text, int index) {
		// TODO Auto-generated method stub
		
	}

	public void clickLongOnView(View view) {
		// TODO Auto-generated method stub
		
	}

	public void clickOnButton(String name) {
		// TODO Auto-generated method stub
		
	}

	public boolean clickOnButton(int index) {
		// TODO Auto-generated method stub
		return false;
	}

	public void clickOnCheckBox(int index) {
		// TODO Auto-generated method stub
		
	}

	public void clickOnEditText(int index) {
		// TODO Auto-generated method stub
		
	}

	public void clickOnImage(int index) {
		// TODO Auto-generated method stub
		
	}

	public void clickOnImageButton(int index) {
		// TODO Auto-generated method stub
		
	}

	public void clickOnMenuItem(String text) {
		// TODO Auto-generated method stub
		
	}

	public void clickOnRadioButton(int index) {
		// TODO Auto-generated method stub
		
	}

	public void clickOnScreen(float x, float y) {
		// TODO Auto-generated method stub
		
	}

	public void clickOnText(String text) {
		// TODO Auto-generated method stub
		
	}

	public void clickOnText(String text, int match) {
		// TODO Auto-generated method stub
		
	}

	public void clickOnToggleButton(String name) {
		// TODO Auto-generated method stub
		
	}

	public void clickOnView(View view) {
		// TODO Auto-generated method stub
		
	}

	public void drag(float fromX, float toX, float fromY, float toY,
			int stepCount) {
		// TODO Auto-generated method stub
		
	}

	public void enterText(int index, String text) {
		// TODO Auto-generated method stub
		
	}

	public ArrayList<Activity> getAllOpenedActivities() {
		// TODO Auto-generated method stub
		return null;
	}

	public Button getButton(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getCurrenButtonsCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Activity getCurrentActivity() {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Button> getCurrentButtons() {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<CheckBox> getCurrentCheckBoxes() {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<EditText> getCurrentEditTexts() {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<GridView> getCurrentGridViews() {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<ImageButton> getCurrentImageButtons() {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<ImageView> getCurrentImageViews() {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<ListView> getCurrentListViews() {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<RadioButton> getCurrentRadioButtons() {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<ScrollView> getCurrentScrollViews() {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Spinner> getCurrentSpinners() {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<TextView> getCurrentTextViews(View parent) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<ToggleButton> getCurrentToggleButtons() {
		// TODO Auto-generated method stub
		return null;
	}

	public EditText getEditText(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	public View getTopParent(View view) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<View> getViews() {
		// TODO Auto-generated method stub
		return null;
	}

	public void goBack() {
		// TODO Auto-generated method stub
		
	}

	public void goBackToActivity(String name) {
		// TODO Auto-generated method stub
		
	}

	public boolean isCheckBoxChecked(int index) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isRadioButtonChecked(int index) {
		// TODO Auto-generated method stub
		return false;
	}

	public void pressMenuItem(int index) {
		// TODO Auto-generated method stub
		
	}

	public void pressSpinnerItem(int spinnerIndex, int itemIndex) {
		// TODO Auto-generated method stub
		
	}

	public boolean scrollDown() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean scrollDownList(int listIndex) {
		// TODO Auto-generated method stub
		return false;
	}

	public void scrollToSide(int side) {
		// TODO Auto-generated method stub
		
	}

	public boolean scrollUp() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean scrollUpList(int listIndex) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean searchButton(String search) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean searchButton(String search, int matches) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean searchEditText(String search) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean searchText(String search) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean searchText(String search, int matches) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean searchText(String search, int matches, boolean scroll) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean searchToggleButton(String search) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean searchToggleButton(String search, int matches) {
		// TODO Auto-generated method stub
		return false;
	}

	public void sendKey(int key) {
		// TODO Auto-generated method stub
		
	}

	public void setActivityOrientation(int orientation) {
		// TODO Auto-generated method stub
		
	}

	public void sleep(int time) {
		// TODO Auto-generated method stub
		
	}

	public boolean waitForActivity(String name, int timeout) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean waitForDialogToClose(long timeout) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean waitForText(String text) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean waitForText(String text, int matches, long timeout) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean waitForText(String text, int matches, long timeout,
			boolean scroll) {
		// TODO Auto-generated method stub
		return false;
	}
	
	// this will be the public API
	public void finalize() throws Throwable {
		//activitiyUtils.finalize();
	}
	
}
