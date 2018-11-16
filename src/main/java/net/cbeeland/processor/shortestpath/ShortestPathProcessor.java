package net.cbeeland.processor.shortestpath;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;

import net.cbeeland.domain.facility.FacilityManager;
import net.cbeeland.exception.DataValidationException;
import net.cbeeland.util.InputValidator;

public class ShortestPathProcessor {

  private volatile static ShortestPathProcessor instance;

  // Cache shortest paths for performance optimization
  private HashMap<String, Path> shortestPaths = new HashMap<String, Path>();

  private HashSet<PathLeg> pairs;
  private HashSet<String> seen;
  private Path lowPath;

  private ShortestPathProcessor() {}

  public static ShortestPathProcessor getInstance() {
    if (instance == null) {
      synchronized (ShortestPathProcessor.class) {
        if (instance == null) {
          instance = new ShortestPathProcessor();
        }
      }
    }
    return instance;
  }

  public Path findBestPath(String start, String end) throws DataValidationException {
    String startEndString = start + ":" + end;

    // Do not re-process if shortest path is already calculated
    if (shortestPaths.containsKey(startEndString)) {
      return shortestPaths.get(startEndString);
    }

    pairs = new HashSet<PathLeg>();
    seen = new HashSet<String>();
    lowPath = new Path(start, end);

    InputValidator.validateStringNotNullOrEmpty(start, "start", "ShortestPathProcessor", "findBestPath(String start, String end)");
    InputValidator.validateStringNotNullOrEmpty(end, "end", "ShortestPathProcessor", "findBestPath(String start, String end)");

    // Generate a set of all Path Legs
    mapPairs(start);

    // findPath is a recursive method to identify the shortest path based on the data in
    // HashSet<PathLeg> pairs
    Path currentPath = new Path(start, end);
    findPath(start, end, currentPath);

    // Add path to shortestPaths HashSet for future use
    shortestPaths.put(startEndString, lowPath);

    return lowPath;
  }

  private void mapPairs(String start) throws DataValidationException {
    // Add starting location to "seen" HashSet to avoid graph cycles when mapping pairs
    seen.add(start);

    // Retrieve starting location's neighboring facilities
    HashMap<String, Integer> facilityNeighbors = FacilityManager.getInstance().getFacilityNeighbors(start);

    // Iterate over facility neighbors and recursively map neighbor facility's pairs
    for (Entry<String, Integer> neighbor : facilityNeighbors.entrySet()) {
      String neighborFacilityLocation = neighbor.getKey();
      Integer neighborFacilityDistance = neighbor.getValue();

      PathLeg newPathLeg = new PathLeg(start, neighborFacilityLocation, neighborFacilityDistance);
      pairs.add(newPathLeg);

      // If "neighbor" is not in the "seen" set, recursively call "mapPairs" with neighbor node
      if (!seen.contains(neighborFacilityLocation)) {
        mapPairs(neighborFacilityLocation);
      }
    }
  }

  private void findPath(String start, String end, Path currentPath) throws DataValidationException {

    // If start == end, then the method should have recursively completed the current path
    if (start.equals(end)) {

      // If lowPath has not been previously set, current path is the first (and in turn, the
      // shortest) that has been encountered during processing
      if (lowPath.getTotalPathDistance() == 0) {
        lowPath = currentPath;

        // Else if current path is shorter than lowPath, set current path to lowPath
      } else if (currentPath.getTotalPathDistance() < lowPath.getTotalPathDistance()) {
        lowPath = currentPath;
      }


    } else {

      // Else, current path is not yet complete.

      HashSet<PathLeg> fromHere = new HashSet<PathLeg>();
      List<PathLeg> pathLegList = new ArrayList<PathLeg>(pairs);

      // Iterate over pairs HashSet and generate a set of PathLegs that start at "start"
      for (PathLeg pathLeg : pathLegList) {
        if (pathLeg.getStart().equals(start)) {
          fromHere.add(pathLeg);
        }
      }

      // Iterate over fromHere HashSet
      pathLegList = new ArrayList<PathLeg>(fromHere);
      for (PathLeg pathLeg : pathLegList) {

        // Does currentPath contain the destination in the PathLeg?
        // If not, copy the current path, add the new PathLeg, and recursively process the new path
        if (!currentPath.doesPathContainLegWithStart(pathLeg.getEnd())) {
          Path newPath = currentPath.getCopy();
          newPath.addPathLeg(pathLeg);
          findPath(pathLeg.getEnd(), end, newPath);
        }
      }
    }

  }

}
