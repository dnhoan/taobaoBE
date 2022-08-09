package com.example.taobao_be.DTO;

import com.example.taobao_be.models.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private Long id;

    private String name;

    private Integer stock;

    private Integer price;

    private String color;

    private String size;

    private String image;

    private Category category;

    private String status;

    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDTO that = (ProductDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(stock, that.stock) && Objects.equals(price, that.price) && Objects.equals(color, that.color) && Objects.equals(size, that.size) && Objects.equals(image, that.image) && Objects.equals(category, that.category) && Objects.equals(status, that.status) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, stock, price, color, size, image, category, status, description);
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", stock=" + stock +
                ", price=" + price +
                ", color='" + color + '\'' +
                ", size='" + size + '\'' +
                ", image='" + image + '\'' +
                ", category=" + category +
                ", status='" + status + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
