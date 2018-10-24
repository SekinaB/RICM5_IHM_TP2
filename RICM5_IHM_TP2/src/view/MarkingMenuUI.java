package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.event.MouseInputListener;
import javax.swing.plaf.ComponentUI;

import control.MarkingMenu;

public class MarkingMenuUI extends ComponentUI {

	MarkingMenu menu;

	// The diameter of the circular base
	private int diameter = 150;
	// The radius of the circular base
	private int radius = diameter / 2;

	public MarkingMenuUI(MarkingMenu menu) {
		this.menu = menu;
	}

	/**
	 * Draw the menu on the view.
	 */
	public void paint(Graphics g, JComponent c) {
		super.paint(g, c);
		double fullCircleAngle = 2 * Math.PI;
		ArrayList<Object> listTools = menu.listTools();
		if (menu.appears()) {
			// Draws the circular base of the marking menu
			g.setColor(Color.BLACK);
			g.drawOval(menu.getX() - radius, menu.getY() - radius, diameter, diameter);

			// Draws the items of the menu in the base
			for (int i = 0; i < Math.min(listTools.size(), 8); i++) {
				// Draws the separator
				g.drawLine(menu.getX(), menu.getY(),
						menu.getX() + radius * ((int) Math.cos(i * fullCircleAngle / listTools.size())),
						menu.getY() + radius * ((int) Math.sin(i * fullCircleAngle / listTools.size())));

				// Draws the label of each items
				g.drawString(listTools.get(i).toString(),
						menu.getX() + (radius * 2 / 3) * ((int) Math.cos(i * fullCircleAngle / listTools.size())),
						menu.getY() + (radius * 2 / 3) * ((int) Math.sin(i * fullCircleAngle / listTools.size())));
			}

			if (listTools.size() > 8) {
				for (int i = 8; i < listTools.size(); i++) {
					g.drawRect(menu.getX() - 50, menu.getY() - 25, 100, 50);
					g.drawString(listTools.get(i).toString(), menu.getX() - listTools.get(i).toString().length() / 2,
							menu.getY());
				}
			}
		}
	}

	/**
	 * Returns the index of tool, in listTools, corresponding to the mouse's
	 * position
	 */
	public int posToTool(int x, int y) {
		// TODO : verify the calculus of the angle
		int nbTools = menu.listTools().size();
		int ind = -1;

		// Calculates the angle with the mouse position
		int angle = (int) Math.toDegrees(Math.atan2(y, x));
		for (int i = 0; i < nbTools; i++) {
			if (angle >= i * 360 / nbTools && angle < (i + 1) * 360 / nbTools) {
				ind = i;
			}
		}
		return ind;
	}

	public class mouseListener implements MouseInputListener {

		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void mouseDragged(MouseEvent e) {

		}

		@Override
		public void mouseMoved(MouseEvent e) {
		}

	}
}
