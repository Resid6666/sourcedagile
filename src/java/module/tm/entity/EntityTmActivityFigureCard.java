package module.tm.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityTmActivityFigureCard extends CoreEntity {

    public static String CARD_NAME = "cardName";
    private String cardName = "";
    public static String CARD_DESCRIPTION = "cardDescription";
    private String cardDescription = "";
    public static String CARD_TYPE = "cardType";
    private String cardType = "";

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardDescription() {
        return cardDescription;
    }

    public void setCardDescription(String cardDescription) {
        this.cardDescription = cardDescription;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    
    
}
