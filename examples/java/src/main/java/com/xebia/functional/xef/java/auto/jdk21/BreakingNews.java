package com.xebia.functional.xef.java.auto.jdk21;

import com.xebia.functional.xef.java.auto.AIScope;
import com.xebia.functional.xef.java.auto.ExecutionContext;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class BreakingNews {

    static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/M/yyyy");
    static LocalDateTime now = LocalDateTime.now();

    public record BreakingNew(String summary){}

    private static CompletableFuture<Void> writeParagraph(AIScope scope) {
        var currentDate = dtf.format(now);

        return scope.prompt("write a paragraph of about 300 words about: " + currentDate + " Covid News", BreakingNews.BreakingNew.class)
              .thenAccept(breakingNews -> System.out.println(currentDate + " Covid news summary:\n" + breakingNews));
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        try (AIScope scope = new AIScope(new ExecutionContext(Executors.newVirtualThreadPerTaskExecutor()))) {
            var currentDate = dtf.format(now);
            scope.contextScope(scope.search(currentDate + " Covid News"), BreakingNews::writeParagraph).get();
        }
    }
}