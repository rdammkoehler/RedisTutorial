package redis.tutorial;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public class MusicSearchClient extends RedisClient {

  public final List<Pair<String, String>> find(String key) {
    if (isEmpty(key)) {
      throw new IllegalArgumentException("A search path is required");
    }
    ArrayList<Pair<String, String>> pairs = new ArrayList<>();
    String value = getConnection().sync().get(key);
    if (!isEmpty(value)) {
      pairs.add(new ImmutablePair<>(key, value));
    }
    return pairs;
  }
}
