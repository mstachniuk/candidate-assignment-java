package ch.aaap.assignment.model.impl;

import ch.aaap.assignment.model.Canton;
import ch.aaap.assignment.model.District;
import ch.aaap.assignment.model.Model;
import ch.aaap.assignment.model.PoliticalCommunity;
import ch.aaap.assignment.model.PostalCommunity;
import java.util.Set;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ModelImpl implements Model {

  private Set<PoliticalCommunity> politicalCommunities;
  private Set<PostalCommunity> postalCommunities;
  private Set<Canton> cantons;
  private Set<District> districts;

}
