let nodeBtnSubmitFilters = document.querySelector("#submit-filters");
nodeBtnSubmitFilters.addEventListener("click", filterAuctions);

let radioAuctions = null;
let radioMyAuctions = null;

if (isAuthenticated) {
    radioAuctions = document.querySelector("#radio-auctions");
    radioAuctions.addEventListener("click",  function () {
        document.querySelectorAll('input[name="auctions"]')
        .forEach((elem) => elem.disabled = false);
        document.querySelectorAll('input[name="my-auctions"]')
            .forEach((elem) => {
                elem.checked = false;
                elem.disabled = true;
            });
    });

    radioMyAuctions = document.querySelector("#radio-my-auctions");
    radioMyAuctions.addEventListener("click",  function () {
        document.querySelectorAll('input[name="my-auctions"]')
            .forEach((elem) => elem.disabled = false);
        document.querySelectorAll('input[name="auctions"]')
            .forEach((elem) => {
                elem.checked = false;
                elem.disabled = true;
            });
    });
}

/**
 * filters sales according to user selection
 *
 * @return void
 */
function filterAuctions() {
    let filters = document.getElementById('search-filters').value;
    let selectedCategory = parseInt(document.getElementById('select-category').value);
    let auctionsChecked = (isAuthenticated ? document.getElementById('radio-auctions').checked : false);
    let myAuctionsChecked = (isAuthenticated ? document.getElementById('radio-my-auctions').checked : false);
    let auctionsCheckboxes = (isAuthenticated
        ?
            {
                openAuctions: document.getElementById('open-auctions').checked,
                myCurrentBids: document.getElementById('my-current-bids').checked,
                myWinningBids: document.getElementById('my-winning-bids').checked
            }
        :
            null
    );
    let myAuctionsCheckboxes = (isAuthenticated
        ?
            {
                myOpenAuctions: document.getElementById('my-open-auctions').checked,
                myNotStartedAuctions: document.getElementById('my-not-started-auctions').checked,
                myEndedAuctions: document.getElementById('my-ended-auctions').checked
            }
        :
            null
    );
    let saleStatus = [];

    // Initialisation du status de vente sélectionné
    if (myAuctionsChecked) {
        if (myAuctionsCheckboxes.myOpenAuctions) {
            saleStatus.push('En cours');
        }
        if (myAuctionsCheckboxes.myNotStartedAuctions) {
            saleStatus.push('Créée');
        }
        if (myAuctionsCheckboxes.myEndedAuctions) {
            saleStatus.push('Enchères terminées');
        }
    }

    // Applications des filtres
    let filteredAuctions = auctions.filter(function (obj) {
        let keepAuction = false;
        let lastBidIsMine = false;
        if (
            isAuthenticated
            &&
            obj.bids
            &&
            obj.bids.length > 0
        ) {
            lastBidIsMine = obj.bids[obj.bids.length - 1].buyer.userId === userSession.userId;
        }

        if (
            !isAuthenticated && 'En cours'.includes(obj.saleStatus)
            || (
                isAuthenticated
                &&
                (
                    (
                        auctionsChecked
                        &&
                            !auctionsCheckboxes.openAuctions
                        &&
                            !auctionsCheckboxes.myCurrentBids
                        &&
                            !auctionsCheckboxes.myWinningBids
                    ) || (
                        auctionsCheckboxes.myCurrentBids
                        &&
                        obj.bids.find(bid => bid.buyer.userId === userSession.userId)
                        &&
                        'En cours'.includes(obj.saleStatus)
                    ) || (
                        auctionsCheckboxes.myWinningBids
                        &&
                        lastBidIsMine
                        &&
                        'Enchères terminées'.includes(obj.saleStatus)
                    ) || (
                        auctionsCheckboxes.openAuctions
                        &&
                        'En cours'.includes(obj.saleStatus)
                    ) || (
                        myAuctionsChecked
                        &&
                        obj.seller.userId === userSession.userId
                        &&
                        saleStatus.includes(obj.saleStatus)
                    )
                )
            )
        ) {
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

    // Affichage des ventes filtrées
    let newDivContent = ``;

    filteredAuctions.forEach((auction) => {
        const date = new Date(auction.endAuctionDate);
        const day = date.getDate();
        const month = date.getMonth() + 1;
        const year = date.getFullYear();
        const endAuctionDate = `${day}/${month.toString().padStart(2, '0')}/${year}`;

        newDivContent += `<div class="col">`;
        newDivContent += `<div class="card shadow border border-black border-3">`;
        newDivContent += `<div class="row g-0">`;
        newDivContent += `<div class="col-md-3 m-3">`;
        newDivContent += `<img src="/images/` + auction.itemId + `.png" onError="this.onerror=null;this.src='/images/default.png';" width="100" height="100"/>`;
        newDivContent += `</div>`;
        newDivContent += `<div class="col-md-7 m-3">`;
        newDivContent += `<div class="text-decoration-underline">`;
        if (isAuthenticated) {
            newDivContent += `<a href="/auctions/details?itemId=` + auction.itemId + `">`;
            newDivContent += `<span>` + auction.itemName + `</span>`;
            newDivContent += `</a>`;
        } else {
            newDivContent += `<span>` + auction.itemName + `</span>`;
        }
        newDivContent += `</div>`;
        if (auction.bids.length > 0) {
            newDivContent += `<div>`;
            newDivContent += `Enchère actuelle : <span>` + auction.bids.at(auction.bids.length - 1).bidAmount + ` points</span>`;
            newDivContent += `</div>`;
        } else {
            newDivContent += `<div>`;
            newDivContent += `Prix initial : <span>` + auction.initialPrice + ` points</span>`;
            newDivContent += `</div>`;
        }
        newDivContent += `<div>`;
        newDivContent += `Fin de l'enchère : <span>` + endAuctionDate + `</span>`;
        newDivContent += `</div>`;
        newDivContent += `<div>`;
        if (isAuthenticated) {
            newDivContent += `Vendeur : <a href="/user/` + auction.seller.userId + `"><span>` + auction.seller.username + `</span></a>`;
        } else {
            newDivContent += `Vendeur : <span>` + auction.seller.username + `</span>`;
        }
        newDivContent += `</div>`;
        newDivContent += `</div>`;
        newDivContent += `</div>`;
        newDivContent += `</div>`;
        newDivContent += `</div>`;
    })

    document.getElementById("auctions-div").innerHTML = newDivContent;
}
