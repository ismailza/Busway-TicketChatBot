package ma.fstm.ilisi.buswayticketchatbot.controller;

import ma.fstm.ilisi.buswayticketchatbot.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.stereotype.Controller
public class Controller {
    @Autowired
    private StationService stationService;

    public Controller(StationService stationService) {
        this.stationService = stationService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("stations", this.stationService.findAll());
        return "index";
    }

    @GetMapping("/chatbot")
    public String chatbot() {
        return "chatbot";
    }

}
