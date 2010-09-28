package example.android.srplayer.test;
import java.util.ArrayList;

import sr.player.SRPlayer;

import android.test.suitebuilder.annotation.LargeTest;
import android.widget.TextView;

import com.jayway.android.robotium.remotesolo.RemoteSolo;

import junit.extensions.TestSetup;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


public class SRPlayerTestCase extends TestCase {
	
	static RemoteSolo solo;
	
	public static Test suite() {
        TestSetup setup = new TestSetup(new TestSuite(SRPlayerTestCase.class)) {
            protected void setUp(  ) throws Exception {
            	// Typical setup()
        		solo = new RemoteSolo(SRPlayer.class);

        		// emulators
        		//solo.addDevice("emulator-5554", 5000, 5000);
        		//solo.addDevice("emulator-5556", 5008, 5008);
        		solo.addDevice("emulator-5558", 5004, 5004);       

        		// v1.6 device
        		//solo.addDevice("HT98YLZ00039", 5001, 5001);

        		// v2.2 device
        		// solo.addDevice("HT04TP800408", 5002, 5002);        		
        		solo.connect();
            }

            protected void tearDown() throws Exception {
                // do your one-time tear down here!
            	solo.disconnect();
            }
        };
        return setup;
    }
	
	
	public void testAddToFavorites()
	{	
		solo.clickOnMenuItem("Alarm", true);
		solo.clickOnText("Kanaler");
		solo.waitForDialogToClose(5000);
		String channel = solo.clickInList(5).get(1).getText().toString();
		
		assertTrue("Did not find " + channel, solo.searchText(channel));
		solo.goBack();
		solo.waitForText(channel);
		solo.clickLongOnTextAndPress(channel, 0);
		
		solo.clickOnText("Favoriter");
		
		assertTrue(solo.searchText("1 favorit är tillagd", 1));
		solo.clickOnText("Kanaler");
		
		solo.clickLongOnTextAndPress(channel, 0);
		
		assertFalse("Found " + channel, solo.searchText(channel, 1));
		solo.goBack();
		assertFalse("Found 1 favorit är tillagd7" + channel, solo.searchText("1 favorit är tillagd", 1));
		
	}

	
	@LargeTest
	public void testCategories() throws InterruptedException
	{	
		solo.clickOnText("Kanaler");
		solo.clickOnText("Kategorier");
		solo.clickOnText("Musik");
		assertTrue("Musik is not found", solo.searchText("Musik", 1, false));

		ArrayList<TextView> categories = solo.clickInList(4);
		String kategorie = categories.get(0).getText().toString();
		assertTrue("" + categories, solo.searchText(kategorie));
		solo.waitForText(kategorie);
		
		ArrayList<TextView> categorieItems = solo.clickInList(1);
		String categorieTitle = categorieItems.get(0).getText().toString();
		String categorieText = categorieItems.get(1).getText().toString();
		assertTrue("Title is not found" + categorieTitle, solo.searchText(categorieTitle));
		assertTrue("Text is not found", solo.searchText(categorieText));
	}
	

	
	
}
