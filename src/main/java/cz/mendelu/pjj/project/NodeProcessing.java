package cz.mendelu.pjj.project;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class NodeProcessing implements INodeProcessing {

	Set<String> allNodesSet;
	List<SubTree> subTreesList;


	public NodeProcessing() {

		allNodesSet = new TreeSet<String>();
		subTreesList = new ArrayList<SubTree>();

	}

	public boolean nodeAlreadyExists(String node) {


		if (allNodesSet.contains(node)) {
			return true;
		} else {
			allNodesSet.add(node);
			return false;
		}
	}

	public Collection<SubTree> getSubTreesList() {
		return Collections.unmodifiableList(subTreesList);
	}
	public Collection<String> getAllNodesSet() { return Collections.unmodifiableSet(allNodesSet); }


	public void printSubTrees() {

		this.uniqueNodes();


		System.out.println("<-- OSTROVY -->");
		Collection <SubTree> st = this.getSubTreesList();
		System.out.println(st.size());
		System.out.println("");

		sortList();

		for(SubTree subTree : st) {
			System.out.println(subTree.getNodes());
		}
	}

	public void printSubTrees(JTextArea area) {

		this.uniqueNodes();

		System.out.println("<-- OSTROVY -->");
		Collection <SubTree> st = this.getSubTreesList();
		area.append(Integer.toString(st.size()));
		area.append("\n");
		area.append("\n");


		subTreesList.sort(Comparator.comparing(SubTree::getNodes));
		subTreesList.forEach(System.out::println);

        sortList();

		for(SubTree subTree : st) {
			area.append(subTree.getNodes());
			area.append("\n");
		}
	}

	public void sortList() {
        for (int i = 0; i < subTreesList.size() -1; i++) {
            for (int j = 0; j < subTreesList.size() -i -1; j++) {
                if (subTreesList.get(j).getNodes().length() < subTreesList.get(j+1).getNodes().length()) {
                    String tmp = subTreesList.get(j).getNodes();
                    subTreesList.get(j).setNodes(subTreesList.get(j+1).getNodes());
                    subTreesList.get(j+1).setNodes(tmp);
                }
            }
        }
    }



	/**
	 * <h1>Unite SubTrees</h1>
	 * uzly z ostrovu s indexom indexOfSubTree sa ulozia do mergingNodes a ostrov sa vymaze. Z mergingNodes sa vymaze ciarka a edgeNode a ulozia sa do ostrovu s indexom i.
	 * @param indexOfSubTree index ostrovu, v ktorom sa uz nachadza dany uzol
	 * @param i index aktualneho ostrovu, ktory prechadza for cyklus a dany uzol by sa v nom mal nachadzat tiez
	 * @param edgeNode uzol, ktory vlazame do ostrova
	 */

	public void uniteSubTrees(int indexOfSubTree, int i, String edgeNode) {


		String mergingNodes = subTreesList.get(indexOfSubTree).getNodes();


		StringBuilder sb = new StringBuilder(mergingNodes);


		int indexOfEdgeNode = mergingNodes.indexOf(edgeNode);

		if (indexOfEdgeNode == 0) {
			sb.deleteCharAt(mergingNodes.indexOf(','));
			sb.deleteCharAt(sb.indexOf(edgeNode));
			mergingNodes = sb.toString();

		} else {

			sb.deleteCharAt(mergingNodes.lastIndexOf(','));
			sb.deleteCharAt(sb.indexOf(edgeNode));
			mergingNodes = sb.toString();
		}


		subTreesList.get(i).addNode(mergingNodes);
		subTreesList.remove(indexOfSubTree);
	}

	public void uniqueNodes() {

		for (String node : allNodesSet) {

			boolean isInSubTree = false;

			for (int j=0; j < subTreesList.size(); j++) {
				if (subTreesList.get(j).getNodes().contains(node)) {
					isInSubTree = true;
					break;
				}
			}

			if (isInSubTree == false) {
				SubTree st = new SubTree();
				st.addNode(node);
				subTreesList.add(st);
			}

		}
	}


	/**
	 * <h1>Reading Nodes</h1>
	 * Kontrola vstupu, pri spravnom vstupe ulozenie pomocou nodeAlreadyExists
	 * @param line - riadok zo vstupu, ktory by mal obsahovat len 1 uzol
	 */
	public void readingNodes(String line) {
		if (line.length() == 1) {
			if (Character.isLetter(line.charAt(0))) {

				if (this.nodeAlreadyExists(line)) {
					System.err.println("Uzol: " + line + " sa opakuje.");
				}

			} else {
				System.err.println("Pomenujte uzly pismenom.");
			}
		} else {
			System.err.println("Pomenovanie uzlom moze obsahovat len 1 pismeno.");
		}
	}


	public void readingNodes(String line, JLabel label) {
		if (line.length() == 1) {
			if (Character.isLetter(line.charAt(0))) {

				if (this.nodeAlreadyExists(line)) {
					warningLabel("Uzol " + line + " uz existuje.", label, Color.red);
				} else {
					warningLabel("Ulozenie uzlov prebehlo v poriadku", label, Color.green);
				}

			} else {
				warningLabel("Pomenujte uzly pismenom.", label, Color.red);
			}
		} else {
			warningLabel("Pomenovanie uzlom moze obsahovat len 1 pismeno.", label, Color.red);
		}
	}

	/**
	 * <h1>Reading Sub Trees</h1>
	 * Kontrola vstupu hran. Pri spravnom vstupe ulozenie do SubTree pomocou addToSubTree.
	 * @param line
	 */
	public void readingSubTrees(String line) {
		if ((line.length() == 3) && (line.charAt(1) == ',')) {

			String firstNode = Character.toString(line.charAt(0));
			String secondNode = Character.toString(line.charAt(2));

			if (!firstNode.equals(secondNode)) {

				if (allNodesSet.contains(firstNode) && allNodesSet.contains(secondNode)) {

					this.addToSubTree(firstNode, secondNode);


				} else {
					System.err.println("V ostrove sa nachadza uzol, ktory neexistuje.");
				}

			} else {
				System.err.println("V ostrove sa opakuje 2x jeden uzol.");
			}
		} else {
			System.err.println("Nespravny format zapisu ostrovov.");
		}
	}


	public void readingSubTrees(String line, JLabel label) {
		if ((line.length() == 3) && (line.charAt(1) == ',')) {

			String firstNode = Character.toString(line.charAt(0));
			String secondNode = Character.toString(line.charAt(2));

			if (!firstNode.equals(secondNode)) {

				if ((this.nodeAlreadyExists(firstNode)) && (this.nodeAlreadyExists(secondNode))) {

					this.addToSubTree(firstNode, secondNode);
					warningLabel("Uspesne pridane do ostrova.", label, Color.green);

				} else {
					warningLabel("V ostrove sa nachadza uzol, ktory neexistuje.", label, Color.red);
				}

			} else {
				warningLabel("V ostrove sa opakuje 2x jeden uzol.", label, Color.red);
			}
		} else {
			warningLabel("Nespravny format zapisu ostrovov.", label, Color.red);
			System.err.println("Nespravny format zapisu ostrovov.");
		}
	}


	public void warningLabel(String msg, JLabel label , Color color) {
		label.setForeground(color);
		label.setText(msg);
	}



	public void addToSubTree(String firstNode, String secondNode) {



		boolean isInAlreadyInSubTree = false;
		int indexOfSubTree = 0;


		if (!subTreesList.isEmpty()) {
			for (int i = 0; i < subTreesList.size(); i++) {

				if ((subTreesList.get(i).getNodes().contains(firstNode)) && (subTreesList.get(i).getNodes()).contains(secondNode)) {

					if (isInAlreadyInSubTree) {

						uniteSubTrees(indexOfSubTree,i, firstNode);

					} else {

						isInAlreadyInSubTree = true;
						indexOfSubTree = i;
					}



				} else if (subTreesList.get(i).getNodes().contains(firstNode)) {

					if (isInAlreadyInSubTree) {

						uniteSubTrees(indexOfSubTree,i, firstNode);

					} else {

						subTreesList.get(i).addNode(secondNode);
						isInAlreadyInSubTree = true;
						indexOfSubTree = i;

					}

				} else if (subTreesList.get(i).getNodes().contains(secondNode)) {

					if (isInAlreadyInSubTree) {

						uniteSubTrees(indexOfSubTree,i,secondNode);

					} else {

						subTreesList.get(i).addNode(firstNode);
						isInAlreadyInSubTree = true;
						indexOfSubTree = i;

					}
				}
			}


			if (!isInAlreadyInSubTree) {
				SubTree st = new SubTree();
				st.addNode(firstNode);
				st.addNode(secondNode);
				subTreesList.add(st);
			}



		} else {

			SubTree st = new SubTree();
			st.addNode(firstNode);
			st.addNode(secondNode);
			subTreesList.add(st);

		}
	}
}
