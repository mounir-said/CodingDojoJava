package com.pfe.hypermax.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity
@Table(name = "online_products",
        indexes = {
                @Index(name = "idx_product_url", columnList = "url"),
                @Index(name = "idx_product_source", columnList = "source"),
                @Index(name = "idx_product_timestamp", columnList = "timestamp")
        })
public class OnlineProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false, length = 100)
    private String source;

    @Column(nullable = false, length = 1000)
    private String url;

    @Column(length = 1000)
    private String imageUrl;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 255)
    private String searchQuery;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Version
    private Integer version;  // For optimistic locking

    public OnlineProduct() {
        this.timestamp = LocalDateTime.now();
    }

    // Builder pattern for easier creation
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final OnlineProduct product = new OnlineProduct();

        public Builder title(String title) {
            product.setTitle(title);
            return this;
        }

        public Builder price(BigDecimal price) {
            product.setPrice(price);
            return this;
        }

        public Builder source(String source) {
            product.setSource(source);
            return this;
        }

        public Builder url(String url) {
            product.setUrl(url);
            return this;
        }

        public Builder imageUrl(String imageUrl) {
            product.setImageUrl(imageUrl);
            return this;
        }

        public Builder description(String description) {
            product.setDescription(description);
            return this;
        }

        public Builder searchQuery(String searchQuery) {
            product.setSearchQuery(searchQuery);
            return this;
        }

        public OnlineProduct build() {
            return product;
        }
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getVersion() {
        return version;
    }

    // Helper methods
    public String getFormattedTimestamp() {
        return timestamp != null ?
                timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) :
                "N/A";
    }

    public String getShortDescription() {
        return description != null && description.length() > 100 ?
                description.substring(0, 100) + "..." :
                description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OnlineProduct that = (OnlineProduct) o;
        return Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }

    @Override
    public String toString() {
        return "OnlineProduct{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", source='" + source + '\'' +
                ", url='" + url + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", description='" + getShortDescription() + '\'' +
                ", searchQuery='" + searchQuery + '\'' +
                ", timestamp=" + getFormattedTimestamp() +
                '}';
    }
}