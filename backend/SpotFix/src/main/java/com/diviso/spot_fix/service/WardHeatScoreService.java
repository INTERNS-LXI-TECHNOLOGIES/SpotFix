package com.diviso.spot_fix.service;

import com.diviso.spot_fix.service.dto.WardHeatScoreDTO;

public interface WardHeatScoreService {

    WardHeatScoreDTO calculateHeatScore(Long wardId);
}