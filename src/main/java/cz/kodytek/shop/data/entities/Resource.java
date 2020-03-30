package cz.kodytek.shop.data.entities;

import cz.kodytek.shop.data.entities.interfaces.reousrce.IResource;

import javax.persistence.*;

@Entity
public class Resource implements IResource {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true)
    private long id;

    @Column(unique = true)
    private String path;

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public long getId() {
        return id;
    }
}
