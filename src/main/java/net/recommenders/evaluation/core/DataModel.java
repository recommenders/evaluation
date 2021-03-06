package net.recommenders.evaluation.core;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Alejandro, Alan
 */
public class DataModel<U, I> {

    private Map<U, Map<I, Double>> userItemPreferences;
    private Map<I, Map<U, Double>> itemUserPreferences;
    private Map<U, Map<I, Set<Long>>> userItemTimestamps;

    public DataModel() {
        this(new HashMap<U, Map<I, Double>>(), new HashMap<I, Map<U, Double>>(), new HashMap<U, Map<I, Set<Long>>>());
    }

    public DataModel(Map<U, Map<I, Double>> userItemPreferences, Map<I, Map<U, Double>> itemUserPreferences, Map<U, Map<I, Set<Long>>> userItemTimestamps) {
        this.userItemPreferences = userItemPreferences;
        this.itemUserPreferences = itemUserPreferences;
        this.userItemTimestamps = userItemTimestamps;
    }

    public Map<I, Map<U, Double>> getItemUserPreferences() {
        return itemUserPreferences;
    }

    public Map<U, Map<I, Double>> getUserItemPreferences() {
        return userItemPreferences;
    }

    public Map<U, Map<I, Set<Long>>> getUserItemTimestamps() {
        return userItemTimestamps;
    }

    public void addPreference(U u, I i, Double d) {
        // update direct map
        Map<I, Double> userPreferences = userItemPreferences.get(u);
        if (userPreferences == null) {
            userPreferences = new HashMap<I, Double>();
//            userPreferences = new FastMap<I, Double>();
            userItemPreferences.put(u, userPreferences);
        }
        Double preference = userPreferences.get(i);
        if (preference == null) {
            preference = 0.0;
        }
        preference += d;
        userPreferences.put(i, preference);
        // update inverse map
        Map<U, Double> itemPreferences = itemUserPreferences.get(i);
        if (itemPreferences == null) {
            itemPreferences = new HashMap<U, Double>();
//            itemPreferences = new FastMap<U, Double>();
            itemUserPreferences.put(i, itemPreferences);
        }
        itemPreferences.put(u, preference);
    }

    public void addTimestamp(U u, I i, Long t) {
        Map<I, Set<Long>> userTimestamps = userItemTimestamps.get(u);
        if (userTimestamps == null) {
            userTimestamps = new HashMap<I, Set<Long>>();
//            userTimestamps = new FastMap<I, Set<Long>>();
            userItemTimestamps.put(u, userTimestamps);
        }
        Set<Long> timestamps = userTimestamps.get(i);
        if (timestamps == null) {
            timestamps = new HashSet<Long>();
//            timestamps = new FastSet<Long>();
            userTimestamps.put(i, timestamps);
        }
        timestamps.add(t);
    }

    public Set<I> getItems() {
        return getItemUserPreferences().keySet();
    }

    public Set<U> getUsers() {
        return getUserItemPreferences().keySet();
    }

    public int getNumItems() {
        return getItems().size();
    }

    public int getNumUsers() {
        return getUsers().size();
    }

    public void clear() {
        userItemPreferences.clear();
        userItemTimestamps.clear();
        itemUserPreferences.clear();
    }
}
