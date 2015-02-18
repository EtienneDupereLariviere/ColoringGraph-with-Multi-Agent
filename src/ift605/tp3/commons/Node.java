package ift605.tp3.commons;

import java.io.Serializable;

public class Node implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	private Color color;

	public Node(Integer id) {
		this.id = id;
		this.color = null;
	}

	public Integer getId() {
		return id;
	}

	public Color getColor() {
		return color;
	}

	public synchronized void setColor(Color color) {
		this.color = color;
	}


	public static String getNodeAgentName(Integer id) {
		if(id <= 7){
			return "agent_A";
		} else if (id > 7 && id <= 12) {
			return "agent_B";
		} else {
			return "agent_C";
		}
	}

	@Override
	public String toString() {
		return "Node [id=" + id + ", color=" + Color.toString(color) + "]";
	}
}
