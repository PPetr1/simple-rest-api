package services;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class PersonService {

    @PersistenceContext(name = "PersonManager")
    private EntityManager entityManager;


}
