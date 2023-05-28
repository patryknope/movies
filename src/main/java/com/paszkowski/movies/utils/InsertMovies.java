package com.paszkowski.movies.utils;

import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import groovy.sql.Sql;
import java.sql.*;
@Component
public class InsertMovies {

    public void populate() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/moviesdb?useSSL=false&serverTimezone=UTC", "root", "");
            Sql sql = new Sql(conn);

            String createTableQuery = "CREATE TABLE IF NOT EXISTS movies (id INT AUTO_INCREMENT PRIMARY KEY, title VARCHAR(255), year INT, category VARCHAR(255), description TEXT, grade INT CHECK (grade >= 1 AND grade <= 5))";
            sql.execute(createTableQuery);

            String insertQuery = "INSERT INTO movie (title, year, category, description, grade) VALUES (?, ?, ?, ?, ?)";
            List<List> movies = Arrays.asList(
                    Arrays.asList("The Shawshank Redemption", 1994, "Drama", "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.", 5),
                    Arrays.asList("The Godfather", 1972, "Drama", "The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.", 5),
                    Arrays.asList("The Dark Knight", 2008, "Drama", "When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.", 4),
                    Arrays.asList("The Lord of the Rings: The Fellowship of the Ring", 2001, "Drama", "A meek Hobbit from the Shire and eight companions set out on a journey to destroy the powerful One Ring and save Middle-earth from the Dark Lord Sauron.", 5),
                    Arrays.asList("Forrest Gump", 1994, "Drama", "The presidencies of Kennedy and Johnson, the events of Vietnam, Watergate, and other historical events unfold through the perspective of an Alabama man with an IQ of 75, whose only desire is to be reunited with his childhood sweetheart.", 4),
                    Arrays.asList("Star Wars: Episode V - The Empire Strikes Back", 1980, "Action", "After the Rebels are brutally overpowered by the Empire on the ice planet Hoth, Luke Skywalker begins Jedi training with Yoda, while his friends are pursued by Darth Vader.", 5),
                    Arrays.asList("The Matrix", 1999, "Action", "A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.", 4),
                    Arrays.asList("The Silence of the Lambs", 1991, "Thriller", "A young F.B.I. cadet must receive the help of an incarcerated and manipulative cannibal killer to help catch another serial killer, a madman who skins his victims.", 5),
                    Arrays.asList("Jurassic Park", 1993, "Action", "A pragmatic Paleontologist visiting an almost complete theme park is tasked with protecting a couple of kids after apower failure causes the park's cloned dinosaurs to run loose.", 4),
                    Arrays.asList("Saving Private Ryan", 1998, "Drama", "Following the Normandy Landings, a group of U.S. soldiers go behind the enemy lines to retrieve a paratrooper whose brothers have been killed in action.", 5),
                    Arrays.asList("The Green Mile", 1999, "Drama", "The lives of guards on Death Row are affected by one of their charges: a black man accused of child murder and rape, yet who has a mysterious gift.", 4),
                    Arrays.asList("Gladiator", 2000, "Drama", "A former Roman General sets out to exact vengeance against the corrupt emperor who murdered his family and sent him into slavery.", 4),
                    Arrays.asList("Terminator 2: Judgment Day", 1991, "Action", "A cyborg, identical to the one who failed to kill Sarah Connor, must now protect her teenage son, John Connor, from a more advanced and powerful cyborg.", 4),
                    Arrays.asList("Back to the Future", 1985, "Comedy", "Marty McFly, a 17-year-old high school student, is accidentally sent thirty years into the past in a time-traveling DeLorean invented by his close friend, the eccentric scientist Doc Brown.", 4),
                    Arrays.asList("The Lion King", 1994, "Drama", "Lion prince Simba and his father are targeted by his bitter uncle, who wants to ascend the throne himself.", 4),
                    Arrays.asList("Aliens", 1986, "Action", "Fifty-seven years after surviving an apocalyptic attack aboard her space vessel by merciless space creatures, Officer Ripley awakens from hyper-sleep and tries to warn anyone who will listen about the predators.", 4),
                    Arrays.asList("Indiana Jones and the Raiders of the Lost Ark", 1981, "Action", "Archaeologist and adventurer Indiana Jones is hired by the U.S. government to find the Ark of the Covenant before the Nazis.", 4),
                    Arrays.asList("Jaws", 1975, "Thriller", "When a killer shark unleashes chaos on a beach community, it's up to a local sheriff, a marine biologist, and an old seafarer to hunt the beast down.", 4),
                    Arrays.asList("Ghostbusters", 1984, "Comedy", "Three former parapsychology professors set up shop as a unique ghost removal service.", 4),
                    Arrays.asList("Die Hard", 1988, "Action", "An NYPD officer tries to save his wife and several others taken hostage by German terrorists during a Christmas party at the Nakatomi Plaza in Los Angeles.", 4),
                    Arrays.asList("The Terminator", 1984, "Action", "A human soldier is sent from 2029 to 1984 to stop an almost indestructible cyborg killing machine, sent from the same year, which has been programmed to execute a young woman whose unborn son is the key to humanity's future salvation.", 4)
            );
            for (List movie : movies) {
                sql.execute(insertQuery, movie.toArray());
            }
            sql.close();
            conn.close();
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
            System.err.println("SQLState: " + ex.getSQLState());
            System.err.println("VendorError: " + ex.getErrorCode());
        }
    }
}
