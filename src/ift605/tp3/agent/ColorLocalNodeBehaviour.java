package ift605.tp3.agent;

import ift605.tp3.commons.AgentGraph;
import ift605.tp3.commons.Node;
import ift605.tp3.commons.Color;
import jade.core.behaviours.SimpleBehaviour;

public class ColorLocalNodeBehaviour extends SimpleBehaviour {
	private static final long serialVersionUID = 1L;
	private AgentGraph graph;

	public ColorLocalNodeBehaviour(AgentGraph graph) {
		this.graph = graph;
	}

	@Override
	public void action() {
		Node node = graph.getLocalNodeToColor();

		while(node != null) {
			node.setColor(Color.getRandomColor());	
			node = graph.getLocalNodeToColor();
		}
	}

	@Override
	public boolean done() {
		if(graph.getLocalNodeToColor() != null)
			return false;

		this.myAgent.addBehaviour(new SenderColorEdgeBehaviour(graph));
		return true;
	}
}
