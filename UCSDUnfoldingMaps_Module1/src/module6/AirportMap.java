package module6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import module6.CommonMarker;
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.data.ShapeFeature;
import de.fhpotsdam.unfolding.marker.AbstractMarker;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.SimpleLinesMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.utils.MapUtils;
import de.fhpotsdam.unfolding.geo.Location;
import parsing.ParseFeed;
import processing.core.PApplet;

/** An applet that shows airports (and routes)
 * on a world map.  
 * @author Kishwar KUMAR
 */
public class AirportMap extends PApplet {
	
	private static final long serialVersionUID = 1L;
	UnfoldingMap map;
	private List<Marker> airportList;
	HashMap<Integer, Location> airports;
	List<Marker> routeList;
	
	private CommonMarker lastSelected;
	private CommonMarker lastClicked;
	
	private String cityFile = "city-data.json";
	
	public void setup() {
		// setting up PAppler
		size(1050,650, OPENGL);
		
		// setting up map and default events
		map = new UnfoldingMap(this, 250, 50, 750, 550, new Google.GoogleMapProvider());
		MapUtils.createDefaultEventDispatcher(this, map);
		
		// get features from airport data
		List<PointFeature> features = ParseFeed.parseAirports(this, "airports.dat");
		
		// list for markers, hashmap for quicker access when matching with routes
		airportList = new ArrayList<Marker>();
		airports = new HashMap<Integer, Location>();
		
		// create markers from features
		for(PointFeature feature : features) {
			AirportMarker m = new AirportMarker(feature);
	
			m.setRadius(5);
			airportList.add(m);
			
			// put airport in hashmap with OpenFlights unique id for key
			airports.put(Integer.parseInt(feature.getId()), feature.getLocation());
		
		}
		
		map.addMarkers(airportList);
		
	}
	
	public void draw() {
		map.draw();
		key();
	}
	
	private void key() {
		//zoom in
		fill(155, 155, 155);
		rect(970, 60, 20, 20);
		fill(255, 255, 255);
		textSize(18);
		text("+", 974, 75);
		
		//zoom out
		fill(155, 155, 155);
		rect(970, 83, 20, 20);
		fill(255, 255, 255);
		textSize(18);
		text("-", 975, 98);
	}
	
	/** Event handler that gets called automatically when the 
	 * mouse moves.
	 */
	@Override
	public void mouseMoved()
	{
		// clear the last selection
		if (lastSelected != null) {
			lastSelected.setSelected(false);
			lastSelected = null;
		}
		
		//check if mouse is over any of the airport
		selectMarkerIfHover(airportList);
	}
	
	// If there is a marker under the cursor, and lastSelected is null 
	// set the lastSelected to be the first marker found under the cursor
	// Make sure you do not select two markers.
	// 
	private void selectMarkerIfHover(List<Marker> markers)
	{
		int len = 0;
		for(len = 0; len < markers.size(); len++) {
			if(((AbstractMarker)markers.get(len)).isInside(map, mouseX, mouseY)) {
				if(lastSelected == null) {
					lastSelected = (CommonMarker) markers.get(len);
					if(lastSelected.isSelected() == false) {
						lastSelected.setSelected(true);
						break;
					}
				}
			}
		}
	}
	
	/** The event handler for mouse clicks
	 * It will display an earthquake and its threat circle of cities
	 * Or if a city is clicked, it will display all the earthquakes 
	 * where the city is in the threat circle
	 */
	@Override
	public void mouseClicked()
	{
		if ((mouseX > 970 && mouseX < 990) && (mouseY > 83 && mouseY < 103)) {
			ZoomOut();
			if (lastClicked != null) {
				lastClicked.setSelected(false);
				lastClicked.setClicked(false);
				lastClicked = null;
			}
			return;
		}
		
		if ((mouseX > 970 && mouseX < 990) && (mouseY > 60 && mouseY < 80)) {
			ZoomIn();
			if (lastClicked != null) {
				lastClicked.setSelected(false);
				lastClicked.setClicked(false);
				lastClicked = null;
			}
			return;
		}
			
		if (lastClicked != null) {
			lastClicked.setSelected(false);
			lastClicked.setClicked(false);
			lastClicked = null;
		}
		
		selectLastClicked(airportList);
		
		drawRoutes(airportList);
	}
	
	//zoom out
	private void ZoomOut() {
		map.zoomOut();
	}
	
	//zoom in
	private void ZoomIn() {
		map.zoomIn();
	}
	
	//selects the last clicked and makes lastClick Object
	private void selectLastClicked(List<Marker> airMarker)
	{
		int len = 0;
		for(len = 0; len < airMarker.size(); len++) {
			if(((AbstractMarker)airMarker.get(len)).isInside(map, mouseX, mouseY)) {
				if(lastClicked == null) {
					lastClicked = (CommonMarker) airMarker.get(len);
					if(lastClicked.isSelected() == false) {
						lastClicked.setSelected(true);
						lastClicked.setClicked(true);
						break;
					}
				}
			}
		}
	}
	
	//draw routes from selected airport
	private void drawRoutes(List<Marker> airMarker) {
		
		//return if lastClicked is null
		if (lastClicked == null)
			return;
		
		int source = 0;
		boolean foundAirport = false;
		int DirRoute = 0;
		
		//parse route data
		List<ShapeFeature> routes = ParseFeed.parseRoutes(this, "routes.dat");
		routeList = new ArrayList<Marker>();
		for(ShapeFeature route : routes) {
			
			// get source and destination airportIds
			source = Integer.parseInt((String)route.getProperty("source"));
			
			//if current clicked Airport and loop Airport distance = 0, then it's source Airport
			if(getDist(lastClicked.getLocation().getLat(), lastClicked.getLocation().getLon(), 
					airports.get(source).getLat(), airports.get(source).getLon()) == 0) {
				//we found the source.. let's plot routes from this source
				foundAirport = true;
				break;
			}
		}
		
		//don't do anything if source is 0
		if ((source == 0) && foundAirport)
			return;
		
		//hide all airports
		for (Marker mhide : airportList) {
			mhide.setHidden(true);
		}
		
		//show only lastClicked
		lastClicked.setHidden(false);
		
		//we have found source.. lets see where are destinations
		for(ShapeFeature route : routes) {
			
			if(Integer.parseInt((String)route.getProperty("source")) != source)
				continue;
			
			// get destination airportIds
			int dest = Integer.parseInt((String)route.getProperty("destination"));
			
			// get locations for airports on route
			if(airports.containsKey(source) && airports.containsKey(dest)) {
				route.addLocation(airports.get(source));
				route.addLocation(airports.get(dest));
				DirRoute++;
				//turn on the destination airport
				for (Marker mhide : airportList) {
					if(getDist(mhide.getLocation().getLat(), mhide.getLocation().getLon(), 
							airports.get(dest).getLat(), airports.get(dest).getLon()) == 0) {
						mhide.setHidden(false);
					}
				}
			}
			
			SimpleLinesMarker sl = new SimpleLinesMarker(route.getLocations(), route.getProperties());
			
			routeList.add(sl);
		}
		
		//add Marker to Map
		map.addMarkers(routeList);
		
		//print Airport Info
		PrintAirportInfo(DirRoute);
		
	}
	
	private void PrintAirportInfo(int DirRoute) {
		
		//{country="Papua New Guinea", altitude=5282, code="GKA", city="Goroka", name="Goroka"}
		
		//make rectangle
		fill(103, 103, 103);
		rect(30, 50, 210, 300, 7);
		
		//text
		fill(255, 255, 255);
		textSize(22);
		text(((String)lastClicked.getProperty("country")).replaceAll("^\"|\"$", ""), 35, 80);
		textSize(16);
		text("City: " + ((String)lastClicked.getProperty("city")).replaceAll("^\"|\"$", ""), 35, 110);
		
		textSize(12);
		text("Airport: " + ((String)lastClicked.getProperty("name")).replaceAll("^\"|\"$", ""), 35, 130);
		text("Altitude: " + ((String)lastClicked.getProperty("altitude")).replaceAll("^\"|\"$", ""), 35, 145);
		text("code: " + ((String)lastClicked.getProperty("code")).replaceAll("^\"|\"$", ""), 35, 160);
		text("Number Of Destinations: " + DirRoute, 35, 175);
		text("Population: " + GetPopulation() + " millions", 35, 190);
	}
	
	private String GetPopulation() {
		List<Feature> cities = GeoJSONReader.loadData(this, cityFile);
		boolean CityFound = false;
		Feature ClickedCity = null;
		String CityLastClicked;
		
		//{country=Germany, name=Berlin, coastal=false, population=3.375}
		for(Feature city : cities) {
			CityLastClicked = (String) lastClicked.getProperty("city");
			CityLastClicked = CityLastClicked.replaceAll("^\"|\"$", "");
			if (CityLastClicked.equals(city.getProperty("name"))) {
				CityFound = true;
				ClickedCity = city;
				break;
			}
		}
		if(CityFound && (ClickedCity != null))
			return (String) ClickedCity.getProperty("population");
		else
			return "0";
	}
	
	private double getDist(double lat1, double lon1, double lat2, double lon2) {
	    int R = 6373; // radius of the earth in kilometres
	    double lat1rad = Math.toRadians(lat1);
	    double lat2rad = Math.toRadians(lat2);
	    double deltaLat = Math.toRadians(lat2-lat1);
	    double deltaLon = Math.toRadians(lon2-lon1);

	    double a = Math.sin(deltaLat/2) * Math.sin(deltaLat/2) +
	    	       Math.cos(lat1rad) * Math.cos(lat2rad) *
	    	       Math.sin(deltaLon/2) * Math.sin(deltaLon/2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

	    double d = R * c;
	    return d;
	}
}
