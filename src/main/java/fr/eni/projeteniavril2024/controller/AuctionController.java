package fr.eni.projeteniavril2024.controller;


import fr.eni.projeteniavril2024.bll.AuctionService;
import fr.eni.projeteniavril2024.bo.Category;
import fr.eni.projeteniavril2024.bo.SoldItem;
import fr.eni.projeteniavril2024.bo.User;
import fr.eni.projeteniavril2024.bo.Withdrawal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@Controller
@RequestMapping("/auctions")
@SessionAttributes({"userSession"})
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
        List<Category> categories = auctionService.getCategories();

        model.addAttribute("auctions", auctions);
        model.addAttribute("categories", categories);
        return "index.html";
    }

    @GetMapping("/sell")
    public String createAuction(
            Model model
    ) {
        SoldItem auction = new SoldItem();
        Withdrawal withdrawal = new Withdrawal();
        model.addAttribute("auction", auction);
        return "redirect:/auctions";
    }
}
