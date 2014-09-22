import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GraphPanel extends JPanel{
	
	AdjacencyList adjList;
	private final int WIDTH = 600, HEIGHT = 600, ADJ_FACTOR = 30, DELAY = 10;
	private List<Edge> edgeList;
	private List<TableEntry> tableList;
	private List<Lines> drawList;
	private Iterator<TableEntry> tableIt;
	private boolean drawn = false;
	private JButton button;
	private Vertex start, end;
	
	private class Lines {
		private Edge edge;
		private Color color;
		
		public Lines(Edge e, Color c) {
			edge = e;
			color = c;
		}
		
	}

	public GraphPanel(AdjacencyList list) {
      //System.out.println("Hello? with one parameter");
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		adjList = list;
	}
	
	public GraphPanel(AdjacencyList list,List<TableEntry> tableList,
			List<Edge> edgeList, Vertex start, Vertex end) {
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
		this.start = start;
		this.end = end;
	}
	
	public void paintComponent(Graphics page) {
		super.paintComponent(page);
		
		Set<Vertex> keySet = adjList.getAdjList().keySet();
		for (Vertex entry: keySet) {
			page.setColor(Color.DARK_GRAY);
			page.drawString(entry.getName(), ADJ_FACTOR + entry.getX() - 20, ADJ_FACTOR + entry.getY() - 20);
			page.setColor(Color.black);
			page.drawOval(ADJ_FACTOR + entry.getX() - 15, ADJ_FACTOR + entry.getY() - 15, 30, 30);
		}
		
		page.setColor(Color.GREEN);
		page.fillOval(ADJ_FACTOR + start.getX() - 15, ADJ_FACTOR + start.getY() - 15, 30, 30);
		
		page.setColor(Color.magenta);
		page.fillOval(ADJ_FACTOR + end.getX() - 15, ADJ_FACTOR + end.getY() - 15, 30, 30);
		
		
		Set<Entry<Vertex, Map<Vertex, Integer>>> entrySet = adjList.getAdjList().entrySet();
		page.setColor(Color.black);
		for (Entry<Vertex, Map<Vertex, Integer>> entry : entrySet) {
			Set<Vertex> associatedNodes = entry.getValue().keySet();
			for (Vertex connectedNode: associatedNodes) {
				page.drawLine(ADJ_FACTOR + entry.getKey().getX(), ADJ_FACTOR + entry.getKey().getY(),
						ADJ_FACTOR + connectedNode.getX(), ADJ_FACTOR + connectedNode.getY());
			}
		}
		
		for (Lines line: drawList) {
			page.setColor(line.color);
			Vertex strt = line.edge.getSource();
			Vertex end = line.edge.getDestination();
			page.drawLine(strt.getX()+ ADJ_FACTOR, strt.getY() + ADJ_FACTOR, 
					end.getX() + ADJ_FACTOR, end.getY() + ADJ_FACTOR);
		}
	}
	
	private class NewLineListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			//System.out.println("Delay Listener is a go");
			if (tableList != null && tableIt.hasNext()) {
				//System.out.println("Did we get here?");
				TableEntry item = tableIt.next();
				if (item.getParent() != null) {
					//System.out.println("how about here?");
					Vertex parentOfItem = item.getParent();
					Vertex itemVertex = item.getVertex();
					Edge currentEdge = new Edge(parentOfItem, itemVertex, item.getWeight());
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

//page.drawLine(parentOfItem.getX()+ ADJ_FACTOR, parentOfItem.getY() +ADJ_FACTOR, 
//		itemVertex.getX() + ADJ_FACTOR, itemVertex.getY() + ADJ_FACTOR);
//System.out.println("drawing blue line is a go");
