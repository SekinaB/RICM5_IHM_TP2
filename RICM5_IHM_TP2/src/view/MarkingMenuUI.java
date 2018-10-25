package view;

import java.awt.Color;
import java.awt.Graphics;

import java.util.ArrayList;

import control.MarkingMenu;

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
	 * @param menu The MarkingMenu corresponding to the UI
	 * @param x abscissa of the menu's position
	 * @param y ordinate of the menu's position
	 */
	public MarkingMenuUI(MarkingMenu menu, int x, int y) {
		this.menu = menu;
		this.x = x;
		this.y = y;
	}

	/**
	 * Draws the menu on the view.
	 * @param g the graphics to draw on
	 */
	public void drawMenu(Graphics g) {
		double fullAngle = 2 * Math.PI;
		String[] listTools = menu.getlistTools();

		// Draws the circular base of the marking menu
		g.setColor(Color.BLACK);
		g.drawOval(x - radius, y - radius, diameter, diameter);

		// Draws the items of the menu in the base
		for (int i = 0; i < Math.min(listTools.length, 8); i++) {
			// Draws the separator
			g.drawLine(x, y, x + (int) (radius * (Math.cos(i * fullAngle / listTools.length))),
					y + (int) (radius * (Math.sin(i * fullAngle / listTools.length))));

			System.out.println(x + (int) (radius * Math.cos(i * fullAngle / listTools.length)));
			System.out.println(y + (int) (radius * Math.sin(i * fullAngle / listTools.length)));
			// Draws the label of each items
			g.drawString(listTools[i], x + (int) ((radius * 2 / 3) * Math.cos(i * fullAngle / listTools.length)),
					y + (int) ((radius * 2 / 3) * Math.sin(i * fullAngle / listTools.length)));
		}

		if (listTools.length > 8) {
			for (int i = 8; i < listTools.length; i++) {
				g.drawRect(x - 50, y - 25, 100, 50);
				g.drawString(listTools[i].toString(), x - listTools[i].toString().length() / 2, y);
			}
		}
	}

	/**
	 * Returns the index of tool, in listTools, corresponding to the mouse's
	 * position
	 * 
	 * @param x abscissa of the position
	 * @param y ordinate of the position
	 * @return index of tool selected
	 */
	public int posToTool(int x, int y) {
		// TODO : verify the calculus of the angle
		int nbTools = menu.getlistTools().length;
		int ind = -1;

		// Calculates the angle with the mouse position
		double angle = Math.atan2((y - this.y), (x - this.x)) * (180 / Math.PI);
		
		// to have a angle between 0 and 360.
		if(angle < 0) {
			angle = angle + 360;
		}
		
		// to have a angle that goes clockwise
		angle = 360 - angle;
		
		// goes through the tools to find the one corresponding to the angle
		for (int i = 0; i < nbTools; i++) {
			if (angle >= (i * 360 / nbTools) && angle < ((i + 1) * 360 / nbTools)) {
				ind = i;
			}
		}
		return ind;
	}

}
