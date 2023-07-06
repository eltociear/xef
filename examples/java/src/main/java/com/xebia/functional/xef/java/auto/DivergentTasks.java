package com.xebia.functional.xef.java.auto;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class DivergentTasks {

    public Long numberOfMedicalNeedlesInWorld;

    private static CompletableFuture<Void> numberOfMedicalNedles(AIScope scope) {
        return scope.prompt("Provide the number of medical needles in the world", DivergentTasks.class)
              .thenAccept(numberOfNeedles -> System.out.println("Needles in world:\n" + numberOfNeedles.numberOfMedicalNeedlesInWorld));
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        try (AIScope scope = new AIScope()) {
            scope.contextScope(scope.search("Estimate amount of medical needles in the world").get(),
                  DivergentTasks::numberOfMedicalNedles).get();
        }
    }

}
