package ch.aaap.assignment.model.impl;

import java.time.LocalDate;

import ch.aaap.assignment.model.PoliticalCommunity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PoliticalCommunityImpl implements PoliticalCommunity {

	private String number;
	private String name;
	private String shortName;
	private LocalDate lastUpdate;
}
