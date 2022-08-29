package com.chargebee;

import com.chargebee.models.Subscription;
import com.chargebee.models.enums.EntityType;
import com.chargebee.subscription.CreateSubscriptionRequest;

import java.util.*;
//import com.chargebee.models.ThirdPartyEntityMapping;


public class Site {
    static  Environment environment = null;
    private final String domainSuffix;
    public final ProductCatalog productCatalog;
    private final Map<String, User> users;
   // private Optional<SalesforceIntegration> salesforceIntegration;
   // private Optional<Settings> settings = Optional.empty();
    public final BusinessEntity businessEntity;

    public static Environment getEnvironment() {
        return environment;
    }

    public Site(String domainSuffix, String siteName, String apiKey, ProductCatalog productCatalog, List<User> users, BusinessEntity businessEntity) {
        Map<String, User> usersMap = new HashMap<>();
        for (User user : users) {
            usersMap.put(user.firstName, user);
        }
        this.domainSuffix = domainSuffix;
        this.productCatalog = productCatalog;
        this.users = usersMap;
        this.businessEntity = businessEntity;
        environment = new Environment(siteName, apiKey);
    }

    public User getUser(String userFirstName) {
        return users.get(userFirstName);
    }

    public Site(String domainSuffix, String siteName, String apiKey, ProductCatalog productCatalog) {
        this(domainSuffix, siteName, apiKey, productCatalog, new ArrayList<>(),null);
    }

    public Site(String domainSuffix, String siteName, String apiKey, ProductCatalog productCatalog,List<User> users) {
        this(domainSuffix, siteName, apiKey, productCatalog, users,null);
    }

    public PricePoint getPricePoint(String pricePointId) {
        return productCatalog.getPricePoint(pricePointId);
    }

    public String baseUrl() {
        return String.format("https://%s.%s", environment.siteName, domainSuffix);
    }

   /* public Optional<com.chargebee.models.Customer> fetchCustomerByEmail(String customerEmailAddress) throws Exception {
        ListResult listResult =
            com.chargebee.models.Customer.list().email().is(customerEmailAddress).request(environment);
        if (listResult.size() != 1) {
            return Optional.empty();
        }
        return Optional.of(com.chargebee.models.Customer.fromCbCustomer(listResult.get(0).customer(), listResult.get(0).card()));
    }

    public Optional<com.chargebee.models.Customer> fetchCustomerByCompany(String customerCompany) throws Exception {
        ListResult listResult =
                com.chargebee.models.Customer.list().company().is(customerCompany).request(environment);
        if (listResult.size() != 1) {
            return Optional.empty();
        }
        return Optional.of(com.chargebee.models.Customer.fromCbCustomer(listResult.get(0).customer(), listResult.get(0).card()));
    }

    public Optional<Subscription> fetchSubscriptionByCustomerId(String customerId) throws Exception {
        ListResult listResult =
                Subscription.list().customerId().is(customerId).request(environment);
        if (listResult.size() != 1) {
            return Optional.empty();
        }
        return Optional.of(listResult.get(0).subscription());
    }

    public Optional<Invoice> fetchInvoiceByCustomerId(String customerId) throws Exception {
        ListResult listResult =
                Invoice.list().customerId().is(customerId).request(environment);
        if (listResult.size() != 1) {
            return Optional.empty();
        }
        return Optional.of(listResult.get(0).invoice());
    }

    public Optional<SalesforceIntegration> getSalesforceIntegration() {
        return salesforceIntegration;
    }

    public void setSalesforceIntegration(Optional<SalesforceIntegration> salesforceIntegration) {
        this.salesforceIntegration = salesforceIntegration;
    }*/

    public Customer createCustomer(String email, String firstName, Card card) throws Exception {
        com.chargebee.models.Customer.CreateRequest customerCreateRequest = com.chargebee.models.Customer.create().email(email);
        if (firstName != null) {
            customerCreateRequest.firstName(firstName);
        }
        if (card != null) {
            customerCreateRequest
                    .cardNumber(card.number)
                    .cardExpiryMonth(Integer.valueOf(card.month))
                    .cardExpiryYear(Integer.valueOf(card.year))
                    .cardCvv(card.cvv);
        }
        com.chargebee.models.Customer customer = customerCreateRequest.request(environment).customer();
        return Customer.fromCbCustomer(customer, card);
    }

    public Customer createCustomer(String email) throws Exception {
        return createCustomer(email, null, null);
    }

    public Subscription createSubscription(String planPricePointId, String customerId) throws Exception {
        Subscription.CreateWithItemsRequest createSubscriptionWithItemsRequest =
                Subscription.createWithItems(customerId)
                        .subscriptionItemItemPriceId(0, planPricePointId);
        return createSubscriptionWithItemsRequest.request(environment).subscription();
    }

    public Subscription createSubscription(CreateSubscriptionRequest createSubscriptionRequest, String customerId) throws Exception {
        Subscription.CreateWithItemsRequest createSubscriptionWithItemsRequest = Subscription.createWithItems(customerId);

        createSubscriptionWithItemsRequest =
                createSubscriptionWithItemsRequest.subscriptionItemItemPriceId(0, createSubscriptionRequest.planPricePoint.id);
        PricePoint planPricePoint = createSubscriptionRequest.planPricePoint;
        if (planPricePoint.hasQuantity()) {
            createSubscriptionWithItemsRequest =
                    planPricePoint.isQuantityInInteger().get()
                        ? createSubscriptionWithItemsRequest.subscriptionItemQuantity(0, planPricePoint.getQuantity().get().intValue())
                        : createSubscriptionWithItemsRequest.subscriptionItemQuantityInDecimal(0, planPricePoint.getQuantity().get().toString());
        }

        for (int i = 0; i < createSubscriptionRequest.addonPricePoints.size(); i++) {
            createSubscriptionWithItemsRequest =
                createSubscriptionWithItemsRequest
                        .subscriptionItemItemPriceId(i + 1, createSubscriptionRequest.addonPricePoints.get(i).id);
            PricePoint pricePoint = createSubscriptionRequest.addonPricePoints.get(i);
            if (pricePoint.hasQuantity()) {
                createSubscriptionWithItemsRequest =
                        pricePoint.isQuantityInInteger().get()
                            ? createSubscriptionWithItemsRequest.subscriptionItemQuantity(i + 1, pricePoint.getQuantity().get().intValue())
                            : createSubscriptionWithItemsRequest.subscriptionItemQuantityInDecimal(i  + 1, pricePoint.getQuantity().get().toString());
            }
        }

        for (int i = 0; i < createSubscriptionRequest.chargePricePoints.size(); i++) {
            createSubscriptionWithItemsRequest =
                    createSubscriptionWithItemsRequest
                            .subscriptionItemItemPriceId(i + 1 + createSubscriptionRequest.addonPricePoints.size(),
                                    createSubscriptionRequest.chargePricePoints.get(i).id);
            PricePoint pricePoint = createSubscriptionRequest.chargePricePoints.get(i);
            if (pricePoint.hasQuantity()) {
                createSubscriptionWithItemsRequest =
                        pricePoint.isQuantityInInteger().get()
                                ? createSubscriptionWithItemsRequest.subscriptionItemQuantity(i + 1, pricePoint.getQuantity().get().intValue())
                                : createSubscriptionWithItemsRequest.subscriptionItemQuantityInDecimal(i  + 1, pricePoint.getQuantity().get().toString());
            }
        }

        if (createSubscriptionRequest.getSubscriptionId().isPresent()) {
            createSubscriptionWithItemsRequest =
                    createSubscriptionWithItemsRequest.id(createSubscriptionRequest.getSubscriptionId().get());
        }

        if (createSubscriptionRequest.getPoNumber().isPresent()) {
            createSubscriptionWithItemsRequest =
                    createSubscriptionWithItemsRequest.poNumber(createSubscriptionRequest.getPoNumber().get());
        }

        return createSubscriptionWithItemsRequest.request(environment).subscription();
    }

    public void checkRecord(Optional<String> id, String entity_type) {

       /* com.chargebee.models.ThirdPartyEntityMapping tpem = com.chargebee.models.ThirdPartyEntityMapping.retrieveEntity().entityId(id)
                .entityType(ThirdPartyEntityMapping.EntityType.CUSTOMER).integrationName("quickbooks").request(environment).thirdPartyEntityMapping();*/

    }

   /* public void turnOffAutoCollectionFor(String customerId) throws Exception {
        com.chargebee.models.Customer.update(customerId).autoCollection(AutoCollection.OFF).request(environment);
    }

    public void turnOnAutoCollectionFor(String customerId) throws Exception {
        com.chargebee.models.Customer.update(customerId).autoCollection(AutoCollection.ON).request(environment);
    }

  /*  public TimeMachine startTimeMachineAt(CbPeriod timePeriodToTravel) throws Exception {
        TimeMachine timeMachine =
                TimeMachine.startAfresh("delorean").genesisTime(timePeriodToTravel.toTimestamp()).request(environment).timeMachine();
        return timeMachine.waitForTimeTravelCompletion(environment);
    }

    public TimeMachine travelTo(Timestamp toTimestamp) throws Exception {
        TimeMachine timeMachine =
                TimeMachine.travelForward("delorean").destinationTime(toTimestamp).request(environment).timeMachine();
        return timeMachine.waitForTimeTravelCompletion(environment);
    }

    public TimeMachine travelTo(CbPeriod period) throws Exception {
        return travelTo(period.toTimestamp());
    }

    public List<Transaction> getCustomerTransactions(String customerId) throws Exception {
        ListResult transactionsResult = Transaction.list().customerId().is(customerId).request(environment);
        List<Transaction> transactions = new ArrayList<>();
        transactionsResult.forEach(transactionResult -> {
            transactions.add(transactionResult.transaction());
        });
        return transactions;
    }

    public Subscription.Status getSubscriptionStatus(String subscriptionId) throws Exception {
        return Subscription.retrieve(subscriptionId).request(environment).subscription().status();
    }

    public void updateItemUsage(String subscriptionId, String itemPricePointId, CbPeriod period, int quantity) throws Exception {
        Usage.create(subscriptionId)
            .itemPriceId(itemPricePointId)
            .usageDate(period.toTimestamp())
            .quantity(String.valueOf(quantity))
            .request(environment);
    }

    public void changeSubscriptionTermEnd(String subscriptionId, CbPeriod endDate) throws Exception {
        Subscription.changeTermEnd(subscriptionId)
            .termEndsAt(endDate.toTimestamp())
            .request(environment);

    }

    public Money nextBillingInvoiceAmountFor(String customerId) throws Exception {
        ListResult result = Invoice.list().customerId().is(customerId).request(environment);
        Integer total = result.get(0).invoice().total();
        String currencyCode = result.get(0).invoice().currencyCode();
        return Money.fromLowestDenomination(Currency.valueOf(currencyCode), total);
    }

    public Quote fetchQuoteByQuoteId(String cbQuoteId) throws Exception {
        return Quote.retrieve(cbQuoteId).request(environment).quote();
    }

    public Optional<Settings> getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = Optional.of(settings);
    }

    public Subscription changeSubscriptionPlan(String subscriptionId, String newPlanPricePointId) throws Exception {
        return Subscription.updateForItems(subscriptionId).
                subscriptionItemItemPriceId(0,newPlanPricePointId)
                .request(environment).subscription();
    }

    public com.chargebee.models.Customer getCustomer(String customerID) throws Exception {
        return Customer.fromCbCustomer(Customer.retrieve(customerID).request(environment).customer());
    }

    public Subscription getSubscription(String subscriptionID) throws Exception {
        return Subscription.retrieve(subscriptionID).request(environment).subscription();
    }

    public Timestamp getNextBillingDate(String subscriptionid) throws Exception {
        Timestamp time = Subscription.retrieve(subscriptionid).request(environment).subscription().nextBillingAt();
        time = new Timestamp(time.getTime() + 172800000);
        return time;
    }

    public void overrideSubscriptionPrice(String subscriptionId, String item_price_id, String overriddenPrice) throws Exception {
        Subscription.updateForItems(subscriptionId).subscriptionItemItemPriceId(0, item_price_id).
                subscriptionItemUnitPriceInDecimal(0,overriddenPrice).request(environment);
    }

    public void applyManualDiscount(String subscriptionId, String discountValue, String discountApplyOn, String discountType, String duration) throws Exception {

        Subscription.updateForItems(subscriptionId).
                param("discounts[percentage][0]", discountValue)
                .param("discounts[apply_on][0]", discountApplyOn)
                .param("discounts[duration_type][0]", duration)
                .param("discounts[operation_type][0]", "add").request(environment);

    }*/
}
