import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class RandomSelection {

    // Input data -- first three characters of students' LUC email addresses
    private static final String[] EMAIL_FIRST_THREE = {
            "dah", "bba", "dba", "sbe", "obe",
            "mdi", "ret", "jha", "kjo",
            "jkn", "wlo", "alu", "jmc", "nqa", "lsa"
    };

    /** Default number of students to select */
    private static final int DEFAULT_SELECT_NUMBER = 8;
    /** ASCII escape + ANSI codes to clear screen */
    private static final String CLEAR_SCREEN = "\033[2J\033[H";
    /** How many simulations to run */
    private static final int DEFAULT_TRIALS = 100;

    /** Random number for shuffling the list of students */
    private static final Random random = new Random();
    /** We'll need a scanner to read data from the keyboard */
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Selects a specified number of random, non-repeating items from a list.
     *
     * @param fromList       the list to select from
     * @param numberToSelect number of items to randomly select
     * @return list of randomly selected items
     */
    private static List<String> selectAtRandom(List<String> fromList, int numberToSelect) {
        // Create a copy of the list of email addresses. A list is a bit more flexible
        // to manipulate an a basic array.
        List<String> copy = new ArrayList<>(fromList);
        // Randomly shuffle the copy of the list of email addresses. If we were using an
        // array, this random shuffling would have required a few lines of code probably
        // as a separate method.
        Collections.shuffle(copy, random);
        // Select the first few elements of the randomly shuffled list and return them.
        return copy.subList(0, numberToSelect);
    } // method selectAtRandom

    /**
     * Runs a simulation of random selections to estimate how likely a target
     * item is to be picked.
     *
     * @param fromArray      array of input items to select from
     * @param trials         number of simulation trials
     * @param numberToSelect number of items selected per trial
     */
    private static void simulate(String[] fromArray, int trials, int numberToSelect) {
        // Guard statement: there are fewer students than those we want to select
        // randomly, cut the target number to half the size of the array.
        if (numberToSelect > fromArray.length) {
            numberToSelect = fromArray.length / 2;
        }

        // Convert the array into a list. It will make things easier when we need to
        // find if the email entered by the user is valid, ie, exists in the list of
        // students.
        List<String> selectFrom = new ArrayList<>(Arrays.asList(fromArray));
        // Create a local (deep) copy of the input array(list) ensuring lower case
        List<String> selectFromLower = new ArrayList<>();
        for (String s : selectFrom) {
            selectFromLower.add(s.toLowerCase());
        }
        // Ask the user for their email's first three characters. Keep asking as long as
        // they type something that does not exist in the list of emails. Rare
        // opportunity to use do-while!
        String email;
        do {
            System.out.print(CLEAR_SCREEN);
            System.out.print("\nEnter the first three characters of your LUC email: ");
            email = scanner.nextLine().trim().toLowerCase();
        } while (!selectFromLower.contains(email));
        // Run the simulations by initialize a counter for how many times the entered
        // email appears in the randomly selected list.
        int foundInSimulation = 0;
        for (int i = 0; i < trials; i++) {
            if (selectAtRandom(selectFromLower, numberToSelect).contains(email)) {
                foundInSimulation++;
            }
        }
        // Prepare to report findings
        double empiricalProbability = 100.0 * foundInSimulation / trials;
        double theoreticalProbability = 100.0 * numberToSelect / selectFrom.size();
        // Report
        System.out.printf(
                "\n\nOut of %d simulations, \"%s\" was selected %d times.",
                trials, email, foundInSimulation);
        System.out.printf(
                "\nThat's a %.2f%% chance to be selected for a final 1-1 with Leo.",
                empiricalProbability);
        System.out.printf(
                "\nIn theory, the probability of \"%s\" being selected is %.2f%%.\n\n",
                email, theoreticalProbability);
    } // method simulate

    public static void main(String[] args) {
        simulate(EMAIL_FIRST_THREE, DEFAULT_TRIALS, DEFAULT_SELECT_NUMBER);
    } // method main
} // class RandomSelection