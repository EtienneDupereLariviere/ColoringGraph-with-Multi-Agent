package ift605.tp3.agent;

import ift605.tp3.commons.AgentGraph;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class SenderColorEdgeBehaviour extends OneShotBehaviour {
	private static final long serialVersionUID = 1L;
	private AgentGraph graph;

	public SenderColorEdgeBehaviour(AgentGraph graph) {
		this.graph = graph;
	}

	@Override
	public void action() {
		List<String> otherAgentsName = graph.getOtherAgentsName();

		//Propose other agent of current nodes edge
		for (String agentName : otherAgentsName) {
			ACLMessage message = new ACLMessage(ACLMessage.PROPOSE);
			message.addReceiver(new AID(agentName, AID.ISLOCALNAME));

			try {
				message.setContentObject((Serializable) graph.getEdgeVertexForAgent(agentName));
				this.myAgent.send(message);
			} catch (IOException e) {
				e.printStackTrace();
			}							
		}
	}
}
