package ma.fstm.ilisi.buswayticketchatbot.controller;

import com.google.zxing.WriterException;
import ma.fstm.ilisi.buswayticketchatbot.dto.PassengerDTO;
import ma.fstm.ilisi.buswayticketchatbot.dto.TripForm;
import ma.fstm.ilisi.buswayticketchatbot.service.BusService;
import ma.fstm.ilisi.buswayticketchatbot.service.StationService;
import ma.fstm.ilisi.buswayticketchatbot.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
public class TripController {
    @Autowired
    private BusService busService;
    @Autowired
    private StationService stationService;
    @Autowired
    private TripService tripService;

    public TripController(BusService busService, StationService stationService, TripService tripService) {
        this.busService = busService;
        this.stationService = stationService;
        this.tripService = tripService;
    }

    @GetMapping("/trips")
    public String getTrips(Model model){
        model.addAttribute("trips", this.tripService.findAll());
        return "trips";
    }

    @PostMapping("/search")
    public String getAvailableTrips(@RequestParam(value = "fromId", required = true) Long fromId,
                                    @RequestParam(value = "toId", required = true) Long toId,
                                    Model model, RedirectAttributes redirectAttributes) {
        List<TripForm> trips = this.tripService.findAll(fromId, toId);
        if (trips.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "No trips available");
            return "redirect:/";
        }
        model.addAttribute("trips", trips);
        return "availableTrips";
    }

    @PostMapping("/trip/confirmBooking")
    public String confirmBooking(@RequestParam(value = "busMatriculation", required = true) Long busMatriculation,
                              @RequestParam(value = "departureId", required = true) Long departureId,
                              @RequestParam(value = "arrivalId", required = true) Long arrivalId,
                              Model model) {
        model.addAttribute("busMatriculation", busMatriculation);
        model.addAttribute("departureId", departureId);
        model.addAttribute("arrivalId", arrivalId);
        return "reserve";
    }

    @PostMapping("/trip/reserve")
    public String reserve(@RequestParam(value = "firstname", required = true) String firstname,
                          @RequestParam(value = "lastname", required = true) String lastname,
                          @RequestParam(value = "email", required = true) String email,
                          @RequestParam(value = "busMatriculation", required = true) Long busMatriculation,
                          @RequestParam(value = "departureId", required = true) Long departureId,
                          @RequestParam(value = "arrivalId", required = true) Long arrivalId,
                          RedirectAttributes redirectAttributes) {
        PassengerDTO passengerDTO = PassengerDTO.builder()
                .firstname(firstname)
                .lastname(lastname)
                .email(email)
                .build();
        redirectAttributes.addAttribute("ticket", this.tripService.reserve(busMatriculation, departureId, arrivalId, passengerDTO));
        return "redirect:/ticket";
    }

    @GetMapping("ticket")
    public String ticket(@RequestParam(value = "ticket", required = true) String ticket,
                         Model model) {
        try {
            String QRCode = this.tripService.createQRCode(ticket);
            model.addAttribute("QRCode", QRCode);
            model.addAttribute("success", "Trip successfully reserved!");
        } catch (WriterException | IOException e) {
            model.addAttribute("success", e.getMessage());
            e.printStackTrace();
        }
        return "ticket";
    }

    @GetMapping("/trip/new")
    public String create(Model model){
        model.addAttribute("buses", this.busService.findAll());
        model.addAttribute("stations", this.stationService.findAll());
        return "tripForm";
    }

    @PostMapping("/trip/save")
    public String save(@ModelAttribute TripForm trip, RedirectAttributes redirectAttributes) {
        this.tripService.save(trip);
        redirectAttributes.addFlashAttribute("success", "Trip successfully created!");
        return "redirect:/trips";
    }

}
