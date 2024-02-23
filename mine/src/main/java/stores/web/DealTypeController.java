package stores.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.micrometer.common.util.StringUtils;
import jakarta.validation.Valid;
import stores.DealType;
import stores.Deals;
import stores.data.DealTypeRepository;

@Controller

public class DealTypeController {
    private final DealTypeRepository dealTypeRepository;

    public DealTypeController(DealTypeRepository dealTypeRepository) {
        this.dealTypeRepository = dealTypeRepository;
    }

    @GetMapping("/typedeal")
    public String showDealTypeForm(Model model) {
    	model.addAttribute("dealTypes", dealTypeRepository.findAll());
        model.addAttribute("newDealType", new DealType());
        return "typedeal";
    }

    
    @PostMapping("/selectdeal")
    public String selectDealType(@RequestParam("dealTypeId") Long dealTypeId, Model model) {
    	
        DealType dealType = dealTypeRepository.findById(dealTypeId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid deal type Id:" + dealTypeId));
        model.addAttribute("selectedDealType", dealType);

        Deals deal = new Deals();
        deal.setTypeId(dealTypeId);
        model.addAttribute("deal", deal);

        return "Deal";
    }
    
   

    @GetMapping("/allTypes")
    public String showAllDealTypes(Model model) {
        model.addAttribute("dealTypes", dealTypeRepository.findAll());
        return "allTypes";
    }

    @GetMapping("/addType")
    public String showAddDealTypeForm(Model model) {
        model.addAttribute("dealType", new DealType());
        return "addType";
    }

    @PostMapping("/addType")
    public String addType(@ModelAttribute("dealType") @Valid DealType dealType, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "addType";
        }

        dealTypeRepository.save(dealType);
        return "redirect:/allTypes";
    }


    @GetMapping("/editType/{id}")
    public String showEditDealTypeForm(@PathVariable Long id, Model model) {
        DealType dealType = dealTypeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid deal type Id:" + id));
        model.addAttribute("dealType", dealType);
        return "editType";
    }

    @PostMapping("/editType/{id}")
    public String editDealType(@PathVariable Long id, @ModelAttribute("dealType") @Valid DealType updatedDealType, BindingResult result) {
        if (result.hasErrors()) {
            return "editType";
        }

        dealTypeRepository.findById(id).ifPresent(dealType -> {
            dealType.setType(updatedDealType.getType());
            dealTypeRepository.save(dealType);
        });
        return "redirect:/allTypes";
    }


    @GetMapping("/deleteType/{id}")
    public String deleteDealType(@PathVariable Long id) {
        dealTypeRepository.deleteById(id);
        return "redirect:/allTypes";
    }

}


