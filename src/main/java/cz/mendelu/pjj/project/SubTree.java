package cz.mendelu.pjj.project;

public class SubTree {

	private String nodes;

	public String getNodes() {
		return nodes;
	}

	public void setNodes(String newNodes) {
		nodes = newNodes;
	}

	public void addNode(String node) {

		if (nodes == null) {
			nodes = node;
		} else {
			nodes = getNodes() + "," + node;
		}
	}

}
