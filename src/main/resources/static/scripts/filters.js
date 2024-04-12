let nodeBtnSubmitFilters = document.querySelector("#submit-filters");
nodeBtnSubmitFilters.addEventListener("click", filterAuctions);

function filterAuctions() {
    let filters = document.getElementById('search-filters').value;
    let selectedCategory = document.getElementById('select-category').value;

    return auctions.filter(function (obj) {
        let auction = null;
        if (selectedCategory === 0) {
            obj.itemName.includes(filters);
        } else {
            obj.itemName.includes(filters) && obj.category.categoryId === selectedCategory;
        }
        return auction;
    });
}
