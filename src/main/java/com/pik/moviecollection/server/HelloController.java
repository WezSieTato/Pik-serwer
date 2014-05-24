package com.pik.moviecollection.server;

import com.pik.moviecollection.model.Movie;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


@RestController
@RequestMapping("/service/greeting")
@ResponseStatus(HttpStatus.OK)
public class HelloController {

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public String getGreeting(@PathVariable String name) {
        String result="123Witojcie panocki na moim websajcie! I nie piszta mnie tu: "+name;
        return result;
        //return new ResponseEntity<String>("{value: " + result + "}", HttpStatus.OK);
    }

    private void exampleInsertToMongoDB()
    {
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("kunderapu");
	EntityManager em = emf.createEntityManager();

	Movie movie = new Movie();
	movie.movieID = "1";
	movie.country = "PL";
	movie.title = "PIK";

	em.persist(movie);
    }
}
