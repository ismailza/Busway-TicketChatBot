package ma.fstm.ilisi.buswayticketchatbot.controller;

import ma.fstm.ilisi.buswayticketchatbot.dto.StationDTO;
import ma.fstm.ilisi.buswayticketchatbot.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class StationController {
    @Autowired
    private StationService stationService;

    public StationController(StationService stationService) {
        this.stationService = stationService;
    }

    @GetMapping("/stations")
    public String getAllStations(Model model) {
        model.addAttribute("stations", this.stationService.findAll());
        return "stations";
    }

    @GetMapping("/station/new")
    public String create(Model model) {
        model.addAttribute("station", new StationDTO());
        return "stationForm";
    }

    @PostMapping("/station/save")
    public String save(@ModelAttribute StationDTO stationDTO, RedirectAttributes redirectAttributes) {
        this.stationService.save(stationDTO);
        redirectAttributes.addFlashAttribute("success", "Station successfully created!");
        return "redirect:/stations";
    }

    @GetMapping("/station/edit/{id}")
    public String edit(Model model, @PathVariable Long id) {
        model.addAttribute("station", this.stationService.findById(id));
        return "stationForm";
    }

    @GetMapping("/station/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            stationService.delete(id);
            redirectAttributes.addFlashAttribute("success", "Station successfully deleted!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting station: " + e.getMessage());
        }
        return "redirect:/stations";
    }


}
