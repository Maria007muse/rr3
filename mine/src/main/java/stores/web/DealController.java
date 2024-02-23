package stores.web;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import jakarta.validation.Valid;
import stores.DealType;
import stores.Deals;
import stores.data.DealTypeRepository;
import stores.data.DealsRepository;
import org.springframework.beans.BeanUtils;

@Controller
@RequestMapping("/deal")
public class DealController {
    private final DealsRepository dealRepository;
    private final DealTypeRepository dealTypeRepository;

    private static final Logger log = LoggerFactory.getLogger(DealController.class);

    public DealController(DealsRepository dealRepository, DealTypeRepository dealTypeRepository) {
        this.dealRepository = dealRepository;
        this.dealTypeRepository = dealTypeRepository;
    }
    
    private List<DealType> getAllDealTypes() {
        List<DealType> allDealTypes = new ArrayList<>();
        dealTypeRepository.findAll().forEach(allDealTypes::add);
        return allDealTypes;
    }


    private void addDealTypeAttributes(Model model, Long selectedDealTypeId) {
        model.addAttribute("allDealTypes", getAllDealTypes());
        model.addAttribute("dealType", dealTypeRepository.findById(selectedDealTypeId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid deal type Id:" + selectedDealTypeId)));
    }

    @GetMapping("/create")
    public String dealForm(Model model) {
        model.addAttribute("deal", new Deals());
        return "Deal";
    }
    
    @GetMapping("/all")
    public String allDeals(Model model) {
        List<Deals> allDeals = (List<Deals>) dealRepository.findAll();
        List<DealType> allDealTypes = (List<DealType>) dealTypeRepository.findAll(); 
        model.addAttribute("allDeals", allDeals);
        model.addAttribute("allDealTypes", allDealTypes);
        return "allDeals";
    }


    @PostMapping("/submit")
    public String processDeal(@Valid @ModelAttribute("deal") Deals deal, Errors errors, SessionStatus sessionStatus, @ModelAttribute("selectedDealTypeid") DealType dealTypeId, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("selectedDealType", dealTypeId);
            return "Deal";
        }
        System.out.println(deal);
        log.info("Deal submitted: {}", deal);
        dealRepository.save(deal);
        sessionStatus.setComplete();
        return "redirect:/deal/all";
    }
    
    @GetMapping("/edit/{id}")
    public String editDeal(@PathVariable Long id, Model model) {
        Deals deal = dealRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid deal Id:" + id));
        model.addAttribute("deal", deal);
        addDealTypeAttributes(model, deal.getTypeId());
        return "DealEdit";
    }

    @PostMapping("/update/{id}")
    public String updateDeal(@PathVariable Long id, @Valid @ModelAttribute("deal") Deals deal,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            addDealTypeAttributes(model, deal.getTypeId());
            return "DealEdit";
        }

        Deals existingDeal = dealRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid deal Id:" + id));

        BeanUtils.copyProperties(deal, existingDeal, "id");
        dealRepository.save(existingDeal);
        return "redirect:/deal/all";
    }


    @GetMapping("/delete/{id}")
    public String deleteDeal(@PathVariable Long id) {
        dealRepository.deleteById(id);
        return "redirect:/deal/all";
    }
}







