package esgi.ascl.file;

import esgi.ascl.User.domain.entities.User;
import esgi.ascl.game.domain.service.TeamService;
import esgi.ascl.tournament.domain.entities.Tournament;
import esgi.ascl.tournament.domain.entities.TournamentInscription;
import esgi.ascl.tournament.domain.service.TournamentInscriptionService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Service
public class TournamentRegistrationWriter {

    private final static String templateFilePath = "src/main/resources/template.xlsx";
private final static String outputFilePath = "src/main/resources/output.xlsx";

    private final TournamentInscriptionService tournamentInscriptionService;
    private final TeamService teamService;

    public TournamentRegistrationWriter(TournamentInscriptionService tournamentInscriptionService, TeamService teamService) {
        this.tournamentInscriptionService = tournamentInscriptionService;
        this.teamService = teamService;
    }


    public void fillExcel(Tournament tournament){
        var tournamentInscriptionList = tournamentInscriptionService.getAllByTournamentId(tournament.getId());

        try {
            FileInputStream templateFile = new FileInputStream(templateFilePath);
            Workbook workbook = new XSSFWorkbook(templateFile);
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
                TournamentInscription currentInscription = tournamentInscriptionList.get(i);

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
            FileOutputStream outputFile = new FileOutputStream(outputFilePath);
            workbook.write(outputFile);

            // Fermez les flux
            outputFile.close();
            templateFile.close();

        } catch (IOException e) {
            e.printStackTrace();
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
        fillExcel(tournament);
        return fileToByteArray();
    }
}
