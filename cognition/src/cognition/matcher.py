"""
Programmer: Zachary Champion
Project:    Cognitive Science & AI
File Description:
- Matcher function takes three parameters: a "pattern" frame, a "constant" frame, and a "binding"
frame. It looks at the variables (if any) in the pattern, binds values to the variables as defined
in the binding, and then checks to see if that matches the constant frame.
Notes:
- In this file, I'm experimenting with a new technique for readability inspired by Dr. Jody Paul's
Clojure code. I'm making more simple functions that return specialized data pieces and describe in
the name of the function what data it's returning. This should make the code more readable by
describing what the piece of code is doing more in the code itself, but may make the code more
fragmented and less readable to an programmer experienced in python.
"""

def bullshit():
    """ The traditional start to proper code.
    Please remember to remove this before submitting a damn thing.
    If this somehow makes it to submission, Dr. Paul, I am sorry for my inattention!
    """
    print("Bullshit")

def is_var(string):
    return string.startswith("?")

def var_name(variable):
    return variable[1:]

def frame_header(frame):
    return frame[0]

def frame_body(frame):
    return frame[1]

def find_binding(variable_name, bindings):
    """ Function tries to find the variable in the bindings it is given.
    If it can't find the binding, returns False. """
    assert(is_var(variable_name))

    # Look to see if the variable is already in the binding form.
    for binding in bindings:
        if var_name(variable_name) == binding[0]:  # Looks for the variable name after the '?'
            return binding

    # Otherwise, report that the variable was not in the binding form.
    return False

def extract_pairs(pattern):
    """ Gets all of the pairs with variables in them from the pattern body.
    Returns a list of the pairs. """
    pattern_pairs = []

    for element in pattern:
        if is_var(frame_body(pattern)):
            pattern_pairs.append(element)

    return pattern_pairs

def match_var(variable, constant, bindings):
    """ Matches a variable and a constant in the context of a binding form. """
    return bullshit()

def match_args(pattern_pairs, constant, bindings):
    """ Matches role pairs in the context of a binding form.
    Takes a list of role pairs, a constant frame, and a binding form. It goes through the pairs and
    matches each pair against the corresponding role pairs in the constant form. All such pairs must
    match. """
    return bullshit()

def matcher(pattern, constant, bindings):
    """ Determines whether the pattern matches the constant given the current binding form and
    returns the updated binding form. """
    return bullshit()

if __name__ == "__main__":
    bullshit()  # Remove this as well!!! <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    test_pattern = ["GO", ["ACTOR", "?PERSON"], ["TO", "?STORE"]]
    test_constant = ["GO", ["ACTOR", ["PERSON", ["NAME", "JACK"]]], ["TO", "STORE"]]
    test_binding = [["SHOPPER", "PERSON"], ["STORE", "STORE"]]

    ###################################### TESTING SECTION #########################################
    print("bind_variable test:")
    print(find_binding("?SHOPPER", test_binding) == ["SHOPPER", "PERSON"])
    print("extract_pairs test:")
    print(extract_pairs(test_pattern) == [["ACTOR", "PERSON"], ["TO", "STORE"]])
    print("match_var test:")
    print(match_var("?PERSON", ["PERSON", ["NAME", "JACK"]], test_binding) == \
        [["SHOPPER", "PERSON"], ["STORE", "STORE"], ["PERSON", ["NAME", "JACK"]]])
    ################################################################################################
