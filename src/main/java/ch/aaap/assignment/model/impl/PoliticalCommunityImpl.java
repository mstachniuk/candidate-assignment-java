package ch.aaap.assignment.model.impl;

import java.time.LocalDate;

import ch.aaap.assignment.model.Canton;
import ch.aaap.assignment.model.District;
import ch.aaap.assignment.model.PoliticalCommunity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PoliticalCommunityImpl implements PoliticalCommunity {

	private String number;
	private String name;
	private String shortName;
	private LocalDate lastUpdate;
	private District district;

	@Override
	public Canton getCanton() {
		return district.getCanton();
	}
}
