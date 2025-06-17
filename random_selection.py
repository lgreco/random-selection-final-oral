# Data: first three characters of student emails

emails_first_three = [
    "lbo", "lbr", "hbu", "ccu", "odi", "ago", "mgr", "bha", "ehe",
    "eme", "eob", "dra", "ara", "fri", "lro", "rsa", "ata", "aus", "kny"
]

# We'll need a random number general and the deep copy function
import random, copy

# DEFAULT number of students to invite
N = 8

def select_at_random(from_list: list[str], number_of_students = N: int) -> list[str]:
  # initialize return list
  selected = list()
  # copy the input
  select_from = copy.deep_copy(from_list)
  # select at random
  while len(selected) < number_of_students:
    # pick a random element from input copy
    selected_index = random.rand(0, len(select_from)-1)
    selected_student = select_from.pop(selected_index)
    selected.append(selected_student)
    print(f"\n{selected_index=}\n{selected_student}=")
  return selected

# Test
print(selected_at_random(emails_first_three))
