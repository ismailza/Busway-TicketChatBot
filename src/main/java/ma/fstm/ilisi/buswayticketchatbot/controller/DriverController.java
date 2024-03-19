package ma.fstm.ilisi.buswayticketchatbot.controller;

import ma.fstm.ilisi.buswayticketchatbot.dto.DriverDTO;
import ma.fstm.ilisi.buswayticketchatbot.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class DriverController {
    @Autowired
    private DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @GetMapping("/drivers")
    public String getAllDrivers(Model model) {
        model.addAttribute("drivers", this.driverService.findAll());
        return "drivers";
    }

    @GetMapping("/driver/new")
    public String create(Model model) {
        model.addAttribute("driver", new DriverDTO());
        return "driverForm";
    }

    @PostMapping("/driver/save")
    public String save(@ModelAttribute DriverDTO driverDTO, RedirectAttributes redirectAttributes) {
        this.driverService.save(driverDTO);
        redirectAttributes.addFlashAttribute("success", "Driver successfully created!");
        return "redirect:/drivers";
    }
}
