package co.edu.uptc.simulacroParcial.controllers;

import co.edu.uptc.simulacroParcial.services.ManagerTxt;
import co.edu.uptc.text.ManagerProperties;

public class ShowManagerTxt {
    public void show () {
        ManagerProperties managerProperties = new ManagerProperties();
        managerProperties.setFileName("data.properties");
        ManagerTxt managerTxt = new ManagerTxt();
        managerTxt.setPath(managerProperties.getValue("personas"));
        try {
            managerTxt.createArchivoMenores("menores.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
