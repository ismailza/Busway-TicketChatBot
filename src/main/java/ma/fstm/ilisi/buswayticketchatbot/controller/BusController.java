package ma.fstm.ilisi.buswayticketchatbot.controller;

import ma.fstm.ilisi.buswayticketchatbot.dto.BusDTO;
import ma.fstm.ilisi.buswayticketchatbot.dto.DriverDTO;
import ma.fstm.ilisi.buswayticketchatbot.service.BusService;
import ma.fstm.ilisi.buswayticketchatbot.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class BusController {
    @Autowired
    private BusService busService;
    @Autowired
    private DriverService driverService;

    public BusController(BusService busService, DriverService driverService) {
        this.busService = busService;
        this.driverService = driverService;
    }

    @GetMapping("/buses")
    public String getAllBuses(Model model) {
        // model.addAttribute("buses", this.busService.findAll());
        return "buses";
    }

    @GetMapping("/bus/new")
    public String create(Model model) {
        model.addAttribute("bus", new BusDTO());
        model.addAttribute("drivers", this.driverService.findAll());
        return "busForm";
    }

    @PostMapping("/bus/save")
    public String save(@ModelAttribute BusDTO busDTO, RedirectAttributes redirectAttributes) {
        this.busService.save(busDTO);
        redirectAttributes.addFlashAttribute("success", "Bus successfully created!");
        return "redirect:/buses";
    }

    @GetMapping("/bus/edit/{id}")
    public String edit(Model model, @PathVariable Long id) {
        model.addAttribute("bus", this.busService.findById(id));
        return "busForm";
    }

}
