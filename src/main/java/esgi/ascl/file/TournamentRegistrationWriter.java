package esgi.ascl.file;

import esgi.ascl.User.domain.entities.User;
import esgi.ascl.game.domain.service.TeamService;
import esgi.ascl.image.service.FileService;
import esgi.ascl.tournament.domain.entities.Tournament;
import esgi.ascl.tournament.domain.entities.TournamentInscription;
import esgi.ascl.tournament.domain.service.TournamentInscriptionService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

@Service
public class TournamentRegistrationWriter {

    private final static String templateFilePath = "src/main/resources/template.xlsx";
    private final static String outputFilePath = "src/main/resources/output.xlsx";

    private final TournamentInscriptionService tournamentInscriptionService;
    private final TeamService teamService;
    private final FileService fileService;

    public TournamentRegistrationWriter(TournamentInscriptionService tournamentInscriptionService, TeamService teamService, FileService fileService) {
        this.tournamentInscriptionService = tournamentInscriptionService;
        this.teamService = teamService;
        this.fileService = fileService;
    }

    private Workbook getTemplate() {
        byte[] template = this.fileService.getFile("template.xlsx");
        Workbook result = null;
        try {
            result = new XSSFWorkbook(new ByteArrayInputStream(template));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private Workbook getExcelOutput() {
        byte[] template = this.fileService.getFile("output.xlsx");
        Workbook result = null;
        try {
            result = new XSSFWorkbook(new ByteArrayInputStream(template));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public byte[] getExcelOutputBytes() {
        return this.fileService.getFile("output.xlsx");
    }


    public Workbook fillExcel(Tournament tournament){
        var tournamentInscriptionList = tournamentInscriptionService.getAllByTournamentId(tournament.getId());

        try {
            var a = Paths.get(templateFilePath).toFile();
            var b = Objects.requireNonNull(getClass().getResource("/template.xlsx")).getFile();
            var fileStream = getClass().getClassLoader().getResourceAsStream("template.xlsx");

            InputStream in = getClass().getClassLoader().getResourceAsStream("/template.xlsx");


            //FileInputStream templateFile = new FileInputStream(templateFilePath);
            //FileInputStream templateFile = new FileInputStream(b);
            //Workbook workbook = new XSSFWorkbook(in);
            Workbook workbook = getTemplate();
            Sheet recapSheet = workbook.getSheetAt(0);

            /******* Lieux du tournois *******/
            Row locationRow = recapSheet.getRow(0);
            Cell cell2 = locationRow.createCell(1);
            cell2.setCellValue(tournament.getLocation());

            /******* Type de tournois *******/
            Row typeRow = recapSheet.getRow(1);
            Cell typeCell = typeRow.createCell(1);
            typeCell.setCellValue(tournament.getType().toString());

            /******* Nombre de places *******/
            Row placesNumberRow = recapSheet.getRow(2);
            Cell placesNumberCell = placesNumberRow.createCell(1);
            placesNumberCell.setCellValue(tournament.getPlaces_number());

            /******* Nombre de participants *******/
            Row nbParticipantsRow = recapSheet.getRow(3);
            Cell nbParticipantsCell = nbParticipantsRow.createCell(1);
            nbParticipantsCell.setCellValue(tournamentInscriptionList.size());

            /******* Fin des inscriptions *******/
            Row deadlineRow = recapSheet.getRow(4);
            Cell deadlineCell = deadlineRow.createCell(1);
            deadlineCell.setCellValue(tournament.getDeadline_inscription_date().toString());

            /******* Début du tournois *******/
            Row startRow = recapSheet.getRow(5);
            Cell startCell = startRow.createCell(1);
            startCell.setCellValue(tournament.getStart_date().toString());

            /******* Fin du tournois *******/
            Row endRow = recapSheet.getRow(6);
            Cell endCell = endRow.createCell(1);
            endCell.setCellValue(tournament.getEnd_date().toString());

            Sheet registrationSheet = workbook.getSheetAt(1);
            for (int i = 1; i < tournamentInscriptionList.size(); i++) {
                TournamentInscription currentInscription = tournamentInscriptionList.get(i-1);

                Row teamRow = registrationSheet.createRow(i);
                Cell teamId = teamRow.createCell(0);
                teamId.setCellValue(currentInscription.getTeam().getId());

                List<User> teamPlayers = teamService.getAllUserByTeam(currentInscription.getTeam().getId());

                for (int j = 0; j < teamPlayers.size(); j++) {
                    var curPlayer = teamPlayers.get(j);
                    Cell teamPlayer = teamRow.createCell(j+1);
                    teamPlayer.setCellValue(curPlayer.getFirstname() + " " + curPlayer.getLastname().toUpperCase());
                }
            }

            // Enregistrez les modifications dans le fichier de sortie
            //FileOutputStream outputFile = new FileOutputStream(outputFilePath);
            //workbook.write(outputFile);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byteArrayOutputStream.write(getExcelOutputBytes());
            workbook.write(byteArrayOutputStream);

            // Fermez les flux
            //outputFile.close();
            //templateFile.close();

            return workbook;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private byte[] workbookToByteArray(Workbook workbook){
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            workbook.write(byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }



    private byte[] fileToByteArray(){
        try {
            File file = new File(outputFilePath);
            return Files.readAllBytes(file.toPath());
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }


    public byte[] excelReview(Tournament tournament){
        var a = fillExcel(tournament);
        //return fileToByteArray();
        return workbookToByteArray(a);
    }
}
