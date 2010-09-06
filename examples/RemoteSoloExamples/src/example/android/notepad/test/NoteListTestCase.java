package example.android.notepad.test;
import java.util.ArrayList;

import android.widget.TextView;

import com.example.android.notepad.NotesList;
import com.jayway.android.robotium.remotesolo.RemoteSolo;

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
        		solo.addDevice("emulator-5554", 5000, 5000);
        		solo.addDevice("emulator-5556", 5003, 5003);
        		//solo.addDevice("emulator-5558", 5004, 5004);       

        		// v1.6 device
        		//solo.addDevice("HT98YLZ00039", 5001, 5001);

        		// v2.2 device
//         	    solo.addDevice("HT04TP800408", 5002, 5002);        		
        		solo.connect();
            }

            protected void tearDown() throws Exception {
                solo.disconnect();
            }
        };
        return setup;
    }
	
	public void testNoButton() {
		int expectButtonCount = 0; 
		int actualCount = solo.getCurrenButtonsCount();
		assertEquals("Should have no buttons", actualCount, expectButtonCount);
	}
	
	
	public void testAddNote(){
		 solo.clickOnMenuItem("Add note");
		 solo.enterText(0, "Note 1"); //Add note
		 solo.goBack();
		 solo.clickOnMenuItem("Add note"); //Clicks on menu item 
		 solo.enterText(0, "Note 2"); //Add note
		 solo.goBack();
		 boolean expected = true;
		 boolean actual = solo.searchText("Note 1") && solo.searchText("Note 2");
		 assertEquals("Note 1 and/or Note 2 are not found", expected, actual);
	}
	
	public void testTextViewCount(){
		ArrayList<TextView> tv = solo.clickInList(2); // Clicks on a list line
		assertEquals(1, tv.size());
		boolean isEmpty = tv.isEmpty();
		assertFalse(isEmpty);
	}

	public void testNoteChange() {
		solo.setActivityOrientation(0); // Change orientation of activity
		solo.pressMenuItem(2); // Change title
		solo.enterText(0, " test");
		solo.goBack();
		solo.goBack();
		boolean expected = true;
		boolean actual = solo.searchText("(?i).*?note 1 test.*"); // (Regexp) case insensitive												// insensitive
		assertEquals("Note 1 test is not found", expected, actual);
	}


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
