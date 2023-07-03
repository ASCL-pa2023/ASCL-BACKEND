package esgi.ascl.file;

public class ExcelFileMapper {
    public static ExcelFileResponse toResponse(byte[] excelFile) {
        return new ExcelFileResponse().setFile(excelFile);
    }
}
