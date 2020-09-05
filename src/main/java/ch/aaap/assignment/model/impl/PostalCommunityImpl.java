package ch.aaap.assignment.model.impl;

import ch.aaap.assignment.model.PostalCommunity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostalCommunityImpl implements PostalCommunity {

	private String zipCode;
	private String zipCodeAddition;
	private String name;
}
