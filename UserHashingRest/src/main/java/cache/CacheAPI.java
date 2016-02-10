package cache;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.constructs.blocking.CacheEntryFactory;
import net.sf.ehcache.constructs.blocking.SelfPopulatingCache;

public class CacheAPI<K, V>{

    // The cache.
    private final Ehcache cache;

    private CacheAPI(final Ehcache cache, final String name) {
        // Plain ordinary.
        this.cache = cache;
    }

    public CacheAPI(final String name) {
        this(CacheManager.getInstance().addCacheIfAbsent(name), name);
    }

    public CacheAPI(final String name, CacheEntryFactory factory) {
        // Wrap it with a factory.
        this(new SelfPopulatingCache(CacheManager.getInstance().addCacheIfAbsent(name), factory), name);
    }

    public void put(final K key, final V value) {
        cache.put(new Element(key, value));
    }

    @SuppressWarnings("unchecked")
    public V get(final K key) {
        final Element element = cache.get(key);
        if (element != null) {
            return (V) element.getObjectValue();
        }
        return null;
    }

    public void remove(final K key) {
        cache.remove(key);
    }

    public void clear() {
        cache.removeAll();
    }

    public boolean expired(final K key) {
        boolean expired = true;
        // Do a quiet get so we don't change the last access time.
        final Element element = cache.getQuiet(key);
        if (element != null) {
            expired = cache.isExpired(element);
        }
        return expired;
    }

    public Ehcache getCache() {
        return cache;
    }
}
