package ift605.tp3.main;

import ift605.tp3.agent.ColorAgent;
import ift605.tp3.commons.AgentGraph;
import ift605.tp3.commons.Node;
import ift605.tp3.commons.Vertex;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

import java.util.ArrayList;
import java.util.List;

public class Main {

	private static AgentGraph agentGraphA;
	private static AgentGraph agentGraphB;
	private static AgentGraph agentGraphC;

	public static void main(String[] args) {
		initGraph();
		initAgent();
	}

	private static void initGraph(){
		// Agent_A
		List<Node> nodesA = new ArrayList<Node>();
		List<Vertex> vertexA = new ArrayList<Vertex>();

		Node a1 = new Node(1); 
		nodesA.add(a1);

		Node a2 = new Node(2); 	
		nodesA.add(a2);
		vertexA.add(new Vertex(a1, a2, false));

		Node a3 = new Node(3);
		nodesA.add(a3);
		vertexA.add(new Vertex(a1, a3, false));

		Node a4 = new Node(4);
		nodesA.add(a4);

		Node a5 = new Node(5);
		nodesA.add(a5);
		vertexA.add(new Vertex(a4, a5, false));
		vertexA.add(new Vertex(a2, a5, false));

		Node a6 = new Node(6);
		nodesA.add(a6);
		vertexA.add(new Vertex(a4, a6, false));

		Node a7 = new Node(7);
		nodesA.add(a7);
		vertexA.add(new Vertex(a2, a7, false));
		vertexA.add(new Vertex(a6, a7, false));

		// Agent_B
		List<Node> nodesB = new ArrayList<Node>();
		List<Vertex> vertexB = new ArrayList<Vertex>();

		Node b8 = new Node(8);
		nodesB.add(b8);

		Node b9 = new Node(9);
		nodesB.add(b9);

		Node b10 = new Node(10);
		nodesB.add(b10);

		Node b11 = new Node(11);
		nodesB.add(b11);
		vertexB.add(new Vertex(b11, b8, false));
		vertexB.add(new Vertex(b11, b10, false));

		Node b12 = new Node(12);
		nodesB.add(b12);
		vertexB.add(new Vertex(b12, b9, false));
		vertexB.add(new Vertex(b12, b11, false));

		// Partial vertex for agent A and B
		vertexB.add(Vertex.createEdgeVertex(b8, 3));
		vertexA.add(Vertex.createEdgeVertex(a3, 8));

		vertexB.add(Vertex.createEdgeVertex(b10, 3));
		vertexA.add(Vertex.createEdgeVertex(a3, 10));

		vertexB.add(Vertex.createEdgeVertex(b9, 5));
		vertexA.add(Vertex.createEdgeVertex(a5, 9));

		vertexB.add(Vertex.createEdgeVertex(b9, 6));
		vertexA.add(Vertex.createEdgeVertex(a6, 9));

		// Agent_C
		List<Node> nodesC = new ArrayList<Node>();
		List<Vertex> vertexC = new ArrayList<Vertex>();

		Node c13 = new Node(13);
		nodesC.add(c13);

		Node c14 = new Node(14);
		nodesC.add(c14);
		vertexC.add(new Vertex(c14, c13, false));

		Node c15 = new Node(15);
		nodesC.add(c15);
		vertexC.add(new Vertex(c15, c13, false));

		Node c16 = new Node(16);
		nodesC.add(c16);
		vertexC.add(new Vertex(c16, c13, false));

		Node c17 = new Node(17);
		nodesC.add(c17);
		vertexC.add(new Vertex(c17, c15, false)); 

		Node c18 = new Node(18);
		nodesC.add(c18);
		vertexC.add(new Vertex(c18, c16, false));
		vertexC.add(new Vertex(c18, c14, false));

		// Partial vertex for agent (A and C) and (B and C)
		vertexC.add(Vertex.createEdgeVertex(c14, 7));
		vertexA.add(Vertex.createEdgeVertex(a7, 14));

		vertexC.add(Vertex.createEdgeVertex(c16, 6));
		vertexA.add(Vertex.createEdgeVertex(a6, 16));

		vertexB.add(Vertex.createEdgeVertex(b12, 15));
		vertexC.add(Vertex.createEdgeVertex(c15, 12));

		vertexB.add(Vertex.createEdgeVertex(b10, 15));
		vertexC.add(Vertex.createEdgeVertex(c15, 10));

		agentGraphA = new AgentGraph("agent_A", nodesA, vertexA);
		agentGraphB = new AgentGraph("agent_B", nodesB, vertexB);
		agentGraphC = new AgentGraph("agent_C", nodesC, vertexC);
	}

	private static void initAgent() {
		jade.core.Runtime rt = jade.core.Runtime.instance();
		Profile p = new ProfileImpl();
		ContainerController cc = rt.createMainContainer(p);

		try {
			Object[] o1 = new Object[1];
			o1[0] = agentGraphA;
			AgentController ac1 = cc.createNewAgent("agent_A", ColorAgent.class.getName() , o1);


			Object[] o2 = new Object[1];
			o2[0] = agentGraphB;
			AgentController ac2 = cc.createNewAgent("agent_B", ColorAgent.class.getName() , o2);


			Object[] o3 = new Object[1];
			o3[0] = agentGraphC;
			AgentController ac3 = cc.createNewAgent("agent_C", ColorAgent.class.getName() , o3);

			ac3.start();
			ac2.start();
			ac1.start();

			while(true){

				if( ac1.getState().getName() == "Idle" 
						&& ac2.getState().getName() == "Idle"
						&& ac3.getState().getName() == "Idle"){
					System.out.println("\n\n*** All the Agents are done negociating***");
					System.out.println("*** A SOLUTION IS FOUND ***\n\n");
					System.out.println(agentGraphA);
					System.out.println(agentGraphB);
					System.out.println(agentGraphC);
					ac1.kill();
					ac2.kill();
					ac3.kill();				
					System.exit(0);
				}



			}




		} catch (StaleProxyException e1) {
			e1.printStackTrace();
		}
	}

}
