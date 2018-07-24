package redis.tutorial;

import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;

public class RedisClient {
  private io.lettuce.core.RedisClient redisClient;
  private StatefulRedisConnection<String, String> connection;

  protected io.lettuce.core.RedisClient getRedisClient() {
    if (null == redisClient) {
      redisClient = io.lettuce.core.RedisClient.create(RedisURI.create("redis://localhost:6379"));
    }
    return redisClient;
  }

  public StatefulRedisConnection<String, String> getConnection() {
    if (null == connection) {
      connection = getRedisClient().connect();
    }
    return connection;
  }

  @Override
  public void finalize() {
    shutdown();
  }

  void shutdown() {
    if (null != connection) {
      connection.close();
    }
    if (null != redisClient) {
      redisClient.shutdown();
    }
  }

}
