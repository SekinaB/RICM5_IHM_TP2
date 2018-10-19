package controller;

import java.awt.Point;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

public class Tool extends AbstractAction implements MouseInputListener {

	JPanel panel;
	Point o;
	Shape shape;
	Tool pastTool;

	public Tool(String name, JPanel panel, Tool pastTool) {
		super(name);
		this.panel = panel;
		this.pastTool = pastTool;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		o = e.getPoint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		shape = null;
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

	@Override
	public void actionPerformed(ActionEvent e) {
		panel.removeMouseListener(pastTool);
		panel.removeMouseMotionListener(pastTool);
		panel.addMouseListener(this);
		panel.addMouseMotionListener(this);
	}
	
}
