package ch.aaap.assignment;

import ch.aaap.assignment.model.Canton;
import ch.aaap.assignment.model.District;
import ch.aaap.assignment.model.Model;
import ch.aaap.assignment.model.PoliticalCommunity;
import ch.aaap.assignment.model.PostalCommunity;
import ch.aaap.assignment.model.impl.ModelFactory;
import ch.aaap.assignment.raw.CsvPoliticalCommunity;
import ch.aaap.assignment.raw.CsvPostalCommunity;
import ch.aaap.assignment.raw.CsvUtil;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang3.tuple.Pair;

public class Application {

  private Model model = null;

  public static void main(String[] args) {
    Application app = new Application();
    Set<PoliticalCommunity> communities = app
        .getPoliticalCommunitiesWithoutPostalCommunities();
    System.out.println("Political Communities Without Postal Communities:");
    communities.forEach(pc -> System.out.println(pc.getName()));

    System.out.println("Canton name | Canton Code | number of political communities");
    app.getModel().getCantons().stream()
        .map(canton -> Pair
            .of(canton, app.getAmountOfPoliticalCommunitiesInCanton(canton.getCode())))
        .sorted(Comparator.comparing(Pair::getValue))
        .forEach(pair -> System.out.println(pair.getKey().getName() + " | "
        + pair.getKey().getCode() + " | " + pair.getValue()));

    System.out.println("Number of districts in cantons");
    app.getModel().getCantons().stream()
        .map(canton -> Pair.of(canton, canton.getDistricts().size()))
        .sorted(Comparator.comparing(Pair::getValue))
        .forEach(pair -> System.out.println(pair.getKey().getName() + " | " + pair.getValue()));
  }

  public Application() {
    initModel();
  }

  /**
   * Reads the CSVs and initializes a in memory model.
   */
  private void initModel() {
    Set<CsvPoliticalCommunity> csvPoliticalCommunities = CsvUtil.getPoliticalCommunities();
    Set<CsvPostalCommunity> csvPostalCommunities = CsvUtil.getPostalCommunities();

    ModelFactory modelFactory = new ModelFactory();
    model = modelFactory.createModel(csvPoliticalCommunities, csvPostalCommunities);
  }

  /**
   * Returns application model.
   * @return model
   */
  public Model getModel() {
    return model;
  }

  /**
   * Return amount of Political Communities in Canton.
   * @param cantonCode of a canton (e.g. ZH)
   * @return amount of political communities in given canton
   */
  public long getAmountOfPoliticalCommunitiesInCanton(String cantonCode) {
    Canton cantonByCode = getCantonByCodeOrThrowException(cantonCode);
    return model.getPoliticalCommunities().stream()
        .map(PoliticalCommunity::getCanton)
        .filter(canton -> canton.equals(cantonByCode))
        .count();
  }

  private Canton getCantonByCodeOrThrowException(String cantonCode) {
    return model.getCantons().stream()
        .filter(canton -> canton.getCode().equals(cantonCode))
        .findAny()
        .orElseThrow(() -> new IllegalArgumentException(
            "Canton with code " + cantonCode + " doesn't exist"));
  }

  /**
   * Return amount of Districts in Canton.
   * @param cantonCode of a canton (e.g. ZH)
   * @return amount of districts in given canton
   */
  public long getAmountOfDistrictsInCanton(String cantonCode) {
    return getCantonByCodeOrThrowException(cantonCode)
        .getDistricts()
        .size();
  }

  /**
   * Returns amount of Political Communities in District.
   * @param districtNumber of a district (e.g. 101)
   * @return amount of districts in given canton
   */
  public long getAmountOfPoliticalCommunitiesInDistrict(String districtNumber) {
    District districtByNumber = getDistrictByNumberOrThrowException(districtNumber);
    return model.getPoliticalCommunities().stream()
        .map(PoliticalCommunity::getDistrict)
        .filter(district -> district.equals(districtByNumber))
        .count();
  }

  private District getDistrictByNumberOrThrowException(String districtNumber) {
    return model.getDistricts().stream()
        .filter(district -> district.getNumber().equals(districtNumber))
        .findAny()
        .orElseThrow(() -> new IllegalArgumentException(
            "Distric with code " + districtNumber + " doesn't exist"));
  }

  /**
   * Return a District by Zip Code.
   * @param zipCode 4 digit zip code
   * @return districts that belongs to specified zip code
   */
  public Set<String> getDistrictsForZipCode(String zipCode) {
    return model.getPostalCommunities().stream()
        .filter(postalCommunity -> postalCommunity.getZipCode().equals(zipCode))
        .map(PostalCommunity::getPoliticalCommunities)
        .flatMap(Collection::stream)
        .map(PoliticalCommunity::getDistrict)
        .map(District::getName)
        .collect(Collectors.toSet());

  }

  /**
   * Returns last update of Political Community by Postal Community name.
   * @param postalCommunityName name
   * @return lastUpdate of the political community by a given postal community name
   */
  public LocalDate getLastUpdateOfPoliticalCommunityByPostalCommunityName(
      String postalCommunityName) {
    return model.getPostalCommunities().stream()
        .filter(postalCommunity -> postalCommunity.getName().equals(postalCommunityName))
        .map(PostalCommunity::getPoliticalCommunities)
        .flatMap(Collection::stream)
        .sorted(Comparator.comparing(PoliticalCommunity::getLastUpdate))
        .findFirst()
        .get()
        .getLastUpdate();
  }

  /**
   * https://de.wikipedia.org/wiki/Kanton_(Schweiz)
   *
   * @return amount of canton
   */
  public long getAmountOfCantons() {
    return model.getCantons().size();
  }

  /**
   * https://de.wikipedia.org/wiki/Kommunanz
   *
   * @return amount of political communities without postal communities
   */
  public long getAmountOfPoliticalCommunityWithoutPostalCommunities() {
    Set<PoliticalCommunity> politicalCommunitiesWithPostalCommunities = model.getPostalCommunities()
        .stream()
        .map(PostalCommunity::getPoliticalCommunities)
        .flatMap(Collection::stream)
        .collect(Collectors.toSet());
    return model.getPoliticalCommunities().stream()
        .filter(politicalCommunity -> !politicalCommunitiesWithPostalCommunities
            .contains(politicalCommunity))
        .count();
  }

  public Set<PoliticalCommunity> getPoliticalCommunitiesWithoutPostalCommunities() {
    Set<PoliticalCommunity> politicalCommunitiesWithPostalCommunities = model.getPostalCommunities()
        .stream()
        .map(PostalCommunity::getPoliticalCommunities)
        .flatMap(Collection::stream)
        .collect(Collectors.toSet());
    return model.getPoliticalCommunities().stream()
        .filter(politicalCommunity -> !politicalCommunitiesWithPostalCommunities
            .contains(politicalCommunity))
        .collect(Collectors.toSet());
  }
}
