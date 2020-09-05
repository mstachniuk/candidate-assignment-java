package ch.aaap.assignment;

import ch.aaap.assignment.model.Canton;
import ch.aaap.assignment.model.District;
import ch.aaap.assignment.model.Model;
import ch.aaap.assignment.model.PoliticalCommunity;
import ch.aaap.assignment.model.PostalCommunity;
import ch.aaap.assignment.model.impl.CantonImpl;
import ch.aaap.assignment.model.impl.DistrictImpl;
import ch.aaap.assignment.model.impl.ModelFactory;
import ch.aaap.assignment.model.impl.ModelImpl;
import ch.aaap.assignment.model.impl.PoliticalCommunityImpl;
import ch.aaap.assignment.model.impl.PostalCommunityImpl;
import ch.aaap.assignment.raw.CSVPoliticalCommunity;
import ch.aaap.assignment.raw.CSVPostalCommunity;
import ch.aaap.assignment.raw.CSVUtil;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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
    long result = model.getPoliticalCommunities().stream()
            .map(PoliticalCommunity::getCanton)
            .map(Canton::getCode)
            .filter(code -> code.equals(cantonCode))
            .count();
    if (result == 0) {
      checkIfCantonExist(cantonCode);
    }
    return result;
  }

  private void checkIfCantonExist(String cantonCode) {
    model.getCantons().stream()
            .map(Canton::getCode)
            .filter(code -> code.equals(cantonCode))
            .findAny()
            .orElseThrow(() -> new IllegalArgumentException("Canton with code " + cantonCode + " doesn't exist"));
  }

  /**
   * @param cantonCode of a canton (e.g. ZH)
   * @return amount of districts in given canton
   */
  public long getAmountOfDistrictsInCanton(String cantonCode) {
    // TODO implementation
    throw new RuntimeException("Not yet implemented");
  }

  /**
   * @param districtNumber of a district (e.g. 101)
   * @return amount of districts in given canton
   */
  public long getAmountOfPoliticalCommunitiesInDistrict(String districtNumber) {
    // TODO implementation
    throw new RuntimeException("Not yet implemented");
  }

  /**
   * @param zipCode 4 digit zip code
   * @return district that belongs to specified zip code
   */
  public Set<String> getDistrictsForZipCode(String zipCode) {
    // TODO implementation
    throw new RuntimeException("Not yet implemented");
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
