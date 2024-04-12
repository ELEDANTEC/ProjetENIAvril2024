let nodeBtnSubmitFilters = document.querySelector("#submit-filters");
nodeBtnSubmitFilters.addEventListener("click", filterAuctions);

function filterAuctions() {

    let filters = document.getElementById('search-filters').value;
    let selectedCategory = document.getElementById('select-category').value;

    filteredAuctions = auctions.filter(function (obj) {
        let auction = null;
        if (selectedCategory === 0) {
            obj.itemName.includes(filters);
        } else {
            obj.itemName.includes(filters) && obj.category.categoryId === selectedCategory;
        }
        return auction;
    });

    let newDivContent = ``;
    
    filteredAuctions.forEach((auction) => {
        newDivContent += `<div class="col">`;
        if (auction.saleStatus === 'En cours') {
            newDivContent += `<div class="card shadow border border-black border-3">`;
            newDivContent += `<div class="row g-0">`;
            newDivContent += `<div class="col-md-3 m-3">`;
            newDivContent += `<img src="/images/` + auction.itemId + `.png" alt="Image" width="140" height="100"/>`;
            newDivContent += `</div>`;
            newDivContent += `<div>`;
            newDivContent += `<a th:if="${#authorization.expression('isAuthenticated')}" href="/auctions/details(auction=` + auction.itemId + `)">`;
            newDivContent += `<span th:text="` + auction.itemName + `"></span>`;
            newDivContent += `</a>`;
            newDivContent += `<span th:unless="${#authorization.expression('isAuthenticated')}" th:text="` + auction.itemName + `"></span>`;
            newDivContent += `</div>`;
            if (auction.bids.size > 0) {
                newDivContent += `<div>`;
                newDivContent += `Enchère actuelle : <span>` + auction.bids.get(auction.bids.size() - 1).getBidAmount + `</span>`;
                newDivContent += `</div>`;
            } else {
                newDivContent += `<div>`;
                newDivContent += `Prix initial : <span>` + auction.initialPrice + `</span>`;
                newDivContent += `</div>`;
            }
            newDivContent += `<div>`;
            newDivContent += `Fin de l'enchère : <span>` + auction.endAuctionDate + `</span>`;
            newDivContent += `</div>`;
            newDivContent += `<div>`;
            newDivContent += `Vendeur : <span` + auction.user.username + `</span>`;
            newDivContent += `</div>`;
            newDivContent += `</div>`;
            newDivContent += `</div>`;
            newDivContent += `</div>`;
        }
        newDivContent += `</div>`;
    })
}
