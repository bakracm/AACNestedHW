package edu.grinnell.csc207.util;

import static java.lang.reflect.Array.newInstance;

/**
 * A basic implementation of Associative Arrays with keys of type K
 * and values of type V. Associative Arrays store key/value pairs
 * and permit you to look up values by key.
 *
 * @param <K> the key type
 * @param <V> the value type
 *
 * @author Samuel A. Rebelsky
 * @author Mina Bakrac
 */
public class AssociativeArray<K, V> {
  // +-----------+---------------------------------------------------
  // | Constants |
  // +-----------+

  /**
   * The default capacity of the initial array.
   */
  static final int DEFAULT_CAPACITY = 16;

  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The size of the associative array (the number of key/value pairs).
   */
  int size;

  /**
   * The array of key/value pairs.
   */
  KVPair<K, V> pairs[];

  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Create a new, empty associative array.
   */
  @SuppressWarnings({ "unchecked" })
  public AssociativeArray() {
    // Creating new arrays is sometimes a PITN.
    this.pairs = (KVPair<K, V>[]) newInstance((new KVPair<K, V>()).getClass(),
        DEFAULT_CAPACITY);
    this.size = 0;
  } // AssociativeArray()

  // +------------------+--------------------------------------------
  // | Standard Methods |
  // +------------------+

  /**
   * Create a copy of this AssociativeArray.
   *
   * @return a new copy of the array
   */
  public AssociativeArray<K, V> clone() {
    AssociativeArray<K, V> newArr = new AssociativeArray<>();
    newArr.size = 0;
    // Loops through the array, at each index assigns K,V value of original array at that index
    for (int i = 0; i < this.size; i++) {
      try {
        newArr.set(this.pairs[i].key, this.pairs[i].val);
      } catch (Exception e) {
      } // try/catch
    } // for
    return newArr;
  } // clone()

  /**
   * Convert the array to a string.
   *
   * @return a string of the form "{Key0:Value0, Key1:Value1, ... KeyN:ValueN}"
   */
  public String toString() {
    String arrToStr = new String();
    arrToStr = "{";
    for (int i = 0; i < (this.size - 1); i++) {
      arrToStr = arrToStr.concat(this.pairs[i].key + ":" + this.pairs[i].val + ", ");
    } // for
    arrToStr = arrToStr.concat(this.pairs[size - 1].key + ":" + this.pairs[size - 1].val + "}");
    return arrToStr;
  } // toString()

  // +----------------+----------------------------------------------
  // | Public Methods |
  // +----------------+

  /**
   * Set the value associated with key to value. Future calls to
   * get(key) will return value.
   *
   * @throws NullKeyException
   *   If the client provides a null key.
   * @param key
   *   The key whose value we are seeting.
   *
   * @param value
   *   The value of that key.
   */
  public void set(K key, V value) throws NullKeyException {
    if (key == null) {
      throw new NullKeyException();
    } // if
    try {
      // Assigns val to index of key
      int j = this.find(key);
      this.pairs[j].val = value;
    } catch (Exception e) {
      // if size of the array is at capacity, expand
      if (this.size == this.pairs.length) {
        this.expand();
      } // if
      // Add pair to the end of array
      this.pairs[size] = new KVPair<>(key, value);
      this.size++;
    } // try/catch
  } // set(K,V)

  /**
   * Get the value associated with key.
   *
   * @param key
   *   A key
   *
   * @throws KeyNotFoundException
   *   when the key is null or does not appear in the associative array.
   *
   * @return value.
   */
  public V get(K key) throws KeyNotFoundException {
    try {
      // Finds index of key and returns value at that index
      int j = this.find(key);
      return this.pairs[j].val;
    } catch (Exception e) {
      throw new KeyNotFoundException();
    } // try/catch
  } // get(K)

  /**
   * Determine if key appears in the associative array. Should
   * return false for the null key.
   *
   * @param key
   *  A key.
   *
   * @return true for key present in array.
   */
  public boolean hasKey(K key) {
    // Loops through array. If key matches a pair, returns true
    for (int i = 0; i < this.size; i++) {
      if (this.pairs[i].key.equals(key)) {
        return true;
      } // if
    } // for
    return false;
  } // hasKey(K)

  /**
   * Remove the key/value pair associated with a key. Future calls
   * to get(key) will throw an exception. If the key does not appear
   * in the associative array, does nothing.
   *
   * @param key
   *  A key
   */
  public void remove(K key) {
    try {
      int i = this.find(key);
      // Loops through pairs starting at index of key we're removing
      for (int j = i; j < (this.size - 1); j++) {
        // Shifts each pair up by one, overwriting the key being removed
        this.pairs[j] = this.pairs[j + 1];
      } // for
      this.size--;
      // Sets last index to null
      this.pairs[size] = null;
    } catch (KeyNotFoundException e) {
      return;
    } // try/catch
  } // remove(K)

  /**
   * Determine how many key/value pairs are in the associative array.
   *
   * @return size of the associative array (filled by key/value pairs)
   */
  public int size() {
    return this.size;
  } // size()

  public String[] getKeys() {
    String[] keys = new String[this.size];
    for (int i = 0; i < this.size; i++) {
      keys[i] = this.pairs[i].key.toString();
    } // for
    return keys;
  } // getKeys()

  // +-----------------+---------------------------------------------
  // | Private Methods |
  // +-----------------+

  /**
   * Expand the underlying array.
   */
  void expand() {
    this.pairs = java.util.Arrays.copyOf(this.pairs, this.pairs.length * 2);
  } // expand()

  /**
   * Find the index of the first entry in `pairs` that contains key.
   * If no such entry is found, throws an exception.
   *
   * @param key
   *   The key of the entry.
   *
   * @throws KeyNotFoundException
   *   If the key does not appear in the associative array.
   *
   * @return index of the key.
   */
  int find(K key) throws KeyNotFoundException {
    if (this.hasKey(key)) {
      // Loops through array looking for index of key
      for (int i = 0; i < this.size; i++) {
        if (this.pairs[i].key.equals(key)) {
          return i;
        } // if
      } // for
    } // if
    throw new KeyNotFoundException();
  } // find(K)

} // class AssociativeArray

