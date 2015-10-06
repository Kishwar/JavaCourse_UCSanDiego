package module5;

import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import processing.core.PConstants;
import processing.core.PGraphics;

/** Implements a visual marker for cities on an earthquake map
 * 
 * @author UC San Diego Intermediate Software Development MOOC team
 * @author Kishwar KUMAR
 *
 */
// TODO: Change SimplePointMarker to CommonMarker as the very first thing you do 
// in module 5 (i.e. CityMarker extends CommonMarker).  It will cause an error.
// That's what's expected.
public class CityMarker extends CommonMarker {
	
	public static int TRI_SIZE = 5;  // The size of the triangle marker
	
	private static int NEW_LINE_SPACE = 20; //space between lines
	//private static int xOffset = -75;  //pop-up offset from x location
	//private static int yOffset = 15;   //pop-up offset from y location
	private static int xtextOffset = 5; //text offset
	private static int ytextOffset = 27; //text offset
	//private static float PopUpSize = (float) 2; //% size of pop-up
	//private static int Multiplier = 5;
	
	public CityMarker(Location location) {
		super(location);
	}
	
	
	public CityMarker(Feature city) {
		super(((PointFeature)city).getLocation(), city.getProperties());
		// Cities have properties: "name" (city name), "country" (country name)
		// and "population" (population, in millions)
	}
	
	/** Show the title of the city if this marker is selected */
	public void showTitle(PGraphics pg, float x, float y)
	{
		
		// TODO: Implement this method
		//pg.fill(255, 250, 240);
		//pg.rect(x, y+yOffset, 
				  // (getCity().length() * Multiplier) + (int)((getCity().length() * Multiplier) / PopUpSize), 
				  //   NEW_LINE_SPACE * 2);
		
		pg.fill(0);
		pg.textSize(10);
		pg.text(getCity(), x+xtextOffset, y+ytextOffset);
		pg.text(getPopulation() + " million", x+xtextOffset, y+ytextOffset+NEW_LINE_SPACE);
	}
	
	
	
	/* Local getters for some city properties.  
	 */
	public String getCity()
	{
		return getStringProperty("name");
	}
	
	public String getCountry()
	{
		return getStringProperty("country");
	}
	
	public float getPopulation()
	{
		return Float.parseFloat(getStringProperty("population"));
	}


	@Override
	public void drawMarker(PGraphics pg, float x, float y) {
		// TODO Auto-generated method stub
		// Save previous drawing style
		pg.pushStyle();
		
		// IMPLEMENT: drawing triangle for each city
		pg.fill(150, 30, 30);
		pg.triangle(x, y-TRI_SIZE, x-TRI_SIZE, y+TRI_SIZE, x+TRI_SIZE, y+TRI_SIZE);
		
		// Restore previous drawing style
		pg.popStyle();
	}
}
