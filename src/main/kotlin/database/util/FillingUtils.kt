package database.util

import database.model.BeerType
import java.util.*

object FillingUtils {
    fun getAllCountries(): List<String> = Locale.getISOCountries().asList()

    fun getAllBeerTypes() = listOf(
        BeerType(
            name = "ale",
            description = "Ale is a general category of beer: You'll find sub-categories like brown ales or pale ales. " +
                    "This is the oldest style of beer, which dates back to antiquity. What distinguishes an ale " +
                    "- and also makes this category of beer accessible for home brewers " +
                    "- is a warm-temperature fermentation for a relatively short period of time."
        ),
        BeerType(
            name = "IPA",
            description = "Originally, India Pale Ale or IPA was a British pale ale brewed with extra hops. " +
                    "High levels of this bittering agent made the beer stable enough to survive the long boat trip to India without spoiling. " +
                    "The extra dose of hops gives IPA beers their bitter taste. Depending on the style of hops used, " +
                    "IPAs may have fruit-forward citrus flavors or taste of resin and pine."
        ),
        BeerType(
            name = "sour",
            description = "An ancient style of beer that's taken off in popularity in recent years, " +
                    "sour ales are crafted from wild yeasts, much like sourdough bread. " +
                    "These beers are known for a tart tang that pairs well with tropical fruit and spices. " +
                    "Within sour beers, you'll find lambics, which are Belgian sour beers mixed with fruit, goses, " +
                    "a German sour beer made with coriander and sea salt, and Flanders, a Belgian sour beer fermented in wood tanks."
        ),
        BeerType(
            name = "lager",
            description = "Lagers are a newer style of beer with two key differences from ales. " +
                    "Lagers ferment for a long time at a low temperature, and they rely on bottom-fermenting yeasts, " +
                    "which sink to the bottom of the fermenting tank to do their magic"
        ),
        BeerType(
            name = "stout",
            description = "Like porters, stouts are dark, roasted ales. Stouts taste less sweet than porters and often " +
                    "feature a bitter coffee taste, which comes from unmalted roasted barley that is added to the wort. " +
                    "They are characterized by a thick, creamy head. " +
                    "Ireland's Guinness may be one of the world's best-known stouts"
        ),
        BeerType(
            name = "porter",
            description = "A type of ale, porter beers are known for their dark black color and roasted malt aroma and notes. " +
                    "Porters may be fruity or dry in flavor, which is determined by the variety of roasted malt used in the brewing process."
        ),
        BeerType(
            name = "pilsner",
            description = "A subspecies of lager, pilsner beers are distinguished by their water, which varies from neutral too hard. " +
                    "Pilsners are among the hoppiest lagers and generally have a dry, slightly bitter flavor. Their light golden color, " +
                    "clear body, and crisp finish make Pilsners a popular summer beer."
        ),
        BeerType(
            name = "wheat",
            description = "An easy-drinking, light style of beer, wheat beers are known for a soft, smooth flavor and a hazy body. " +
                    "Wheat beers tend to taste like spices or citrus, " +
                    "with the hefeweizen or unfiltered wheat beer being one of the more common styles."
        )
    )
}