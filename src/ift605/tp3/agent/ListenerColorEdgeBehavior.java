package ift605.tp3.agent;

import java.util.List;
import java.util.Vector;

import ift605.tp3.commons.AgentGraph;
import ift605.tp3.commons.Node;
import ift605.tp3.commons.Vertex;
import jade.core.AID;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class ListenerColorEdgeBehavior extends SimpleBehaviour {
	private static final long serialVersionUID = 1L;

	AgentGraph graph;	
	Vector<AID> confirmedSender;
	public ListenerColorEdgeBehavior(AgentGraph graph) {
		this.graph = graph;
		this.confirmedSender = new Vector<AID>();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void action() {
		// Verify if the other agent list edge are correct
		MessageTemplate pattern = MessageTemplate.or(MessageTemplate.MatchPerformative(ACLMessage.PROPOSE),
				MessageTemplate.MatchPerformative(ACLMessage.CONFIRM));

		ACLMessage message = this.myAgent.receive(pattern);

		if( message != null) {
			switch (message.getPerformative()) {
			case ACLMessage.PROPOSE:
				try {
					System.out.println("["+this.myAgent.getLocalName()+"] - Received a PROPOSE message from "+message.getSender().getLocalName());	
					boolean noError = true;
					List<Vertex> edgeVertex = (List<Vertex>) message.getContentObject();

					for (Vertex vertex : edgeVertex) {
						Node remoteNode = vertex.getNodeA();
						Node localNode = graph.getNodeById(vertex.getNodeB().getId());

						if (remoteNode.getColor() == localNode.getColor()) {
							noError = false;
							graph.eraseAll();
							graph.deleteAllConfirmedEdges();
							// Clear message stack
							while (this.myAgent.receive() != null);						

							this.myAgent.addBehaviour(new ColorLocalNodeBehaviour(graph));
							break;
						}
					}

					if(noError) {
						ACLMessage confirmMessage = message.createReply();
						confirmMessage.setPerformative(ACLMessage.CONFIRM);
						this.myAgent.send(confirmMessage);
					}

				} catch (UnreadableException e) {
					e.printStackTrace();
				}

			case ACLMessage.CONFIRM:
				jade.core.AID sender = 	message.getSender();
				graph.confirmeEdge(sender);
				System.out.println("["+this.myAgent.getLocalName()+"] - Received a CONFIRM message from "+message.getSender().getLocalName());	
				break;
			default:
				break;
			}
		} else {
			block();
		}
	}

	@Override
	public boolean done() {

		if(this.graph.numberOfConfirmationEdges() == 2){
			System.out.println("["+this.myAgent.getLocalName()+"] - The agent is done");
		}

		return false;
	}
}
