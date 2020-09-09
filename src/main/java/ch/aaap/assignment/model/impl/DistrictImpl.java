package ch.aaap.assignment.model.impl;

import ch.aaap.assignment.model.Canton;
import ch.aaap.assignment.model.District;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DistrictImpl implements District {

  private String number;
  private String name;
  private Canton canton;
}
