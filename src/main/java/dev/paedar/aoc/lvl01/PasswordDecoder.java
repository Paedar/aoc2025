package dev.paedar.aoc.lvl01;

public class PasswordDecoder {

    private static final int DIAL_SIZE = 100;

    private int dialPosition;
    private int countTimesDialWasEqualToZero;

    public PasswordDecoder() {
        dialPosition = 50;
        countTimesDialWasEqualToZero = 0;
    }

    public int getPassword() {
        return countTimesDialWasEqualToZero;
    }

    public void process(Instruction instruction) {
        var action = getAction(instruction);
        dialPosition += action;
        ensureWithinRange();

        if(dialPosition == 0) {
            ++countTimesDialWasEqualToZero;
        }
    }

    public void processMethod0x434C49434B(Instruction instruction) {
        var action = getAction(instruction);
        while(action != 0) {
            switch (action) {
                case int a when a > 0 -> {
                    positiveTick();
                    --action;
                }
                case int a when a < 0 -> {
                    negativeTick();
                    ++action;
                }
                default -> throw new IllegalStateException("Unexpected action: " + action);
            }
        }
        ensureWithinRange();
    }

    public void positiveTick() {
        ++dialPosition;
        if(dialPosition % DIAL_SIZE == 0) {
            ++countTimesDialWasEqualToZero;
        }
    }

    public void negativeTick() {
        --dialPosition;
        if(dialPosition % DIAL_SIZE  == 0) {
            ++countTimesDialWasEqualToZero;
        }
    }

    private static int getAction(Instruction instruction) {
        return switch (instruction) {
            case LeftTurn(int value) -> -value;
            case RightTurn(int value) -> value;
        };
    }

    private void ensureWithinRange() {
        while(dialPosition < 0) {
            dialPosition += DIAL_SIZE;
        }
        dialPosition %= DIAL_SIZE;
    }

}
