package dev.paedar.aoc.lvl01;

public sealed interface Instruction permits LeftTurn, RightTurn {

    int value();

}
