package paint;


public class MarkingMenu {
	
	// List of the elements' names in the menu
	private String[]  listTools;
	
	/**
	 * Constructor of MarkingMenu
	 * @param listTools List of the tools' names
	 */
	public MarkingMenu(String[] listTools){
		this.listTools = listTools;
	}
	
	/**
	 * Getter of the listTools
	 * @return listTools
	 */
	public String[] getlistTools() {
		return listTools;
	}

}
