package ch.aaap.assignment.model;

import java.util.Set;

public interface PostalCommunity {

  String getZipCode();

  String getZipCodeAddition();

  String getName();

  Set<PoliticalCommunity> getPoliticalCommunities();

  void addPoliticalCommunity(PoliticalCommunity politicalCommunity);
  // TODO add more features here representing the relations
}
