package ma.fstm.ilisi.buswayticketchatbot.controller;

import ma.fstm.ilisi.buswayticketchatbot.dto.StationDTO;
import ma.fstm.ilisi.buswayticketchatbot.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NearestStationController {
    @Autowired
    private StationService stationService;

    public NearestStationController(StationService stationService) {
        this.stationService = stationService;
    }

    @GetMapping("nearestStation")
    public StationDTO getNearestStation(@RequestParam(value = "latitude", required = true) Double latitude,
                                       @RequestParam(value = "longitude", required = true) Double longitude) {
        return this.stationService.findNearest(latitude, longitude);
    }
}
