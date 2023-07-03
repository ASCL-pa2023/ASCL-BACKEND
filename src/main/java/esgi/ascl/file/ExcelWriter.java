package esgi.ascl.file;


import esgi.ascl.User.domain.entities.User;
import esgi.ascl.game.domain.service.TeamService;
import esgi.ascl.tournament.domain.entities.TournamentInscription;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelWriter {

    private final TeamService teamService;

    public ExcelWriter(TeamService teamService) {
        this.teamService = teamService;
    }

/*
    public void fillTournamentRegistration(List<TournamentInscription> tournamentInscriptionList){
        Workbook workbook = new XSSFWorkbook();



        // Créer une feuille de calcul
        Sheet sheet = workbook.createSheet("Résumé");

        // Créer une ligne et définir les valeurs des cellules

        Row row = sheet.createRow(0);
        Cell cell1 = row.createCell(0);
        //cell1.setCellValue("Tournois : ");
        Cell cell2 = row.createCell(1);
        cell2.setCellValue(tournamentInscriptionList.get(0).getTournament().getLocation());


        Cell cell3 = row2.createCell(0);
        cell3.setCellValue("Nombre de places : ");
        Cell cell4 = row2.createCell(1);
        cell4.setCellValue(12);



        Sheet registrationSheet = workbook.createSheet("Inscriptions");

        for (int i = 0; i < tournamentInscriptionList.size(); i++) {
            TournamentInscription currentInscription = tournamentInscriptionList.get(i);


            Row teamRow = registrationSheet.createRow(i);
            Cell teamId = teamRow.createCell(0);
            teamId.setCellValue("Equipe n° : " + currentInscription.getTeam().getId());

            List<User> teamPlayers = teamService.getAllUserByTeam(currentInscription.getTeam().getId());

            for (int j = 0; j < teamPlayers.size(); j++) {
                var curPlayer = teamPlayers.get(j);
                Cell teamPlayer = teamRow.createCell(j+1);
                teamPlayer.setCellValue(curPlayer.getFirstname() + " " + curPlayer.getLastname());
            }

        }




        // Écrire le classeur dans un fichier
        try (FileOutputStream outputStream = new FileOutputStream("C:\\Users\\kekeb\\Documents\\ESGI\\5eme_annee\\projet_annuel\\ASCL-BACKEND\\src\\main\\resources/tournois.xlsx")) {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Fermer le classeur
        try {
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Le fichier Excel a été créé avec succès !");
    }

    public static void main(String[] args) {

    }

 */

}
