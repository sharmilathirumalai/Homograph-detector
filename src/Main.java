import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

import domain.AdjancentGraph;

public class Main {
	public static void main(String[] args) throws Exception {
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter the domain names separated by comma");
		String[] domainnames = scan.nextLine().split(",");
		AdjancentGraph graph = new AdjancentGraph();
		graph.load(new HashSet<>(Arrays.asList(domainnames)));
		while(true) {
			displayOptions();
			int option = Integer.parseInt(scan.nextLine());
			String domainName1;
			String domainName2;
			int maxDistance;
			
			switch (option) {
			case 1:
				System.out.println("Enter the src & dest domain");
				domainName1 = scan.nextLine();
				domainName2 = scan.nextLine();
				System.out.println(graph.editDistance(domainName1, domainName2));
				break;
			case 2:
				System.out.println("Enter the src & dest domain");
				domainName1 = scan.nextLine();
				domainName2 = scan.nextLine();
				String[] path = graph.editPath(domainName1, domainName2);
				for(String s: path) {
					System.out.println(s);
				}
				break;
			case 3:
				System.out.println("Enter the src domain and limit");
				domainName1 = scan.nextLine();
				maxDistance = Integer.parseInt(scan.nextLine());
				System.out.println(graph.numNearby(domainName1, maxDistance));
				break;
			case 4:
				System.out.println("Terminated..");
				scan.close();
				return;
			}
		}
	}
	
	static void displayOptions() {
		System.out.println("\nChoose an Option");
		System.out.println("1. editDistance");
		System.out.println("2. editPath");
		System.out.println("3. numNearby");
		System.out.println("4. exit");
	}
}