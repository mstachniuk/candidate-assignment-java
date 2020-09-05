package ch.aaap.assignment.model.impl;

import java.util.HashSet;
import java.util.Set;

import ch.aaap.assignment.model.Canton;
import ch.aaap.assignment.model.District;
import ch.aaap.assignment.model.Model;
import ch.aaap.assignment.model.PoliticalCommunity;
import ch.aaap.assignment.model.PostalCommunity;
import ch.aaap.assignment.raw.CSVPoliticalCommunity;
import ch.aaap.assignment.raw.CSVPostalCommunity;

public class ModelFactory {

	public Model createModel(Set<CSVPoliticalCommunity> csvPoliticalCommunities,
	                         Set<CSVPostalCommunity> csvPostalCommunities) {
		Set<PoliticalCommunity> politicalCommunities = new HashSet<>();
		Set<Canton> cantons = new HashSet<>();
		Set<District> districts = new HashSet<>();
		csvPoliticalCommunities.forEach(element -> {
			PoliticalCommunity politicalCommunity = createPoliticalCommunity(element);
			politicalCommunities.add(politicalCommunity);

			Canton canton = createCanton(element);
			cantons.add(canton);

			District district = createDistrict(element);
			districts.add(district);
		});

		Set<PostalCommunity> postalCommunities = new HashSet<>();
		csvPostalCommunities.forEach(element -> {
			PostalCommunity postalCommunity = createPostalCommunity(element);
			postalCommunities.add(postalCommunity);
		});

		return ModelImpl.builder()
				.politicalCommunities(politicalCommunities)
				.postalCommunities(postalCommunities)
				.cantons(cantons)
				.districts(districts)
				.build();
	}

	private PostalCommunity createPostalCommunity(CSVPostalCommunity element) {
		return PostalCommunityImpl.builder()
						.zipCode(element.getZipCode())
						.zipCodeAddition(element.getZipCodeAddition())
						.name(element.getName())
						// TODO canton, politicalCommunities
						.build();
	}

	private District createDistrict(CSVPoliticalCommunity element) {
		return DistrictImpl.builder()
						.number(element.getDistrictNumber())
						.name(element.getDistrictName())
						.build();
	}

	private Canton createCanton(CSVPoliticalCommunity element) {
		return CantonImpl.builder()
						.code(element.getCantonCode())
						.name(element.getCantonName())
						.build();
	}

	private PoliticalCommunity createPoliticalCommunity(CSVPoliticalCommunity element) {
		return PoliticalCommunityImpl.builder()
						.number(element.getNumber())
						.name(element.getName())
						.shortName(element.getShortName())
						.lastUpdate(element.getLastUpdate())
						.build();
	}
}
