
# We'll need a random number general and the deep copy function
import random, copy

# DEFAULT number of students to invite
DEFAULT_NUMBER_SELECTED = 8

# Number of simulations to perform
DEFAULT_TRIALS = 100

# ANSI clear screen and move cursor to home position
CLEAR_SCREEN = "\033[2J\033[H"

def select_at_random(from_list: list[str], 
                     number_of_students: int) -> list[str]:
    """Selects a given number of values from a the input list at random."""
    
    if number_of_students > len(from_list):
        raise ValueError(
            f"\n\nYou cannot select more items than there are in the input list" +
            "\nThere are {len(from_list)} items and you want to select {number_of_students}, really?\n\n")

    # Make a deep copy to avoid modifying the original list
    select_from = copy.deepcopy(from_list)

    # Use random.sample to select without replacement
    selected = random.sample(select_from, number_of_students)

    return selected
    # end method select_at_random

def simulate(from_list: list[str], 
             trials: int = DEFAULT_TRIALS, 
             number_to_select: int = DEFAULT_NUMBER_SELECTED) -> None:
    """Perform a simulation to compute how likely it is for 
    someone to be selected at random.
    """
    print(CLEAR_SCREEN)

    # Obtain a valid email prefix (first three characters)
    email = ""
    while email not in from_list:
        email = input(f"\nWhat's the first three characters of your LUC email? ")
    
    # make sure input is in lower case because 
    email = email.lower()

    # Initialize experiment outcome count
    count_selected = 0
    
    # Perform the experiments
    for trial in range(trials):
        if email in select_at_random(from_list, number_to_select):
            # The given email was randomly selected in this experiment
            count_selected += 1
    
    # Report findings
    print(f"\nOut of {trials} simulations, \"{email}\" was selected {count_selected} times.")
    print(f"That's a {100*count_selected/trials:.2f}% chance to be selected for a final 1-1 with Leo.")
    print(f"In theory, the probability of \"{email}\" being selected is {100*number_to_select/len(from_list):.2f}%.\n\n")
    # end method simulate


# Data: first three characters of student emails
emails_first_three = [
    "lbo", "lbr", "hbu", "ccu", "odi", "ago", "mgr", "bha", "ehe",
    "eme", "eob", "dra", "ara", "fri", "lro", "rsa", "ata", "aus", "kny"
]

# Run the simulation
if __name__ == "__main__":
    simulate(emails_first_three)