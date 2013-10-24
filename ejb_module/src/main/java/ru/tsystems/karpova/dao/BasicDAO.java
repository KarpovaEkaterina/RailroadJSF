package ru.tsystems.karpova.dao;

import javax.ejb.Stateless;
import javax.persistence.*;

@Stateless
public class BasicDAO {

    @PersistenceContext(unitName = "myapp")
    protected EntityManager em;
}
