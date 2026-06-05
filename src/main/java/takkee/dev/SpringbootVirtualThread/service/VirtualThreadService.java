package takkee.dev.SpringbootVirtualThread.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class VirtualThreadService {

    private final int platformThreadCount = Runtime.getRuntime().availableProcessors();
    private final ExecutorService fixedThreadPoolExecutor = Executors.newFixedThreadPool(platformThreadCount * 2);
    private final ExecutorService virtualThreadPerTaskExecutor = Executors.newVirtualThreadPerTaskExecutor();

    public long fixedThreadPool(int taskCount) {
        long startTime = System.currentTimeMillis();
        log.info("Testing with Fixed Thread Pool. Available Processors: {}", platformThreadCount);
        CompletableFuture<?>[] futures = new CompletableFuture[taskCount];
        for (int i = 0; i < taskCount; i++) {
            final int taskId = i;
            futures[i] = CompletableFuture.supplyAsync(() -> {
                simulateTask(taskId);
                return "Task " + taskId + " completed by Platform Thread";
            }, fixedThreadPoolExecutor);
        }
        CompletableFuture.allOf(futures).join();
        return System.currentTimeMillis() - startTime;
    }

    public long virtualThreadManual(int taskCount) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < taskCount; i++) {
            final int taskId = i;
            Thread thread = Thread.startVirtualThread(() -> simulateTask(taskId));
            threads.add(thread);
        }
        for (Thread thread : threads) {
            thread.join();
        }
        log.info("Total Virtual Threads spawned: {}", threads.size());
        return System.currentTimeMillis() - startTime;
    }

    public long virtualThreadPerTaskExecutor(int taskCount) {
        long startTime = System.currentTimeMillis();
        log.info("Testing with Virtual Thread Per Task Executor");

        CompletableFuture<?>[] futures = new CompletableFuture[taskCount];
        for (int i = 0; i < taskCount; i++) {
            final int taskId = i;
            futures[i] = CompletableFuture.supplyAsync(() -> {
                simulateTask(taskId);
                return "Task " + taskId + " completed by Virtual Thread";
            }, virtualThreadPerTaskExecutor);
        }
        CompletableFuture.allOf(futures).join();
        return System.currentTimeMillis() - startTime;
    }

    public String oneThreadSync(int taskCount) {
        for (int i = 0; i < taskCount; i++) {
            simulateTask(i);
        }
        return "Success";
    }

    private void simulateTask(int taskId) {
        try {
            log.info("Thread: {} | Task ID: {} | Status: Starting", Thread.currentThread().getName(), taskId);
            Thread.sleep(1000); // จำลองการรอ (Blocking I/O)
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Task {} interrupted", taskId);
        }
    }
}