package com.flowwise.controller;

import com.flowwise.dto.ApiResponse;
import com.flowwise.dto.CommandCenterSnapshotDTO;
import com.flowwise.service.CommandCenterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/merchants/{merchantId}/command-center")
@CrossOrigin(origins = "*")
public class CommandCenterController {

    private final CommandCenterService commandCenterService;

    public CommandCenterController(CommandCenterService commandCenterService) {
        this.commandCenterService = commandCenterService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<CommandCenterSnapshotDTO>> getCommandCenterSnapshot(
            @PathVariable Long merchantId) {

        CommandCenterSnapshotDTO snapshot = commandCenterService.getCommandCenterSnapshot(merchantId);
        return ResponseEntity.ok(ApiResponse.success(snapshot));
    }
}
