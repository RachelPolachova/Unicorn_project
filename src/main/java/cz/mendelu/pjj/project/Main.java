package cz.mendelu.pjj.project;

import java.io.*;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		if (args.length == 0) {

			//citam uzly zo standartneho vstupu

			Scanner sc = new Scanner(System.in);
			NodeProcessing np = new NodeProcessing();

			String line;
			boolean readingNodes = true;
			int emptyLines = 0;

			while (sc.hasNextLine()) {
				line = sc.nextLine().trim();

				if (line.length() == 0) {
					readingNodes = false;
					emptyLines += 1;
				}

				if (readingNodes) {

					np.readingNodes(line);

				} else if (emptyLines == 1) {
					//zabrani vypisovaniu chyby prveho prazdneho riadku
					emptyLines += 1;
				} else {
					np.readingSubTrees(line);
				}
			}

			np.printSubTrees();


		} else {

			//GUI

			if (args[0].compareToIgnoreCase("--gui") == 0) {

				NodeForm nodeForm = new NodeForm();
				nodeForm.runGUI();

			} else {

				if (args.length == 1 ) {

					//vstup zo suboru

					File f = new File(args[0]);
					NodeProcessing np = new NodeProcessing();
					int emptyLines = 0;


					if (f.exists()) {

						if (f.canRead()) {


							try (BufferedReader br = new BufferedReader(new FileReader(f))) {

								boolean readingNodes = true;

								String line = null;
								while ((line = br.readLine()) != null) {

									line = line.trim();

									if (line.length() == 0) {
										readingNodes = false;
										emptyLines +=1;
									}

									if (readingNodes) {
										np.readingNodes(line);
									} else if (emptyLines == 1){
										emptyLines +=1;
									} else {
										np.readingSubTrees(line);
									}
								}
							} catch (FileNotFoundException e) {
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							}


						} else {
							System.err.println("Program nema prava k citaniu suboru.");
						}

						np.printSubTrees();


					} else {
						System.err.println("Subor " + args[0] + "nenajdeny.");
						System.exit(0);
					}

				} else {
					System.err.println("Chybny pocet argumentov.");
				}
			}
		}

	}

}
