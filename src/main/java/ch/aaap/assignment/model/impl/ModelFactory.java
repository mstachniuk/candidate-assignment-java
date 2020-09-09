package ch.aaap.assignment.model.impl;

import ch.aaap.assignment.model.Canton;
import ch.aaap.assignment.model.District;
import ch.aaap.assignment.model.Model;
import ch.aaap.assignment.model.PoliticalCommunity;
import ch.aaap.assignment.model.PostalCommunity;
import ch.aaap.assignment.raw.CsvPoliticalCommunity;
import ch.aaap.assignment.raw.CsvPostalCommunity;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.apache.commons.lang3.tuple.Pair;

public class ModelFactory {

  /**
   * Creates the whole model based on arguments.
   *
   * @param csvPoliticalCommunities Political Communities
   * @param csvPostalCommunities    Postal Communities
   * @return model
   */
  public Model createModel(Set<CsvPoliticalCommunity> csvPoliticalCommunities,
      Set<CsvPostalCommunity> csvPostalCommunities) {
    Set<PoliticalCommunity> politicalCommunities = new HashSet<>();
    Map<String, Canton> cantons = new HashMap<>();
    Map<String, District> districts = new HashMap<>();
    csvPoliticalCommunities.forEach(element -> {
      Canton canton = getOrCreateCanton(cantons, element);

      District district = getOrCreateDistrict(districts, element, canton);

      PoliticalCommunity politicalCommunity = createPoliticalCommunity(element, district);
      politicalCommunities.add(politicalCommunity);
    });

    Map<Pair<String, String>, PostalCommunity> postalCommunities = new HashMap<>();
    csvPostalCommunities.forEach(element -> {
      updateOrCreatePostalCommunity(postalCommunities, element, politicalCommunities);
    });

    return ModelImpl.builder()
        .politicalCommunities(politicalCommunities)
        .postalCommunities(new HashSet<>(postalCommunities.values()))
        .cantons(new HashSet<>(cantons.values()))
        .districts(new HashSet<>(districts.values()))
        .build();
  }

  private Canton getOrCreateCanton(Map<String, Canton> cantons, CsvPoliticalCommunity element) {
    Canton canton = cantons.get(element.getCantonCode());
    if (canton == null) {
      canton = createCanton(element);
      cantons.put(canton.getCode(), canton);
    }
    return canton;
  }

  private District getOrCreateDistrict(Map<String, District> districts,
      CsvPoliticalCommunity element, Canton canton) {
    District district = districts.get(element.getDistrictNumber());
    if (district == null) {
      district = createDistrict(element, canton);
      districts.put(district.getNumber(), district);
      canton.addDistrict(district);
    }
    return district;
  }

  private void updateOrCreatePostalCommunity(
      Map<Pair<String, String>, PostalCommunity> postalCommunities,
      CsvPostalCommunity element,
      Set<PoliticalCommunity> politicalCommunities) {
    String zipCode = element.getZipCode();
    String zipCodeAddition = element.getZipCodeAddition();
    Pair<String, String> key = Pair.of(zipCode, zipCodeAddition);
    PostalCommunity postalCommunity = postalCommunities.get(key);
    if (postalCommunity == null) {
      postalCommunity = createPostalCommunity(element);
      postalCommunities.put(key, postalCommunity);
    }

    String politicalCommunityNumber = element.getPoliticalCommunityNumber();
    Optional<PoliticalCommunity> politicalCommunity = politicalCommunities.stream()
        .filter(pc -> pc.getNumber().equals(politicalCommunityNumber))
        .findFirst();
    postalCommunity.addPoliticalCommunity(politicalCommunity.get());
  }

  private PostalCommunity createPostalCommunity(CsvPostalCommunity element) {
    return PostalCommunityImpl.builder()
        .zipCode(element.getZipCode())
        .zipCodeAddition(element.getZipCodeAddition())
        .name(element.getName())
        .build();
  }

  private District createDistrict(CsvPoliticalCommunity element, Canton canton) {

    return DistrictImpl.builder()
        .number(element.getDistrictNumber())
        .name(element.getDistrictName())
        .canton(canton)
        .build();
  }

  private Canton createCanton(CsvPoliticalCommunity element) {
    return CantonImpl.builder()
        .code(element.getCantonCode())
        .name(element.getCantonName())
        .build();
  }

  private PoliticalCommunity createPoliticalCommunity(CsvPoliticalCommunity element,
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
