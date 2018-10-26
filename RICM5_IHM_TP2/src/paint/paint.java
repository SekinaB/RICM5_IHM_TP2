package paint;
/**
 * NOTATION : PHOTO
 */

import static java.lang.Math.*;

import java.util.HashMap;
import java.util.Map.Entry;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Point;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

class Paint extends JFrame {
	// The labels of the different MarkingMenus
	String[] firstOption = { "Color", "Tools", "Back" };
	String[] secondOption = { "Back", "Elli", "Rect", "Pen" };

	MarkingMenu menu = new MarkingMenu(firstOption, true);
	MarkingMenuUI menuUI;

	// The list of Shape created by the user
	HashMap<Shape, Color> shapesList = new HashMap<Shape, Color>();
	Color currentColor = Color.BLACK;

	/**
	 * Class enum to know in which state we are in to act accordingly IDLE =
	 * Nothing RIGHT_PRESSED = Right click is pressed LEFT_PRESSED = Left click
	 * is pressed
	 */
	enum State {
		IDLE, RIGHT_PRESSED, LEFT_PRESSED;
	}

	class Tool extends AbstractAction implements MouseInputListener {
		// Shape currently drawn
		Shape shape;
		// Position of the mouse
		Point o;
		// State in which we're in
		State s;

		public Tool(String name) {
			super(name);
		}

		public void actionPerformed(ActionEvent e) {
			System.out.println("using tool " + this);
			panel.removeMouseListener(tool);
			panel.removeMouseMotionListener(tool);
			tool = this;
			panel.addMouseListener(tool);
			panel.addMouseMotionListener(tool);
		}

		public void mousePressed(MouseEvent e) {
			o = e.getPoint();
			switch (e.getButton()) {
			// Left Button
			case 1:
				// Change state and get current position
				s = State.LEFT_PRESSED;
				o = e.getPoint();
				break;

			// Right Button
			case 3:
				// Change state and draw the MarkingMenu
				s = State.RIGHT_PRESSED;
				menuUI = new MarkingMenuUI(menu, (int) o.getX(), (int) o.getY());
				panel.repaint();
				break;
			default:
			}
		}

		public void mouseDragged(MouseEvent e) {

			switch (s) {
			// Left Button
			case LEFT_PRESSED:
				// Does the action depending on the tool
				toolMouseDragged(e);
				break;

			// Right Button
			case RIGHT_PRESSED:
				o = e.getPoint();
				// If the user was in the tool option and went out of the menu
				// Draws MarkingMenu with tools option
				if (menuUI.outOfTools((int) o.getX(), (int) o.getY())) {
					menu = new MarkingMenu(secondOption, false);
					menuUI = new MarkingMenuUI(menu, (int) o.getX(), (int) o.getY());
					panel.repaint();
				}
				break;
			default:
			}
		}

		public void mouseReleased(MouseEvent e) {
			switch (s) {
			// Left Button
			case LEFT_PRESSED:
				// End the drawing and change state
				shape = null;
				s = State.IDLE;
				break;
			// Right Button
			case RIGHT_PRESSED:
				// Get the position of the mouse
				o = e.getPoint();
				// if the mouse moved from the moment the MarkingMenu was
				// display
				if (((int) o.getX() != menuUI.getX() || ((int) o.getY()) != menuUI.getY())) {
					// Get the option wanted
					int ind = menuUI.posToTool((int) o.getX(), (int) o.getY());
					if (menu.isfirst()) {
						// The user can only selected Color by releasing on the
						// first MarkingMenu
						// for the rest (Tool option, Back option, or error) we
						// do nothing
						if (ind == 2) {
							// Color selected
							currentColor = JColorChooser.showDialog(null, "Pen's Color", Color.WHITE);
						}
					} else { // This is the second menu
						// The user selected a tool
						if (ind != 3) {
							JButton selectedTool = new JButton(tools[ind]);
							selectedTool.doClick();
						}
						// If selected Back we do nothing
					}
				}

				// Remove the current MarkingMenu goes back to the first menu
				menu = new MarkingMenu(firstOption, true);
				menuUI = null;
				panel.repaint();
				s = State.IDLE;
				break;
			default:
			}
		}

		public void mouseMoved(MouseEvent e) {
		}

		public void mouseClicked(MouseEvent e) {
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}

		public void toolMouseDragged(MouseEvent e) {
		}
	}

	Tool tools[] = { new Tool("Pen") {
		public void toolMouseDragged(MouseEvent e) {
			Path2D.Double path = (Path2D.Double) shape;
			if (path == null) {
				path = new Path2D.Double();
				path.moveTo(o.getX(), o.getY());
				shapesList.put(shape = path, currentColor);
			}
			path.lineTo(e.getX(), e.getY());
			panel.repaint();
		}
	}, new Tool("Rect") {
		public void toolMouseDragged(MouseEvent e) {
			Rectangle2D.Double rect = (Rectangle2D.Double) shape;
			if (rect == null) {
				rect = new Rectangle2D.Double(o.getX(), o.getY(), 0, 0);
				shapesList.put(shape = rect, currentColor);
			}
			rect.setRect(min(e.getX(), o.getX()), min(e.getY(), o.getY()), abs(e.getX() - o.getX()),
					abs(e.getY() - o.getY()));
			panel.repaint();
		}
	}, new Tool("Ellipse") {
		public void toolMouseDragged(MouseEvent e) {
			Ellipse2D.Double elli = (Ellipse2D.Double) shape;
			if (elli == null) {
				elli = new Ellipse2D.Double(o.getX(), o.getY(), 0, 0);
				shapesList.put(shape = elli, currentColor);
			}
			elli.setFrame(min(e.getX(), o.getX()), min(e.getY(), o.getY()), abs(e.getX() - o.getX()),
					abs(e.getY() - o.getY()));
			panel.repaint();
		}
	} };
	
	Tool tool;
	JPanel panel;
	JButton button;

	public Paint(String title) {
		super(title);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(800, 600));
		add(new JToolBar() {
			{
				for (AbstractAction tool : tools) {
					add(tool);
				}
				add(button = new JButton("Color"));
			}
		}, BorderLayout.NORTH);
		add(panel = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g;
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(Color.WHITE);
				g2.fillRect(0, 0, getWidth(), getHeight());

				// Draws each Shape with their own color
				for (Entry<Shape, Color> entry : shapesList.entrySet()) {
					g2.setColor(entry.getValue());
					g2.draw(entry.getKey());
				}
				// In the menu is not null, draw it in the graphics
				if (menuUI != null) {
					menuUI.drawMenu(g2);
				}
			}
		});

		/*
		 * Implementation of the Function inspired by
		 * https://perso.telecom-paristech.fr/hudry/coursJava/interSwing/
		 * couleurs.html
		 */
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Opens a Dialog to choose a color
				currentColor = JColorChooser.showDialog(null, "Pen's Color", Color.WHITE);
			}
		});

		pack();
		setVisible(true);
	}

	public static void main(String argv[]) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Paint paint = new Paint("paint");
			}
		});
	}
}
