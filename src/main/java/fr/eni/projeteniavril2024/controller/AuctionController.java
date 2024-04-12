package fr.eni.projeteniavril2024.controller;


import fr.eni.projeteniavril2024.bll.AuctionService;
import fr.eni.projeteniavril2024.bo.Category;
import fr.eni.projeteniavril2024.bo.SoldItem;
import fr.eni.projeteniavril2024.bo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@Controller
@SessionAttributes({"userSession"})
public class AuctionController {
    private final AuctionService auctionService;

    public AuctionController(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @GetMapping("/auctions")
    public String displayAuctions(
            WebRequest request,
            Model model
    ) {
        User userSession = (User) request.getAttribute("userSession", RequestAttributes.SCOPE_SESSION);
        List<SoldItem> auctions = auctionService.getAuctions();
        List<Category> categories = auctionService.getCategories();

        if(userSession != null && userSession.getUserId() > 0) {
            model.addAttribute("userSession", userSession);
        }
        model.addAttribute("auctions", auctions);
        model.addAttribute("categories", categories);
        return "index.html";
    }
}
