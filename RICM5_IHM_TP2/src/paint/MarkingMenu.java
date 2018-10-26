package paint;

public class MarkingMenu {

	// List of the elements' names in the menu
	private String[] listLabels;
	// True is the MarkingManu is the first displayed
	private boolean first;

	/**
	 * Constructor of MarkingMenu
	 * 
	 * @param listTools
	 *            List of the tools' names
	 */
	public MarkingMenu(String[] listLabels, boolean first) {
		this.listLabels = listLabels;
		this.first = first;
	}

	/**
	 * Getter of the listTools
	 * 
	 * @return listTools
	 */
	public String[] getlistLabels() {
		return listLabels;
	}

	/**
	 * Getter of first
	 * 
	 * @return first
	 */
	public boolean isfirst() {
		return first;
	}

}
