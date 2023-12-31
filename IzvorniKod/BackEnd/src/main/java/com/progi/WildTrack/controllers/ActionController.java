package com.progi.WildTrack.controllers;

import com.progi.WildTrack.dto.CreateActionDTO;
import com.progi.WildTrack.service.ActionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/action")
@CrossOrigin(origins = "${FRONTEND_API_URL}")
@RequiredArgsConstructor
public class ActionController {

    private ActionService service;

}
