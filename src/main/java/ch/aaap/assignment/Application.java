package ch.aaap.assignment;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import ch.aaap.assignment.model.Canton;
import ch.aaap.assignment.model.District;
import ch.aaap.assignment.model.Model;
import ch.aaap.assignment.model.PoliticalCommunity;
import ch.aaap.assignment.model.PostalCommunity;
import ch.aaap.assignment.model.impl.ModelFactory;
import ch.aaap.assignment.raw.CSVPoliticalCommunity;
import ch.aaap.assignment.raw.CSVPostalCommunity;
import ch.aaap.assignment.raw.CSVUtil;

public class Application {

  private Model model = null;

  public static void main(String[] args) {
    new Application();
  }

  public Application() {
    initModel();
  }

  /** Reads the CSVs and initializes a in memory model */
  private void initModel() {
    Set<CSVPoliticalCommunity> csvPoliticalCommunities = CSVUtil.getPoliticalCommunities();
    Set<CSVPostalCommunity> csvPostalCommunities = CSVUtil.getPostalCommunities();

    ModelFactory modelFactory = new ModelFactory();
    model = modelFactory.createModel(csvPoliticalCommunities, csvPostalCommunities);
  }
  /** @return model */
  public Model getModel() {
    return model;
  }

  /**
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
            .orElseThrow(() -> new IllegalArgumentException("Canton with code " + cantonCode + " doesn't exist"));
  }

  /**
   * @param cantonCode of a canton (e.g. ZH)
   * @return amount of districts in given canton
   */
  public long getAmountOfDistrictsInCanton(String cantonCode) {
    return getCantonByCodeOrThrowException(cantonCode)
            .getDistricts()
            .size();
  }

  /**
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
            .orElseThrow(() -> new IllegalArgumentException("Distric with code " + districtNumber + " doesn't exist"));
  }

  /**
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
   * @param postalCommunityName name
   * @return lastUpdate of the political community by a given postal community name
   */
  public LocalDate getLastUpdateOfPoliticalCommunityByPostalCommunityName(
      String postalCommunityName) {
    // TODO implementation
    throw new RuntimeException("Not yet implemented");
  }

  /**
   * https://de.wikipedia.org/wiki/Kanton_(Schweiz)
   *
   * @return amount of canton
   */
  public long getAmountOfCantons() {
    // TODO implementation
    return model.getCantons().size();
  }

  /**
   * https://de.wikipedia.org/wiki/Kommunanz
   *
   * @return amount of political communities without postal communities
   */
  public long getAmountOfPoliticalCommunityWithoutPostalCommunities() {
    // TODO implementation
    throw new RuntimeException("Not yet implemented");
  }
}
