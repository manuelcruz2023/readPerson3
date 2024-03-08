package co.edu.uptc.simulacroParcial.services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.simulacroParcial.models.Person;

public class ManagerTxt {
    private String path;
    private BufferedReader br;

    public void setPath(String path) {
        this.path = path;
    }

    public List<String> readTxt() throws IOException {
        List<String> personas = new ArrayList<>();
        File file = new File(path);
        FileReader fr = new FileReader(file);
        br = new BufferedReader(fr);
        String line = "";
        while (br.readLine() != null) {
            personas.add(line += br.readLine());
        }
        fr.close();
        br.close();
        return personas;
    }

    public byte[] infoBytes() throws IOException {
        return Files.readAllBytes(
                Paths.get(path));
    }

    public List<Person> splitTxt() throws IOException {
        List<String> lines = readTxt();
        List<Person> listPersons = new ArrayList<>();
        for (String line : lines) {
            String[] split = line.split("//");
            if (split.length == 2) {
                Person person = new Person();
                person.setNombre(split[0]);
                person.setApellido(split[1]);
                person.setEdad(Integer.parseInt(split[2]));
                person.setSalario(Integer.parseInt(split[3]));
                listPersons.add(person);
            }
        }
        return listPersons;
    }

    public byte[] readBytes() throws IOException {
        File File_Path = new File(path);
        // Instance of the FileInputStream
        FileInputStream File_Input_Stream = new FileInputStream(File_Path);
        byte[] Demo_Array = new byte[(int) File_Path.length()];
        File_Input_Stream.read(Demo_Array);
        File_Input_Stream.close();
        return Demo_Array;
    }

    public List<Person> readPersons() throws Exception {
        List<Person> listaPersonas = new ArrayList<>();
        byte[] demo_Array = infoBytes();
        int iterador = 0;
        
        for (int o = 0; o < 896; o++) {
            Person persona = new Person();
            int cont1 = 0;
            int cont2 = 0;
            int cont3 = 0;
            int cont4 = 0;

            if (demo_Array[iterador] != 0 || demo_Array[iterador] != 10 || demo_Array[iterador] != 13) {
                if (cont1 == 0) {
                    int iterador1 = iterador + 3;
                    int edad = 0;
                    for (int i = iterador; i <= iterador1; i++) {
                        edad += Byte.toUnsignedInt(demo_Array[i]);
                        iterador++;
                    }
                    persona.setEdad(edad);
                    cont1++;
                }

                if (cont2 == 0) {
                    int iterador2 = 0;
                    if (demo_Array[iterador + 2] != 0) {
                        iterador2 = demo_Array[iterador + 1];
                    }
                    String nombre = "";
                    for (int i = iterador + 2; i < (iterador + 2) + iterador2; i++) {
                        nombre += (char) demo_Array[i];
                    }
                    persona.setNombre(nombre);
                    iterador += (iterador2 + 2);
                    cont2++;
                }

                if (cont3 == 0) {
                    int iterador3 = 0;
                    if (demo_Array[iterador + 2] != 0) {
                        iterador3 = demo_Array[iterador + 1];
                    }
                    String apellido = "";
                    for (int i = iterador + 2; i < (iterador + 2) + iterador3; i++) {
                        apellido += (char) demo_Array[i];
                    }
                    persona.setApellido(apellido);
                    iterador += (iterador3 + 2);
                    cont3++;
                }

                if (cont4 == 0) {
                    int iterador4 = iterador + 4;
                    int salario = 0;
                    StringBuilder hexa = new StringBuilder();
                    for (int i = iterador; i < iterador4; i++) {
                        hexa.append(Integer.toHexString(Byte.toUnsignedInt(demo_Array[i])));
                    }
                    salario = Integer.parseInt(hexa.toString(), 16);
                    persona.setSalario(salario);
                    iterador += 4;
                    cont4++;
                }
                listaPersonas.add(persona);
            }
        }
        for (Person person : listaPersonas) {
            System.out.println(person.getEdad() + ":" + person.getNombre() + ":" + person.getApellido() + ":"
                    + person.getSalario());
        }
        return listaPersonas;
    }

    public List<Person> menoresDeEdad() throws Exception {
        List<Person> listaPersonas = readPersons();
        List<Person> listaMenores = new ArrayList<>();
        for (Person person : listaPersonas) {
            if (person.getEdad() < 18) {
                listaMenores.add(person);
            }
        }
        return listaMenores;
    }

    public List<String> personToString() throws Exception {
        List<Person> listaPersonas = menoresDeEdad();
        List<String> listaPersonasString = new ArrayList<>();
        for (Person person : listaPersonas) {
            listaPersonasString.add(person.getApellido() + "&" + person.getNombre() + "&" + person.getSalario()
                    + "&" + person.getEdad() + "\n");
        }
        return listaPersonasString;
    }

    public void createArchivoMenores(String nombreArchivo) throws Exception {
        List<String> listaPersonasString = personToString();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nombreArchivo))) {
            for (String elemento : listaPersonasString) {
                bw.write(elemento);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
