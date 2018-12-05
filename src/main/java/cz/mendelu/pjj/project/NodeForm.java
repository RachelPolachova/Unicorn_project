package cz.mendelu.pjj.project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class NodeForm {
	private JButton findSubTreesButton;
	private JPanel rootPanel;
	private JTextArea nodesArea;
	private JTextArea edgeNodesArea;
	private JLabel nodesLabel;
	private JLabel subTreesLabel;
	private JTextArea subTreesArea;
	NodeProcessing np = new NodeProcessing();


	public void runGUI() {
		NodeForm nodeForm = new NodeForm();
		JFrame jFrame = new JFrame("Nodes.");
		jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		jFrame.setContentPane(nodeForm.rootPanel);
		jFrame.pack();
		jFrame.setMinimumSize(new Dimension(500,400));
		jFrame.setVisible(true);
	}

	public NodeForm() {
		findSubTreesButton.addActionListener(this::findSubTrees);
	}



	public void readNodes() {

		String[] nodeLines = nodesArea.getText().split("\n");

		for (int i = 0; i < nodeLines.length; i++) {

			np.readingNodes(nodeLines[i], nodesLabel);

		}
	}


	public void readEdgeNodes() {

		String[] edgeNodes = edgeNodesArea.getText().split("\n");

		for (int i = 0; i < edgeNodes.length; i++) {
			np.readingSubTrees(edgeNodes[i],subTreesLabel);
		}

	}

	public void findSubTrees(ActionEvent e) {
	    subTreesArea.setText("");
		readNodes();
		readEdgeNodes();
		np.printSubTrees(subTreesArea);
		np.allNodesSet.clear();
		np.subTreesList.clear();
	}

}
