package esgi.ascl.tournament.domain.service;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class NombreDePoules {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Entrez le nombre d'inscrits : ");
        int nombreInscrits = scanner.nextInt();

        List<Poule> poules = formerPoules(nombreInscrits);

        System.out.println("Le nombre de poules est : " + poules.size());
        System.out.println("Répartition des joueurs par poule :");
        for (int i = 0; i < poules.size(); i++) {
            Poule poule = poules.get(i);
            System.out.println("Poule " + (i + 1) + " : " + poule.getJoueurs().size() + " joueurs");
        }

        List<Participant> participantsQualifies = getParticipantsQualifies(poules);
        System.out.println("\nParticipants qualifiés pour les phases finales :");
        for (Participant participant : participantsQualifies) {
            System.out.println("Participant " + participant.getNumero() + " - Victoires : " + participant.getNombreVictoires());
        }

        List<PhaseFinale> phasesFinales = formerPhasesFinales(participantsQualifies);
        System.out.println("\nPhases finales :");
        for (int i = 0; i < phasesFinales.size(); i++) {
            PhaseFinale phaseFinale = phasesFinales.get(i);
            System.out.println("Phase " + (i + 1) + " - Joueur 1 : " + phaseFinale.getJoueur1().getNumero() +
                    ", Joueur 2 : " + phaseFinale.getJoueur2().getNumero());
        }
    }

    public static List<Poule> formerPoules(int nombreInscrits) {
        List<Poule> poules = new ArrayList<>();

        // Calculer le nombre de poules nécessaires
        int nombrePoules = (int) Math.ceil(nombreInscrits / 4.0);

        for (int i = 0; i < nombrePoules; i++) {
            poules.add(new Poule());
        }

        int joueurCourant = 1;

        // Répartir les joueurs dans les poules
        for (int i = 0; i < nombrePoules; i++) {
            Poule poule = poules.get(i);

            while (poule.getJoueurs().size() < 4 && joueurCourant <= nombreInscrits) {
                poule.ajouterJoueur(joueurCourant);
                joueurCourant++;
            }
        }

        // Répartir les joueurs restants pour assurer que chaque joueur affronte tous les autres joueurs de sa poule
        for (Poule poule : poules) {
            List<Integer> joueurs = poule.getJoueurs();
            int nombreJoueurs = joueurs.size();

            if (nombreJoueurs > 1) {
                for (int i = 0; i < nombreJoueurs - 1; i++) {
                    for (int j = i + 1; j < nombreJoueurs; j++) {
                        int joueur1 = joueurs.get(i);
                        int joueur2 = joueurs.get(j);
                        if (joueur1 != joueur2) {
                            System.out.println("Dans la poule " + (poules.indexOf(poule) + 1) + ", le joueur " + joueur1 + " affronte le joueur " + joueur2);
                        }
                    }
                }
            }
        }

        return poules;
    }

    public static List<Participant> getParticipantsQualifies(List<Poule> poules) {
        List<Participant> participantsQualifies = new ArrayList<>();

        for (Poule poule : poules) {
            List<Participant> participants = new ArrayList<>();
            for (Integer joueur : poule.getJoueurs()) {
                participants.add(new Participant(joueur));
            }
            participants.sort(Comparator.comparingInt(Participant::getNombreVictoires).reversed());
            participantsQualifies.add(participants.get(0));
        }

        return participantsQualifies;
    }

    public static List<PhaseFinale> formerPhasesFinales(List<Participant> participantsQualifies) {
        List<PhaseFinale> phasesFinales = new ArrayList<>();

        int nombreParticipants = participantsQualifies.size();
        int nombrePhases = (int) Math.ceil(Math.log(nombreParticipants) / Math.log(2));

        Random random = new Random();

        // Ajouter les huitièmes de finale si nécessaire
        if (nombrePhases > 3) {
            int nombreHuitiemes = nombreParticipants - (int) Math.pow(2, nombrePhases - 3);
            for (int i = 0; i < nombreHuitiemes; i++) {
                int index1 = random.nextInt(participantsQualifies.size());
                int index2 = random.nextInt(participantsQualifies.size());

                while (index2 == index1) {
                    index2 = random.nextInt(participantsQualifies.size());
                }

                Participant joueur1 = participantsQualifies.get(index1);
                Participant joueur2 = participantsQualifies.get(index2);
                phasesFinales.add(new PhaseFinale(joueur1, joueur2));
                participantsQualifies.remove(index1);
                participantsQualifies.remove(index2 < index1 ? index2 : index2 - 1);
            }
        }

        // Ajouter les quarts de finale
        int nombreQuarts = nombreParticipants / 2;
        for (int i = 0; i < nombreQuarts; i++) {
            int index1 = random.nextInt(participantsQualifies.size());
            int index2 = random.nextInt(participantsQualifies.size());

            while (index2 == index1) {
                index2 = random.nextInt(participantsQualifies.size());
            }

            Participant joueur1 = participantsQualifies.get(index1);
            Participant joueur2 = participantsQualifies.get(index2);
            phasesFinales.add(new PhaseFinale(joueur1, joueur2));
            participantsQualifies.remove(index1);
            participantsQualifies.remove(index2 < index1 ? index2 : index2 - 1);
        }

        // Ajouter les demi-finales
        int nombreDemis = nombreQuarts / 2;
        for (int i = 0; i < nombreDemis; i++) {
            int index1 = random.nextInt(participantsQualifies.size());
            int index2 = random.nextInt(participantsQualifies.size());

            while (index2 == index1) {
                index2 = random.nextInt(participantsQualifies.size());
            }

            Participant joueur1 = participantsQualifies.get(index1);
            Participant joueur2 = participantsQualifies.get(index2);
            phasesFinales.add(new PhaseFinale(joueur1, joueur2));
            participantsQualifies.remove(index1);
            participantsQualifies.remove(index2 < index1 ? index2 : index2 - 1);
        }

        // Ajouter la finale
        Participant joueur1 = participantsQualifies.get(0);
        Participant joueur2 = participantsQualifies.get(1);
        phasesFinales.add(new PhaseFinale(joueur1, joueur2));

        return phasesFinales;
    }

    static class Poule {
        private List<Integer> joueurs;

        public Poule() {
            joueurs = new ArrayList<>();
        }

        public List<Integer> getJoueurs() {
            return joueurs;
        }

        public void ajouterJoueur(int joueur) {
            joueurs.add(joueur);
        }
    }

    static class Participant {
        private int numero;
        private int nombreVictoires;

        public Participant(int numero) {
            this.numero = numero;
            this.nombreVictoires = genererVictoiresAleatoires();
        }

        public int getNumero() {
            return numero;
        }

        public int getNombreVictoires() {
            return nombreVictoires;
        }

        private int genererVictoiresAleatoires() {
            Random random = new Random();
            return random.nextInt(5); // Génère un nombre de victoires aléatoire entre 0 et 4 inclus
        }
    }

    static class PhaseFinale {
        private Participant joueur1;
        private Participant joueur2;

        public PhaseFinale(Participant joueur1, Participant joueur2) {
            this.joueur1 = joueur1;
            this.joueur2 = joueur2;
        }

        public Participant getJoueur1() {
            return joueur1;
        }

        public Participant getJoueur2() {
            return joueur2;
        }
    }
}
