package esgi.ascl.training.infastructure.web.request;

public class TrainingCategoryRequest {
    private long id;
    private String name;

    public long getId() {
        return id;
    }

    public TrainingCategoryRequest setId(long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public TrainingCategoryRequest setName(String name) {
        this.name = name;
        return this;
    }
}
