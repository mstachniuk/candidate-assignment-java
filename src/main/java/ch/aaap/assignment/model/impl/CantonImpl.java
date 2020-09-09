package ch.aaap.assignment.model.impl;

import ch.aaap.assignment.model.Canton;
import ch.aaap.assignment.model.District;
import java.util.HashSet;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CantonImpl implements Canton {

  private String code;
  private String name;
  private Set<District> districts;

  @Override
  public void addDistrict(District district) {
    if (districts == null) {
      districts = new HashSet<>();
    }
    districts.add(district);
  }

}
