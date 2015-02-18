package ift605.tp3.commons;

import java.io.Serializable;
import java.util.Random;

public class Vertex implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final Random random = new Random();

	private Node nodeA;
	private Node nodeB;
	private boolean isPartialVertex;

	public Vertex(Node nodeA, Node nodeB, boolean isPartialVertex) {
		this.nodeA = nodeA;
		this.nodeB = nodeB;
		this.isPartialVertex = isPartialVertex;
	}

	public boolean isPartialVertex() {
		return isPartialVertex;
	}

	private boolean isColorSame() {
		return (nodeA.getColor().equals(nodeB.getColor())) ? true : false;
	}

	public Node getRandomInvalidNode() {
		if(isPartialVertex || !isColorSame()){
			return null;
		} else {
			int i = random.nextInt(1);
			return (i == 0) ? nodeA : nodeB;
		}
	}

	public Node getNodeA() {
		return nodeA;
	}

	public Node getNodeB() {
		return nodeB;
	}

	public static Vertex createEdgeVertex(Node nodeA, int getNodeUnknownId) {
		if(nodeA == null || getNodeUnknownId == 0)
			return null;

		Node partialNode = new Node(getNodeUnknownId);
		partialNode.setColor(null);

		return new Vertex(nodeA, partialNode, true);
	}

	@Override
	public String toString() {
		return "Vertex [nodeA=" + nodeA.toString() + ", nodeB=" + nodeB.toString() + ", isPartialVertex=" + isPartialVertex + "]";
	}
}
