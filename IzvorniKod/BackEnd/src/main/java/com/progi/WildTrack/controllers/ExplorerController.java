package com.progi.WildTrack.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/explorer")
@RequiredArgsConstructor
public class ExplorerController {

    @GetMapping("/test")
    public ResponseEntity<String> getExplorer() {
        return ResponseEntity.ok("Explorer");
    }
}
