package plugin.google.maps;

import android.util.LruCache;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ObjectCache {
  // Marker/map objects are added and removed from both the UI and Cordova
  // worker threads. Keep key-set operations (including toArray snapshots)
  // synchronized so cleanup cannot race with a mutation.
  public final Set<String> keys = Collections.synchronizedSet(new HashSet<String>());
  private LruCache<String, Object> objects = new LruCache<String, Object>(10000);

  public boolean containsKey(String key) {
    return keys.contains(key);
  }

  public void put(String key, Object object) {
    keys.add(key);
    objects.put(key, object);
  }

  public Object remove(String key) {
    keys.remove(key);
    return objects.remove(key);
  }

  public Object get(String key) {
    return objects.get(key);
  }

  public int size() {
    return objects.size();
  }


  public void clear() {
    synchronized (keys) {
      for (String key : keys) {
        objects.remove(key);
      }
      keys.clear();
    }
  }

  public void destroy() {
    objects.evictAll();
  }


}
