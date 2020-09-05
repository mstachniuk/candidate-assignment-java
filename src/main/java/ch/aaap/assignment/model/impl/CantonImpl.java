package ch.aaap.assignment.model.impl;

import ch.aaap.assignment.model.Canton;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CantonImpl implements Canton {

	private String code;
	private String name;
}
