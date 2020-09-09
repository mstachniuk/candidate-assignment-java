package ch.aaap.assignment.model.impl;

import ch.aaap.assignment.model.PoliticalCommunity;
import ch.aaap.assignment.model.PostalCommunity;
import java.util.HashSet;
import java.util.Set;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostalCommunityImpl implements PostalCommunity {

  private String zipCode;
  private String zipCodeAddition;
  private String name;
  private Set<PoliticalCommunity> politicalCommunities;

  @Override
  public void addPoliticalCommunity(PoliticalCommunity politicalCommunity) {
    if (politicalCommunities == null) {
      politicalCommunities = new HashSet<>();
    }
    politicalCommunities.add(politicalCommunity);
  }

}
