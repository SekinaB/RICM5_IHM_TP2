package paint;
import static java.lang.Math.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Vector;

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

import controller.Tool;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

class Paint extends JFrame {
	HashMap<Shape, Color> shapesList = new HashMap<Shape, Color>();
	Color color = Color.BLACK;

	Tool tool;
	JPanel panel;
	Tool tools[] = { new Tool("Pen", panel, tool) {
		public void mouseDragged(MouseEvent e) {
			Path2D.Double path = (Path2D.Double) shape;
			if (path == null) {
				path = new Path2D.Double();
				path.moveTo(o.getX(), o.getY());
				shapesList.put(shape = path, color);
			}
			path.lineTo(e.getX(), e.getY());
			panel.repaint();
		}
	}, new Tool("Rect", panel, tool) {
		public void mouseDragged(MouseEvent e) {
			Rectangle2D.Double rect = (Rectangle2D.Double) shape;
			if (rect == null) {
				rect = new Rectangle2D.Double(o.getX(), o.getY(), 0, 0);
				shapesList.put(shape = rect, color);
			}
			rect.setRect(min(e.getX(), o.getX()), min(e.getY(), o.getY()), abs(e.getX() - o.getX()),
					abs(e.getY() - o.getY()));
			panel.repaint();
		}
	}, new Tool("Ellipse", panel, tool) {
		public void mouseDragged(MouseEvent e) {
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
