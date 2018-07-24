package redis.tutorial;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MusicSearchTest {

  public static final String ARTIST = "Artist";
  public static final String DRUMMER = "Drummer";
  public static final String ELTON_JOHN = "Elton John";
  public static final ImmutablePair<String, String> ARTIST_ELTON_JOHN = new ImmutablePair<>(ARTIST, ELTON_JOHN);
  private final MusicSearchClient musicSearchClient = new MusicSearchClient();

  @BeforeEach
  void beforeEach() {
    TestSupport.clearAll();
    MusicSink sink = new MusicSink();
    sink.write(ARTIST_ELTON_JOHN);
  }

  @Test
  void itRequiresASearchString() {
    assertThrows(IllegalArgumentException.class, () -> musicSearchClient.find(null));
  }

  @Test
  void itReturnsAnEmptyNodeIfThereIsntAMatch() {
    List<Pair<String, String>> result = musicSearchClient.find(DRUMMER);

    assertThat(result, is(empty()));
  }

  @Test
  void itReturnsTheMatchingRecordsWhenThereIsAMatchInTheKey() {
    List<Pair<String, String>> result = musicSearchClient.find(ARTIST);

    assertThat(result, contains(ARTIST_ELTON_JOHN));
  }

}
