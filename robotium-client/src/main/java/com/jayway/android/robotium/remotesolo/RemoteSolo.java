package com.jayway.android.robotium.remotesolo;

import java.lang.reflect.Method;
import java.util.ArrayList;
import com.jayway.android.robotium.remotesolo.proxy.ClientProxyCreator;
import com.jayway.android.robotium.solo.ISolo;
import com.jayway.android.robotium.solo.Solo;

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
	
	public void disconnect() throws RemoteException {
		devices.disconnectAllDevices();
	}

	public void assertCurrentActivity(String message, String name) {
		try {
			invokeMethod("assertCurrentActivity", 
					new Class<?>[] { String.class, String.class },
					new Object[] { message, name });
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public void assertCurrentActivity(String message, Class expectedClass) {
		try {
			invokeMethod("assertCurrentActivity", 
					new Class<?>[] { String.class, Class.class },
					new Object[] { message, expectedClass });
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
	

	public void assertCurrentActivity(String message, String name,
			boolean isNewInstance) {
		try {
			invokeMethod("assertCurrentActivity", 
					new Class<?>[] { String.class, String.class, boolean.class },
					new Object[] { message, name, isNewInstance });
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public void assertCurrentActivity(String message, Class expectedClass,
			boolean isNewInstance) {
		try {
			invokeMethod("assertCurrentActivity", 
					new Class<?>[] { String.class, Class.class, boolean.class },
					new Object[] { message, expectedClass, isNewInstance });
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	public void assertLowMemory() {
		try {
			invokeMethod("assertLowMemory", new Class<?>[] {});
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	public void clearEditText(int index) {
		try {
			invokeMethod("clearEditText", 
					new Class<?>[] { int.class },
					new Object[] { index });
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public ArrayList<TextView> clickInList(int line) {
		try {
			
			return (ArrayList<TextView>) invokeMethod("clickInList", 
					 new Class<?>[] { int.class },
					 new Object[] { line });

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<TextView> clickInList(int line, int listIndex) {
		try {
			return (ArrayList<TextView>) invokeMethod("clickInList", 
					new Class<?>[] { int.class, int.class },
					new Object[] { line, listIndex });
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
		return null;
	}

	public void clickLongOnScreen(float x, float y) {
		try {
			invokeMethod("clickLongOnScreen", 
					new Class<?>[] { float.class, float.class },
					new Object[] { x, y });
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	public void clickLongOnText(String text) {
		try {
			invokeMethod("clickLongOnText", 
					new Class<?>[] { String.class },
					new Object[] { text });
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	public void clickLongOnText(String text, int match) {
		try {
			invokeMethod("clickLongOnText", 
					new Class<?>[] { String.class, int.class },
					new Object[] { text, match });
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	public void clickLongOnTextAndPress(String text, int index) {
		try {
			invokeMethod("clickLongOnTextAndPress", 
					new Class<?>[] { String.class, int.class },
					new Object[] { text, index });
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	public void clickLongOnView(View view) {
		try {
			invokeMethod("clickLongOnView", 
					new Class<?>[] { View.class },
					new Object[] { view });
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	public void clickOnButton(String name) {
		try {
			invokeMethod("clickOnButton", 
					new Class<?>[] { String.class },
					new Object[] { name });
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
	

	public void clickOnButton(int index) {
		try {
		    invokeMethod("clickOnButton", 
					  new Class<?>[] { int.class },
					  new Object[] { index });
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	public void clickOnCheckBox(int index) {
		try {
			invokeMethod("clickOnCheckBox", 
					new Class<?>[] { int.class },
					new Object[] { index });
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	public void clickOnEditText(int index) {
		try {
			invokeMethod("clickOnEditText", 
					new Class<?>[] { int.class },
					new Object[] { index });
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	public void clickOnImage(int index) {
		try {
			invokeMethod("clickOnImage", 
					new Class<?>[] { int.class },
					new Object[] { index });
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	public void clickOnImageButton(int index) {
		try {
			invokeMethod("clickOnImageButton", 
					new Class<?>[] { int.class },
					new Object[] { index });
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	public void clickOnMenuItem(String text) {
		try {
			invokeMethod("clickOnMenuItem", 
					new Class<?>[] { String.class },
					new Object[] { text });
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	public void clickOnRadioButton(int index) {
		try {
			invokeMethod("clickOnRadioButton", 
					new Class<?>[] { int.class },
					new Object[] { index });
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	public void clickOnScreen(float x, float y) {
		try {
			invokeMethod("clickOnScreen", 
					new Class<?>[] { float.class, float.class },
					new Object[] { x, y });
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	public void clickOnText(String text) {
		try {
			invokeMethod("clickOnText", 
					new Class<?>[] { String.class },
					new Object[] { text });
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	public void clickOnText(String text, int match) {
		try {
			invokeMethod("clickOnText", 
					new Class<?>[] { String.class, int.class },
					new Object[] { text, match });
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	public void clickOnToggleButton(String name) {
		try {
			invokeMethod("clickOnToggleButton", 
					new Class<?>[] { String.class },
					new Object[] { name });
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	public void clickOnView(View view) {
		try {
			invokeMethod("clickOnView", 
					new Class<?>[] { View.class },
					new Object[] { view });
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	public void drag(float fromX, float toX, float fromY, float toY,
			int stepCount) {
		try {
			invokeMethod("drag", 
					new Class<?>[] { float.class, float.class, float.class, float.class,
					int.class },
					new Object[] { fromX, toX, fromY, toY, stepCount });
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	public void enterText(int index, String text) {
		try {
			invokeMethod("enterText", 
					new Class<?>[] { int.class, String.class },
					new Object[] { index, text });
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Activity> getAllOpenedActivities() {
		try {
			return  (ArrayList<Activity>) invokeMethod("getAllOpenedActivities", 
					new Class<?>[] { } );
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}	
		return null;
	}

	public Button getButton(int index) {
		try {
			return (Button) invokeMethod("getButton", 
					new Class<?>[] { int.class },
					new Object[]{ index });
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}	
		return null;
	}

	public int getCurrenButtonsCount() {
		try {
			return  Integer.parseInt(invokeMethod("getCurrenButtonsCount", 
					new Class<?>[] { }).toString());
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}	
		return -1;
	}

	public Activity getCurrentActivity() {
		try {
			return  (Activity) invokeMethod("getCurrentActivity", 
					new Class<?>[] {});
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Button> getCurrentButtons() {
		try {
			return  (ArrayList<Button>) invokeMethod("getCurrentButtons", 
					new Class<?>[] {  });
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}	
		return null;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<CheckBox> getCurrentCheckBoxes() {
		try {
			return  (ArrayList<CheckBox>) invokeMethod("getCurrentCheckBoxes", 
					new Class<?>[] { });
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}	
		return null;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<EditText> getCurrentEditTexts() {
		try {
			return  (ArrayList<EditText>) invokeMethod("getCurrentEditTexts", 
					new Class<?>[] { });
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}	
		return null;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<GridView> getCurrentGridViews() {
		try {
			return  (ArrayList<GridView>) invokeMethod("getCurrentGridViews", 
					new Class<?>[] { });
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}	
		return null;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<ImageButton> getCurrentImageButtons() {
		try {
			return  (ArrayList<ImageButton>) invokeMethod("getCurrentImageButtons", 
					new Class<?>[] { });
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}	
		return null;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<ImageView> getCurrentImageViews() {
		try {
			return  (ArrayList<ImageView>) invokeMethod("getCurrentImageViews", 
					new Class<?>[] { });
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}	
		return null;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<ListView> getCurrentListViews() {
		try {
			return  (ArrayList<ListView>) invokeMethod("getCurrentListViews", 
					new Class<?>[] { });
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}	
		return null;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<RadioButton> getCurrentRadioButtons() {
		try {
			return  (ArrayList<RadioButton>) invokeMethod("getCurrentRadioButtons", 
					new Class<?>[] { });
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}	
		return null;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<ScrollView> getCurrentScrollViews() {
		try {
			return  (ArrayList<ScrollView>) invokeMethod("getCurrentScrollViews", 
					new Class<?>[] { });
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}	
		return null;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Spinner> getCurrentSpinners() {
		try {
			return  (ArrayList<Spinner>) invokeMethod("getCurrentSpinners", 
					new Class<?>[] { });
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}	
		return null;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<TextView> getCurrentTextViews(View parent) {
		try {
			return  (ArrayList<TextView>) invokeMethod("getCurrentTextViews", 
					new Class<?>[] { View.class },
					new Object[]{ parent });
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}	
		return null;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<ToggleButton> getCurrentToggleButtons() {
		try {
			return  (ArrayList<ToggleButton>) invokeMethod("getCurrentToggleButtons", 
					new Class<?>[] {} );
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}	
		return null;
	}

	public EditText getEditText(int index) {
		try {
			return  (EditText) invokeMethod("getEditText", 
					new Class<?>[] { int.class }, new Object[]{ index } );
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}	
		return null;
	}

	public View getTopParent(View view) {
		try {
			return  (View) invokeMethod("getTopParent", 
					new Class<?>[] { View.class }, new Object[]{ view } );
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}	
		return null;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<View> getViews() {
		try {
			return  (ArrayList<View>) invokeMethod("getViews", 
					new Class<?>[] {});
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}	
		return null;
	}

	public void goBack() {
		try {
			invokeMethod("goBack", 
					new Class<?>[] { } );
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	public void goBackToActivity(String name) {
		try {
			invokeMethod("goBackToActivity", 
					new Class<?>[] { String.class },
					new Object[] { name });
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	public boolean isCheckBoxChecked(int index) {

		try {
			return Boolean.parseBoolean(invokeMethod("isCheckBoxChecked", 
					new Class<?>[] { int.class}, 
					new Object[] { index } ).toString());
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
		return false;	
	}

	public boolean isRadioButtonChecked(int index) {
		try {
			return Boolean.parseBoolean(invokeMethod("isRadioButtonChecked", 
					new Class<?>[] { int.class}, 
					new Object[] { index } ).toString());
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
		return false;
	}

	public void pressMenuItem(int index) {
		try {
			invokeMethod("pressMenuItem", 
					new Class<?>[] { int.class}, 
					new Object[] { index } );
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}

	}

	public void pressSpinnerItem(int spinnerIndex, int itemIndex) {
		try {
			invokeMethod("pressSpinnerItem", 
					new Class<?>[] { int.class, int.class}, 
					new Object[] { spinnerIndex, itemIndex } );
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	public boolean scrollDown() {
		try {
			return Boolean.parseBoolean(invokeMethod("scrollDown", 
					new Class<?>[] {}).toString());
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
		return false;
	}

	public boolean scrollDownList(int listIndex) {
		try {
			return Boolean.parseBoolean(invokeMethod("scrollDownList", 
					new Class<?>[] { int.class}, 
					new Object[] { listIndex } ).toString());
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
		return false;
	}

	public void scrollToSide(int side) {
		try {
			invokeMethod("scrollToSide", 
					new Class<?>[] { int.class }, 
					new Object[] { side } );
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	public boolean scrollUp() {
		try {
			return Boolean.parseBoolean(invokeMethod("scrollUp", 
					new Class<?>[] { }).toString());
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
		return false;
	}

	public boolean scrollUpList(int listIndex) {
		try {
			return Boolean.parseBoolean(invokeMethod("scrollUpList", 
					new Class<?>[] { int.class }, 
					new Object[] { listIndex }).toString());
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
		return false;
	}

	public boolean searchButton(String search) {
		try {
			return Boolean.parseBoolean(invokeMethod("searchButton", 
					new Class<?>[] { String.class }, 
					new Object[] { search }).toString());
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
		return false;
	}

	public boolean searchButton(String search, int matches) {
		try {
			return Boolean.parseBoolean(invokeMethod("searchButton", 
					new Class<?>[] { String.class, int.class }, 
					new Object[] { search, matches }).toString());
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
		return false;
	}

	public boolean searchEditText(String search) {
		try {
			return Boolean.parseBoolean(invokeMethod("searchEditText", 
					new Class<?>[] { String.class }, 
					new Object[] { search }).toString());
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
		return false;
	}

	public boolean searchText(String search) {
		try {
			return Boolean.parseBoolean(invokeMethod("searchText", 
					new Class<?>[] { String.class }, 
					new Object[] { search }).toString());
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
		return false;
	}

	public boolean searchText(String search, int matches) {
		try {
			return Boolean.parseBoolean(invokeMethod("searchText", 
					new Class<?>[] { String.class, int.class }, 
					new Object[] { search, matches }).toString());
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
		return false;
	}

	public boolean searchText(String search, int matches, boolean scroll) {
		try {
			return Boolean.parseBoolean(invokeMethod("searchText", 
					new Class<?>[] { String.class, int.class , boolean.class}, 
					new Object[] { search, matches, scroll }).toString());
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
		return false;
	}

	public boolean searchToggleButton(String search) {
		try {
			return Boolean.parseBoolean(invokeMethod("searchToggleButton", 
					new Class<?>[] { String.class }, 
					new Object[] { search }).toString());
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
		return false;
	}

	public boolean searchToggleButton(String search, int matches) {
		try {
			return Boolean.parseBoolean(invokeMethod("searchToggleButton", 
					new Class<?>[] { String.class, int.class }, 
					new Object[] { search, matches }).toString());
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
		return false;
	}

	public void sendKey(int key) {
		try {
			invokeMethod("sendKey", 
					new Class<?>[] { int.class }, 
					new Object[] { key });
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	public void setActivityOrientation(int orientation) {
		try {
			invokeMethod("setActivityOrientation", 
					new Class<?>[] { int.class }, 
					new Object[] { orientation });
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	public void sleep(int time) {
		try {
			invokeMethod("sleep", 
					new Class<?>[] { int.class }, 
					new Object[] { time });
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	public boolean waitForActivity(String name, int timeout) {
		try {
			return Boolean.parseBoolean(invokeMethod("waitForActivity", 
					new Class<?>[] { String.class, int.class }, 
					new Object[] { name, timeout }).toString());
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
		return false;
	}

	public boolean waitForDialogToClose(long timeout) {
		try {
			return Boolean.parseBoolean(invokeMethod("waitForDialogToClose", 
					new Class<?>[] { long.class }, 
					new Object[] { timeout }).toString());
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
		return false;
	}

	public boolean waitForText(String text) {
		try {
			return Boolean.parseBoolean(invokeMethod("waitForText", 
					new Class<?>[] { String.class }, 
					new Object[] { text }).toString());
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
		return false;
	}

	public boolean waitForText(String text, int matches, long timeout) {
		try {
			return Boolean.parseBoolean(invokeMethod("waitForText", 
					new Class<?>[] { String.class, int.class, long.class }, 
					new Object[] { text, matches, timeout }).toString());
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
		return false;
	}

	public boolean waitForText(String text, int matches, long timeout,
			boolean scroll) {
		try {
			return Boolean.parseBoolean(invokeMethod("waitForText", 
					new Class<?>[] { String.class, int.class, long.class,
					boolean.class }, 
					new Object[] { text, matches, timeout, scroll }).toString());
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
		return false;
	}

	public void finalize() throws Throwable {
		try {
			invokeMethod("finalize", 
					new Class<?>[] {});
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	public void clickLongOnText(String text, int match, boolean scroll) {
		try {
			invokeMethod("clickLongOnText", 
					new Class<?>[] {String.class, int.class, boolean.class},
					new Object[] {text, match, scroll});
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
		
	}

	public void clickOnMenuItem(String text, boolean subMenu) {
		try {
			invokeMethod("clickOnMenuItem", 
					new Class<?>[] {String.class, boolean.class},
					new Object[] {text, subMenu});
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	public void clickOnText(String text, int matches, boolean scroll) {
		try {
			invokeMethod("clickOnText", 
					new Class<?>[] {String.class, int.class, boolean.class},
					new Object[] {text, matches, scroll});
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	public ImageView getImage(int index) {
		try {
			return (ImageView) invokeMethod("getImage", 
					new Class<?>[] { int.class }, 
					new Object[] { index });
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
		return null;
	}

	public ImageButton getImageButton(int index) {
		try {
			return (ImageButton) invokeMethod("getImageButton", 
					new Class<?>[] { int.class }, 
					new Object[] { index });
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
		return null;
	}

	public String getString(int resId) {
		try {
			return (String) invokeMethod("getString", 
					new Class<?>[] { int.class }, 
					new Object[] { resId });
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
		return null;
	}

	public TextView getText(int index) {
		try {
			return (TextView) invokeMethod("getText", 
					new Class<?>[] { int.class }, 
					new Object[] { index });
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
		return null;
	}
	
	public void restartActivity() {
		try {
			invokeMethod("restartActivity", 
					new Class<?>[] {} );
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
	
	private Object invokeMethod(String methodToExecute, Class<?>[] argumentTypes, Object... arguments) throws Exception {
		// create Proxy object for solo class when needed
		Solo mSoloProxy = (Solo) ClientProxyCreator.createProxy(Solo.class);
		// the invoked method
		Method receivedMethod = mSoloProxy.getClass().getMethod(methodToExecute, argumentTypes);
		// invoke, should bubble up to the ClientInvocationHandler
		return receivedMethod.invoke(mSoloProxy, arguments);
	}


}
