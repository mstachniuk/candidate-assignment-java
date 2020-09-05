package ch.aaap.assignment.model.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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
		Map<String, Canton> cantons = new HashMap<>();
		Map<String, District> districts = new HashMap<>();
		csvPoliticalCommunities.forEach(element -> {
			Canton canton = getOrCreateCanton(cantons, element);

			District district = getOrCreateDistrict(districts, element, canton);

			PoliticalCommunity politicalCommunity = createPoliticalCommunity(element, district);
			politicalCommunities.add(politicalCommunity);
		});

		Set<PostalCommunity> postalCommunities = new HashSet<>();
		csvPostalCommunities.forEach(element -> {
			PostalCommunity postalCommunity = createPostalCommunity(element);
			postalCommunities.add(postalCommunity);
		});

		return ModelImpl.builder()
				.politicalCommunities(politicalCommunities)
				.postalCommunities(postalCommunities)
				.cantons(new HashSet<>(cantons.values()))
				.districts(new HashSet<>(districts.values()))
				.build();
	}

	private Canton getOrCreateCanton(Map<String,Canton> cantons, CSVPoliticalCommunity element) {
		Canton canton = cantons.get(element.getCantonCode());
		if (canton == null) {
			canton = createCanton(element);
			cantons.put(canton.getCode(), canton);
		}
		return canton;
	}

	private District getOrCreateDistrict(Map<String,District> districts, CSVPoliticalCommunity element, Canton canton) {
		District district = districts.get(element.getDistrictNumber());
		if (district == null) {
			district = createDistrict(element, canton);
			districts.put(district.getNumber(), district);
			canton.addDistrict(district);
		}
		return district;
	}

	private PostalCommunity createPostalCommunity(CSVPostalCommunity element) {
		return PostalCommunityImpl.builder()
						.zipCode(element.getZipCode())
						.zipCodeAddition(element.getZipCodeAddition())
						.name(element.getName())
						// TODO canton, politicalCommunities
						.build();
	}

	private District createDistrict(CSVPoliticalCommunity element, Canton canton) {

		return DistrictImpl.builder()
						.number(element.getDistrictNumber())
						.name(element.getDistrictName())
						.canton(canton)
						.build();
	}

	private Canton createCanton(CSVPoliticalCommunity element) {
		return CantonImpl.builder()
						.code(element.getCantonCode())
						.name(element.getCantonName())
						.build();
	}

	private PoliticalCommunity createPoliticalCommunity(CSVPoliticalCommunity element,
	                                                    District district) {
		return PoliticalCommunityImpl.builder()
						.number(element.getNumber())
						.name(element.getName())
						.shortName(element.getShortName())
						.lastUpdate(element.getLastUpdate())
						.district(district)
						.build();
	}
}
