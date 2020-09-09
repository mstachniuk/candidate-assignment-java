package ch.aaap.assignment.raw;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

/**
 * This is a helper class to read the provided CSV.
 *
 * <p>You don't have adapt anything within this class!
 */
public class CsvUtil {

  private static final String POLITICAL_COMMUNITY_FILE = "/GDE_from_be-b-00.04-agv-01.xlsx.csv";
  private static final String POSTAL_COMMUNITY_FILE = "/PLZ6_from_do-t-09.02-gwr-37.xlsx.csv";

  private CsvUtil() {
  }

  /**
   * Returns Political Communities.
   *
   * @return Political Communities
   */
  public static Set<CsvPoliticalCommunity> getPoliticalCommunities() {
    try {
      InputStream is = CsvUtil.class.getResourceAsStream(POLITICAL_COMMUNITY_FILE);
      Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
      CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader());
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

      Set<CsvPoliticalCommunity> models = new HashSet<>();

      for (final CSVRecord record : parser) {
        CsvPoliticalCommunity row =
            CsvPoliticalCommunity.builder()
                .number(record.get("GDENR"))
                .name(record.get("GDENAME"))
                .shortName(record.get("GDENAMK"))
                .cantonCode(record.get("GDEKT"))
                .cantonName(record.get("GDEKTNA"))
                .districtNumber(record.get("GDEBZNR"))
                .districtName(record.get("GDEBZNA"))
                .lastUpdate(LocalDate.parse(record.get("GDEMUTDAT"), formatter))
                .build();
        models.add(row);
      }
      parser.close();
      return models;

    } catch (IOException e) {
      throw new RuntimeException("Could not parse political communities csv", e);
    }
  }

  /**
   * Returns Postal Communities.
   *
   * @return Postal Communities
   */
  public static Set<CsvPostalCommunity> getPostalCommunities() {
    try {
      InputStream is = CsvUtil.class.getResourceAsStream(POSTAL_COMMUNITY_FILE);
      Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
      CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader());
      Set<CsvPostalCommunity> models = new HashSet<>();
      for (final CSVRecord record : parser) {
        CsvPostalCommunity row =
            CsvPostalCommunity.builder()
                .zipCode(record.get("PLZ4"))
                .zipCodeAddition(record.get("PLZZ"))
                .name(record.get("PLZNAMK"))
                .cantonCode(record.get("KTKZ"))
                .politicalCommunityShortName(record.get("GDENAMK"))
                .politicalCommunityNumber(record.get("GDENR"))
                .build();

        models.add(row);
      }
      parser.close();
      return models;
    } catch (IOException e) {
      throw new RuntimeException("could not parse postal community csv", e);
    }
  }
}
