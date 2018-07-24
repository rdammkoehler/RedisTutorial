package redis.tutorial;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class MusicSinkTest {

  private MusicSink sink = new MusicSink();

  @BeforeEach
  void beforeEach() {
    TestSupport.clearAll();
  }

  private Pair<String, String> getRedisRecord(String key) {
    String redisValue = new RedisClient().getConnection().sync().get(key);
    return new ImmutablePair<>(key, redisValue);
  }

  @Test
  void itSavesAKeyValuePair() {
    Pair<String, String> musician = new ImmutablePair<>("Guitarist", "Kerry King");

    sink.write(musician);

    assertThat(getRedisRecord(musician.getKey()), is(equalTo(musician)));
  }

  @Test
  void itSavesManyKeyValuePairs() {
    Pair<String, String> drummer = new ImmutablePair("Drummer", "Dave Lombardo");
    Pair<String, String> bassist = new ImmutablePair("Bassist", "Tony Araya");
    Pair<String, String> guitarist = new ImmutablePair("Guitarist", "Jeff Hanneman");

    sink.write(drummer, bassist, guitarist);

    assertThat(getRedisRecord(drummer.getKey()), is(equalTo(drummer)));
    assertThat(getRedisRecord(bassist.getKey()), is(equalTo(bassist)));
    assertThat(getRedisRecord(guitarist.getKey()), is(equalTo(guitarist)));
  }

  @Test
  void itOverwritesOldValuesWithNew() {
    Pair<String, String> guitarist0 = new ImmutablePair("Guitarist", "Jeff Hanneman");
    Pair<String, String> guitarist1 = new ImmutablePair("Guitarist", "Kerry King");

    sink.write(guitarist0, guitarist1);

    assertThat(getRedisRecord(guitarist1.getKey()), is(equalTo(guitarist1)));
  }
}
