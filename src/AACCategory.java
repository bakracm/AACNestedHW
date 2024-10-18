import java.util.NoSuchElementException;
import edu.grinnell.csc207.util.AssociativeArray;
import edu.grinnell.csc207.util.KeyNotFoundException;
import edu.grinnell.csc207.util.NullKeyException;

/**
 * Represents the mappings for a single category of items that should
 * be displayed
 * 
 * @author Catie Baker & Mina Bakrac
 *
 */
public class AACCategory implements AACPage {
  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The size of the category (the number of items pairs).
   */
  int size;

  /**
   * The array of key/value (location/text) pairs.
   */
  AssociativeArray<String, String> category;

  /**
   * The name of the category.
   */
  String name;

  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Creates a new empty category with the given name
   * @param name the name of the category
   */
  public AACCategory(String name) {
    this.category = new AssociativeArray<String, String>();
	  this.name = name;
	  this.size = 0;
  } // AACCategory(String)

  // +---------+-----------------------------------------------------
  // | Methods |
  // +---------+

  /**
   * Adds the image location, text pairing to the category
   * @param imageLoc the location of the image
   * @param text the text that image should speak
   */
  public void addItem(String imageLoc, String text) {
    try {
	  category.set(imageLoc, text);
	} catch (NullKeyException e) {
	} // try/catch
  } // addItem(String, String);

  /**
   * Returns an array of all the images in the category
   * @return the array of image locations; if there are no images,
   * it should return an empty array
   */
  public String[] getImageLocs() {
    return category.getKeys();
  } // getImageLocs()

  /**
   * Returns the name of the category
   * @return the name of the category
   */
  public String getCategory() {
	  return this.name;
  } // getCategory()

  /**
   * Returns the text associated with the given image in this category
   * @param imageLoc the location of the image
   * @return the text associated with the image
   * @throws NoSuchElementException if the image provided is not in the current
   * 		   category
   */
  public String select(String imageLoc) {
    try {
      return category.get(imageLoc);
    } catch (KeyNotFoundException e) {
      throw new NoSuchElementException();
    } // try/catch
  } // select(String)

  /**
   * Determines if the provided images is stored in the category
   * @param imageLoc the location of the category
   * @return true if it is in the category, false otherwise
   */
  public boolean hasImage(String imageLoc) {
	  return category.hasKey(imageLoc);
  } // hasImage(String)
} // class AACCategory
