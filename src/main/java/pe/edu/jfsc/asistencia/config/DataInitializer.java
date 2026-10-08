package pe.edu.jfsc.asistencia.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pe.edu.jfsc.asistencia.model.*;
import pe.edu.jfsc.asistencia.repository.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {
 @Bean CommandLineRunner init(AppUserRepository users, SectionRepository sections, StudentRepository students, PasswordEncoder encoder){
   return args -> {
     if(users.count()==0){
       users.save(new AppUser("admin",encoder.encode("Admin123!"),"Administrador del Sistema",Role.ADMINISTRADOR));
       users.save(new AppUser("director",encoder.encode("Director123!"),"Dirección Académica",Role.DIRECTOR));
       users.save(new AppUser("profesor",encoder.encode("Profesor123!"),"Profesor Principal",Role.DOCENTE));
       users.save(new AppUser("profesor2",encoder.encode("Profesor123!"),"Profesor Secundario",Role.DOCENTE));
     }
     if(sections.count()==0){
       Section s1=sections.save(new Section("1ro Secundaria","A","profesor"));
       Section s2=sections.save(new Section("2do Secundaria","A","profesor2"));
       Section s3=sections.save(new Section("3ro Secundaria","A","profesor"));
       students.save(new Student("EST001","María","Quispe López","70123456",s1));
       students.save(new Student("EST002","Juan","Huamán Pérez","70123457",s1));
       students.save(new Student("EST003","Lucía","Mendoza Ayala","70123458",s1));
       students.save(new Student("EST004","Carlos","Paredes Condori","70123459",s2));
       students.save(new Student("EST005","Ana","Gutiérrez Ramos","70123460",s2));
       students.save(new Student("EST006","Diego","Soto Vilca","70123461",s3));
       students.save(new Student("EST007","Sofía","Ccahuana Flores","70123462",s3));
     }
   };
 }
}
