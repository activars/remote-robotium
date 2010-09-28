package com.jayway.android.robotium.core.impl;

import android.widget.EditText;
import junit.framework.Assert;
import android.app.Instrumentation;
import android.view.KeyEvent;

/**
 * This class contains a method to enter text into text fields.
 * 
 * @author Renas Reda, renas.reda@jayway.com
 *
 */

public class TextEnterer{
	
	private final ViewFetcher viewFetcher;
	private final RobotiumUtils robotiumUtils;
	private final Clicker clicker;
	private final Instrumentation inst;

    /**
     * Constructs this object.
     *
     * @param viewFetcher the {@link ViewFetcher} instance.
     * @param robotiumUtils the {@link RobotiumUtils} instance.
     * @param clicker the {@link Clicker} instance.
     * @param inst the {@link Instrumentation} instance.
     */
    public TextEnterer(ViewFetcher viewFetcher, RobotiumUtils robotiumUtils, Clicker clicker, Instrumentation inst) {
        this.viewFetcher = viewFetcher;
        this.robotiumUtils = robotiumUtils;
        this.clicker = clicker;
        this.inst = inst;
    }

	
	/**
	 * Enters text into an {@link android.widget.EditText} with a certain index.
	 *
	 * @param index the index of the text field. Index 0 if only one available.
	 * @param text the text string that is to be entered into the text field
	 *
	 */
	
    public void enterText(int index, String text) {
    	robotiumUtils.waitForIdle();
    	Boolean focused = false;
    	try {
			if (viewFetcher.getCurrentViews(EditText.class).size() > 0) {
				for (int i = 0; i < viewFetcher.getCurrentViews(EditText.class).size(); i++) {
					if (viewFetcher.getCurrentViews(EditText.class).get(i).isFocused())
    					focused = true;
    			}
    		}
			if (!focused && viewFetcher.getCurrentViews(EditText.class).size() > 0) {
				clicker.clickOn(EditText.class, index);
				try{
    				inst.sendStringSync(text);
    				inst.sendKeyDownUpSync(KeyEvent.KEYCODE_ENTER);
    			}catch(SecurityException e){
    				Assert.assertTrue("Text can not be entered!", false);	
    			}

    		} else if (focused && viewFetcher.getCurrentViews(EditText.class).size() >1)
    		{
				clicker.clickOn(EditText.class, index);
				try{
    				inst.sendStringSync(text);
    			}catch(SecurityException e){
    				Assert.assertTrue("Text can not be entered!", false);
    			}
    		}
    		else {
    			try{
    				inst.sendStringSync(text);
    			}catch(SecurityException e){
    				Assert.assertTrue("Text can not be entered!", false);
    			}
    		}
    	} catch (IndexOutOfBoundsException e) {
    		Assert.assertTrue("Index is not valid!", false);
    	} catch (NullPointerException e) {
    		Assert.assertTrue("NullPointerException!", false);
    	}

    }

}
