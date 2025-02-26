package takkee.dev.SpringbootVirtualThread.service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.stereotype.Service;

@Service
public class VirtualThreadService {

    private final int threadCount = Runtime.getRuntime().availableProcessors();
    private final ExecutorService executorService = Executors.newFixedThreadPool(threadCount*2);
    private final ExecutorService executorService2 = Executors.newVirtualThreadPerTaskExecutor();

    public long fixedThreadPool(int task) {
        long startTime = System.currentTimeMillis();
        System.out.println("newFixedThreadPool");
        CompletableFuture<?>[] futures = new CompletableFuture[task];
        for (int i = 0; i < task; i++) {
            final int taskId = i;
            futures[i] = CompletableFuture.supplyAsync(() -> {
                simulateTask(taskId);
                return "Task " + taskId + " completed by " + "Visual Thread";
            }, executorService);
        }
        CompletableFuture.allOf(futures).join();
        return System.currentTimeMillis() - startTime;
    }

    public long virtualThreadPerTaskExecutor(int task) {
        long startTime = System.currentTimeMillis();
        System.out.println("newVirtualThreadPerTaskExecutor");
        CompletableFuture<?>[] futures = new CompletableFuture[task];
        for (int i = 0; i < task; i++) {
            final int taskId = i;
            futures[i] = CompletableFuture.supplyAsync(() -> {
                simulateTask(taskId);
                return "Task " + taskId + " completed by " + "Visual Thread";
            }, executorService2);
        }
        CompletableFuture.allOf(futures).join();
        return System.currentTimeMillis() - startTime;
    }

    public void simulateTask(int taskId) {
        try {
            System.out.println(Thread.currentThread() + " virsual thread : task ID => " + taskId);
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
