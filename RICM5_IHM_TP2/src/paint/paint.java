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
	String[] nameTools = { "Pen", "Rect", "Elli" };
	HashMap<Shape, Color> shapesList = new HashMap<Shape, Color>();
	Color color = Color.BLACK;
	MarkingMenu menu = new MarkingMenu(nameTools);
	MarkingMenuUI menuUI;

	enum State {
		IDLE, RIGHT_PRESSED, LEFT_PRESSED;
	}

	class Tool extends AbstractAction implements MouseInputListener {
		Point o;
		Shape shape;
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
				s = State.LEFT_PRESSED;
				o = e.getPoint();
				break;

			// Right Button
			case 3:
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
				toolMouseDragged(e);
				break;
			// Right Button
			case RIGHT_PRESSED:
				o = e.getPoint();
				break;
			default:
			}
		}

		public void mouseReleased(MouseEvent e) {
			switch (s) {
			// Left Button
			case LEFT_PRESSED:
				s = State.IDLE;
				shape = null;
				break;
			// Right Button
			case RIGHT_PRESSED:
				s = State.I
				DLE;
				if(((int) o.getX() != menuUI.getX() || ((int) o.getY()) != menuUI.getY())){
					JButton selectedTool = new JButton(tools[menuUI.posToTool((int) o.getX(), (int) o.getY())]);
					selectedTool.doClick();
				}
				menuUI = null;
				panel.repaint();
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
				shapesList.put(shape = path, color);
			}
			path.lineTo(e.getX(), e.getY());
			panel.repaint();
		}
	}, new Tool("Rect") {
		public void toolMouseDragged(MouseEvent e) {
			Rectangle2D.Double rect = (Rectangle2D.Double) shape;
			if (rect == null) {
				rect = new Rectangle2D.Double(o.getX(), o.getY(), 0, 0);
				shapesList.put(shape = rect, color);
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
				shapesList.put(shape = elli, color);
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

				for (Entry<Shape, Color> entry : shapesList.entrySet()) {
					g2.setColor(entry.getValue());
					g2.draw(entry.getKey());
				}
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
				color = JColorChooser.showDialog(null, "Pen's Color", Color.WHITE);
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
