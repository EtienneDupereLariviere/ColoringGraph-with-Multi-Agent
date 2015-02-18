package ift605.tp3.commons;

import jade.core.AID;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AgentGraph {
	private String agentName;
	private List<Node> nodeList;
	private List<Vertex> vertexList;
	private Set<AID> confirmedEdge;

	public AgentGraph(String agentName, List<Node> nodeList, List<Vertex> vertexList) {
		this.agentName = agentName;
		this.nodeList = nodeList;
		this.vertexList = vertexList;
		this.confirmedEdge = new java.util.HashSet<AID>();
	}

	private Node getUncoloredNode() {
		for (Node node : nodeList) {
			if (node.getColor() == null) {
				return node;
			}
		}
		return null;
	}

	private Node getInvalidNode() {
		for (Vertex vertex : vertexList) {
			Node invalidNode = vertex.getRandomInvalidNode();
			if (invalidNode != null) {
				return invalidNode;
			}
		}
		return null;
	}

	public Node getLocalNodeToColor() {
		Node node = getUncoloredNode();

		if (node == null)
			node = getInvalidNode();

		// return null if no local node to color
		return node;
	}

	public void eraseAll() {
		for (Node n : nodeList) {
			n.setColor(null);
		}
	}

	public List<Vertex> getEdgeVertexForAgent(String agentName) {
		List<Vertex> edgeNodes = new ArrayList<Vertex>();

		for(Vertex vertex : vertexList) {
			if(vertex.isPartialVertex()) {
				if(agentName.equals(Node.getNodeAgentName(vertex.getNodeB().getId()))) 
					edgeNodes.add(vertex);
			} 
		}
		return edgeNodes;
	}

	public List<String> getOtherAgentsName(){
		List<String> agentName = new ArrayList<String>();

		if (this.agentName != "agent_A")
			agentName.add("agent_A");
		if (this.agentName != "agent_B")
			agentName.add("agent_B");
		if (this.agentName != "agent_C")
			agentName.add("agent_C");

		return agentName;
	}

	public Node getNodeById(int id){
		for(Node node : nodeList){
			if(node.getId().equals(id)){
				return node;
			}
		}
		return null;
	}





	public void confirmeEdge(AID sender){
		this.confirmedEdge.add(sender);
	}

	public void deleteAllConfirmedEdges() {
		this.confirmedEdge.clear();
	}	
	public int numberOfConfirmationEdges() {
		return this.confirmedEdge.size();
	}	


	@Override
	public String toString() {
		String graphString = "AgentGraph [agentName=" + agentName + ", node id and color:\n";

		for(Node node : nodeList)
		{
			graphString = graphString + node.toString() + "\n";
		}

		return graphString;
	}

}
