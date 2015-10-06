package module6;

import java.util.List;

import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.SimpleLinesMarker;
import processing.core.PConstants;
import processing.core.PGraphics;

/** 
 * A class to represent AirportMarkers on a world map.
 *   
 * @author Kishwar KUMAR
 */
public class AirportMarker extends CommonMarker {
	public static List<SimpleLinesMarker> routes;
	public static int TRI_SIZE = 5;  // The size of the triangle marker
	
	public AirportMarker(Feature city) {
		super(((PointFeature)city).getLocation(), city.getProperties());
	
	}
	
	@Override
	public void drawMarker(PGraphics pg, float x, float y) {
		pg.fill(255, 0, 0);
		pg.ellipse(x, y, 5, 5);	
	}

	@Override
	public void showTitle(PGraphics pg, float x, float y) {
		
		String name = getAirportName() + ", " + getAirportCode() + ", " + 
				getAirportCity();
		
		//backup current style to stack
		pg.pushStyle();
		
		pg.fill(255, 255, 255);
		pg.textSize(12);
		pg.rectMode(PConstants.CORNER);
		pg.rect(x, y-TRI_SIZE-39, pg.textWidth(name) + 6, 29);
		pg.fill(0, 0, 0);
		pg.textAlign(PConstants.LEFT, PConstants.TOP);
		pg.text(name, x+3, y-TRI_SIZE-33);
		
		//get back original style
		pg.popStyle();
	}
	
	private String getAirportName()
	{
		return getStringProperty("name");
	}
	
	private String getAirportCode()
	{
		return getStringProperty("code");
	}
	
	private String getAirportCity()
	{
		return getStringProperty("city");
	}

	@Override
	public void ClickDetail(PGraphics pg, float x, float y) {
		// TODO Auto-generated method stub
		//backup current style to stack
		pg.pushStyle();
		
		pg.fill(255, 255, 255);
		pg.rect(50, 20, 50, 100);
		
		//get back original style
		pg.popStyle();
	}
}
