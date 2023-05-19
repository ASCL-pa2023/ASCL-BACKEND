package esgi.ascl.Tournament.domain.exceptions;

public class SurveyNotFoundException extends RuntimeException {
    public SurveyNotFoundException(Long id) {
        super("Survey with id " + id + " not found");
    }
}
