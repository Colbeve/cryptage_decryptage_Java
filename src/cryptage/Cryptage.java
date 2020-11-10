/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Véronique
 */
public class Cryptage {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        //List<String> lignes = new ArrayList<String>();
        //List<String> listEncode = new ArrayList<String>();
        Scanner sc;
        sc = new Scanner(System.in);
        String msgNomFichier, msgNomFichierCrypte;
        char choix = '0';
        msgNomFichier = "";
        msgNomFichierCrypte = "";
        
        System.out.println("1 ... Crypter le fichier"+"\n"+"2 ... Décrypter le fichier"+"\n"+"0 ... Quitter");
        choix = sc.next().charAt(0);
        while(choix != '0'){
            switch (choix) {
                case '1' :
                    action(choix, msgNomFichier, msgNomFichierCrypte);//cryptage
                    msgNomFichier = "crypter";
                    msgNomFichierCrypte = "crypté";
                    break;
                case '2' :
                    action(choix, msgNomFichier, msgNomFichierCrypte);//décryptage
                    msgNomFichier = "décrypter";
                    msgNomFichierCrypte = "décrypté";
                    break;
                default :
                    System.out.println("Vous n’avez choisi aucune des options proposées");
                    break;
            }
            System.out.println("1 ... Crypter le fichier"+"\n"+"2 ... Décrypter le fichier"+"\n"+"0 ... Quitter");
            choix = sc.next().charAt(0);
        }
    } 
    
        /**Demande à l'utilisateur d'entrer les différentes saisies de données:
         * cle, nomFichier, nomFichierCrypte
         * Récupère la saisie utilisateur.
         * @param typeModif
         * @return saisieUtilisateur
         */
        public static String saisieDonnees(String typeModif){
            Scanner sc;
            String saisieUtilisateur;
            
            sc = new Scanner(System.in);
            System.out.println("Saisie" + typeModif);
            saisieUtilisateur = sc.nextLine();//sc.next().charAt(0);
            return saisieUtilisateur;
        }
         
        /**Appel des différentes procédures/fonctions nécessaires à l'action de cryptage ou décryptage.
         * @throws Exception 
         */ 
        public static void action(char choix, String msgNomFichier, String msgNomFichierCrypte)throws Exception{
           List<String> lignes = new ArrayList<String>();
           List<String> listEncode = new ArrayList<String>();
           String cle = saisieDonnees(" de la clé de cryptage : ");
           String nomFichier = saisieDonnees(" du nom du fichier à " + msgNomFichier + " au format (c:/dossier/source.txt) : ");
           String nomFichierCrypte = saisieDonnees(" du nom du fichier  " + msgNomFichierCrypte + " au format (c:/dossier/crypte.txt) : ");
           lignes = lireFichier(nomFichier);
           listEncode = traitement(lignes, cle, choix);//problème
           ecrireFichier(nomFichierCrypte, listEncode);//création du nouveau fichier ok mais 1 seule ligne dedans, la dernière du fichier
           System.out.println("Voici le fichier " + msgNomFichierCrypte + ": ");
           afficherFichier(listEncode);// qui affiche le contenu du fichier crypté
        }
        
        /**A partir du fichier saisi, extrait chaque ligne qu'il ajoute dans collection lignes,
         * puis, referme le fichier.
         * @param String nomFichier
         * @return lignes;
         * @throws Exception 
         */
        public static List<String> lireFichier(String nomFichier)throws Exception { 
            BufferedReader source;
            String ligne;
            
            List<String> lignes = new ArrayList<String>();
            source = new BufferedReader(new FileReader(nomFichier));
            ligne = source.readLine();
            while(ligne != null){
                lignes.add(ligne);
                ligne = source.readLine();
                }
            source.close();
            return lignes;
        }
        
        /**Suivant taille de collection lignes,
         * affiche chaque ligne.
         * @param List<String> lignes
         * @throws Exception 
         */
        private static void afficherFichier(List<String> listEncode)throws Exception {
            String encode;
                
            for (int i = 0; i < listEncode.size(); i++){
            encode = (listEncode.get(i)); 
            //System.out.println(encode);
            }
            System.out.println(listEncode);
            //return listEncode;
        }
        
        /**Suivant taille de collection lignes,
         * écrit en revenant à la ligne à chaque ligne,
         * puis, referme le fichier.
         * @param nomFichierCrypte
         * @return
         * @throws Exception 
         */
        private static List<String> ecrireFichier(String nomFichierCrypte, List<String> listEncode)throws Exception {
            BufferedWriter cible;
            cible = new BufferedWriter(new FileWriter(nomFichierCrypte));
            for (int i = 0; i < listEncode.size(); i ++) {
            cible.write(listEncode.get(i) + "\n");
            }
            cible.close();
            return listEncode;
        }
        
        /**Suivant taille de la collection lignes, découpe ligne par ligne mis dans phrase,
         * puis, appel operation(phrase, cle);
         * @param List<String>lignes
         * @param String cle
         * @throws Exception 
         */
        private static List<String> traitement(List<String>lignes, String cle, char choix)throws Exception {
            String phrase; 
            List<String> listEncode = new ArrayList<String>();            
            for (int i = 0; i < lignes.size(); i++){//j'ai retirer le -1 après lignes.size()
                phrase = lignes.get(i);//on découpe la liste pour s'occuper de ligne par ligne
                listEncode = operation(phrase, cle, choix);//ajout listEncode = oper
            }
            return listEncode;
        }
        
        /**Calcul caractère par caractère de la ligne et de la clé afin d'obtenir un nouveau caractère correspondant "code ascii",
         * comprends la gestion des espaces et des caractères supérieurs à 255.
         * @param String phrase
         * @param String cle
         * @return encode;
         * @throws Exception 
         */
        private static List<String> operation(String phrase, String cle, char choix)throws Exception {
            List<Integer> listascii1 = new ArrayList<Integer>();
            List<String> listEncode = new ArrayList<String>();
            List<Integer> listcle = new ArrayList<Integer>();
            String encode; 
            int ascii1, ascii2, somme;
            encode = "";
                //Suivant taille de la phrase et p.index, découpe chaque phrase en caractere, passe chaque caractere en "code ascii", puis le stock dans une Collection.
                for(int i = 0; i < phrase.length(); i++){ 
                    ascii1 = (int) phrase.charAt(i);
                    listascii1.add(ascii1);
                }
                //Suivant taille de la cle et P.index, découpe la cle en caractere, passe chaque caractere en "code ascii", puis le stock dans une Collection.
                for(int j = 0; j < cle.length(); j++){
                    while(cle.length() < phrase.length()){//la longueur de la clé est égale à la longueur de la ligne soit: xfois la longeur de la clé (cleclecleclecle)
                    int m = 0;
                    cle += cle;
                    m++;
                    }
                    ascii2 = (int) cle.charAt(j);//ascii2 contient: 65 122 101 114 116 89 ("AzertY", saisie de l'utilisateur)
                    listcle.add(ascii2);
                } 
                
                for(int i = 0; i < phrase.length(); i++){//Suivant la taille de la phrase et position index,
                    if(listascii1.get(i) != 32){//vérification condition présence caractere espace
                        if(choix == '1'){
                        somme = listascii1.get(i) + listcle.get(i);//soit ascii1(i) + ascii2(i)
                        }else{
                        //choix == '2';
                        somme = listascii1.get(i) - listcle.get(i);//soit ascii1(i) - ascii2(i)
                        }
                        if(somme > 255){//vérification condition si somme>255 alors faire 255-somme
                            somme -= 255;
                        }
                            encode += (char) somme;//bascule de la somme en code ascii en caractere y correspondant
                            }else{//sinon s'il rencontre un 32 (espace) il garde ce 32
                                    somme = 32;
                                    encode += (char) somme;//String encode; int somme; Bascule de la somme en code ascii en caractere y correspondant
                                    }
                    }
                    listEncode.add(encode);
                    //System.out.println(encode);//affichage seulement pour tester, à retirer
                    return listEncode;
        }         
         
}
