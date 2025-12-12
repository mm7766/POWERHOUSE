import java.util.*;

public class Q6_PriorityQueueHospital {

    public static void main(String[] args) {

        LinkedList<Integer> critical = new LinkedList<>();
        LinkedList<Integer> normal = new LinkedList<>();
        Map<Integer, Integer> severityMap = new HashMap<>();

        Scanner sc = new Scanner(System.in);

        int totalPatients = 0;
        int nextId = 1;

        while (true) {
            System.out.println("\n1. Join Queue");
            System.out.println("2. Send to Visit Doctor");
            System.out.println("3. Check First Patient");
            System.out.println("4. Remaining Patients");
            System.out.println("5. Exit");

            System.out.print("\nChoose: ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {

                case 1:
                    System.out.print("Enter severity (1-10): ");
                    int sev = sc.nextInt();
                    sc.nextLine();

                    int id = nextId++;
                    severityMap.put(id, sev);

                    if (sev >= 8) {
                        critical.addLast(id);
                    } else {
                        normal.addLast(id);
                    }

                    totalPatients++;
                    System.out.println("JOINED ID = " + id + " Severity = " + sev);
                    break;

                case 2:
                    Integer treatId = null;

                    if (!critical.isEmpty()) {
                        treatId = critical.pollFirst();
                    } else if (!normal.isEmpty()) {
                        treatId = normal.pollFirst();
                    }

                    if (treatId == null) {
                        System.out.println("Queue is empty.");
                    } else {
                        int s = severityMap.remove(treatId);
                        totalPatients--;
                        System.out.println("Sent to Doctor : ID = " + treatId + " Severity = " + s);
                    }
                    break;

                case 3:
                    if (!critical.isEmpty()) {
                        int first = critical.peekFirst();
                        System.out.println("Next: ID = " + first + " Severity = " + severityMap.get(first));
                    } else if (!normal.isEmpty()) {
                        int first = normal.peekFirst();
                        System.out.println("Next: ID = " + first + " Severity = " + severityMap.get(first));
                    } else {
                        System.out.println("Queue empty.");
                    }
                    break;

                case 4:
                    System.out.println("Remaining Patients = " + totalPatients);
                    System.out.println(" - Critical: " + critical.size());
                    System.out.println(" - Normal: " + normal.size());
                    break;

                case 5:
                    System.out.println("Closing...");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
