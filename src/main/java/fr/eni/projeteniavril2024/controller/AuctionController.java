package fr.eni.projeteniavril2024.controller;


import fr.eni.projeteniavril2024.bll.AuctionService;
import fr.eni.projeteniavril2024.bo.Category;
import fr.eni.projeteniavril2024.bo.SoldItem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AuctionController {
    private final AuctionService auctionService;

    public AuctionController(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @GetMapping("/auctions")
    public String displayAuctions(
            Model model
    ) {
        List<SoldItem> auctions = auctionService.getAuctions();
        List<Category> categories = auctionService.getCategories();
        model.addAttribute("auctions", auctions);
        model.addAttribute("categories", categories);
        return "index.html";
    }
}
