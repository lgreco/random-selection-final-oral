import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class RandomSelection {

    static private final String[] emailFirstThree = {
            "dah", "bba", "dba", "sbe", "obe",
            "mdi", "ret", "jha", "kjo",
            "jkn", "wlo", "alu", "jmc", "nqa", "lsa" };

    // Default number of students to select
    static private final int DEFAULT_SELECT_NUMBER = 8;
    // ANSI clear screen and move cursor to home position
    static private final String CLEAR_SCREEN = "\033[2J\033[H";
    // Default number of trials for simulation
    static private final int DEFAULT_TRIALS = 100;

    static private Random random = new Random();
    static private Scanner scanner = new Scanner(System.in);

    /**
     * Selects randomly a few entries from a list.
     * 
     * @param fromList       list with values to select a few of them randomly
     * @param numberToSelect int how many values to select randomly
     * @return list with randomly selected values
     */
    static private List<String> selectAtRandom(List<String> fromList, int numberToSelect) {
        // List to return
        List<String> selected = new ArrayList<>();
        // Copy of input list to avoid depleting original input via successive removals
        List<String> selectFrom = new ArrayList<>(fromList);
        // Remove items from the input copy at random
        for (int i = 0; i < numberToSelect; i++) {
            int index = random.nextInt(selectFrom.size());
            selected.add(selectFrom.remove(index));
        }
        // Done
        return selected;
    } // method selectAtRandom

    /**
     * Simulates random selections multiple times to determine the likelihood that a
     * user provided value will be selected. The theoretical probality is also
     * computed for camparison with the experimental outcome.
     * 
     * @param fromArray      String[] with data to randomly select from
     * @param trials         int number of simulations to run
     * @param numberToSelect int how many items to select randomly
     */
    static private void simulate(String[] fromArray, int trials, int numberToSelect) {
        // Guard statement
        if (numberToSelect > fromArray.length) {
            // Instead of throwing an exception, adjust the number of items to select to a
            // practical size, maybe half the length of the input array
            numberToSelect = fromArray.length / 2;
        }
        // Convert the input array to an ArrayList for easier removals and contains()
        List<String> selectFrom = new ArrayList<>(Arrays.asList(fromArray));
        // Obtain a value to see how likely it is to be selected
        String email = "";
        // Ensure that value is a valid element from the input arraylist.
        while (!selectFrom.contains(email)) {
            System.out.println(CLEAR_SCREEN);
            System.out.print("\nEnter the first three characters of your LUC email: ");
            email = scanner.nextLine().toLowerCase();
        }
        // Initialize counter for how many times email is found in randomly selected data
        int foundInSimulation = 0;
        // Perform the experiments
        for (int i = 0; i < trials; i++) {
            if (selectAtRandom(selectFrom, numberToSelect).contains(email)) {
                // The target email was found in this simulation, update the counter
                foundInSimulation += 1;
            }
        }
        // Report findings
        System.out.printf("\n\nOut of %d simulations, \"%s\" was selected %d times.",
                trials, email, foundInSimulation);
        System.out.printf("\nThat's a %.2f%% chance to be selected for a final 1-1 with Leo.",
                100.0 * foundInSimulation / (double) trials);
        System.out.printf("\nIn theory, the probability of \"%s\" being selected is %.2f%%.\n\n",
                email, 100.0 * numberToSelect / (double) selectFrom.size());
    } // method simulate

    public static void main(String[] args) {
        simulate(emailFirstThree, DEFAULT_TRIALS, DEFAULT_SELECT_NUMBER);
    }
}