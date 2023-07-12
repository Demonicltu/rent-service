## Rental service API documentation

See documentation [here](src/main/asciidoc/documentation.html)

## Customizing parameters

```
cache.product.list.time=3
cache.product.list.size=5

cache.product.specific.time=3
cache.product.specific.size=10

cache.product.price.time=3
cache.product.price.size=20
```

# Requirements

## Equipment rental price calculation API

For an equipment rental service app we need an API to be able to calculate prices for rent.

### Glossary

* commitment:
    * a number of months that the user promises to rent the equipment for, e.g. none, 3 months, 6 months

### Use cases

* API users can get a list of equipment for rent (consoles, VR devices, mobile-phones etc)
* API users can choose a single product and get it's title and possible commitment options
* API users can calculate the total price for the chosen rental period, if they provide:
    * the chosen product
    * the chosen commitment time (number)
    * optionally, the number of months after which they will return the equipment

### Equipment types and price

Our product catalog:

| Product | Price/month EUR no commitment| Price/month EUR for 3 month commitment | Price/month EUR for 6 month commitment|Initial charge EUR| Available for rent
|---:|---:|---:|---:|---:|---
|Xbox Series X|35|30|25|35|Yes
|Xbox Series S|25|20|17|25|Yes
|Sony PS5|35|30|25|35|Yes
|Nintendo Switch Lite|17|13|10|17|Yes
|Oculus Quest 2|35|30|25|35|Yes
|Oculus Quest|30|25|20|30|No
|Oculus Rift S|35|30|25|35|Yes
|HTC Vive Cosmos|45|40|35|45|Yes
|iPhone X|35|30|25|35|Yes
|Samsung 20|45|40|35|45|No

### Business requirements

Expose a REST HTTP API, with operations for listing products, choosing products and calculating the total price for
renting a chosen product

* Provide a product list
    * user should be able to get products available for rent
* Provide product details
    * user should be able to provide a product identification, and receive the product title, identification and
      possible commitment times
* Calculate the total price
    * user can provide a commitment value as one of:
        * no commitment
        * 3 months commitment
        * 6 months commitment
    * users that chose a 3 or 6 month commitment and want to return equipment earlier have to pay the "no commitment"
      price for the months they rented
    * user can indicate a number of RETURN_MONTHS, which means "I will return this after RETURN_MONTHS months"
        * default values for RETURN_MONTHS:
            * if no commitment is chosen, then RETURN_MONTHS = 1
            * if commitment is chosen, then RETURN_MONTHS = the chosen commitment number of months
    * price = initial charge + (RETURN_MONTHS * price per month, based on commitment)
        * if user provides a non-default number for RETURN_MONTHS, and has chosen a commitment period:
            * price = initial charge + (RETURN_MONTHS * no commitment price per month)

### Examples of price calculations

* User chooses an Oculus Quest 2, a commitment of 6 months, and wants to see the price they would pay if they returned
  the equipment after 2 months:
    * price = (initial charge) 35 + (what they would pay without commitment)(2 * 35) = 105 EUR
* User chooses a Nintendo Switch, no commitment, and wants to see the price they would pay if they returned the
  equipment after 7 months:
    * price = (initial charge) 17 + (what they would pay without commitment)(7 * 17) = 136 EUR

### Other requirements

* Please use this skeleton repository and apply your changes on top of it (hint: there could be something missing)
* Reduce strain on service, utilize caching
* Add basic authentication on endpoints
* H2 file based DB setup with flyway migration scripts is already provided, please use it
  (if you prefer Liquibase, you can use it and write migrations yourself)
* Maven has added JPA dependencies, if prefer Mybatis or JdbcTemplate, you can use those
* Dockerize the application
* Document the API

