package entities.main;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@JsonAutoDetect
@JsonRootName("Category")
@JsonPropertyOrder({"id, name"})
public class Category {
    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;
    @JsonIgnore
    boolean availableToDelete;

    public Category() {}

    public Category(String name) {
        this.name = name;
    }

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 45)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Transient
    public boolean isAvailableToDelete() {
        return availableToDelete;
    }

    public void setAvailableToDelete(boolean availableToDelete) {
        this.availableToDelete = availableToDelete;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;

        if (id != category.id) return false;
        return Objects.equals(name, category.name);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
