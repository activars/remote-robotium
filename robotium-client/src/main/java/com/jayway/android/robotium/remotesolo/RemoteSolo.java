package com.jayway.android.robotium.remotesolo;

import java.util.ArrayList;

import com.jayway.android.robotium.solo.ISolo;

import junit.framework.Assert;
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


public class RemoteSolo implements ISolo {
	private DeviceClientManager devices;

	/**
	 * RemoteSolo constructor
	 * 
	 * @param activityClass
	 *            the class of an Activity that will be instrumented
	 */
	public RemoteSolo(Class<?> activityClass) {
		devices = new DeviceClientManager();
		devices.setTargetClass(activityClass);
	}

	/**
	 * Adds devices for initiating connections. This can be called multiple
	 * times if there are several devices/emulators used for remote testing.
	 * 
	 * @param deviceSerialAZ							`~````
	 *            device/emulator serial number. The serial number can be
	 *            obtained using adb tool(adb devices).
	 * @param pcPort
	 *            Port number on PC for establishing connection
	 * @param devicePort
	 *            Port number on device/emulator for establishing connection
	 */
	public void addDevice(String deviceSerial, int pcPort, int devicePort) {
		devices.addDevice(deviceSerial, pcPort, devicePort);
	}

	public void connect() {
		devices.connectAll();
	}
	
	public void disconnect() {
		devices.disconnectAllDevices();
	}

	public void assertCurrentActivity(String message, String name) {
		try {
			devices.invokeMethod("assertCurrentActivity", 
					new Class<?>[] { String.class, String.class },
					new Object[] { message, name });
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	public void assertCurrentActivity(String message, Class expectedClass) {
		try {
			devices.invokeMethod("assertCurrentActivity", 
					new Class<?>[] { String.class, Class.class },
					new Object[] { message, expectedClass });
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
	

	public void assertCurrentActivity(String message, String name,
			boolean isNewInstance) {
		try {
			devices.invokeMethod("assertCurrentActivity", 
					new Class<?>[] { String.class, String.class, boolean.class },
					new Object[] { message, name, isNewInstance });
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	public void assertCurrentActivity(String message, Class expectedClass,
			boolean isNewInstance) {
		try {
			devices.invokeMethod("assertCurrentActivity", 
					new Class<?>[] { String.class, Class.class, boolean.class },
					new Object[] { message, expectedClass, isNewInstance });
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	public void assertLowMemory() {
		try {
			devices.invokeMethod("assertLowMemory", new Class<?>[] {});
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	public void clearEditText(int index) {
		try {
			devices.invokeMethod("clearEditText", 
					new Class<?>[] { int.class },
					new Object[] { index });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public ArrayList<TextView> clickInList(int line) {
		ArrayList<TextView> textViews = null;
		try {
			 textViews = (ArrayList<TextView>) devices.invokeMethod("clickInList", 
					 new Class<?>[] { int.class },
					 new Object[] { line });
			 return textViews;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return textViews;
	}

	public ArrayList<TextView> clickInList(int line, int listIndex) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not Implemented yet");
	}

	public void clickLongOnScreen(float x, float y) {
		try {
			devices.invokeMethod("clickLongOnScreen", 
					new Class<?>[] { float.class, float.class },
					new Object[] { x, y });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clickLongOnText(String text) {
		try {
			devices.invokeMethod("clickLongOnText", 
					new Class<?>[] { String.class },
					new Object[] { text });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clickLongOnText(String text, int match) {
		try {
			devices.invokeMethod("clickLongOnText", 
					new Class<?>[] { String.class, int.class },
					new Object[] { text, match });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clickLongOnTextAndPress(String text, int index) {
		try {
			devices.invokeMethod("clickLongOnTextAndPress", 
					new Class<?>[] { String.class, int.class },
					new Object[] { text, index });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clickLongOnView(View view) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not Implemented yet");
	}

	public void clickOnButton(String name) {
		try {
			devices.invokeMethod("clickOnButton", 
					new Class<?>[] { String.class },
					new Object[] { name });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public void clickOnButton(int index) {
		try {
		    devices.invokeMethod("clickOnButton", 
					  new Class<?>[] { int.class },
					  new Object[] { index });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clickOnCheckBox(int index) {
		try {
			devices.invokeMethod("clickOnCheckBox", 
					new Class<?>[] { int.class },
					new Object[] { index });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clickOnEditText(int index) {
		try {
			devices.invokeMethod("clickOnEditText", 
					new Class<?>[] { int.class },
					new Object[] { index });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clickOnImage(int index) {
		try {
			devices.invokeMethod("clickOnImage", 
					new Class<?>[] { int.class },
					new Object[] { index });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clickOnImageButton(int index) {
		try {
			devices.invokeMethod("clickOnImageButton", 
					new Class<?>[] { int.class },
					new Object[] { index });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clickOnMenuItem(String text) {
		try {
			devices.invokeMethod("clickOnMenuItem", 
					new Class<?>[] { String.class },
					new Object[] { text });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clickOnRadioButton(int index) {
		try {
			devices.invokeMethod("clickOnRadioButton", 
					new Class<?>[] { int.class },
					new Object[] { index });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clickOnScreen(float x, float y) {
		try {
			devices.invokeMethod("clickOnScreen", 
					new Class<?>[] { float.class, float.class },
					new Object[] { x, y });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clickOnText(String text) {
		try {
			devices.invokeMethod("clickOnText", 
					new Class<?>[] { String.class },
					new Object[] { text });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clickOnText(String text, int match) {
		try {
			devices.invokeMethod("clickOnText", 
					new Class<?>[] { String.class, int.class },
					new Object[] { text, match });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clickOnToggleButton(String name) {
		try {
			devices.invokeMethod("clickOnToggleButton", 
					new Class<?>[] { String.class },
					new Object[] { name });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clickOnView(View view) {
		try {
			devices.invokeMethod("clickOnView", 
					new Class<?>[] { View.class },
					new Object[] { view });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void drag(float fromX, float toX, float fromY, float toY,
			int stepCount) {
		try {
			devices.invokeMethod("drag", 
					new Class<?>[] { float.class, float.class, float.class, float.class,
					int.class },
					new Object[] { fromX, toX, fromY, toY, stepCount });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void enterText(int index, String text) {
		try {
			devices.invokeMethod("enterText", 
					new Class<?>[] { int.class, String.class },
					new Object[] { index, text });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Activity> getAllOpenedActivities() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not Implemented yet");
	}

	public Button getButton(int index) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not Implemented yet");
	}

	public int getCurrenButtonsCount() {
		try {
			return Integer.parseInt((String) devices.invokeMethod("getCurrenButtonsCount", 
					new Class<?>[] { }));
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return 0;
	}

	public Activity getCurrentActivity() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not Implemented yet");
	}

	public ArrayList<Button> getCurrentButtons() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not Implemented yet");
	}

	public ArrayList<CheckBox> getCurrentCheckBoxes() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not Implemented yet");
	}

	public ArrayList<EditText> getCurrentEditTexts() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not Implemented yet");
	}

	public ArrayList<GridView> getCurrentGridViews() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not Implemented yet");
	}

	public ArrayList<ImageButton> getCurrentImageButtons() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not Implemented yet");
	}

	public ArrayList<ImageView> getCurrentImageViews() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not Implemented yet");
	}

	public ArrayList<ListView> getCurrentListViews() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not Implemented yet");
	}

	public ArrayList<RadioButton> getCurrentRadioButtons() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not Implemented yet");
	}

	public ArrayList<ScrollView> getCurrentScrollViews() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not Implemented yet");
	}

	public ArrayList<Spinner> getCurrentSpinners() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not Implemented yet");
	}

	public ArrayList<TextView> getCurrentTextViews(View parent) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not Implemented yet");
	}

	public ArrayList<ToggleButton> getCurrentToggleButtons() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not Implemented yet");
	}

	public EditText getEditText(int index) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not Implemented yet");
	}

	public View getTopParent(View view) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not Implemented yet");
	}

	public ArrayList<View> getViews() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not Implemented yet");
	}

	public void goBack() {
		try {
			devices.invokeMethod("goBack", 
					new Class<?>[] { } );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void goBackToActivity(String name) {
		try {
			devices.invokeMethod("goBackToActivity", 
					new Class<?>[] { String.class },
					new Object[] { name });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isCheckBoxChecked(int index) {

		try {
			return Boolean.parseBoolean(devices.invokeMethod("isCheckBoxChecked", 
					new Class<?>[] { int.class}, 
					new Object[] { index } ).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;	
	}

	public boolean isRadioButtonChecked(int index) {
		try {
			return Boolean.parseBoolean(devices.invokeMethod("isRadioButtonChecked", 
					new Class<?>[] { int.class}, 
					new Object[] { index } ).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public void pressMenuItem(int index) {
		try {
			devices.invokeMethod("pressMenuItem", 
					new Class<?>[] { int.class}, 
					new Object[] { index } );
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void pressSpinnerItem(int spinnerIndex, int itemIndex) {
		try {
			devices.invokeMethod("pressSpinnerItem", 
					new Class<?>[] { int.class, int.class}, 
					new Object[] { spinnerIndex, itemIndex } );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean scrollDown() {
		try {
			return Boolean.parseBoolean(devices.invokeMethod("scrollDown", 
					new Class<?>[] {}).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean scrollDownList(int listIndex) {
		try {
			return Boolean.parseBoolean(devices.invokeMethod("scrollDownList", 
					new Class<?>[] { int.class}, 
					new Object[] { listIndex } ).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public void scrollToSide(int side) {
		try {
			devices.invokeMethod("scrollToSide", 
					new Class<?>[] { int.class }, 
					new Object[] { side } );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean scrollUp() {
		try {
			return Boolean.parseBoolean(devices.invokeMethod("scrollUp", 
					new Class<?>[] { }).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean scrollUpList(int listIndex) {
		try {
			return Boolean.parseBoolean(devices.invokeMethod("scrollUpList", 
					new Class<?>[] { int.class }, 
					new Object[] { listIndex }).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean searchButton(String search) {
		try {
			return Boolean.parseBoolean(devices.invokeMethod("searchButton", 
					new Class<?>[] { String.class }, 
					new Object[] { search }).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean searchButton(String search, int matches) {
		try {
			return Boolean.parseBoolean(devices.invokeMethod("searchButton", 
					new Class<?>[] { String.class, int.class }, 
					new Object[] { search, matches }).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean searchEditText(String search) {
		try {
			return Boolean.parseBoolean(devices.invokeMethod("searchEditText", 
					new Class<?>[] { String.class }, 
					new Object[] { search }).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean searchText(String search) {
		try {
			return Boolean.parseBoolean(devices.invokeMethod("searchText", 
					new Class<?>[] { String.class }, 
					new Object[] { search }).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean searchText(String search, int matches) {
		try {
			return Boolean.parseBoolean(devices.invokeMethod("searchText", 
					new Class<?>[] { String.class, int.class }, 
					new Object[] { search, matches }).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean searchText(String search, int matches, boolean scroll) {
		try {
			return Boolean.parseBoolean(devices.invokeMethod("searchText", 
					new Class<?>[] { String.class, int.class , boolean.class}, 
					new Object[] { search, matches, scroll }).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean searchToggleButton(String search) {
		try {
			return Boolean.parseBoolean(devices.invokeMethod("searchToggleButton", 
					new Class<?>[] { String.class }, 
					new Object[] { search }).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean searchToggleButton(String search, int matches) {
		try {
			return Boolean.parseBoolean(devices.invokeMethod("searchToggleButton", 
					new Class<?>[] { String.class, int.class }, 
					new Object[] { search, matches }).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public void sendKey(int key) {
		try {
			devices.invokeMethod("sendKey", 
					new Class<?>[] { int.class }, 
					new Object[] { key });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setActivityOrientation(int orientation) {
		try {
			devices.invokeMethod("setActivityOrientation", 
					new Class<?>[] { int.class }, 
					new Object[] { orientation });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sleep(int time) {
		try {
			devices.invokeMethod("sleep", 
					new Class<?>[] { int.class }, 
					new Object[] { time });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean waitForActivity(String name, int timeout) {
		try {
			return Boolean.parseBoolean(devices.invokeMethod("waitForActivity", 
					new Class<?>[] { String.class, int.class }, 
					new Object[] { name, timeout }).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean waitForDialogToClose(long timeout) {
		try {
			return Boolean.parseBoolean(devices.invokeMethod("waitForDialogToClose", 
					new Class<?>[] { long.class }, 
					new Object[] { timeout }).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean waitForText(String text) {
		try {
			return Boolean.parseBoolean(devices.invokeMethod("waitForText", 
					new Class<?>[] { String.class }, 
					new Object[] { text }).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean waitForText(String text, int matches, long timeout) {
		try {
			return Boolean.parseBoolean(devices.invokeMethod("waitForText", 
					new Class<?>[] { String.class, int.class, long.class }, 
					new Object[] { text, matches, timeout }).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean waitForText(String text, int matches, long timeout,
			boolean scroll) {
		try {
			return Boolean.parseBoolean(devices.invokeMethod("waitForText", 
					new Class<?>[] { String.class, int.class, long.class,
					boolean.class }, 
					new Object[] { text, matches, timeout, scroll }).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public void finalize() throws Throwable {
		try {
			devices.invokeMethod("finalize", 
					new Class<?>[] {});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clickLongOnText(String text, int match, boolean scroll) {
		try {
			devices.invokeMethod("clickLongOnText", 
					new Class<?>[] {String.class, int.class, boolean.class},
					new Object[] {text, match, scroll});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void clickOnMenuItem(String text, boolean subMenu) {
		try {
			devices.invokeMethod("clickOnMenuItem", 
					new Class<?>[] {String.class, boolean.class},
					new Object[] {text, subMenu});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clickOnText(String text, int matches, boolean scroll) {
		try {
			devices.invokeMethod("clickOnText", 
					new Class<?>[] {String.class, int.class, boolean.class},
					new Object[] {text, matches, scroll});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ImageView getImage(int index) {
		try {
			return (ImageView) devices.invokeMethod("getImage", 
					new Class<?>[] { int.class }, 
					new Object[] { index });
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ImageButton getImageButton(int index) {
		try {
			return (ImageButton) devices.invokeMethod("getImageButton", 
					new Class<?>[] { int.class }, 
					new Object[] { index });
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getString(int resId) {
		try {
			return (String) devices.invokeMethod("getString", 
					new Class<?>[] { int.class }, 
					new Object[] { resId });
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public TextView getText(int index) {
		try {
			return (TextView) devices.invokeMethod("getText", 
					new Class<?>[] { int.class }, 
					new Object[] { index });
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
