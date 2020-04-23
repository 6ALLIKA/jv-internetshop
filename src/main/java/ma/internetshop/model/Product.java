package ma.internetshop.model;

import java.math.BigDecimal;

public class Product {
    private Long Id;
    private String name;
    private BigDecimal price;

    public Product(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long itemId) {
        this.Id = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{"
                + "itemId=" + Id
                + ", name='" + name + '\''
                + ", price=" + price + '}';
    }
}
