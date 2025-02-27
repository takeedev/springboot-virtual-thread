package takkee.dev.SpringbootVirtualThread.service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class VirtualThreadService {

    private final int threadCount = Runtime.getRuntime().availableProcessors();
    private final ExecutorService executorService = Executors.newFixedThreadPool(threadCount * 2);
    private final ExecutorService executorService2 = Executors.newVirtualThreadPerTaskExecutor();

    public long fixedThreadPool(int task) {
        long startTime = System.currentTimeMillis();
        log.info("newFixedThreadPool");
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
        log.info("newVirtualThreadPerTaskExecutor");
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

    public String oneThread(int param) {
        Thread thread = new Thread();
        thread.start();
        for (int i = 0; i < param; i++) {
            simulateTask(i);
        }
        return "Success";
    }

    public void simulateTask(int taskId) {
        try {
            log.info(Thread.currentThread() + " virsual thread : task ID => " + taskId);
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
