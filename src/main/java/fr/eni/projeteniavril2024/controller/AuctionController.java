package fr.eni.projeteniavril2024.controller;


import fr.eni.projeteniavril2024.bll.AuctionService;
import fr.eni.projeteniavril2024.bo.Category;
import fr.eni.projeteniavril2024.bo.SoldItem;
import fr.eni.projeteniavril2024.bo.User;
import fr.eni.projeteniavril2024.bo.Withdrawal;
import fr.eni.projeteniavril2024.exception.BusinessException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/auctions")
@SessionAttributes({"userSession", "categoriesSession"})
public class AuctionController {
    private final AuctionService auctionService;

    public AuctionController(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @GetMapping
    public String displayAuctions(
            Model model
    ) {
        List<SoldItem> auctions = auctionService.getAuctions();

        model.addAttribute("auctions", auctions);
        return "index.html";
    }

    @GetMapping("/sell")
    public String getAuctionForm(
            Model model
    ) {
        SoldItem auction = new SoldItem();
        Withdrawal withdrawal = new Withdrawal();

        model.addAttribute("auction", auction);
        model.addAttribute("withdrawal", withdrawal);
        return "auctions/create.html";
    }

    @PostMapping("/sell")
    public String createAuction(
            @ModelAttribute("userSession") User userSession,
            @Valid @ModelAttribute("auction") SoldItem auction,
            @Valid @ModelAttribute("withdrawal") Withdrawal withdrawal,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "auctions/create.html";
        } else {
            try {
                auction.setSeller(userSession);
                auctionService.createAuction(auction);
                return "redirect:/auctions";
            } catch (BusinessException businessException) {
                businessException.getKeys().forEach(key -> {
                    ObjectError error = new ObjectError("globalError", key);
                    bindingResult.addError(error);
                });
                return "auctions/create.html";
            }
        }
    }

    @ModelAttribute("categoriesSession")
    public List<Category> getCategories() {
        return auctionService.getCategories();
    }
}
