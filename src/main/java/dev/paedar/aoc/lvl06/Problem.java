package dev.paedar.aoc.lvl06;

public sealed interface Problem permits SumProblem, ProductProblem {

    long solution();

}
