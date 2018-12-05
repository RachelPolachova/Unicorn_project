package cz.mendelu.pjj.project;

import org.junit.Test;

import static org.junit.Assert.*;

public class NodeProcessingTest {

	@Test
	public void nodeAlreadyExists() {

		NodeProcessing np = new NodeProcessing();

		np.allNodesSet.add("x");

		boolean result1 = np.nodeAlreadyExists("a");
		boolean result2 = np.nodeAlreadyExists("b");
		boolean result3 = np.nodeAlreadyExists("a");
		boolean result4 = np.nodeAlreadyExists("x");

		assertEquals(result1,false);
		assertEquals(result2,false);
		assertEquals(result3,true);
		assertEquals(result4,true);

	}


	@Test
	public void readingNodes() {

		NodeProcessing np = new NodeProcessing();

		np.readingNodes("a");
		np.readingNodes("a");
		np.readingNodes("bc");
		np.readingNodes(".");
		np.readingNodes("x");
		String result1 = np.allNodesSet.toString();
		String expectedResult1 = "[a, x]";
		int result2 = np.allNodesSet.size();
		int expectedResult2 = 2;

		assertEquals(result1,expectedResult1);
		assertEquals(result2,expectedResult2);

	}

	@Test
	public void addToSubTree() {

		NodeProcessing np = new NodeProcessing();

		np.nodeAlreadyExists("a");
		np.nodeAlreadyExists("b");
		np.nodeAlreadyExists("c");
		np.nodeAlreadyExists("d");
		np.nodeAlreadyExists("e");

		np.addToSubTree("a","b");
		np.addToSubTree("c","d");
		np.addToSubTree("e","c");

		int result1 = np.subTreesList.size();
		int expectedResult1 = 2;

		for (int i=0;i < np.subTreesList.size(); i++) {
			System.out.println(np.subTreesList.get(i).getNodes());
		}

		assertEquals(result1,expectedResult1);

	}

	@Test
	public void readingSubTrees() {

		NodeProcessing np = new NodeProcessing();

		np.allNodesSet.add("a");
		np.allNodesSet.add("b");
		np.allNodesSet.add("c");
		np.allNodesSet.add("d");
		np.allNodesSet.add("e");
		np.allNodesSet.add("r");
		np.allNodesSet.add("s");

		np.readingSubTrees("r,r");
		np.readingSubTrees("a,b");
		np.readingSubTrees("a,x");
		np.readingSubTrees("c,d");
		np.readingSubTrees("e,c");
		np.readingSubTrees("p,,u");

		int result1 = np.subTreesList.size();
		int expectedResult1 = 2;
		boolean result2 = np.subTreesList.contains("x");
		boolean expectedResult2 = false;
		boolean result3 = np.subTreesList.contains("p") || np.subTreesList.contains("u");
		boolean expectedResult3 = false;
		boolean result4 = np.subTreesList.contains("r");
		boolean expectedResult4 = false;

		assertEquals(result1,expectedResult1);
		assertEquals(result2,expectedResult2);
		assertEquals(result3,expectedResult3);
		assertEquals(result4,expectedResult4);
	}
}
