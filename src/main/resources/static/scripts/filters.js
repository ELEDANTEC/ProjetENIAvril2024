let nodeBtnSubmitFilters = document.querySelector("#submit-filters");
nodeBtnSubmitFilters.addEventListener("click", filterAuctions);

function filterAuctions() {
    let filters = document.getElementById('search-filters').value;
    let selectedCategory = parseInt(document.getElementById('select-category').value);
    let newDivContent = ``;

    let filteredAuctions = auctions.filter(function (obj) {
        let keepAuction = false;
        if (obj.saleStatus === 'En cours') {
            if (selectedCategory !== 0 && filters !== "") {
                keepAuction = obj.itemName.toLowerCase().includes(filters.toLowerCase()) && obj.category.categoryId === selectedCategory;
            } else if (selectedCategory !== 0) {
                keepAuction = obj.category.categoryId === selectedCategory;
            }  else if (filters !== "") {
                keepAuction = obj.itemName.toLowerCase().includes(filters.toLowerCase());
            } else {
                keepAuction = true;
            }
        }
        return keepAuction;
    });

    filteredAuctions.forEach((auction) => {
        const date = new Date(auction.endAuctionDate);
        const day = date.getDate();
        const month = date.getMonth() + 1;
        const year = date.getFullYear();
        const endAuctionDate = `${day}/${month.toString().padStart(2, '0')}/${year}`;

        newDivContent += `<div class="col">\r\n`;
        newDivContent += `<div class="card shadow border border-black border-3">`;
        newDivContent += `<div class="row g-0">`;
        newDivContent += `<div class="col-md-3 m-3">`;
        // newDivContent += `<img src="/images/` + auction.itemId + `.png" alt="Image" width="140" height="100"/>`;
        newDivContent += `<img src="/images/default.png" alt="Image" width="100" height="100"/>`;
        newDivContent += `</div>`;
        newDivContent += `<div class="col-md-7 m-3">`;
        newDivContent += `<div>`;
        if (isAuthenticated) {
            newDivContent += `<a href="/auctions/details(auction=` + auction.itemId + `)">`;
            newDivContent += `<span>` + auction.itemName + `</span>`;
            newDivContent += `</a>`;
        } else {
            newDivContent += `<span>` + auction.itemName + `</span>`;
        }
        newDivContent += `</div>`;
        if (auction.bids.length > 0) {
            newDivContent += `<div>`;
            newDivContent += `Enchère actuelle : <span>` + auction.bids.at(auction.bids.length - 1).bidAmount + `</span>`;
            newDivContent += `</div>`;
        } else {
            newDivContent += `<div>`;
            newDivContent += `Prix initial : <span>` + auction.initialPrice + `</span>`;
            newDivContent += `</div>`;
        }
        newDivContent += `<div>`;
        newDivContent += `Fin de l'enchère : <span>` + endAuctionDate + `</span>`;
        newDivContent += `</div>`;
        newDivContent += `<div>`;
        newDivContent += `Vendeur : <span>` + auction.user.username + `</span>`;
        newDivContent += `</div>`;
        newDivContent += `</div>`;
        newDivContent += `</div>`;
        newDivContent += `</div>`;
        newDivContent += `</div>`;
    })

    document.getElementById("auctions-div").innerHTML = newDivContent;
}
