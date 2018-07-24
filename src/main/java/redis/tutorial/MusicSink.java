package redis.tutorial;

import io.lettuce.core.api.StatefulRedisConnection;
import org.apache.commons.lang3.tuple.Pair;

public class MusicSink extends RedisClient {
  public void write(Pair<String, String>... pairs) {
    StatefulRedisConnection<String, String> conn = getConnection();
    for (Pair<String, String> pair : pairs) {
      conn.sync().set(pair.getKey(), pair.getValue());
    }
  }
}
