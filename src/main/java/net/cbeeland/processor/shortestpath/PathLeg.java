package net.cbeeland.processor.shortestpath;

import net.cbeeland.exception.DataValidationException;
import net.cbeeland.util.InputValidator;

public class PathLeg {
  private String start;
  private String end;
  private Integer distance;

  public PathLeg(String newStart, String newEnd, int newDistance) throws DataValidationException {
    InputValidator.validateStringNotNullOrEmpty(newStart, "newStart", "PathLeg", "Constructor(String newStart, String newEnd, int newDistance)");
    InputValidator.validateStringNotNullOrEmpty(newEnd, "newEnd", "PathLeg", "Constructor(String newStart, String newEnd, int newDistance)");
    InputValidator.validatePositiveInteger(newDistance, "newDistance", "PathLeg", "Constructor(String newStart, String newEnd, int newDistance)");

    start = newStart;
    end = newEnd;
    distance = new Integer(newDistance);
  }

  public String getStart() {
    return start;
  }

  public String getEnd() {
    return end;
  }

  public int getDistance() {
    return distance;
  }

  public PathLeg getCopy() {
    try {
      return new PathLeg(start, end, distance);
    } catch (DataValidationException e) {
      System.out.println("ERROR: Unexpected DataValidationException occurred in PathLeg.getCopy()");
      e.printStackTrace();
    }
    return null;
  }

  public String toString() {
    return "Path Leg: [start=" + start + ", end=" + end + ", distance=" + distance + "]";
  }
}
