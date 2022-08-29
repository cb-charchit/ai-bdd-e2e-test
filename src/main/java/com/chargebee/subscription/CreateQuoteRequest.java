package com.chargebee.subscription;

import java.time.OffsetDateTime;
import java.util.Optional;

public abstract class CreateQuoteRequest {
    public final OffsetDateTime expiresOn;

    private Optional<String> quoteName = Optional.empty();

    private Optional<String> quoteNotes = Optional.empty();

    protected CreateQuoteRequest(OffsetDateTime expiresOn) {
        this.expiresOn = expiresOn;
    }

    public Optional<String> getQuoteName() {
        return quoteName;
    }

    public void setQuoteName(String quoteName) {
        this.quoteName = Optional.of(quoteName);
    }

    public Optional<String> getQuoteNotes() {
        return quoteName;
    }

    public void setQuoteNotes(String quoteNotes) {
        this.quoteNotes = Optional.of(quoteNotes);
    }
}
