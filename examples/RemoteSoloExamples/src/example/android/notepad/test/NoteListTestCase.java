package example.android.notepad.test;
import java.util.ArrayList;

import android.test.suitebuilder.annotation.Smoke;
import android.widget.TextView;

import com.example.android.notepad.NotesList;
import com.jayway.android.robotium.remotesolo.RemoteSolo;
import com.jayway.android.robotium.solo.Solo;

import junit.extensions.TestSetup;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


public class NoteListTestCase extends TestCase {
	
	static RemoteSolo solo;
	
	public static Test suite() {
        TestSetup setup = new TestSetup(new TestSuite(NoteListTestCase.class)) {
            protected void setUp(  ) throws Exception {
            	// Typical setup()
        		solo = new RemoteSolo(NotesList.class);

        		// emulators
        		solo.addDevice("emulator-5554", 6000, 6000);
        		solo.addDevice("emulator-5556", 5003, 5003);
        	    solo.addDevice("emulator-5558", 5004, 5004);
        	    solo.addDevice("emulator-5560", 5007, 5007);
        	    solo.addDevice("emulator-5562", 5008, 5008);

        		// v1.6 device
        		//solo.addDevice("HT98YLZ00039", 6565, 6565);

        		// v2.2 device
        		//solo.addDevice("HT04TP800408", 5002, 5002);        		
        		solo.connect();
            }

            protected void tearDown() throws Exception {
                solo.disconnect();
            }
        };
        return setup;
    }
	
	
	@Smoke
	 public void testAddNote() throws Exception {
		 solo.clickOnMenuItem("Add note");
		 solo.assertCurrentActivity("Expected NoteEditor activity", "NoteEditor"); //Assert that NoteEditor activity is opened
		 solo.enterText(0, "Note 1"); //Add note
		 solo.goBack();
		 solo.clickOnMenuItem("Add note"); //Clicks on menu item 
		 solo.enterText(0, "Note 2"); //Add note
		 solo.goBack();
		 boolean expected = true;
		 boolean actual = solo.searchText("Note 1") && solo.searchText("Note 2");
		 assertEquals("Note 1 and/or Note 2 are not found", expected, actual);
		
	 }
	
	@Smoke 
	public void testNoteChange() throws Exception {
		solo.clickInList(2); // Clicks on a list line
		solo.setActivityOrientation(Solo.LANDSCAPE); // Change orientation of activity
		solo.pressMenuItem(2); // Change title
		solo.enterText(0, " test");
		solo.goBack();
		solo.goBack();
		boolean expected = true;
		boolean actual = solo.searchText("(?i).*?note 1 test"); // (Regexp) case insensitive												// insensitive
		assertEquals("Note 1 test is not found", expected, actual);

	}
	

	@Smoke
	 public void testNoteRemove() throws Exception {
		 solo.clickOnText("(?i).*?test.*");   //(Regexp) case insensitive/text that contains "test"
		 solo.pressMenuItem(1);   //Delete Note 1 test
		 boolean expected = false;   //Note 1 test & Note 2 should not be found
		 boolean actual = solo.searchText("Note 1 test");
		 assertEquals("Note 1 Test is found", expected, actual);  //Assert that Note 1 test is not found
		 solo.clickLongOnText("Note 2");
		 solo.clickOnText("(?i).*?Delete.*");  //Clicks on Delete in the context menu
		 actual = solo.searchText("Note 2");
		 assertEquals("Note 2 is found", expected, actual);  //Assert that Note 2 is not found
	 }
}
