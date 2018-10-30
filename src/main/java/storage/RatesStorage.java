package storage;

import data.CountryRate;

import java.util.Collection;
import java.util.List;

/**
 * Storage of country rates
 */
public interface RatesStorage {

    /**
     * Adds new {@link CountryRate} record into the storage
     * @param rate
     * @return true if country rate was added
     */
    boolean add(CountryRate rate);

    /**
     * Gets all country rates
     * @return stored country rates
     */
    Collection getRates();

    /**
     * Gets all country rates sorted by value of rate with given name
     * @param rateName
     * @param ascending
     * @return country rates (which contain rate with specified name) in required order
     */
    List<CountryRate> getRatesSortedBy(String rateName, boolean ascending);
}
