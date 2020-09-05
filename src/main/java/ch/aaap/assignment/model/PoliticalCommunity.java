package ch.aaap.assignment.model;

import java.time.LocalDate;

public interface PoliticalCommunity {

  String getNumber();

  String getName();

  String getShortName();

  LocalDate getLastUpdate();

  // TODO add more features here representing the relations
}
