import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;


public class MazePanel extends JPanel{

	AdjacencyList adjList;
	private final int WIDTH = 500, HEIGHT = 500, ADJ_FACTOR = 10, SIZE = 40, START = 50;
	private List<Edge> edgeList;
	private List<MazeTableEntry> tableList;
	private List<Lines> drawList;
	private Iterator<MazeTableEntry> tableIt;
	private boolean drawn = false;
	private JButton button;
	private int numSquares;
	
	private class Lines {
		private Edge edge;
		private Color color;
		
		public Lines(Edge e, Color c) {
			edge = e;
			color = c;
		}
		
	}
	
	public MazePanel(AdjacencyList list,List<MazeTableEntry> tableList, List<Edge> edgeList, int numPanels) {
		//System.out.println("Hello? with three parameters");
		setPreferredSize(new Dimension(550, 550));
		adjList = list;
		this.edgeList = edgeList;
		this.tableList = tableList;
		drawList = new ArrayList<Lines>();
		tableIt = tableList.iterator();
		button = new JButton("Next Path");
		button.addActionListener(new NewLineListener());
		add(button);
		tableIt.next();
		numSquares = numPanels;
	}
	
	public void paintComponent(Graphics page) {
		super.paintComponent(page);
		
		page.setColor(Color.WHITE);
		page.fillRect(START - 20, START - 20, SIZE * (numSquares), SIZE * (numSquares));
		
		page.setColor(Color.BLACK);
		page.drawLine(START - 20, START - 20, SIZE * (numSquares) + 30, START - 20);
		page.drawLine(START - 20, START - 20, START - 20, SIZE * (numSquares) + 30);
		for (int i = 0; i < numSquares; i++) {
			for (int j = 0; j < numSquares; j++) {
				page.drawLine(START + SIZE * j - 20, START + SIZE * (i + 1) - 20,
						START + SIZE * (j + 1) - 20, START + SIZE * (i + 1) - 20);
				
				page.drawLine(START + SIZE * (j + 1) - 20, START + SIZE * i - 20,
						START + SIZE * (j + 1) - 20, START + SIZE * (i + 1) - 20);
			}
		}
		
		
		Set<Entry<Vertex, Map<Vertex, Integer>>> entrySet = adjList.getAdjList().entrySet();
		page.setColor(Color.black);
		for (Entry<Vertex, Map<Vertex, Integer>> entry : entrySet) {
			Set<Vertex> associatedNodes = entry.getValue().keySet();
			Vertex source = entry.getKey();
			for (Vertex dest: associatedNodes) {
				page.setColor(Color.WHITE);
				if (source.getY() == dest.getY()) {
					int median = (source.getX() + dest.getX()) / 2;
					page.drawLine(median, source.getY() - 20,
							median, dest.getY() + 20);
				} else {
					int median = (source.getY() + dest.getY()) / 2;
					page.drawLine(entry.getKey().getX() - 20, median,
							dest.getX() + 20, median);
				}
			}
		}
		
		for (Lines line: drawList) {
			page.setColor(line.color);
			Vertex strt = line.edge.getSource();
			Vertex end = line.edge.getDestination();
			page.drawLine(strt.getX(), strt.getY(), 
					end.getX(), end.getY());
		}
	}
	
	private class NewLineListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			//System.out.println("Delay Listener is a go");
			if (tableList != null && tableIt.hasNext()) {
				//System.out.println("Did we get here?");
				MazeTableEntry item = tableIt.next();
				if (item.getParent() != null) {
					//System.out.println("how about here?");
					Vertex parentOfItem = item.getParent();
					Vertex itemVertex = item.getVertex();
					Edge currentEdge = new Edge(parentOfItem, itemVertex, item.getDistance());
					if (edgeList.contains(currentEdge)) {
						Lines line = new Lines(currentEdge, Color.BLUE);
						drawList.add(line);
					} else {
						Lines line = new Lines(currentEdge, Color.RED);
						drawList.add(line);
					}
					
				}
			}
			repaint();
			revalidate();
		}
	}
}

