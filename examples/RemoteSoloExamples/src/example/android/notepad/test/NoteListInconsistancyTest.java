package example.android.notepad.test;
import java.util.ArrayList;

import android.widget.ListView;

import com.example.android.notepad.NotesList;
import com.jayway.android.robotium.remotesolo.RemoteSolo;

import junit.extensions.TestSetup;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


public class NoteListInconsistancyTest extends TestCase {
	
	static RemoteSolo solo;
	
	public static Test suite() {
        TestSetup setup = new TestSetup(new TestSuite(NoteListInconsistancyTest.class)) {
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
        		// solo.addDevice("HT04TP800408", 5002, 5002);        		
        		solo.connect();
            }

            protected void tearDown() throws Exception {
                solo.disconnect();
            }
        };
        return setup;
    }
	
	
	// NOTE: one of the device should have added Note 1, 
	//      but the other one do not have Note 1.
	// The test should fail if search text results are not consistent on multiple devices.
	public void testAddNote(){
		 
		 solo.clickOnMenuItem("Add note"); //Clicks on menu item 
		 solo.enterText(0, "Note 2"); //Add note
		 solo.goBack();
		 boolean expected = true;
		 boolean actual = solo.searchText("Note 1") && solo.searchText("Note 2");
		 assertEquals("Note 1 and/or Note 2 are not found", expected, actual);
	}
	
	public void testListViewEqual(){
		ArrayList<ListView> lists = solo.getCurrentListViews();; // Clicks on a list line
		ListView lv1 = lists.get(0);
		ListView lv2 = lists.get(0);
		boolean isequal = lv1.equals(lv2);
		assertTrue(isequal);
	}

	
}
