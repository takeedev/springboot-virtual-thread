package takkee.dev.SpringbootVirtualThread.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import takkee.dev.SpringbootVirtualThread.service.VirtualThreadService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/virthread")
public class VirtualThreadController {

    private final VirtualThreadService service;

    @GetMapping(value = "fixedThreadPool")
    public Long fixedThreadPool(@RequestParam @Schema(example = "200") int task) {
        return service.fixedThreadPool(task);
    }
    
    @GetMapping(value = "virtualThreadPerTaskExecutor")
    public Long virtualThreadPerTaskExecutor(@RequestParam @Schema(example = "200") int task) {
        return service.virtualThreadPerTaskExecutor(task);
    }

}
