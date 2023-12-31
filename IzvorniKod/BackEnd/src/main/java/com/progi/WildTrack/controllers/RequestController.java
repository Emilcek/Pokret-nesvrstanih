package com.progi.WildTrack.controllers;

import com.progi.WildTrack.dto.CreateRequestDTO;
import com.progi.WildTrack.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/request")
@CrossOrigin(origins = "${FRONTEND_API_URL}")
@RequiredArgsConstructor
public class RequestController {

}
