import java.util.HashMap;
import java.util.Map;

public class TuringMachine {
    private char[] tape;
    private int head;
    private String currentState;

    private Map<String, Transition> transitionFunction;

    private static final char BLANK = '_';

    public TuringMachine(String input, String initialState) {
        this.tape = (input + "______").toCharArray();
        this.head = 0;
        this.currentState = initialState;
        this.transitionFunction = new HashMap<>();
    }
    public void addTransition(String currentState, char currentSymbol, String newState, char newSymbol, char direction) {
        String key = currentState + currentSymbol;
        transitionFunction.put(key, new Transition(newState, newSymbol, direction));
    }
    public void run() {
        while (!currentState.equals("HALT")) {
            char currentSymbol = tape[head];
            String key = currentState + currentSymbol;

            if (!transitionFunction.containsKey(key)) {
                System.out.println("No valid transition found. Halting...");
                break;
            }

            Transition transition = transitionFunction.get(key);

            tape[head] = transition.newSymbol;

            if (transition.direction == 'R') {
                head++;
            } else if (transition.direction == 'L') {
                head--;
            }

            currentState = transition.newState;

            if (head < 0 || head >= tape.length) {
                System.out.println("Head moved out of bounds. Halting...");
                break;
            }
        }
    }

    public void printTape() {
        System.out.println("Tape: " + new String(tape));
        System.out.println("Head position: " + head);
    }

    class Transition {
        String newState;
        char newSymbol;
        char direction;

        Transition(String newState, char newSymbol, char direction) {
            this.newState = newState;
            this.newSymbol = newSymbol;
            this.direction = direction;
        }
    }

    public static void main(String[] args) {
        TuringMachine tm = new TuringMachine("1011", "q0");

        tm.addTransition("q0", '1', "q1", '0', 'R');
        tm.addTransition("q1", '0', "q2", '1', 'R');
        tm.addTransition("q2", '1', "HALT", '1', 'N');
        tm.run();

        tm.printTape();
    }
}