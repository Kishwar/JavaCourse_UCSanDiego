package peerrev;

import java.util.HashMap;
import java.util.List;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;

public class FinanceInfo  extends PApplet {

	private static final long serialVersionUID = 1L;
	UnfoldingMap map;
	private String countryFile = "countries.geo.json";
	
	// A List of country markers
	private List<Marker> countryMarkers;
	
	//executes only once
	public void setup() {
		size(900, 700, OPENGL);
		map = new UnfoldingMap(this, 50, 50, 700, 500, new Google.GoogleMapProvider());
		
		//checks the events [mouse, keyboard], we will override mouse
		MapUtils.createDefaultEventDispatcher(this, map);
		
		//load features for country.. need to download for all 
		//TODO
		List<Feature> countries = GeoJSONReader.loadData(this, countryFile);
		countryMarkers = MapUtils.createSimpleMarkers(countries);
		map.addMarkers(countryMarkers);
		
	}

	//repeated execuation call
	public void draw() {
		map.draw();
	}
	
	/** Event handler that gets called automatically when the 
	 * mouse moves.
	 */
	@Override
	public void mouseMoved()
	{
		PrintFinancialInfo(countryMarkers);
	}
	
	//helper method
	public void PrintFinancialInfo(List<Marker> CounMarker) {
		//First High-light country
		HighLightCountry(CounMarker);
		
		//Print 
	}
	
	public void HighLightCountry(List<Marker> CounMarker) {
		//loop through the countries to find it we are somewhere on any country?
		for(Marker Country : CounMarker) {
			if(Country.isInside(map, mouseX, mouseY)) {
				//mouse is over some country.. repaint country
				int colorLevel = (int) map(60, 40, 90, 10, 255);
				fill(0);
				rect(10, 10, 100, 100);
				fill(255, 255, 255);
				Country.setColor(color(255-colorLevel, 100, colorLevel));
				text(Country.getStringProperty("name"), 50, 50);
				
			} else {
				//mouse is out of countries
				text("Outside", 50, 50);
				map = new UnfoldingMap(this, 50, 50, 700, 500, new Google.GoogleMapProvider());
			}
		}
	}
}
