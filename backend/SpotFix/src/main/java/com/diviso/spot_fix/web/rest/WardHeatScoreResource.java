package com.diviso.spot_fix.web.rest;

import com.diviso.spot_fix.service.WardHeatScoreService;
import com.diviso.spot_fix.service.dto.WardHeatScoreDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wards")
public class WardHeatScoreResource {

    private final WardHeatScoreService wardHeatScoreService;

    public WardHeatScoreResource(
        WardHeatScoreService wardHeatScoreService
    ) {
        this.wardHeatScoreService = wardHeatScoreService;
    }

    @GetMapping("/{wardId}/heat-score")
    public ResponseEntity<WardHeatScoreDTO> getHeatScore(
        @PathVariable Long wardId
    ) {
        return ResponseEntity.ok(
            wardHeatScoreService.calculateHeatScore(wardId)
        );
    }
}