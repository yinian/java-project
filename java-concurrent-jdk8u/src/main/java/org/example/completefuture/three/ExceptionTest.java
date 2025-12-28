package org.example.completefuture.three;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import static org.junit.Assert.*;

public class ExceptionTest {

    private static final List<Class<? extends Throwable>> EXPECTED = Arrays.asList(
            CompletionException.class,
            IllegalArgumentException.class
    );

    @Test
    public void testExceptionSimple() {
        CompletableFuture<Object> future = new CompletableFuture<>();
        future.completeExceptionally(new IllegalArgumentException());
        assertEquals(IllegalArgumentException.class, Util.exceptionFromCallback(future).getClass());
        Util.assertException(future, Arrays.asList(CompletionException.class, IllegalArgumentException.class));

        // Wrapped, since it has gotten a transform operation!
        CompletableFuture<Object> future2 = future.thenApply(x -> x);
        assertEquals(CompletionException.class, Util.exceptionFromCallback(future2).getClass());
        Util.assertException(future2, Arrays.asList(CompletionException.class, IllegalArgumentException.class));

        // Same for rethrowing the exception in a transform
        CompletableFuture<Object> future3 = future.exceptionally(e -> Util.doThrow((RuntimeException) e));
        assertEquals(CompletionException.class, Util.exceptionFromCallback(future3).getClass());
        Util.assertException(future3, Arrays.asList(CompletionException.class, IllegalArgumentException.class));

    }
}
