package paint;

import java.awt.Color;
import java.awt.Graphics;

import java.util.ArrayList;

public class MarkingMenuUI {

	MarkingMenu menu;

	// The diameter of the circular base
	private int diameter = 150;
	// The radius of the circular base
	private int radius = diameter / 2;
	// Position of the center of the menu
	private int x, y;

	/**
	 * Constructor of MarkingMenuUI
	 * 
	 * @param menu
	 *            The MarkingMenu corresponding to the UI
	 * @param x
	 *            abscissa of the menu's position
	 * @param y
	 *            ordinate of the menu's position
	 */
	public MarkingMenuUI(MarkingMenu menu, int x, int y) {
		this.menu = menu;
		this.x = x;
		this.y = y;
	}

	/**
	 * Draws the menu on the view.
	 * 
	 * @param g
	 *            the graphics to draw on
	 */
	public void drawMenu(Graphics g) {
		double fullAngle = 2 * Math.PI;
		String[] listLabels = menu.getlistLabels();

		// Draws the circular base of the marking menu
		g.setColor(Color.BLACK);
		g.drawOval(x - radius, y - radius, diameter, diameter);

		// Draws the items of the menu in the base
		for (int i = 0; i < listLabels.length; i++) {
			// Draws the separator
			g.drawLine(x, y, x + (int) (radius * (Math.cos(i * fullAngle / listLabels.length))),
					y + (int) (radius * (Math.sin(i * fullAngle / listLabels.length))));
			// Draws the label of each items
			g.drawString(listLabels[i],
					x + (int) ((radius * 2 / 3)
							* Math.cos((i * fullAngle / listLabels.length) + Math.PI / listLabels.length)),
					y + (int) ((radius * 2 / 3)
							* Math.sin((i * fullAngle / listLabels.length) + Math.PI / listLabels.length)));
		}

		/*
		 * Implementation abandoned : Not useful in the future
		 * 
		 * // If more then 8 items if (listLabels.length > 8) { for (int i = 8;
		 * i < listLabels.length; i++) { g.drawRect(x - 50, i * (y + radius),
		 * 100, 50); g.drawString(listLabels[i].toString(), x -
		 * listLabels[i].toString().length() / 2, i * (y + 25 + radius)); } }
		 */
	}

	/**
	 * Returns the index of tool, in listLabels, corresponding to the mouse's
	 * position
	 * 
	 * @param x
	 *            abscissa of the position
	 * @param y
	 *            ordinate of the position
	 * @return index of tool selected
	 */
	public int posToTool(int x, int y) {
		// TODO : verify the calculus of the angle
		int nbLabels = menu.getlistLabels().length;
		int ind = -1;

		// Calculates the angle with the mouse position
		double angle = Math.atan2((y - this.y), (x - this.x)) * (180 / Math.PI);

		// to have a angle between 0 and 360.
		if (angle < 0) {
			angle = angle + 360;
		}

		// to have a angle that goes clockwise
		angle = 360 - angle;

		// goes through the Labels to find the one corresponding to the angle
		for (int i = 0; i < nbLabels; i++) {
			if (angle >= (i * 360 / nbLabels) && angle < ((i + 1) * 360 / nbLabels)) {
				ind = i;
			}
		}
		return ind;
	}

	/**
	 * Returns true if the mouse it out of the first MarkingMenu, in the
	 * direction of the option Tool
	 * 
	 * @param x
	 *            abscissa of the mouse's position
	 * @param y
	 *            ordinate of the mouse's position
	 * @return boolean
	 */
	public boolean outOfTools(int x, int y) {
		// Calculate the distance between the center of the menu and the mouse's position
		int l = (int) Math.sqrt(Math.pow((this.x - x), 2) + Math.pow((this.y - y), 2));
		
		return (posToTool(x, y) == 1 && l >= radius && menu.isfirst());
	}

	/**
	 * Getter for the abscissa of the menu's position
	 * 
	 * @return abscissa of the menu's position
	 */
	public int getX() {
		return x;
	}

	/**
	 * Getter for the ordinate of the menu's position
	 * 
	 * @return ordinate of the menu's position
	 */
	public int getY() {
		return y;
	}

}
