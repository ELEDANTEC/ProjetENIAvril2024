package fr.eni.projeteniavril2024.exception;

public class BusinessCode {
//    AUCTION VALIDATION

    public static final String VALIDATION_AUCTION_NULL = "validation.auction.null";
    public static final String VALIDATION_AUCTION_UNIQUE = "validation.auction.unique";
    public static final String VALIDATION_AUCTION_NAME_BLANK = "validation.auction.name.blank";
    public static final String VALIDATION_AUCTION_NAME_LENGTH = "validation.auction.name.length";
    public static final String VALIDATION_AUCTION_DESCRIPTION_BLANK = "validation.auction.description.blank";
    public static final String VALIDATION_AUCTION_DESCRIPTION_LENGTH = "validation.auction.description.length";
    public static final String VALIDATION_AUCTION_DATE_START_NULL = "validation.auction.date.start.null";
    public static final String VALIDATION_AUCTION_DATE_END_NULL = "validation.auction.date.end.null";
    public static final String VALIDATION_AUCTION_DATE_START_WRONG = "validation.auction.date.start.wrong";
    public static final String VALIDATION_AUCTION_DATE_END_WRONG = "validation.auction.date.end.wrong";
    public static final String VALIDATION_AUCTION_SELLER_NULL = "validation.auction.seller.null";
    public static final String VALIDATION_AUCTION_SELLER_ID_UNKNOWN = "validation.auction.seller.id.unknown";
    public static final String VALIDATION_AUCTION_CATEGORY_NULL = "validation.auction.category.null";
    public static final String VALIDATION_AUCTION_CATEGORY_ID_UNKNOWN = "validation.auction.category.id.unknown";
    public static final String VALIDATION_AUCTION_WITHDRAWAL_NULL = "validation.auction.withdrawal.null";
    public static final String VALIDATION_AUCTION_WITHDRAWAL_STREET_BLANK = "validation.auction.withdrawal.street.blank";
    public static final String VALIDATION_AUCTION_WITHDRAWAL_STREET_LENGTH = "validation.auction.withdrawal.street.length";
    public static final String VALIDATION_AUCTION_WITHDRAWAL_POSTAL_CODE_BLANK = "validation.auction.withdrawal.postal.code.blank";
    public static final String VALIDATION_AUCTION_WITHDRAWAL_POSTAL_CODE_LENGTH = "validation.auction.withdrawal.postal.code.length";
    public static final String VALIDATION_AUCTION_WITHDRAWAL_CITY_BLANK = "validation.auction.withdrawal.city.blank";
    public static final String VALIDATION_AUCTION_WITHDRAWAL_CITY_LENGTH = "validation.auction.withdrawal.city.length";

//    BID VALIDATION

    public static final String VALIDATION_BID_NULL = "validation.bid.null";
    public static final String VALIDATION_BID_AMOUNT_SMALLER = "validation.bid.amount.smaller";
    public static final String VALIDATION_BID_CREDIT_INSUFFICIENT = "validation.bid.credit.insufficient";

//    BLL AUCTION

    public static final String BLL_AUCTION_CREATE_ERROR = "bll.auction.create.error";
    public static final String BLL_BID_CREATE_ERROR = "bll.bid.create.error";
}
