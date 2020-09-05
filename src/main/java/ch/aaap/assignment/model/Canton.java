package ch.aaap.assignment.model;

import java.util.Set;

public interface Canton {

  String getCode();

  String getName();

  Set<District> getDistricts();

  void addDistrict(District district);
  // TODO add more features here representing the relations
}
