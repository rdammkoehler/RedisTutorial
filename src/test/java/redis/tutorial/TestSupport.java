package redis.tutorial;

public class TestSupport {
  static void clearAll() {
    new RedisClient().getConnection().sync().del(new RedisClient().getConnection().sync().keys("*").toArray(new String[]{}));
  }
}
