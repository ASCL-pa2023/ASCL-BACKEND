package esgi.ascl.training.infastructure.web.response;

public class TrainingCategoryResponse {
    private long id;
    private String name;

    public long getId() {
        return id;
    }

    public TrainingCategoryResponse setId(long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public TrainingCategoryResponse setName(String name) {
        this.name = name;
        return this;
    }
}
