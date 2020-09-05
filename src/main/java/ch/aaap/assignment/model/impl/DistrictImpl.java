package ch.aaap.assignment.model.impl;

import ch.aaap.assignment.model.District;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DistrictImpl implements District {

	private String number;
	private String name;
}
