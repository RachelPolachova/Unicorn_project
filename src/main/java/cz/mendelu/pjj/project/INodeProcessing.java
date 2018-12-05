package cz.mendelu.pjj.project;

public interface INodeProcessing {

	boolean nodeAlreadyExists(String node);
	void addToSubTree(String firstNode, String secondNode);
	void uniteSubTrees(int indexOfSubTree, int i, String edgeNode);

	void uniqueNodes();
}
