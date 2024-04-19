package fr.eni.projeteniavril2024.controller;

import fr.eni.projeteniavril2024.bll.AuctionService;
import fr.eni.projeteniavril2024.bo.*;
import fr.eni.projeteniavril2024.exception.BusinessException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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

    @GetMapping("/details")
    public String displayAuction(
            @RequestParam(value="itemId") int itemId,
            @ModelAttribute("userSession") User userSession,
            Model model
    ) {
        SoldItem auction = auctionService.getAuctionById(itemId);
        if ("Créée".equals(auction.getSaleStatus()) && userSession.getUserId() == auction.getSeller().getUserId()) {
            model.addAttribute("itemId", itemId);
            return "redirect:/auctions/update?itemId=" + itemId;
        }
        Bid bid = new Bid();
        bid.setAuction(auction);
        model.addAttribute("bid", bid);
        model.addAttribute("auction", auction);
        return "auction/details.html";
    }

    @PostMapping("/details")
    public String createBid(
            @ModelAttribute("userSession") User userSession,
            @ModelAttribute("bid") Bid bid,
            BindingResult bindingResult,
            Model model
    ) {
        SoldItem auction = auctionService.getAuctionById(bid.getAuction().getItemId());
        try {
            bid.setBidDate(LocalDateTime.now());
            bid.setBuyer(userSession);
            bid.setAuction(auction);
            auctionService.createBid(bid);
            return "redirect:/auctions";
        } catch (BusinessException businessException) {
            businessException.getKeys().forEach(key -> {
                ObjectError error = new ObjectError("globalError", key);
                bindingResult.addError(error);
            });
            model.addAttribute("bid", bid);
            model.addAttribute("auction", auction);
            return "auction/details.html";
        }
    }

    @GetMapping("/sell")
    public String displayCreateAuctionForm(
            @ModelAttribute("userSession") User userSession,
            Model model
    ) {
        SoldItem auction = new SoldItem();
        Withdrawal withdrawal = new Withdrawal(
                userSession.getStreet(),
                userSession.getCity(),
                userSession.getPostalCode()
        );
        auction.setWithdrawal(withdrawal);

        model.addAttribute("auction", auction);
        return "auction/create.html";
    }

    @PostMapping("/sell")
    public String createAuction(
            @ModelAttribute("userSession") User userSession,
            @Valid @ModelAttribute("auction") SoldItem auction,
            BindingResult bindingResult
    ) {
        try {
            auction.setSaleStatus("Créée");
            auction.setSeller(userSession);
            auction.setCategory(auctionService.getCategoryById(auction.getCategory().getCategoryId()));
            auctionService.createAuction(auction);
            return "redirect:/auctions";
        } catch (BusinessException businessException) {
            businessException.getKeys().forEach(key -> {
                ObjectError error = new ObjectError("globalError", key);
                bindingResult.addError(error);
            });
            return "auction/create.html";
        }
    }

    @GetMapping("/update")
    public String displayUpdateAuctionForm(
            @RequestParam(value="itemId") int itemId,
            @ModelAttribute("userSession") User userSession,
            Model model
    ) {
        SoldItem auction = auctionService.getAuctionById(itemId);
        model.addAttribute("auction", auction);
        return "auction/update.html";
    }

    @PostMapping("/update")
    public String updateAuction(
            @ModelAttribute("userSession") User userSession,
            @Valid @ModelAttribute("auction") SoldItem auction,
            BindingResult bindingResult
    ) {
        try {
            auctionService.updateAuction(auction);
            return "redirect:/auctions";
        } catch (BusinessException businessException) {
            businessException.getKeys().forEach(key -> {
                ObjectError error = new ObjectError("globalError", key);
                bindingResult.addError(error);
            });
            return "auction/update.html";
        }
    }

    @GetMapping("/delete")
    public String deleteAnAuction(
            @RequestParam(value="itemId") int itemId
    ) {
        auctionService.deleteAuction(itemId);
        return "redirect:/auctions";
    }

    @ModelAttribute("categoriesSession")
    public List<Category> getCategories() {
        return auctionService.getCategories();
    }
}
