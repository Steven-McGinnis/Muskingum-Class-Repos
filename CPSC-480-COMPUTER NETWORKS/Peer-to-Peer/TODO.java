import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TODO {
    private UUID id;
    private String name;
    private List<String> items;

    public TODO(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.items = new ArrayList<>();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getItems() {
        return items;
    }

    public void addItem(String item) {
        items.add(item);
    }

    public void removeItem(String item) {
        items.remove(item);
    }

    public void updateItem(String item) {
        int index = items.indexOf(item);
        if (index != -1) {
            items.set(index, item);
        }
    }
}
