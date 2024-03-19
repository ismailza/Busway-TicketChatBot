package ma.fstm.ilisi.buswayticketchatbot.controller;

import ma.fstm.ilisi.buswayticketchatbot.dto.TripDTO;
import ma.fstm.ilisi.buswayticketchatbot.service.BusService;
import ma.fstm.ilisi.buswayticketchatbot.service.StationService;
import ma.fstm.ilisi.buswayticketchatbot.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class TripController {
    @Autowired
    private BusService busService;
    @Autowired
    private StationService stationService;
    private TripService tripService;

    public TripController(BusService busService, StationService stationService, TripService tripService) {
        this.busService = busService;
        this.stationService = stationService;
        this.tripService = tripService;
    }

    @GetMapping("/trips")
    public String getTrips(Model model){

        return "trips";
    }

    @PostMapping("/search")
    public String getAvailableTrips(@ModelAttribute Long fromId, Long toId, Model model) {
        List<TripDTO> trips = this.tripService.findAll(fromId, toId);
        model.addAttribute("trips", trips);
        return "availableTrips";
    }

    @GetMapping("/trip/new")
    public String create(Model model){
        model.addAttribute("buses", this.busService.findAll());
        model.addAttribute("stations", this.stationService.findAll());
        return "tripForm";
    }

    @PostMapping("/trip/save")
    public String save(@ModelAttribute TripDTO trip, RedirectAttributes redirectAttributes) {
        this.tripService.save(trip);
        redirectAttributes.addFlashAttribute("success", "Trip successfully created!");
        return "redirect:/trips";
    }

}
