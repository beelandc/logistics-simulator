package net.cbeeland.processor.shortestpath;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import net.cbeeland.domain.facility.FacilityManager;
import net.cbeeland.exception.DataValidationException;
import net.cbeeland.util.InputValidator;

public class Path {
  List<PathLeg> path;
  Integer totalPathDistance;
  String start;
  String end;
  HashSet<String> legStartSet;
  HashSet<String> legEndSet;

  public Path(String newStart, String newEnd) throws DataValidationException {
    InputValidator.validateStringNotNullOrEmpty(newStart, "newStart", "Path", "Constructor(String start, String end)");
    InputValidator.validateStringNotNullOrEmpty(newEnd, "newEnd", "Path", "Constructor(String start, String end)");
    start = newStart;
    end = newEnd;
    path = new ArrayList<PathLeg>();
    legStartSet = new HashSet<String>();
    legEndSet = new HashSet<String>();
    totalPathDistance = 0;
  }

  public String getStart() {
    return start;
  }

  public String getEnd() {
    return end;
  }

  public int getTotalPathDistance() {
    return totalPathDistance;
  }

  public void addPathLeg(PathLeg newLeg) throws DataValidationException {
    InputValidator.validateInstantiatedObject(newLeg, "newLeg", "Path", "addPathLeg(PathLeg newLeg)");
    totalPathDistance += newLeg.getDistance();
    legStartSet.add(newLeg.getStart());
    legEndSet.add(newLeg.getEnd());
    path.add(newLeg);
  }

  public boolean doesPathContainLegWithStart(String start) throws DataValidationException {
    InputValidator.validateStringNotNullOrEmpty(start, "start", "Path", "doesPathContainLegWithStart(String start)");
    return legStartSet.contains(start);
  }

  public boolean doesPathContainLegWithEnd(String end) throws DataValidationException {
    InputValidator.validateStringNotNullOrEmpty(end, "end", "Path", "doesPathContainLegWithEnd(String end)");
    return legStartSet.contains(end);
  }

  public double getTravelTimeInDays() {
    return (double) getTotalPathDistance() / (FacilityManager.getInstance().getDrivingHoursPerDay() * FacilityManager.getInstance().getAverageMilesPerHour());
  }

  public Path getCopy() {
    Path pathCopy = null;

    try {
      pathCopy = new Path(start, end);
      for (int i = 0; i < path.size(); i++) {
        pathCopy.addPathLeg(path.get(i).getCopy());
      }
    } catch (DataValidationException e) {
      System.out.println("ERROR: Unexpected DataValidationException occurred in Path.getCopy()");
      e.printStackTrace();
    }

    return pathCopy;
  }

  public List<PathLeg> getPathLegs() {
    List<PathLeg> pathLegList = new ArrayList<PathLeg>();
    for (PathLeg pathLeg : path) {
      pathLegList.add(pathLeg.getCopy());
    }
    return pathLegList;
  }

  public String toString() {
    StringBuilder pathDetails = new StringBuilder();
    pathDetails.append("Path: [");
    for (int i = 0; i < path.size(); i++) {
      PathLeg pathLeg = path.get(i);
      pathDetails.append(pathLeg.toString());

      if (i < (path.size() - 1)) {
        pathDetails.append(" --> ");
      }
    }
    pathDetails.append("]");

    return pathDetails.toString();
  }

}
